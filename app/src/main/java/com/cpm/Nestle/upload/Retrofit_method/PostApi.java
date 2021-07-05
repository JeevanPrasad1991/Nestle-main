package com.cpm.Nestle.upload.Retrofit_method;


import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/*import retrofit.http.Body;
import retrofit.http.POST;*/


/**
 * Created by jeevanp on 16-05-2017.
 */

public interface PostApi {
    @POST("Login")
    Call<ResponseBody> getLogindetail(@Body RequestBody request);

    @POST("Uploadimages")
    Call<String> getUploadImage(@Body RequestBody request);

    @POST("DownloadJson")
    Call<ResponseBody> getDownloadAll(@Body RequestBody request);

    @POST("Coverage")
    Call<ResponseBody> getCoverageDetail(@Body RequestBody request);
    @POST("CoverageDetail")
    Call<ResponseBody> getCoverageDetailClient(@Body RequestBody request);

    @POST("UploadJson")
    Call<ResponseBody> getUploadJsonDetail(@Body RequestBody request);
    @POST("CoverageStatusDetail")
    Call<ResponseBody> getCoverageStatusDetail(@Body RequestBody request);
    @POST("CheckoutDetail")
    Call<ResponseBody> getCheckout(@Body RequestBody request);

    @POST("StoreCheckOutDBSR")
    Call<ResponseBody> getCheckoutClient(@Body RequestBody request);

    @POST("CreateJourneyPlan")
    Call<ResponseBody> getmidClient(@Body RequestBody request);

    @POST("DeleteCoverage")
    Call<ResponseBody> deleteCoverageData(@Body RequestBody request);

    @POST("CoverageNotAllow")
    Call<ResponseBody> setCoverageNonWorkingData(@Body RequestBody request);

    @POST("ChangePassword")
    Call<ResponseBody> setNewPassword(@Body RequestBody request);

    @retrofit2.http.POST("DownloadJson")
    Call<ResponseBody> getDownloadAllUSINGLOGIN(@Body RequestBody request);
    @POST("UploadJCP")
   Call<ResponseBody> getUploadJCPDetail(@Body RequestBody request);
    @POST("StoreCoverageDBSR")
    Call<ResponseBody> getCoverageDBSR(@Body RequestBody request);

    @POST("StoreCoverageStatusDBSR")
    Call<ResponseBody> getStoreCoverageStatusDBSR(@Body RequestBody request);
}
