package com.example.myapplication.main;

import com.example.myapplication.activities.leave.RoomData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("get")
        //Call<com.example.myapplication.main.Post> getPosts(@Query("name") String name);
    Call<List<Post>> getPosts(@Query("name") String name);

    @GET("get/id")
    Call<List<String>> getId(@Query("id") int id);

    @GET("get/room")
    Call<List<RoomData>> gets(@Query("id") int id);
//
//    @GET("get/other_id")
//    Call<Integer> getOtherId(@Query("user_id") int id);

    @GET("get/myroom")
    Call<List<RoomData>> getMyroom(@Query("id") int id);

    @GET("get/user_id")
    Call<List<RoomData>> getRoom(@Query("user_id") int id);
    @POST("post/room")
    Call<String> posts(@Body RoomData roomData);

    @FormUrlEncoded
    @POST("post/other_id")
    Call<String> postOtherId(@Field("user_id") Integer id, @Field("id") Integer room_id);

    @GET("get/other_id")
    Call<List<RoomData>> getOtherId(@Query("user_id") int id);

    @POST("post")
    Call<String> createPosts(@Body Post post );

}