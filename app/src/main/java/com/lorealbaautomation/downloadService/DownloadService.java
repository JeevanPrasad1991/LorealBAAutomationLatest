package com.lorealbaautomation.downloadService;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import com.lorealbaautomation.Database.Lorealba_Database;
import com.lorealbaautomation.constant.CommonString;
import com.lorealbaautomation.retrofit.DownloadAllDatawithRetro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DownloadService extends IntentService {
    private static final String TAG = "DownloadService";
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;


    public DownloadService() {
        super(DownloadService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Service Started!");
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String url = intent.getStringExtra("Username");
        String user_name = intent.getStringExtra("Username");
        String param_1 = intent.getStringExtra("Param1");

       /* intent.putExtra("receiver", mReceiver);
        intent.putExtra("requestId", 101);
        intent.putExtra("Username", "testba1");
        intent.putExtra("Param1", "1");
        intent.putExtra(CommonString.KEY_DOWNLOAD_INDEX, downloadindex);
        intent.putExtra("Database", (Serializable) db);*/

        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(url)) {
            /* Update UI: Download Service is Running */
            receiver.send(STATUS_RUNNING, Bundle.EMPTY);
            try {

                String[] results = downloadData(url);
                /* Sending result back to activity */
                if (null != results && results.length > 0) {
                    bundle.putStringArray("result", results);
                    receiver.send(STATUS_FINISHED, bundle);
                }

            } catch (Exception e) {
                /* Sending error message back to activity */
                bundle.putString(Intent.EXTRA_TEXT, e.toString());
                receiver.send(STATUS_ERROR, bundle);
            }
        }
        Log.d(TAG, "Service Stopping!");
        this.stopSelf();
    }

    private String[] downloadData(String requestUrl) throws IOException, DownloadException {
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        /* forming th java.net.URL object */
        URL url = new URL(requestUrl);
        urlConnection = (HttpURLConnection) url.openConnection();
        /* optional request header */
        urlConnection.setRequestProperty("Content-Type", "application/json");

        /* optional request header */
        urlConnection.setRequestProperty("Accept", "application/json");

        /* for Get request */
        urlConnection.setRequestMethod("GET");

        int statusCode = urlConnection.getResponseCode();

        /* 200 represents HTTP OK */
        if (statusCode == 200) {
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            String response = convertInputStreamToString(inputStream);
            String[] results = parseResult(response);
            return results;
        } else {
            throw new DownloadException("Failed to fetch data!!");
        }
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        /* Close Stream */
        if (null != inputStream) {
            inputStream.close();
        }

        return result;
    }

    private String[] parseResult(String result) {
        String[] blogTitles = null;
        try {

            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            blogTitles = new String[posts.length()];
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                String title = post.optString("title");
                blogTitles[i] = title;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return blogTitles;
    }

    public class DownloadException extends Exception {

        public DownloadException(String message) {
            super(message);
        }

        public DownloadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

/*
    public void UploadDataTask(String user_name, String counter_id, Context context, Lorealba_Database db) {
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

            if (keysList.size() > 0) {
                for (int i = 0; i < keysList.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Downloadtype", keysList.get(i));
                    jsonObject.put("Username", user_name);
                    jsonObject.put("Param1", counter_id);
                    jsonObject.put("Param2", "");
                    jsonList.add(jsonObject.toString());
                    KeyNames.add(keysList.get(i));
                }

                if (jsonList.size() > 0) {

                    DownloadAllDatawithRetro downloadData = new DownloadAllDatawithRetro(context, db, pd, CommonString.TAG_FROM_CURRENT);
                    downloadData.listSize = jsonList.size();
                    downloadData.downloadDataUniversalWithoutWait(jsonList, KeyNames, 0, CommonString.DOWNLOAD_ALL_SERVICE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

}
