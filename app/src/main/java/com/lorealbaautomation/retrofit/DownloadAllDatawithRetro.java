package com.lorealbaautomation.retrofit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lorealbaautomation.Database.Lorealba_Database;
import com.lorealbaautomation.constant.AlertandMessages;
import com.lorealbaautomation.constant.CommonString;
import com.lorealbaautomation.gettersetter.ReferenceVariablesForDownloadActivity;
import com.lorealbaautomation.gsonGetterSetter.JCPGetterSetter;
import com.lorealbaautomation.gsonGetterSetter.NonStockReasonGetterSetter;
import com.lorealbaautomation.gsonGetterSetter.NonWorkingReasonGetterSetter;
import com.lorealbaautomation.gsonGetterSetter.PromotionMaster;
import com.lorealbaautomation.gsonGetterSetter.StockDataGetterSetter;
import com.lorealbaautomation.gsonGetterSetter.StockSampleDataGetterSetter;
import com.lorealbaautomation.gsonGetterSetter.StockTesterDataGetterSetter;
import com.lorealbaautomation.gsonGetterSetter.TableStructure;
import com.lorealbaautomation.gsonGetterSetter.TableStructureGetterSetter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jeevanp on 15-12-2017.
 */

public class DownloadAllDatawithRetro extends ReferenceVariablesForDownloadActivity {
    boolean isvalid;
    private Retrofit adapter;
    Context context;
    public int listSize = 0;
    int status = 0;
    Lorealba_Database db;
    //    ProgressDialog pd;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String _UserId, date, app_ver;
    int from;

    public DownloadAllDatawithRetro(Context context, Lorealba_Database db, ProgressDialog pd, int from) {
        this.context = context;
        this.db = db;
        //this.pd = pd;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        date = preferences.getString(CommonString.KEY_DATE, "");
        this.from = from;
        db.open();
    }


    public void downloadDataUniversalWithoutWait(final ArrayList<String> jsonStringList, final ArrayList<String> KeyNames, int downloadindex, int type) {
        status = 0;
        isvalid = false;
        final String[] data_global = {""};
        String jsonString = "", KeyName = "";
        int jsonIndex = 0;

        if (jsonStringList.size() > 0) {
            final OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(20, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS).connectTimeout(20, TimeUnit.SECONDS).build();
            jsonString = jsonStringList.get(downloadindex);
            KeyName = KeyNames.get(downloadindex);
            jsonIndex = downloadindex;

            RequestBody jsonData = RequestBody.create(MediaType.parse("application/json"), jsonString);
            adapter = new Retrofit.Builder().baseUrl(CommonString.URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
            PostApi api = adapter.create(PostApi.class);
            Call<String> call = api.getDownloadAll(jsonData);
            final int[] finalJsonIndex = {jsonIndex};
            final String finalKeyName = KeyName;

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String responseBody = response.body();
                    String data = null;
                    if (responseBody != null && response.isSuccessful()) {
                        try {
                            data = response.body();
                            if (data.equals("")) {
                                data_global[0] = "";
                            } else {
                                data_global[0] = data;
                                if (finalKeyName.equalsIgnoreCase("Table_Structure")) {
                                    editor.putInt(CommonString.KEY_DOWNLOAD_INDEX, finalJsonIndex[0]);
                                    editor.apply();
                                    tableStructureObj = new Gson().fromJson(data, TableStructureGetterSetter.class);
                                    String isAllTableCreated = createTable(tableStructureObj);

                                    if (isAllTableCreated != CommonString.KEY_SUCCESS) {
                                        AlertandMessages.showAlert((Activity) context, isAllTableCreated + " not created", true);
                                    }
                                } else {
                                    editor.putInt(CommonString.KEY_DOWNLOAD_INDEX, finalJsonIndex[0]);
                                    editor.apply();
                                    switch (finalKeyName) {
                                        case "BA_List":
                                            if (!data.contains("No Data")) {
                                                baListObject = new Gson().fromJson(data, JCPGetterSetter.class);
                                                if (baListObject != null && !db.insertBalistData(baListObject)) {
                                                    AlertandMessages.showSnackbarMsg(context, "Ba List data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }
                                            break;

                                        case "Journey_Plan":
                                            if (!data.contains("No Data")) {
                                                jcpObject = new Gson().fromJson(data, JCPGetterSetter.class);
                                                if (jcpObject != null && !db.insertJCPData(jcpObject)) {
                                                    AlertandMessages.showSnackbarMsg(context, "JCP data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }
                                            break;

                                        case "Mapping_CounterGroup_Brand":
                                            if (!data.contains("No Data")) {
                                                mappingCounterGroupBrandObject = new Gson().fromJson(data, JCPGetterSetter.class);
                                                if (mappingCounterGroupBrandObject != null && !db.insertmappingcountergroupBrand(mappingCounterGroupBrandObject)) {
                                                    AlertandMessages.showSnackbarMsg(context, "Mapping_CounterGroup_Brand data not saved");
                                                }
                                            }

                                            break;

                                        case "Product_Master":
                                            if (!data.contains("No Data")) {
                                                product_masterObject = new Gson().fromJson(data, TableStructureGetterSetter.class);
                                                if (product_masterObject != null && !db.insertproductmasterdata(product_masterObject)) {
                                                    AlertandMessages.showSnackbarMsg(context, "Product Master data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }
                                            break;

                                            case "Mapping_Visibility":
                                            if (!data.contains("No Data")) {
                                                mapping_visibilityObject = new Gson().fromJson(data, TableStructureGetterSetter.class);
                                                if (mapping_visibilityObject != null && !db.insertmappingvisibility(mapping_visibilityObject)) {
                                                    AlertandMessages.showSnackbarMsg(context, "Mapping_Visibility data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }
                                            break;

                                            case "Master_Posm":
                                            if (!data.contains("No Data")) {
                                                posmmasterObject = new Gson().fromJson(data, TableStructureGetterSetter.class);
                                                if (posmmasterObject != null && !db.insertmappingposmdata(posmmasterObject)) {
                                                    AlertandMessages.showSnackbarMsg(context, "Master_Posm data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }
                                            break;


                                        case "Non_Working_Reason":
                                            if (!data.contains("No Data")) {
                                                nonWorkingObj = new Gson().fromJson(data, NonWorkingReasonGetterSetter.class);
                                                if (nonWorkingObj != null && !db.insertNonWorkingData(nonWorkingObj)) {
                                                    AlertandMessages.showSnackbarMsg(context, "Non Working Reason not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }

                                            break;



                                        case "Dashboard_Data":
                                            if (!data.contains("No Data")) {
                                                dashboardObject = new Gson().fromJson(data, NonWorkingReasonGetterSetter.class);
                                                if (dashboardObject != null && !db.insert_dashboard_data(dashboardObject)) {
                                                    //  pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Dashboard Data not saved");
                                                }
                                            }
                                            break;

                                        case "Promotion_Master":
                                            if (!data.contains("No Data")) {
                                                promotionMasterObject = new Gson().fromJson(data, NonWorkingReasonGetterSetter.class);
                                                if (promotionMasterObject != null && !db.insert_promotion_master(promotionMasterObject)) {
                                                    // pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Promotion Master Data not saved");
                                                }
                                            }
                                            break;

                                        case "Non_Promotion_Reason":
                                            if (!data.contains("No Data")) {
                                                nonpromotionReason = new Gson().fromJson(data, NonWorkingReasonGetterSetter.class);
                                                if (nonpromotionReason != null && !db.insert_non_promotion_reason(nonpromotionReason)) {
                                                    //  pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Non Promotion Reason Data not saved");
                                                }
                                            }
                                            break;

                                        case "InwardSales_PO":
                                            if (!data.contains("No Data")) {
                                                inwardSales_POObject = new Gson().fromJson(data, TableStructureGetterSetter.class);
                                                if (inwardSales_POObject != null && !db.insertInwordSalesPoData(inwardSales_POObject)) {
                                                    // pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "InwardSales PO Data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }
                                            break;

                                        case "Non_Stock_Reason":
                                            if (!data.contains("No Data")) {
                                                reasonObj = new Gson().fromJson(data, NonStockReasonGetterSetter.class);
                                                if (reasonObj != null && !db.insertNonStockReasonData(reasonObj)) {
                                                    //  pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Non Stock Data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }
                                            break;


                                        case "Stock_Data":
                                            if (!data.contains("No Data")) {
                                                stockDataObj = new Gson().fromJson(data, StockDataGetterSetter.class);
                                                if (stockDataObj != null && !db.insertStockData(stockDataObj)) {
                                                    //   pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Stock Data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }
                                            break;

                                        case "Stock_Tester_Data":
                                            if (!data.contains("No Data")) {
                                                stockTesterObj = new Gson().fromJson(data, StockTesterDataGetterSetter.class);
                                                if (stockTesterObj != null && !db.insertStockTesterData(stockTesterObj)) {
                                                    //   pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Stock Tester Data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }
                                            break;


                                        case "Stock_Sample_Data":
                                            if (!data.contains("No Data")) {
                                                stockSampleObj = new Gson().fromJson(data, StockSampleDataGetterSetter.class);
//                                                if (stockSampleObj != null && !db.insertsampleData(stockSampleObj)) {
//                                                    pd.dismiss();
//                                                    AlertandMessages.showSnackbarMsg(context, "Stock Sample data not saved");
//                                                }
                                            }

                                            break;

                                        case "Consumer_Sales_History":
                                            if (!data.contains("No Data")) {
                                                cst_sales_histryObject = new Gson().fromJson(data, NonWorkingReasonGetterSetter.class);
                                                if (cst_sales_histryObject != null && !db.insertcst_sales_histry(cst_sales_histryObject)) {
                                                    //  pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Consumer Sales History not saved");
                                                }
                                            }
                                            break;
                                        case "Attendance_History":
                                            if (!data.contains("No Data")) {
                                                attendance_historyObject = new Gson().fromJson(data, NonWorkingReasonGetterSetter.class);
                                                if (attendance_historyObject != null && !db.insert_attendancehistory(attendance_historyObject)) {
                                                    //  pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Attendance History not saved");
                                                }
                                            }
                                            break;

                                        case "Sales_Performance":
                                            if (!data.contains("No Data")) {
                                                salesperformanceObject = new Gson().fromJson(data, NonWorkingReasonGetterSetter.class);
                                                if (salesperformanceObject != null && !db.insert_salesperformance(salesperformanceObject)) {
                                                    // pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Sales Performance not saved");
                                                }
                                            }
                                            break;

                                        case "Dashboard_Achivement_Detail":
                                            if (!data.contains("No Data")) {
                                                dashboard_value_achievementObject = new Gson().fromJson(data, NonWorkingReasonGetterSetter.class);
                                                if (dashboard_value_achievementObject != null && !db.insertdashboard_achievement(dashboard_value_achievementObject)) {
                                                    // pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Dashboard_Achivement_Detail not saved");
                                                }
                                            }
                                            break;

                                        case "Customer_Visited":
                                            if (!data.contains("No Data")) {
                                                customer_visited_count_object = new Gson().fromJson(data, NonWorkingReasonGetterSetter.class);
                                                if (customer_visited_count_object != null && !db.insert_customer_visited_count(customer_visited_count_object)) {
                                                    //pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Customer_Visited not saved");
                                                }
                                            }
                                            break;

                                        case "Customer_Visited_CurMonth":
                                            if (!data.contains("No Data")) {
                                                customer_visited_curmonth_object = new Gson().fromJson(data, NonWorkingReasonGetterSetter.class);
                                                if (customer_visited_curmonth_object != null && !db.insert_customer_info_data(customer_visited_curmonth_object)) {
                                                    //  pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Customer_Visited_CurMonth not saved");
                                                }
                                            }
                                            break;

                                    }
                                }
                            }

                            finalJsonIndex[0]++;
                            if (finalJsonIndex[0] != KeyNames.size()) {
                                editor.putInt(CommonString.KEY_DOWNLOAD_INDEX, finalJsonIndex[0]);
                                editor.apply();
                                downloadDataUniversalWithoutWait(jsonStringList, KeyNames, finalJsonIndex[0], CommonString.DOWNLOAD_ALL_SERVICE);
                            } else {
                                editor.putInt(CommonString.KEY_DOWNLOAD_INDEX, 0);
                                editor.apply();
                                //pd.setMessage("Downloading Images");
                                new DownloadImageTask(context, promotionMasterObject).execute();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            editor.putInt(CommonString.KEY_DOWNLOAD_INDEX, finalJsonIndex[0]);
                            editor.apply();
                            // pd.dismiss();
                            AlertandMessages.showAlert((Activity) context, finalKeyName + " Data not found ", true);
                        }
                    } else {
                        editor.putInt(CommonString.KEY_DOWNLOAD_INDEX, finalJsonIndex[0]);
                        editor.apply();
                        //pd.dismiss();
                        AlertandMessages.showAlert((Activity) context, "Error in downloading Data at " + finalKeyName, true);

                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    isvalid = true;
                    // pd.dismiss();
                    if (t != null) {
                        AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_INTERNET_NOT_AVALABLE + "(" + t.toString() + ")", true);
                    } else {
                        AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_INTERNET_NOT_AVALABLE, true);
                    }
                }
            });
        } else {

            editor.putInt(CommonString.KEY_DOWNLOAD_INDEX, 0);
            editor.apply();
            // pd.setMessage("Downloading Images");
            new DownloadImageTask(context, promotionMasterObject).execute();
        }
    }

    String createTable(TableStructureGetterSetter tableGetSet) {
        List<TableStructure> tableList = tableGetSet.getTableStructure();
        for (int i = 0; i < tableList.size(); i++) {
            String table = tableList.get(i).getSqlText();
            if (db.createtable(table) == 0) {
                return table;
            }
        }
        return CommonString.KEY_SUCCESS;
    }

    public String downloadDataUniversal(final String jsonString, int type) {
        try {
            status = 0;
            isvalid = false;
            final String[] data_global = {""};
            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .build();
            RequestBody jsonData = RequestBody.create(MediaType.parse("application/json"), jsonString);
            adapter = new Retrofit.Builder()
                    .baseUrl(CommonString.URL).client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            PostApi api = adapter.create(PostApi.class);
            Call<JsonObject> call = api.getGeotag(jsonData);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    String responseBody = response.body().get("UploadJsonDetailResult").toString();
                    String data = null;
                    if (responseBody != null && response.isSuccessful()) {
                        try {
                            data = response.body().get("UploadJsonDetailResult").toString();

                            if (data.equalsIgnoreCase("")) {
                                data_global[0] = "";
                                isvalid = true;
                                status = 1;
                            } else {
                                data = data.substring(1, data.length() - 1).replace("\\", "");
                                data_global[0] = data;
                                isvalid = true;
                                status = 1;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            isvalid = true;
                            status = -2;
                        }
                    } else {
                        isvalid = true;
                        status = -1;
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    isvalid = true;
                    if (t instanceof SocketTimeoutException) {
                        status = 3;
                    } else if (t instanceof IOException) {
                        status = 3;
                    } else {
                        status = 3;
                    }

                }
            });

            while (isvalid == false) {
                synchronized (this) {
                    this.wait(25);
                }
            }
            if (isvalid) {
                synchronized (this) {
                    this.notify();
                }
            }
            if (status == 1) {
                return data_global[0];
            } else if (status == 2) {
                return CommonString.MESSAGE_NO_RESPONSE_SERVER;
            } else if (status == 3) {
                return CommonString.MESSAGE_SOCKETEXCEPTION;
            } else if (status == -2) {
                return CommonString.MESSAGE_INVALID_JSON;
            } else {
                return CommonString.KEY_FAILURE;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonString.KEY_FAILURE;
        }
    }

    class DownloadImageTask extends AsyncTask<String, String, String> {
        Context context;
        NonWorkingReasonGetterSetter promotionMasterObject;

        DownloadImageTask(Context context, NonWorkingReasonGetterSetter promotionMasterObject) {
            this.promotionMasterObject = promotionMasterObject;
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                //insert stock all dats
                if (promotionMasterObject != null) {
                    downloadImages(promotionMasterObject);
                }
                return CommonString.KEY_SUCCESS;
            } catch (FileNotFoundException ex) {
                return CommonString.KEY_FAILURE;
            } catch (IOException ex) {
                return CommonString.KEY_FAILURE;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
               /* Intent in = new Intent(context, DealarBoardActivity.class);
                context.startActivity(in);*/
                //   AlertandMessages.showAlert((Activity) context, "All data downloaded Successfully", true);
            } else {
                AlertandMessages.showAlert((Activity) context, "Error in downloading", true);
            }
        }
    }


    void downloadImages(NonWorkingReasonGetterSetter promotion_object) throws IOException, FileNotFoundException {
        List<PromotionMaster> data = promotion_object.getPromotionMaster();
        for (int i = 0; i < data.size(); i++) {
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "LorealBaPromoImages");
            folder.mkdir();
            if (data.get(i).getImageName() != null && !data.get(i).getImageName().equals("") && data.get(i).getUrl() != null && !data.get(i).getUrl().equals("")) {
                File pdfFile = new File(folder, data.get(i).getUrl());
                try {
                    pdfFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                downloadFile(data.get(i).getUrl(), data.get(i).getImageName(), folder);
            }
        }

    }


    public void downloadFile(String fileUrl, String directory, File folder_path) {
        try {
            final int MEGABYTE = 1024 * 1024;
            URL url = new URL(fileUrl + directory);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.getResponseCode();
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                int length = urlConnection.getContentLength();
                String size = new DecimalFormat("##.##").format((double) ((double) length / 1024)) + " KB";
                if (!new File(folder_path.getPath() + directory).exists() && !size.equalsIgnoreCase("0 KB")) {
                    File outputFile = new File(folder_path, directory);
                    FileOutputStream fos = new FileOutputStream(outputFile);
                    InputStream is1 = (InputStream) urlConnection.getInputStream();
                    int bytes = 0;
                    byte[] buffer = new byte[1024];
                    int len1 = 0;

                    while ((len1 = is1.read(buffer)) != -1) {
                        bytes = (bytes + len1);
                        fos.write(buffer, 0, len1);
                    }
                    fos.close();
                    is1.close();
                }
            }
        } catch (FileNotFoundException e) {
            Crashlytics.logException(e);
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            Crashlytics.logException(e);
            e.printStackTrace();
        } catch (IOException e) {
            Crashlytics.logException(e);
            e.printStackTrace();
        }
    }


}
