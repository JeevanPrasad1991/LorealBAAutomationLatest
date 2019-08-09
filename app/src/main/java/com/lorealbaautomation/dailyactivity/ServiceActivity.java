package com.lorealbaautomation.dailyactivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.lorealbaautomation.R;
import com.lorealbaautomation.constant.CommonString;
public class ServiceActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    String date;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        date = preferences.getString(CommonString.KEY_DATE, null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //preference data
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        setTitle("Backup");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
