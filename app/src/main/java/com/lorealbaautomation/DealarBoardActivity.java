package com.lorealbaautomation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lorealbaautomation.Database.Lorealba_Database;
import com.lorealbaautomation.constant.CommonString;
import com.lorealbaautomation.constant.ImageConverter;
import com.lorealbaautomation.dailyactivity.ServiceActivity;
import com.lorealbaautomation.download.DownloadActivity;
import com.lorealbaautomation.downloadService.DownloadResultReceiver;
import com.lorealbaautomation.downloadService.DownloadService;

import java.io.File;
import java.io.Serializable;

import de.hdodenhof.circleimageview.CircleImageView;

public class DealarBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DownloadResultReceiver.Receiver {
    private SharedPreferences preferences = null;
    int downloadindex = 0;
    Lorealba_Database db;
    private View headerView;
    Context context;
    private DownloadResultReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealar_board);
        context = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        downloadindex = preferences.getInt(CommonString.KEY_DOWNLOAD_INDEX, 0);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_dealar_board, navigationView, false);
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher_round);
        CircleImageView circularimagwview = headerView.findViewById(R.id.circleView);
        circularimagwview.setImageBitmap(bitmap);

        TextView tv_username = (TextView) headerView.findViewById(R.id.nav_user_name);
        TextView tv_usertype = (TextView) headerView.findViewById(R.id.nav_user_type);
//        tv_username.setText(user_name);
//        tv_usertype.setText(user_type);
        setTitle("");
        // Create a Folder for Images
        File file = new File(Environment.getExternalStorageDirectory(), ".LorealBa_Images");
        if (!file.isDirectory()) {
            file.mkdir();
        }
        db = new Lorealba_Database(context);
        db.open();

      /*  *//* Starting Download Service *//*
        mReceiver = new DownloadResultReceiver(new Handler());
        mReceiver.setReceiver(this);

        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);
        *//* Send optional extras to Download IntentService   intent.putExtra("url", url);*//*
        intent.putExtra("receiver", mReceiver);
        intent.putExtra("requestId", 101);
        intent.putExtra("Username", "testba1");
        intent.putExtra("Param1", "1");
        intent.putExtra(CommonString.KEY_DOWNLOAD_INDEX, downloadindex);

        startService(intent);*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dealar_board, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
           /* case R.id.action_add_contact:

                break;
            case R.id.action_settings:

                break;
            case R.id.action_about_us:*/

            //  break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.sync_data_nav) {
            // Handle the camera action
            startActivity(new Intent(context, DownloadActivity.class));
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

        } else if (id == R.id.nav_ba_profile) {

        } else if (id == R.id.nav_counter_menu) {

        } else if (id == R.id.nav_services) {
            startActivity(new Intent(context, ServiceActivity.class));
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

        } else if (id == R.id.nav_reset_password) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case DownloadService.STATUS_RUNNING:
                setProgressBarIndeterminateVisibility(true);
                break;

            case DownloadService.STATUS_FINISHED:
                /* Hide progress & extract result from bundle */
                setProgressBarIndeterminateVisibility(false);
                String[] results = resultData.getStringArray("result");

               /* *//* Update ListView with result *//*
                arrayAdapter = new ArrayAdapter(MyActivity.this, android.R.layout.simple_list_item_2, results);
                listView.setAdapter(arrayAdapter);*/

                break;
            case DownloadService.STATUS_ERROR:
                /* Handle the error */
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
