package com.ydhnwb.resepmaumvp.webservices

import com.ydhnwb.resepmaumvp.models.Post
import com.ydhnwb.resepmaumvp.models.User
import com.ydhnwb.resepmaumvp.responses.WrappedListResponse
import retrofit2.Call
import retrofit2.http.*
import com.ydhnwb.resepmaumvp.responses.WrappedResponse
import retrofit2.http.PUT
import retrofit2.http.FormUrlEncoded
import retrofit2.http.DELETE
import retrofit2.http.GET

interface APIService {
    @GET("api/post")
    fun all(@Header("Authorization") token : String) : Call<WrappedListResponse<Post>>

    @FormUrlEncoded
    @POST("api/post")
    fun create(@Header("Authorization") token : String, @Field("title") title : String, @Field("content") content : String) : Call<WrappedResponse<Post>>

    @GET("api/post/{id}")
    fun find(@Header("Authorization") token: String, @Path("id") id: String): Call<WrappedResponse<Post>>

    @DELETE("api/post/{id}")
    fun delete(@Header("Authorization") token: String, @Path("id") id: String): Call<WrappedResponse<Post>>

    @FormUrlEncoded
    @PUT("api/post/{id}")
    fun update(@Header("Authorization") token: String, @Path("id") id: String, @Field("title") title: String, @Field("content") content: String): Call<WrappedResponse<Post>>

    @FormUrlEncoded
    @POST("api/login")
    fun login(@Field("email") email : String, @Field("password") password : String) : Call<WrappedResponse<User>>

    @FormUrlEncoded
    @POST("api/register")
    fun register(@Field("name") name : String, @Field("email") email : String, @Field("password") password : String) : Call<WrappedResponse<User>>
}