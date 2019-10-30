package com.unlogicon.bubbleface.interfaces.api;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RestApi {
    @Multipart
    @POST("_api/face")
    Observable<ResponseBody> magic(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part image);
}
