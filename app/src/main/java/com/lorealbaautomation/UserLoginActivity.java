package com.lorealbaautomation;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.lorealbaautomation.Database.Lorealba_Database;
import com.lorealbaautomation.Get_IMEI_number.ImeiNumberClass;
import com.lorealbaautomation.constant.AlertandMessages;
import com.lorealbaautomation.constant.CommonString;
import com.lorealbaautomation.dailyactivity.TabLoginActivity;
import com.lorealbaautomation.gsonGetterSetter.BaListGetterSetter;
import com.lorealbaautomation.gsonGetterSetter.CounterDeviceLoginGetterSetter;
import com.lorealbaautomation.retrofit.DownloadAllDatawithRetro;
import com.lorealbaautomation.retrofit.PostApi;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
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

public class UserLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private String counterId = "", appversion = "", app_path = "", visitdate = "";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor = null;
    private Context context;
    private LinearLayout lay_ba, lay_ma, lay_tba;
    private Button btncontinue;
    String userId;
    private Spinner sp_present;
    Lorealba_Database db;
    int downloadindex = 0;
    ArrayList<BaListGetterSetter> bsListData = new ArrayList<>();
    private ArrayAdapter<CharSequence> store_visited_adapter;
    private ImeiNumberClass imei;
    private String app_ver;
    private String manufacturer;
    private String model;
    private String os_version;
    private String[] imeiNumbers;
    ProgressDialog loading;
    private int versionCode;
    private Retrofit adapter;
    String lat="0.0";
    String lon="0.0";
    String passwordData;
    EditText user_password;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 10;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 11;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE_READ = 12;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE_WRITE = 14;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_user_login);

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


    }

    void getViewId() {

        context = this;
        db = new Lorealba_Database(this);
        db.open();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        lay_ba = (LinearLayout) findViewById(R.id.lay_ba);
        lay_ma = (LinearLayout) findViewById(R.id.lay_ma);
        lay_tba = (LinearLayout) findViewById(R.id.lay_tba);
        btncontinue = (Button) findViewById(R.id.btncontinue);
        sp_present = (Spinner) findViewById(R.id.sp_present);
        user_password = (EditText) findViewById(R.id.user_password);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.user_login) + " - " + "");

        counterId = preferences.getString(CommonString.KEY_COUNTER_ID, "");
        appversion = preferences.getString(CommonString.KEY_VERSION, "");
        app_path = preferences.getString(CommonString.KEY_PATH, "");
        visitdate = preferences.getString(CommonString.KEY_DATE, "");
        lat = preferences.getString(CommonString.KEY_LATITUDE, "");
        lon = preferences.getString(CommonString.KEY_LONGITUDE, "");

        if (lat==null){

        }else {
            lat="0.0";
        }
        if (lat==null){

        }else {
            lon="0.0";
        }

        lay_ba.setOnClickListener(this);
        lay_ma.setOnClickListener(this);
        lay_tba.setOnClickListener(this);
        btncontinue.setOnClickListener(this);

        imei = new ImeiNumberClass(context);
        imeiNumbers = imei.getDeviceImei();
        getDeviceName();

        try {
            app_ver = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);

        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        bsListData = db.getBAListAllData();
        if (bsListData.size()==0){
            UploadDataTask();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_ba:
                Intent balogin = new Intent(context, TabLoginActivity.class);
                balogin.putExtra(CommonString.TAG_FROM, CommonString.TAG_FROM_BA);
                balogin.putExtra(CommonString.TAG_MODE, CommonString.MODE_FROM_BA_LOGIN);
                startActivity(balogin);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                break;
            case R.id.lay_ma:
                Intent malogin = new Intent(context, TabLoginActivity.class);
                malogin.putExtra(CommonString.TAG_FROM, CommonString.TAG_FROM_MA);
                malogin.putExtra(CommonString.TAG_MODE, CommonString.MODE_FROM_MA_LOGIN);
                startActivity(malogin);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                break;
            case R.id.lay_tba:
                Intent tbalogin = new Intent(context, TabLoginActivity.class);
                tbalogin.putExtra(CommonString.TAG_FROM, CommonString.TAG_FROM_TBA);
                tbalogin.putExtra(CommonString.TAG_MODE, CommonString.MODE_FROM_TBA_LOGIN);
                startActivity(tbalogin);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                break;
            case R.id.btncontinue:
                if (checkNetIsAvailable()) {
                    attemptLogin();
                } else {
                    AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_INTERNET_NOT_AVALABLE, false);
                }

                break;
        }
    }

    private boolean checkNetIsAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public void UploadDataTask() {
        try {
            ArrayList<String> keysList = new ArrayList<>();
            ArrayList<String> jsonList = new ArrayList<>();
            ArrayList<String> KeyNames = new ArrayList<>();
            KeyNames.clear();
            keysList.clear();
            keysList.add("Table_Structure");
            keysList.add("BA_List");

            if (keysList.size() > 0) {
                for (int i = 0; i < keysList.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Downloadtype", keysList.get(i));
                    jsonObject.put("Username", "");
                    jsonObject.put("Param1", counterId);
                    jsonObject.put("Param2", "");
                    jsonList.add(jsonObject.toString());
                    KeyNames.add(keysList.get(i));
                }

                if (jsonList.size() > 0) {
                    ProgressDialog pd = new ProgressDialog(context);
                    DownloadAllDatawithRetro downloadData = new DownloadAllDatawithRetro(context, db, pd, CommonString.TAG_FROM_CURRENT);
                    downloadData.listSize = jsonList.size();
                    downloadData.downloadDataUniversalBa(jsonList, KeyNames, downloadindex, CommonString.DOWNLOAD_ALL_SERVICE);
                   /* Intent in = new Intent(context, DealarBoardActivity.class);
                    context.startActivity(in);*/
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSppinerData() {

        store_visited_adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        store_visited_adapter.add("Select Store Type");
        for (int i = 0; i < bsListData.size(); i++) {
            store_visited_adapter.add(bsListData.get(i).getUserName());
        }
        sp_present.setAdapter(store_visited_adapter);


        sp_present.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    userId = bsListData.get(position - 1).getUserName();
                } else {
                    userId = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    public void getDeviceName() {
        manufacturer = Build.MANUFACTURER;
        model = Build.MODEL;
        os_version = android.os.Build.VERSION.RELEASE;
    }

    private void AttempLogin() {
        try {
            loading = ProgressDialog.show(UserLoginActivity.this, "Processing", "Please wait...", false, false);
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
                                    AlertandMessages.showAlertlogin(UserLoginActivity.this, CommonString.MESSAGE_CHANGED);
                                } else if (data.contains("No data")) {
                                    loading.dismiss();
                                    AlertandMessages.showAlertlogin(UserLoginActivity.this, CommonString.MESSAGE_LOGIN_NO_DATA);

                                } else if (data.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                    AlertandMessages.showAlertlogin(UserLoginActivity.this, CommonString.KEY_FAILURE + " Please try again");
                                    loading.dismiss();
                                } else {
                                    Gson gson = new Gson();

                                    final CounterDeviceLoginGetterSetter userObject = gson.fromJson(data, CounterDeviceLoginGetterSetter.class);
                                    editor.putString(CommonString.KEY_USERNAME, userId);
                                    editor.putString(CommonString.KEY_PASSWORD, passwordData);
                                    editor.putString(CommonString.KEY_DATE, userObject.getLOGIN().get(0).getVisitDate());
                                    editor.commit();
                                 /*   loginGetterSetter=new LoginGetterSetter();
                                    loginGetterSetter.setUserId(userId);
                                    loginGetterSetter.setPassword(passwordData);
                                    db.insertLoginData(loginGetterSetter);*/

                                    Intent in = new Intent(getApplicationContext(), DealarBoardActivity.class);
                                    in.putExtra(CommonString.IS_PASSWORD_CHECK, false);
                                    startActivity(in);
                                    finish();
                                    loading.dismiss();

                                }

                               /* Gson gson = new Gson();
                                CounterDeviceLoginGetterSetter userObject= gson.fromJson(data, CounterDeviceLoginGetterSetter.class);
                                if (userObject.getLOGIN().get(0).getCounterId().equals(0)) {
                                    loading.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("Parinaam");
                                    builder.setMessage("Wrong IMEI number Please contact to Administer").setCancelable(false)
                                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.dismiss();


                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();

                                }  else if (data.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                    AlertandMessages.showAlertlogin(TabLoginActivity.this, CommonString.KEY_FAILURE + " Please try again");
                                    loading.dismiss();
                                } else if (userObject.getLOGIN().get(0).getCounterId().equals(-1)) {

                                    //AlertandMessages.showAlertlogin(TabLoginActivity.this, CommonString.KEY_FAILURE + " wrong counter ");
                                    loading.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("Parinaam");
                                    builder.setMessage("wrong counter").setCancelable(false)
                                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.dismiss();

                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                    finish();
                                } else {
                                    editor.putString(CommonString.KEY_VERSION, String.valueOf(userObject.getLOGIN().get(0).getAppVersion()));
                                    editor.putString(CommonString.KEY_PATH, userObject.getLOGIN().get(0).getAppPath());
                                    editor.putString(CommonString.KEY_DATE, userObject.getLOGIN().get(0).getVisitDate());
                                    editor.putString(CommonString.KEY_COUNTER_ID, String.valueOf(userObject.getLOGIN().get(0).getCounterId()));
                                    editor.commit();
                                    Intent intent = new Intent(getBaseContext(), UserLoginActivity.class);
                                    startActivity(intent);
                                    finish();

                                }*/

                            } catch (Exception e) {
                                loading.dismiss();
                                e.printStackTrace();
                                AlertandMessages.showAlertlogin(UserLoginActivity.this, CommonString.MESSAGE_SOCKETEXCEPTION + "(" + e.toString() + ")");

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        loading.dismiss();
                        if (t instanceof SocketTimeoutException || t instanceof IOException || t instanceof Exception) {
                            AlertandMessages.showAlertlogin(UserLoginActivity.this, CommonString.MESSAGE_INTERNET_NOT_AVALABLE + "(" + t.getMessage().toString() + ")");
                        } else {
                            AlertandMessages.showAlertlogin(UserLoginActivity.this, CommonString.MESSAGE_SOCKETEXCEPTION);

                        }
                    }
                });

            } catch (Exception e) {
                loading.dismiss();
                e.printStackTrace();
                AlertandMessages.showAlertlogin(UserLoginActivity.this, CommonString.MESSAGE_SOCKETEXCEPTION + "(" + e.toString() + ")");
            }

        } catch (PackageManager.NameNotFoundException e) {
            loading.dismiss();
            AlertandMessages.showAlertlogin(UserLoginActivity.this, CommonString.MESSAGE_SOCKETEXCEPTION + "(" + e.toString() + ")");

        } catch (JSONException e) {
            loading.dismiss();
            AlertandMessages.showAlertlogin(UserLoginActivity.this, CommonString.MESSAGE_SOCKETEXCEPTION + "(" + e.toString() + ")");
        }
    }

    private void attemptLogin() {
        passwordData = user_password.getText().toString().trim();
        AttempLogin();
    }
    void checkAppPermission(String permission, int requestCode) {

        boolean permission_flag = false;
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(UserLoginActivity.this,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(UserLoginActivity.this,
                    permission)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                showOnPermissiondenied(Manifest.permission.CAMERA, MY_PERMISSIONS_REQUEST_CAMERA, 1);
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(UserLoginActivity.this, new String[]{permission}, requestCode);

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

                if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getApplicationContext(),
                                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

            }

        }
    }

    void showOnPermissiondenied(final String permissionsRequired, final int request_code, final int check) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserLoginActivity.this);
        builder.setTitle("Need Multiple Permissions");
        builder.setMessage("This app needs Camera, Storage and Location permissions.");
        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                if (check == 0) {
                    checkAppPermission(permissionsRequired, request_code);
                } else {
                    ActivityCompat.requestPermissions(UserLoginActivity.this, new String[]{permissionsRequired}, request_code);
                }

            }
        });
        builder.show();
    }

}
