package com.lorealbaautomation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lorealbaautomation.constant.CommonString;

public class UserLoginActivity extends AppCompatActivity {
    private String counterId = "";
    private SharedPreferences preferences;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        getViewId();
    }

   void getViewId(){

       context = this;
       preferences = PreferenceManager.getDefaultSharedPreferences(context);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
       getSupportActionBar().setHomeButtonEnabled(true);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setTitle(getString(R.string.user_login) + " - " + "");
       counterId = preferences.getString(CommonString.KEY_COUNTER_ID, "");

   }
}
