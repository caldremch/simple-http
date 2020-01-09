package com.caldremch

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * @author Caldremch
 * @date 2019-02-22 17:51
 * @describe
 */
interface Api {
    @GET
    operator fun get(@Url url: String, @QueryMap maps: Map<String, Any>): Observable<ResponseBody>

    @GET
    operator fun get(@Url url: String): Observable<ResponseBody>

    @POST
    fun post(@Url url: String, @Body requestBody: RequestBody): Observable<ResponseBody>

    @POST
    fun postQuery(@Url url: String, @QueryMap maps: Map<String, Any>): Observable<ResponseBody>

    @POST
    fun post(@Url url: String): Observable<ResponseBody>

    @FormUrlEncoded
    @POST
    fun post(@Url url: String, @FieldMap requestData: Map<String, Any>): Observable<ResponseBody>

    @Multipart
    @POST("File/upload")
    fun upload(@PartMap requestData: Map<String, Any>): Observable<ResponseBody>

    @Multipart
    @POST("File/upload")
    fun upload(@Part parts: List<MultipartBody.Part>): Observable<ResponseBody>

    @Streaming
    @GET
    fun downloadImage(@Url url: String): Call<ResponseBody>
}