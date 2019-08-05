package com.lorealbaautomation.retrofit;

import com.google.gson.JsonObject;
import com.lorealbaautomation.constant.CommonString;

import org.json.JSONObject;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


/**
 * Created by jeevanp on 19-05-2017.
 */


//using interface for post data
public interface PostApi {
    @POST(CommonString.KEY_LOGIN_DETAILS)
    Call<ResponseBody> getLogindetail(@Body RequestBody request);

    @POST("DownloadJson")
    Call<String> getDownloadAll(@Body RequestBody request);

    @POST("DownloadJson")
    Call<ResponseBody> getDownloadAllUSINGLOGIN(@Body RequestBody request);


    @POST("CoverageDetail")
    Call<ResponseBody> getCoverageDetail(@Body RequestBody request);

    @POST("SendOTP")
    Call<ResponseBody> getOTPMethod(@Body RequestBody request);

    @POST("UploadJCPDetail")
    Call<ResponseBody> getUploadJCPDetail(@Body RequestBody request);

    @POST("UploadJson")
    Call<ResponseBody> getUploadJsonDetail(@Body RequestBody request);

    @POST("CoverageStatusDetail")
    Call<ResponseBody> getCoverageStatusDetail(@Body RequestBody request);

    @POST("Upload_StoreGeoTag_IMAGES")
    Call<ResponseBody> getGeoTagImage(@Body RequestBody request);

    @POST("CheckoutDetail")
    Call<ResponseBody> getCheckout(@Body RequestBody request);

    @POST("DeleteCoverage")
    Call<ResponseBody> deleteCoverageData(@Body RequestBody request);

    @POST("CoverageNonworking")
    Call<ResponseBody> setCoverageNonWorkingData(@Body RequestBody request);

    @POST("Uploadimageswithpath")
    retrofit.Call<String> getUploadDataBaseBackup(@Body RequestBody body1);
    @POST("UploadJsonDetail")
    Call<JsonObject> getGeotag(@Body RequestBody request);
    @POST("TokenDetail")
    Call<ResponseBody> uploadTokenDetails(@Body RequestBody request);


    @POST("UploadJsonDetail")
    Call<JSONObject> getUploadJsonDetailForFileList(@Body RequestBody request);
    @POST("UploadAttendanceDetail")
    Call<ResponseBody> getAttendanceDetails(@Body RequestBody request);
}

