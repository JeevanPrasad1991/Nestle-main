package com.cpm.Nestle.upload.Retrofit_method;

/**
 * Created by deepakp on 10/4/2017.
 */

import com.squareup.okhttp.RequestBody;

import retrofit.http.Body;
import retrofit.http.POST;
import retrofit2.Call;

public interface PostApiForUpload {

    @POST("Uploadimages")
    retrofit.Call<String> getUploadImageRetrofitOne(@Body RequestBody body1);

   /* @retrofit2.http.POST("Uploadimages")
    Call<String> getUploadImage(@retrofit2.http.Body okhttp3.RequestBody request);*/

    @POST("Uploadimages")
    retrofit.Call<String> getUploadImages(@Body RequestBody body1);
}
