package com.lorealbaautomation.dailyactivity;

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
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.lorealbaautomation.Database.Lorealba_Database;
import com.lorealbaautomation.R;
import com.lorealbaautomation.constant.AlertandMessages;
import com.lorealbaautomation.constant.CommonFunctions;
import com.lorealbaautomation.constant.CommonString;
import com.lorealbaautomation.gsonGetterSetter.GroomingGetterSetter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.lorealbaautomation.constant.CommonFunctions.getCurrentTime;

public class GroomedActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView grooming_img_morng, grooming_img_noon, grooming_img_evning;
    FloatingActionButton btn_save;
    TextView txt_login_tym;
    Lorealba_Database db;
    Context context;
    AlertDialog alert;
    private SharedPreferences preferences;
    String counter_id, visit_date, username, user_type, _pathforcheck, _path;
    SimpleDateFormat simpleDateFormat;
    String time;
    Calendar calander;
    boolean update_flag = false, clicked_flag = false;
    GroomingGetterSetter groomedObject = new GroomingGetterSetter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groomed);
        context = this;
        db = new Lorealba_Database(context);
        db.open();
        validateuidata();
        validateinserted_data();
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
        setTitle("Grooming Image");
        grooming_img_morng = (ImageView) findViewById(R.id.grooming_img_morng);
        grooming_img_noon = (ImageView) findViewById(R.id.grooming_img_noon);
        grooming_img_evning = (ImageView) findViewById(R.id.grooming_img_evning);
        btn_save = (FloatingActionButton) findViewById(R.id.btn_save);
        txt_login_tym = (TextView) findViewById(R.id.txt_login_tym);

        grooming_img_morng.setOnClickListener(this);
        grooming_img_noon.setOnClickListener(this);
        grooming_img_evning.setOnClickListener(this);

        btn_save.setOnClickListener(this);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (clicked_flag){
                AlertDialog.Builder builder = new AlertDialog.Builder(context).setMessage(CommonString.ONBACK_ALERT_MESSAGE).setCancelable(false).setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                                GroomedActivity.this.finish();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }else {
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                GroomedActivity.this.finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (clicked_flag){
            AlertDialog.Builder builder = new AlertDialog.Builder(context).setMessage(CommonString.ONBACK_ALERT_MESSAGE).setCancelable(false).setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(
                                DialogInterface dialog, int id) {
                            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                            GroomedActivity.this.finish();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }else {
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            GroomedActivity.this.finish();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.grooming_img_morng:
                try {
                    clicked_flag=true;
                    calander = Calendar.getInstance();
                    simpleDateFormat = new SimpleDateFormat("HH");
                    time = simpleDateFormat.format(calander.getTime());
                    if (Integer.parseInt(time) >= 9 && Integer.parseInt(time) < 12) {
                        _pathforcheck = counter_id + "_groomed_img_mrng_" + visit_date.replace("/", "") + "_" + getCurrentTime().replace(":", "") + ".jpg";
                        _path = CommonString.FILE_PATH + _pathforcheck;
                        CommonFunctions.startAnncaCameraActivity(this, _path, null, false);
                    } else {
                        AlertandMessages.showToastMsg(context, "Time up !");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.grooming_img_noon:
                try {
                    clicked_flag=true;
                    calander = Calendar.getInstance();
                    simpleDateFormat = new SimpleDateFormat("HH");
                    time = simpleDateFormat.format(calander.getTime());
                    if (Integer.parseInt(time) >= 12 && Integer.parseInt(time) < 15) {
                        _pathforcheck = counter_id + "_groomed_img_noon_" + visit_date.replace("/", "") + "_" + getCurrentTime().replace(":", "") + ".jpg";
                        _path = CommonString.FILE_PATH + _pathforcheck;
                        CommonFunctions.startAnncaCameraActivity(this, _path, null, false);
                    } else {
                        AlertandMessages.showToastMsg(context, "Time up !");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.grooming_img_evning:
                try {
                    clicked_flag=true;
                    calander = Calendar.getInstance();
                    simpleDateFormat = new SimpleDateFormat("HH");
                    time = simpleDateFormat.format(calander.getTime());
                    if (Integer.parseInt(time) >= 15 && Integer.parseInt(time) <= 18) {
                        _pathforcheck = counter_id + "_groomed_img_evning_" + visit_date.replace("/", "") + "_" + getCurrentTime().replace(":", "") + ".jpg";
                        _path = CommonString.FILE_PATH + _pathforcheck;
                        CommonFunctions.startAnncaCameraActivity(this, _path, null, false);
                    } else {
                        AlertandMessages.showToastMsg(context, "Time up !");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.btn_save:
                if (validategroomedimg()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(R.string.dialog_title).setMessage(R.string.title_activity_save_data).setCancelable(false).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                            db.open();
                            db.inseetgroomingdata(counter_id, username, visit_date, user_type, groomedObject);
                            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                            AlertandMessages.showToastMsg(context, "Data saved successfully.");
                            GroomedActivity.this.finish();
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

                        if (_pathforcheck.contains("_groomed_img_mrng_")) {
                            grooming_img_morng.setImageDrawable(getResources().getDrawable(R.mipmap.camera_tick));
                            groomedObject.setMorning_groom_img_str(_pathforcheck);
                            groomedObject.setMorning_groom_time_str(getCurrentTime());

                        } else if (_pathforcheck.contains("_groomed_img_noon_")) {
                            grooming_img_noon.setImageDrawable(getResources().getDrawable(R.mipmap.camera_tick));
                            groomedObject.setNoon_groom_img_str(_pathforcheck);
                            groomedObject.setNoon_groom_time_str(getCurrentTime());

                        } else if (_pathforcheck.contains("_groomed_img_evning_")) {
                            grooming_img_evning.setImageDrawable(getResources().getDrawable(R.mipmap.camera_tick));
                            groomedObject.setEvenning_groom_img_str(_pathforcheck);
                            groomedObject.setEvenning_groom_time_str(getCurrentTime());
                        }

                        _pathforcheck = "";
                    }
                }

                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private boolean validategroomedimg() {
        boolean status = true;
        calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH");
        time = simpleDateFormat.format(calander.getTime());
        if (Integer.parseInt(time) >= 9 && Integer.parseInt(time) < 12) {
            if (groomedObject.getMorning_groom_img_str().equals("")) {
                AlertandMessages.showToastMsg(context, "Please Capture Full Grooming Image (Before 09 : 45 AM).");
                status = false;
            }
        } else if (Integer.parseInt(time) >= 12 && Integer.parseInt(time) < 15) {
            if (groomedObject.getNoon_groom_img_str().equals("")) {
                AlertandMessages.showToastMsg(context, "Please Capture Full Grooming Image (Between 12:00 PM - 02:59 PM).");
                status = false;
            }
        } else if (Integer.parseInt(time) >= 15 && Integer.parseInt(time) <= 18) {
            if (groomedObject.getEvenning_groom_img_str().equals("")) {
                AlertandMessages.showToastMsg(context, "Please Capture Full Grooming Image (Between 03:00 PM - 05:59 PM).");
                status = false;
            }
        }

        return status;
    }

    private void validateinserted_data() {
        db.open();
        groomedObject = db.getinserted_groomingdata(counter_id, username, visit_date);
        if (!groomedObject.getMorning_groom_img_str().equals("") || !groomedObject.getNoon_groom_img_str().equals("") || !groomedObject.getEvenning_groom_img_str().equals("")) {
            update_flag = true;
            if (!groomedObject.getMorning_groom_img_str().equals("")) {
                grooming_img_morng.setImageDrawable(getResources().getDrawable(R.mipmap.camera_tick));
            }
            if (!groomedObject.getNoon_groom_img_str().equals("")) {
                grooming_img_noon.setImageDrawable(getResources().getDrawable(R.mipmap.camera_tick));
            }

            if (!groomedObject.getEvenning_groom_img_str().equals("")) {
                grooming_img_evning.setImageDrawable(getResources().getDrawable(R.mipmap.camera_tick));
            }

            btn_save.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.edit_txt));
        }

    }
}
