package com.lorealbaautomation.download;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.lorealbaautomation.Database.Lorealba_Database;
import com.lorealbaautomation.DealarBoardActivity;
import com.lorealbaautomation.R;
import com.lorealbaautomation.constant.CommonString;
import com.lorealbaautomation.retrofit.DownloadAllDatawithRetro;

import org.json.JSONObject;

import java.util.ArrayList;


public class DownloadActivity extends AppCompatActivity {
    Lorealba_Database db;
    String userId, date;
    private SharedPreferences preferences = null;
    Toolbar toolbar;
    Context context;
    int downloadindex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        db = new Lorealba_Database(context);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = preferences.getString(CommonString.KEY_USERNAME, null);
        date = preferences.getString(CommonString.KEY_DATE, "");
        downloadindex = preferences.getInt(CommonString.KEY_DOWNLOAD_INDEX, 0);
        setTitle("Download - " + date);
        UploadDataTask();
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
            keysList.add("Mapping_JourneyPlan");
            keysList.add("Mapping_CounterGroup_Brand");
            keysList.add("Product_Master");
            keysList.add("Mapping_Visibility");
            keysList.add("Master_Posm");

            /*keysList.add("Journey_Plan");
            keysList.add(" Posm_Master");
            keysList.add("Non_Working_Reason");
            keysList.add("Dashboard_Data");
            keysList.add("Promotion_Master");
            keysList.add("Non_Promotion_Reason");
            keysList.add("InwardSales_PO");
            keysList.add("Non_Stock_Reason");
            keysList.add("Stock_Data");
            keysList.add("Stock_Tester_Data");
            keysList.add("Stock_PwpGwp_Data");
            keysList.add("Stock_Sample_Data");
            keysList.add("Consumer_Sales_History");
            keysList.add("Attendance_History");
            keysList.add("Sales_Performance");
            keysList.add("Dashboard_Achivement_Detail");
            keysList.add("Customer_Visited");
            keysList.add("Customer_Visited_CurMonth");*/

            if (keysList.size() > 0) {
                for (int i = 0; i < keysList.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Downloadtype", keysList.get(i));
                    jsonObject.put("Username", "testba1");
                    jsonObject.put("Param1", "1");
                    jsonObject.put("Param2", "");
                    jsonList.add(jsonObject.toString());
                    KeyNames.add(keysList.get(i));
                }

                if (jsonList.size() > 0) {
                    ProgressDialog pd = new ProgressDialog(context);
                    pd.setCancelable(false);
                    pd.setMessage("Downloading Data" + "(" + "/" + ")");
                    pd.show();
                    DownloadAllDatawithRetro downloadData = new DownloadAllDatawithRetro(context, db, pd, CommonString.TAG_FROM_CURRENT);
                    downloadData.listSize = jsonList.size();
                    downloadData.downloadDataUniversalWithoutWait(jsonList, KeyNames, downloadindex, CommonString.DOWNLOAD_ALL_SERVICE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
