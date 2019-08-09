package com.lorealbaautomation.dailyactivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.lorealbaautomation.Database.Lorealba_Database;
import com.lorealbaautomation.R;
import com.lorealbaautomation.constant.AlertandMessages;
import com.lorealbaautomation.constant.CommonFunctions;
import com.lorealbaautomation.constant.CommonString;
import com.lorealbaautomation.gsonGetterSetter.JourneyPlan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.lorealbaautomation.constant.CommonFunctions.getCurrentTime;

public class CounterImageActivity extends AppCompatActivity implements
        View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    ImageView presetup_img_one, presetup_img_two, post_setup_img_one, post_setup_img_two;
    String _pathforcheck, _path, counter_id, visit_date, username,user_type;
    FloatingActionButton btn_save_selfie;
    JourneyPlan counterimg_object = new JourneyPlan();

    private SharedPreferences preferences;
    private Lorealba_Database db;
    double lat = 0.0, lon = 0.0;
    GoogleApiClient mGoogleApiClient;
    AlertDialog alert;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_image);
        context = this;
        db = new Lorealba_Database(context);
        db.open();
        validateuidata();


    }

    private void validateuidata() {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        counter_id = preferences.getString(CommonString.KEY_STORE_ID, "");
        visit_date = preferences.getString(CommonString.KEY_DATE, "");
        username = preferences.getString(CommonString.KEY_USERNAME, "");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Counter Image");
        presetup_img_one = (ImageView) findViewById(R.id.presetup_img_one);
        presetup_img_two = (ImageView) findViewById(R.id.presetup_img_two);
        post_setup_img_one = (ImageView) findViewById(R.id.post_setup_img_one);
        post_setup_img_two = (ImageView) findViewById(R.id.post_setup_img_two);
        btn_save_selfie = (FloatingActionButton) findViewById(R.id.btn_save_selfie);

        presetup_img_one.setOnClickListener(this);
        presetup_img_two.setOnClickListener(this);
        post_setup_img_one.setOnClickListener(this);
        post_setup_img_two.setOnClickListener(this);
        btn_save_selfie.setOnClickListener(this);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        }

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context).setMessage(CommonString.ONBACK_ALERT_MESSAGE).setCancelable(false).setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(
                                DialogInterface dialog, int id) {
                            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                            CounterImageActivity.this.finish();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setMessage(CommonString.ONBACK_ALERT_MESSAGE).setCancelable(false).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(
                            DialogInterface dialog, int id) {
                        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                        CounterImageActivity.this.finish();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.presetup_img_one:
                _pathforcheck = counter_id + "_countprestu_img_one_" + visit_date.replace("/", "") + "_" + getCurrentTime().replace(":", "") + ".jpg";
                _path = CommonString.FILE_PATH + _pathforcheck;
                CommonFunctions.startAnncaCameraActivity(this, _path, null, false);
                break;

            case R.id.presetup_img_two:
                _pathforcheck = counter_id + "_countprestu_img_two_" + visit_date.replace("/", "") + "_" + getCurrentTime().replace(":", "") + ".jpg";
                _path = CommonString.FILE_PATH + _pathforcheck;
                CommonFunctions.startAnncaCameraActivity(this, _path, null, false);
                break;

            case R.id.post_setup_img_one:
                _pathforcheck = counter_id + "_countpoststu_img_one_" + visit_date.replace("/", "") + "_" + getCurrentTime().replace(":", "") + ".jpg";
                _path = CommonString.FILE_PATH + _pathforcheck;
                CommonFunctions.startAnncaCameraActivity(this, _path, null, false);
                break;

            case R.id.post_setup_img_two:
                _pathforcheck = counter_id + "_countpoststu_img_two_" + visit_date.replace("/", "") + "_" + getCurrentTime().replace(":", "") + ".jpg";
                _path = CommonString.FILE_PATH + _pathforcheck;
                CommonFunctions.startAnncaCameraActivity(this, _path, null, false);
                break;


            case R.id.btn_save_selfie:
                if (validateimg_str()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(R.string.dialog_title).setMessage(R.string.title_activity_save_data).setCancelable(false)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                                    db.open();
                                    db.insertcounteruserdata(counter_id, username, visit_date,user_type, counterimg_object);
                                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                                    AlertandMessages.showToastMsg(context, "Data saved successfully.");
                                    CounterImageActivity.this.finish();
                                }
                            }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    alert = builder.create();
                    alert.show();
                }
                break;
        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                lat = mLastLocation.getLatitude();
                lon = mLastLocation.getLongitude();
                // If any additional address line present than only, c// heck with max available address lines by getMaxAddressLineIndex()
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("MakeMachine", "resultCode: " + resultCode);
        switch (resultCode) {

            case 0:
                Log.i("MakeMachine", "User cancelled");
                break;

            case -1:
                if (_pathforcheck != null && !_pathforcheck.equals("")) {
                    if (new File(CommonString.FILE_PATH + _pathforcheck).exists()) {
                        Bitmap bmp = BitmapFactory.decodeFile(CommonString.FILE_PATH + _pathforcheck);
                        Bitmap dest = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
                        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                        String dateTime = sdf.format(Calendar.getInstance().getTime()); // reading local time in the system

                        Canvas cs = new Canvas(dest);
                        Paint tPaint = new Paint();
                        tPaint.setTextSize(20);
                        tPaint.setColor(Color.RED);
                        tPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                        cs.drawBitmap(bmp, 0f, 0f, null);
                        float height = tPaint.measureText("yY");
                        cs.drawText(dateTime, 20f, height + 15f, tPaint);
                        try {
                            dest.compress(Bitmap.CompressFormat.JPEG, 100,
                                    new FileOutputStream(new File(CommonString.FILE_PATH + _pathforcheck)));
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        if (_pathforcheck.contains("_countprestu_img_one_")) {
                            presetup_img_one.setImageDrawable(getResources().getDrawable(R.mipmap.camera_tick));
                            counterimg_object.setPresetup_img_one_str(_pathforcheck);

                        } else if (_pathforcheck.contains("_countprestu_img_two_")) {
                            presetup_img_two.setImageDrawable(getResources().getDrawable(R.mipmap.camera_tick));
                            counterimg_object.setPresetup_img_two_str(_pathforcheck);

                        } else if (_pathforcheck.contains("_countpoststu_img_one_")) {
                            post_setup_img_one.setImageDrawable(getResources().getDrawable(R.mipmap.camera_tick));
                            counterimg_object.setPostsetup_img_one_str(_pathforcheck);

                        } else if (_pathforcheck.contains("_countpoststu_img_two_")) {
                            post_setup_img_two.setImageDrawable(getResources().getDrawable(R.mipmap.camera_tick));
                            counterimg_object.setPostsetup_img_two_str(_pathforcheck);
                        }

                        _pathforcheck = "";
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean validateimg_str() {
        boolean status = true;
        if (counterimg_object.getPresetup_img_one_str().equals("") || counterimg_object.getPresetup_img_two_str().equals("") || counterimg_object.getPostsetup_img_one_str().equals("") || counterimg_object.getPostsetup_img_two_str().equals("")) {
            AlertandMessages.showToastMsg(context, "Please click all counter imges");
            status = false;
        }
        return status;
    }

}
