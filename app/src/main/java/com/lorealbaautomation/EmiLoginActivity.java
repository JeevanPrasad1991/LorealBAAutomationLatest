package com.lorealbaautomation;

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
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

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
import com.lorealbaautomation.Get_IMEI_number.ImeiNumberClass;
import com.lorealbaautomation.constant.AlertandMessages;
import com.lorealbaautomation.constant.CommonString;
import com.lorealbaautomation.gsonGetterSetter.CounterDeviceLoginGetterSetter;
import com.lorealbaautomation.password.MPinActivity;
import com.lorealbaautomation.retrofit.PostApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
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

public class EmiLoginActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private String[] imeiNumbers;
    private String app_ver;
    private Context context;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    ProgressDialog loading;
    private Retrofit adapter;
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor editor = null;
    private double lat = 0.0;
    private double lon = 0.0;
    private int versionCode;
    private String manufacturer;
    private String model;
    LocationManager locationManager;
    private String os_version;
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
    private ImeiNumberClass imei;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_counter_login);

       getViewId();

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
        if (ContextCompat.checkSelfPermission(EmiLoginActivity.this,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(EmiLoginActivity.this,
                    permission)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                showOnPermissiondenied(Manifest.permission.CAMERA, MY_PERMISSIONS_REQUEST_CAMERA, 1);
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(EmiLoginActivity.this,
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
              //  AttempLogin();

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
        AlertDialog.Builder builder = new AlertDialog.Builder(EmiLoginActivity.this);
        builder.setTitle("Need Multiple Permissions");
        builder.setMessage("This app needs Camera, Storage and Location permissions.");
        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                if (check == 0) {
                    checkAppPermission(permissionsRequired, request_code);
                } else {
                    ActivityCompat.requestPermissions(EmiLoginActivity.this,
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

    private void AttempLogin() {
        try {
            loading = ProgressDialog.show(EmiLoginActivity.this, "Processing", "Please wait...", false, false);
            versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("Latitude", lat);
            jsonObject.put("Longitude", lon);
            jsonObject.put("Appversion", app_ver);
            jsonObject.put("Attmode", "0");
            jsonObject.put("Networkstatus", "0");
            jsonObject.put("Manufacturer", manufacturer);
            jsonObject.put("ModelNumber", model);
            jsonObject.put("OSVersion", os_version);
            jsonObject.put("CounterId", "0");


            if (imeiNumbers.length > 0) {
                jsonObject.put("IMEINumber1", imeiNumbers[0]);
            } else {
                jsonObject.put("IMEINumber1", "0");
            }
           /* Latitude
                    Longitude
            Appversion
                    Attmode
            Networkstatus
                    Manufacturer
            ModelNumber
                    OSVersion
            CounterId
                    IMEINumber1*/



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
                Call<ResponseBody> call = api.getLogindetail(jsonData);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody responseBody = response.body();
                        String data = null;
                        if (responseBody != null && response.isSuccessful()) {
                            try {
                                data = response.body().string();
                                data = data.substring(1, data.length() - 1).replace("\\", "");

                                Gson gson = new Gson();
                                CounterDeviceLoginGetterSetter userObject= gson.fromJson(data, CounterDeviceLoginGetterSetter.class);
                                if (userObject.getCounterDeviceLogin().get(0).getCounterId().equals(0)) {
                                    loading.dismiss();
                                    Intent intent = new Intent(getBaseContext(), CounterLoginActivity.class);
                                    startActivity(intent);
                                    finish();

                                }  else if (data.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                    AlertandMessages.showAlertlogin(EmiLoginActivity.this, CommonString.KEY_FAILURE + " Please try again");
                                    loading.dismiss();
                                } else if (userObject.getCounterDeviceLogin().get(0).getAppVersion().equals(versionCode)) {
                                   /* loading.dismiss();
                                    editor.putString(CommonString.KEY_VERSION, String.valueOf(userObject.getLOGIN().get(0).getAppVersion()));
                                    editor.putString(CommonString.KEY_PATH, userObject.getLOGIN().get(0).getAppPath());
                                    editor.putString(CommonString.KEY_DATE, userObject.getLOGIN().get(0).getVisitDate());
                                    editor.putString(CommonString.KEY_COUNTER_ID, String.valueOf(userObject.getLOGIN().get(0).getCounterId()));
                                    editor.putString(CommonString.KEY_LATITUDE, String.valueOf(lat));
                                    editor.putString(CommonString.KEY_LONGITUDE, String.valueOf(lon));
                                    editor.commit();
                                    Intent intent = new Intent(getBaseContext(), UserLoginActivity.class);
                                    startActivity(intent);
                                    finish();
*/

                                    String mpin = preferences.getString(CommonString.MPIN, null);
                                    if(mpin!=null){
                                        loading.dismiss();
                                        editor.putString(CommonString.KEY_VERSION, String.valueOf(userObject.getCounterDeviceLogin().get(0).getAppVersion()));
                                        editor.putString(CommonString.KEY_PATH, userObject.getCounterDeviceLogin().get(0).getAppPath());
                                        editor.putString(CommonString.KEY_DATE, userObject.getCounterDeviceLogin().get(0).getVisitDate());
                                        editor.putString(CommonString.KEY_COUNTER_ID, String.valueOf(userObject.getCounterDeviceLogin().get(0).getCounterId()));
                                        editor.putString(CommonString.KEY_LATITUDE, String.valueOf(lat));
                                        editor.putString(CommonString.KEY_LONGITUDE, String.valueOf(lon));
                                        editor.commit();
                                        Intent in = new Intent(getApplicationContext(), MPinActivity.class);
                                        in.putExtra(CommonString.IS_PASSWORD_CHECK, true);
                                        startActivity(in);

                                        finish();
                                    }
                                    else {
                                        editor.putString(CommonString.KEY_VERSION, String.valueOf(userObject.getCounterDeviceLogin().get(0).getAppVersion()));
                                        editor.putString(CommonString.KEY_PATH, userObject.getCounterDeviceLogin().get(0).getAppPath());
                                        editor.putString(CommonString.KEY_DATE, userObject.getCounterDeviceLogin().get(0).getVisitDate());
                                        editor.putString(CommonString.KEY_COUNTER_ID, String.valueOf(userObject.getCounterDeviceLogin().get(0).getCounterId()));
                                        editor.commit();
                                        Intent i = new Intent(context, UserLoginActivity.class);
                                        startActivity(i);
                                        finish();
                                    }


                                }else {
                                    Intent intent = new Intent(getBaseContext(), AutoUpdateActivity.class);
                                    intent.putExtra(CommonString.KEY_PATH, preferences.getString(CommonString.KEY_PATH, ""));
                                    startActivity(intent);
                                    finish();
                                }

                            } catch (Exception e) {
                                loading.dismiss();
                                e.printStackTrace();
                                AlertandMessages.showAlertlogin(EmiLoginActivity.this, CommonString.MESSAGE_SOCKETEXCEPTION + "(" + e.toString() + ")");


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        loading.dismiss();
                        if (t instanceof SocketTimeoutException || t instanceof IOException || t instanceof Exception) {
                            AlertandMessages.showAlertlogin(EmiLoginActivity.this,
                                    CommonString.MESSAGE_INTERNET_NOT_AVALABLE + "(" + t.getMessage().toString() + ")");
                        } else {
                            AlertandMessages.showAlertlogin(EmiLoginActivity.this, CommonString.MESSAGE_SOCKETEXCEPTION);

                        }
                    }
                });

            } catch (Exception e) {
                loading.dismiss();
                e.printStackTrace();
                AlertandMessages.showAlertlogin(EmiLoginActivity.this, CommonString.MESSAGE_SOCKETEXCEPTION + "(" + e.toString() + ")");
            }

        } catch (PackageManager.NameNotFoundException e) {
            loading.dismiss();
            AlertandMessages.showAlertlogin(EmiLoginActivity.this, CommonString.MESSAGE_SOCKETEXCEPTION + "(" + e.toString() + ")");

        } catch (JSONException e) {
            loading.dismiss();
            AlertandMessages.showAlertlogin(EmiLoginActivity.this, CommonString.MESSAGE_SOCKETEXCEPTION + "(" + e.toString() + ")");
        }
    }

    public void getDeviceName() {
        manufacturer = Build.MANUFACTURER;
        model = Build.MODEL;
        os_version = android.os.Build.VERSION.RELEASE;
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
    void  getViewId(){
        context = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        imei = new ImeiNumberClass(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE},
                    PERMISSIONS_REQUEST_READ_PHONE_STATE);
        } else {
            imeiNumbers = imei.getDeviceImei();
        }
        imeiNumbers = imei.getDeviceImei();
        getDeviceName();
        AttempLogin();
        try {
            app_ver = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);

        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}
