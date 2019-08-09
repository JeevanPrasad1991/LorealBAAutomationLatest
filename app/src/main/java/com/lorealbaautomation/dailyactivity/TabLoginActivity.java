package com.lorealbaautomation.dailyactivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.lorealbaautomation.AutoUpdateActivity;
import com.lorealbaautomation.Get_IMEI_number.ImeiNumberClass;
import com.lorealbaautomation.Database.Lorealba_Database;
import com.lorealbaautomation.DealarBoardActivity;
import com.lorealbaautomation.R;
import com.lorealbaautomation.UserLoginActivity;
import com.lorealbaautomation.constant.AlertandMessages;
import com.lorealbaautomation.constant.CommonString;
import com.lorealbaautomation.gettersetter.LoginGetterSetter;
import com.lorealbaautomation.gettersetter.LoginGsonGetterSetter;
import com.lorealbaautomation.gsonGetterSetter.BaListGetterSetter;
import com.lorealbaautomation.gsonGetterSetter.CounterDeviceLoginGetterSetter;
import com.lorealbaautomation.password.MPinActivity;
import com.lorealbaautomation.retrofit.PostApi;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TabLoginActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private String counterId = "", appversion = "", app_path = "", visitdate = "";
    private Spinner sp_sppiner;
    private EditText password;
    private Button btncontinue;
    private String tag_from = "";
    private String tag_mode = "";
    private Context context;
    private SharedPreferences preferences;
    private TextView user_type_login;
    Lorealba_Database db;
    ArrayList<BaListGetterSetter> bsListData = new ArrayList<>();
    private ArrayAdapter<CharSequence> store_visited_adapter;
    String userId;
    String passwordData;
    LoginGetterSetter loginGetterSetter;
    private int versionCode;
    ProgressDialog loading;
    private String[] imeiNumbers;
    private Retrofit adapter;
    private SharedPreferences.Editor editor = null;
    private double lat = 0.0;
    private double lon = 0.0;
    GoogleApiClient mGoogleApiClient;
    private static int UPDATE_INTERVAL = 200; // 5 sec
    private static int FATEST_INTERVAL = 100; // 1 sec
    private static int DISPLACEMENT = 1; // 10 meters
    private static final int REQUEST_LOCATION = 1;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 10;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 11;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE_READ = 12;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE_WRITE = 14;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    private ImeiNumberClass imei;
    private String app_ver;
    private String manufacturer;
    private String model;
    private String os_version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_tab_login);
        getViewId();
        setSppinerData();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        checkAppPermission(Manifest.permission.CAMERA, MY_PERMISSIONS_REQUEST_CAMERA);

        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            imeiNumbers = imei.getDeviceImei();
        }

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();

            createLocationRequest();
        }

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();

        }
        checkgpsEnableDevice();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }
    private boolean checkgpsEnableDevice() {
        boolean flag = true;
        if (!hasGPSDevice(context)) {
            Toast.makeText(context, "Gps not Supported", Toast.LENGTH_SHORT).show();
        }
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(context)) {
            enableLoc();
            flag = false;
        } else if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(context)) {
            flag = true;
        }
        return flag;
    }

    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    private void enableLoc() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        if (mGoogleApiClient != null) {
            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult((Activity) context, REQUEST_LOCATION);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
        }
    }

    void checkAppPermission(String permission, int requestCode) {

        boolean permission_flag = false;
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(TabLoginActivity.this,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(TabLoginActivity.this,
                    permission)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                showOnPermissiondenied(Manifest.permission.CAMERA, MY_PERMISSIONS_REQUEST_CAMERA, 1);
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(TabLoginActivity.this,
                        new String[]{permission},
                        requestCode);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
                checkAppPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, MY_PERMISSIONS_REQUEST_STORAGE_WRITE);
            } else if (requestCode == MY_PERMISSIONS_REQUEST_STORAGE_WRITE) {
                checkAppPermission(Manifest.permission.READ_EXTERNAL_STORAGE, MY_PERMISSIONS_REQUEST_STORAGE_READ);
            } else if (requestCode == MY_PERMISSIONS_REQUEST_STORAGE_READ) {
                checkAppPermission(Manifest.permission.ACCESS_FINE_LOCATION, MY_PERMISSIONS_REQUEST_LOCATION);
            } else {

                // Create a Folder for Images

                File file = new File(Environment.getExternalStorageDirectory(), ".GSK_MT_ORANGE_IMAGES");
                if (!file.isDirectory()) {
                    file.mkdir();
                }
                File file_planogram = new File(Environment.getExternalStorageDirectory(), "GSK_MT_ORANGE_Planogram_Images");
                if (!file_planogram.isDirectory()) {
                    file_planogram.mkdir();
                }

                if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getApplicationContext(),
                                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                if (checkPlayServices()) {
                    // Building the GoogleApi client
                    buildGoogleApiClient();

                    createLocationRequest();
                }

                // Create an instance of GoogleAPIClient.
                if (mGoogleApiClient == null) {
                    mGoogleApiClient = new GoogleApiClient.Builder(this)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .addApi(LocationServices.API)
                            .build();
                }


                //attemptLogin();
            }

        }
    }
    void showOnPermissiondenied(final String permissionsRequired, final int request_code, final int check) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TabLoginActivity.this);
        builder.setTitle("Need Multiple Permissions");
        builder.setMessage("This app needs Camera, Storage and Location permissions.");
        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                if (check == 0) {
                    checkAppPermission(permissionsRequired, request_code);
                } else {
                    ActivityCompat.requestPermissions(TabLoginActivity.this,
                            new String[]{permissionsRequired},
                            request_code);
                }

            }
        });
       /* builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });*/
        builder.show();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.notsuppoted)
                        , Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }
    protected synchronized void buildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    void  getViewId(){
        context = this;
        db = new Lorealba_Database(this);
        db.open();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        sp_sppiner=(Spinner)findViewById(R.id.sp_sppiner);
        password=(EditText) findViewById(R.id.password);
        btncontinue=(Button) findViewById(R.id.btncontinue);
        user_type_login=(TextView) findViewById(R.id.user_type_login);

        if (getIntent().getSerializableExtra(CommonString.TAG_FROM) != null && getIntent().getSerializableExtra(CommonString.TAG_MODE) != null) {
            tag_from = getIntent().getStringExtra(CommonString.TAG_FROM);
            tag_mode = getIntent().getStringExtra(CommonString.TAG_MODE);
        }

        imei = new ImeiNumberClass(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE},
                    PERMISSIONS_REQUEST_READ_PHONE_STATE);
        } else {
            imeiNumbers = imei.getDeviceImei();
        }
        getDeviceName();

        try {
            app_ver = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);

        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        user_type_login.setText(tag_mode);
        counterId = preferences.getString(CommonString.KEY_COUNTER_ID, "");
        appversion = preferences.getString(CommonString.KEY_VERSION, "");
        app_path = preferences.getString(CommonString.KEY_PATH, "");
        visitdate = preferences.getString(CommonString.KEY_DATE, "");

        bsListData = db.getBAListData(tag_from);

        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNetIsAvailable()) {

                    attemptLogin();

                } else {
                    AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_INTERNET_NOT_AVALABLE, false);
                }

            }
        });
    }
    private void setSppinerData() {

        store_visited_adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        store_visited_adapter.add("Select Store Type");
        for (int i = 0; i < bsListData.size(); i++) {
            store_visited_adapter.add(bsListData.get(i).getUserName());
        }
        sp_sppiner.setAdapter(store_visited_adapter);


        sp_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    userId = bsListData.get(position - 1).getUserName();
                    //store_type_cd = String.valueOf(storeVisitedWitheData.get(position - 1).getStoreTypeId());
                } else {
                 //   store_type_cd = "0";
                    userId = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    private void attemptLogin() {
        passwordData = password.getText().toString().trim();
        AttempLogin();
      /*  password.setError(null);
        // Store values at the time of the login attempt.
        passwordData = password.getText().toString().trim();

        boolean cancel = true;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(passwordData)) {

            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }
        // Check for a valid userid address.
       *//* if (TextUtils.isEmpty(userId)) {
            museridView.setError(getString(R.string.error_field_required));
            focusView = museridView;
            cancel = true;
        }*//*

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } *//*else if (!isuseridValid(userid)) {
            Snackbar.make(museridView, getString(R.string.error_incorrect_username), Snackbar.LENGTH_SHORT).show();
        } *//*else if (!isPasswordValid(passwordData)) {
            Snackbar.make(password, getString(R.string.error_incorrect_password), Snackbar.LENGTH_SHORT).show();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            AttempLogin();
        }*/
    }


    private boolean checkNetIsAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        boolean flag = true;
        String pw = preferences.getString(CommonString.KEY_PASSWORD, "");
        if (!pw.equals("") && !password.equals(pw)) {
            flag = false;
        }
        return flag;
    }



    private void AttempLogin() {
        try {
            loading = ProgressDialog.show(TabLoginActivity.this, "Processing", "Please wait...", false, false);
            versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("UserName", userId);
            jsonObject.put("Password", passwordData);
            jsonObject.put("CounterId", counterId);
            jsonObject.put("Latitude", lat);
            jsonObject.put("Longitude", lon);
            jsonObject.put("Appversion", app_ver);
            jsonObject.put("Attmode", "0");
            jsonObject.put("Networkstatus", "0");
            jsonObject.put("ModelNumber", model);
            jsonObject.put("Manufacturer", manufacturer);
            jsonObject.put("OSVersion", os_version);


            if (imeiNumbers.length > 0) {
                jsonObject.put("IMEINumber1", imeiNumbers[0]);
                if (imeiNumbers.length > 1) {
                    jsonObject.put("IMEINumber2", imeiNumbers[1]);
                } else {
                    jsonObject.put("IMEINumber2", "0");
                }
            } else {
                jsonObject.put("IMEINumber1", "0");
                jsonObject.put("IMEINumber2", "0");
            }


            String jsonString = jsonObject.toString();
            try {
                final String[] data_global = {""};
                final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .readTimeout(20, TimeUnit.SECONDS)
                        .writeTimeout(20, TimeUnit.SECONDS)
                        .connectTimeout(20, TimeUnit.SECONDS)
                        .build();

                RequestBody jsonData = RequestBody.create(MediaType.parse("application/json"), jsonString);
                adapter = new Retrofit.Builder().baseUrl(CommonString.URL).client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                PostApi api = adapter.create(PostApi.class);
                Call<ResponseBody> call = api.getLoginUserdetail(jsonData);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody responseBody = response.body();
                        String data = null;
                        if (responseBody != null && response.isSuccessful()) {
                            try {
                                data = response.body().string();
                                data = data.substring(1, data.length() - 1).replace("\\", "");

                                if (data.contains("Changed")) {
                                    loading.dismiss();
                                    AlertandMessages.showAlertlogin(TabLoginActivity.this, CommonString.MESSAGE_CHANGED);
                                } else if (data.contains("No data")) {
                                    loading.dismiss();
                                    AlertandMessages.showAlertlogin(TabLoginActivity.this, CommonString.MESSAGE_LOGIN_NO_DATA);

                                } else if (data.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                    AlertandMessages.showAlertlogin(TabLoginActivity.this, CommonString.KEY_FAILURE + " Please try again");
                                    loading.dismiss();
                                } else {
                                    Gson gson = new Gson();

                                    final CounterDeviceLoginGetterSetter userObject = gson.fromJson(data, CounterDeviceLoginGetterSetter.class);
                                    editor.putString(CommonString.KEY_USERNAME, userId);
                                    editor.putString(CommonString.KEY_PASSWORD, passwordData);
                                    editor.putString(CommonString.KEY_DATE, userObject.getLOGIN().get(0).getVisitDate());
                                    editor.commit();
                                   /* loginGetterSetter=new LoginGetterSetter();
                                    loginGetterSetter.setUserId(userId);
                                    loginGetterSetter.setPassword(passwordData);
                                    db.insertLoginData(loginGetterSetter);
*/
                                    /*Intent in = new Intent(getApplicationContext(), DealarBoardActivity.class);
                                    in.putExtra(CommonString.IS_PASSWORD_CHECK, false);
                                    startActivity(in);
                                    finish();
                                    loading.dismiss();*/
                                    if (preferences.getString(CommonString.KEY_VERSION, "").equals(Integer.toString(versionCode))) {
                                        loginGetterSetter=new LoginGetterSetter();
                                        loginGetterSetter.setUserId(userId);
                                        loginGetterSetter.setPassword(passwordData);
                                        db.insertLoginData(loginGetterSetter);
                                        Intent in = new Intent(getApplicationContext(), MPinActivity.class);
                                        in.putExtra(CommonString.IS_PASSWORD_CHECK, false);
                                        startActivity(in);
                                        finish();
                                        loading.dismiss();
                                    } else {
                                        Intent intent = new Intent(context, AutoUpdateActivity.class);
                                        intent.putExtra(CommonString.KEY_PATH, preferences.getString(CommonString.KEY_PATH, ""));
                                        startActivity(intent);
                                        finish();
                                    }

                                }
                            } catch (Exception e) {
                                loading.dismiss();
                                e.printStackTrace();
                                AlertandMessages.showAlertlogin(TabLoginActivity.this, CommonString.MESSAGE_SOCKETEXCEPTION + "(" + e.toString() + ")");

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        loading.dismiss();
                        if (t instanceof SocketTimeoutException || t instanceof IOException || t instanceof Exception) {
                            AlertandMessages.showAlertlogin(TabLoginActivity.this, CommonString.MESSAGE_INTERNET_NOT_AVALABLE + "(" + t.getMessage().toString() + ")");
                        } else {
                            AlertandMessages.showAlertlogin(TabLoginActivity.this, CommonString.MESSAGE_SOCKETEXCEPTION);

                        }
                    }
                });

            } catch (Exception e) {
                loading.dismiss();
                e.printStackTrace();
                AlertandMessages.showAlertlogin(TabLoginActivity.this, CommonString.MESSAGE_SOCKETEXCEPTION + "(" + e.toString() + ")");
            }

        } catch (PackageManager.NameNotFoundException e) {
            loading.dismiss();
            AlertandMessages.showAlertlogin(TabLoginActivity.this, CommonString.MESSAGE_SOCKETEXCEPTION + "(" + e.toString() + ")");

        } catch (JSONException e) {
            loading.dismiss();
            AlertandMessages.showAlertlogin(TabLoginActivity.this, CommonString.MESSAGE_SOCKETEXCEPTION + "(" + e.toString() + ")");
        }
    }

    private String getCurrentTime() {
        Calendar m_cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(m_cal.getTime());
    }
    @Override
    public void onConnected(Bundle bundle) {
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
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mLastLocation != null) {
                lat = mLastLocation.getLatitude();
                lon = mLastLocation.getLongitude();
                //  Toast.makeText(getApplicationContext(), "onconnected lat-" + lat + " Long-" + lon, Toast.LENGTH_SHORT).show();
            }
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        // Toast.makeText(this,  " WORKS_lat_lon " + latLng, Toast.LENGTH_LONG).show();
        //  updateLocation(latLng);
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    protected void startLocationUpdates() {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mGoogleApiClient != null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                // Toast.makeText(getApplicationContext(), "startLocation - Lat" + lat + "Long" + lon, Toast.LENGTH_LONG).show();
            }
        }

    }

    public void getDeviceName() {
        manufacturer = Build.MANUFACTURER;
        model = Build.MODEL;
        os_version = android.os.Build.VERSION.RELEASE;
    }


}
