package com.example.myapplication.activities.leave;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.main.JsonPlaceHolderApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LeaveActivity extends AppCompatActivity {

    private String host, roomName, roomDetail, imageUrl;

    int roomId, user_id;
    Integer other_id;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    List<RoomData> qqlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

//        posts();
//          gets();


        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.158:443/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        jsonPlaceHolderApi = retrofit.create(com.example.myapplication.main.JsonPlaceHolderApi.class);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        roomId = intent.getIntExtra("roomId", 0);
        user_id = intent.getIntExtra("user_id", 0);

        getMyroom();

        TextView join_btn = findViewById(R.id.leave_join);
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postOtherId();
                join_btn.setVisibility(View.GONE);
            }
        });

    }

//    private void gets() {
//        jsonPlaceHolderApi.gets(roomId).enqueue(new Callback<List<RoomData>>() {
//            @Override
//            public void onResponse(Call<List<RoomData>> call, Response<List<RoomData>> response) {
//                if(response.isSuccessful()) {
//                    qqlist = response.body();
//

//
//

//
//                    TextView details = findViewById(R.id.leave_details);
//                    details.setText(roomDetail);
//
//
//                }
//                else {
//                    Log.d("sss", "sss");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<RoomData>> call, Throwable t) {
//                Log.d("CCCCCCCCCC", t.getMessage());
//            }
//        });
//    }

//    private void posts() {
//
//        RoomData roomData = new RoomData(user_id);
//
//        Call<String> call = jsonPlaceHolderApi.posts(roomData);
//
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful()) {
//                    Log.d("SSSSSSSS", "Success");
//                }
//                if(response.body() == null)
//                    return;
//            }
//            @Override
//            public void onFailure(Call<String> call, Throwable t){
//                Log.d("SSSSSSSS", "Failure");
//            }
//        });
//    }


    private void getMyroom() {
        jsonPlaceHolderApi.getMyroom(roomId).enqueue(new Callback<List<RoomData>>() {
            @Override
            public void onResponse(Call<List<RoomData>> call, Response<List<RoomData>> response) {
                if (response.isSuccessful()) {
                    Log.d("ddddd", "ddd");
                    qqlist = response.body();

                    roomName = qqlist.get(0).getRoomName();
                    roomDetail = qqlist.get(0).getRoomDetails();
                    imageUrl = qqlist.get(0).getImageUrl();
                    host = qqlist.get(0).getHostName();
                    other_id = qqlist.get(0).getOtherId();


                    TextView hostname = findViewById(R.id.leave_host);
                    hostname.setText(host);

                    TextView details = findViewById(R.id.leave_details);
                    details.setText(roomDetail);

                    ImageView imageView = findViewById(R.id.leave_img);
                    Glide.with(LeaveActivity.this).load(imageUrl).into(imageView);

                    TextView roomname = findViewById(R.id.leave_roomname);
                    roomname.setText(roomName);

                    if (other_id != null) {
                        TextView join_btn = findViewById(R.id.leave_join);
                        join_btn.setVisibility(View.GONE);
                        TextView full = findViewById(R.id.leave_unavail);
                        full.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RoomData>> call, Throwable t) {
                Log.d("CCCCCCCCCC", t.getMessage());
            }
        });
    }

    //room 데이터 베이스에 other_id 값 삽입
    private  void postOtherId(){

        jsonPlaceHolderApi.postOtherId(user_id, roomId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }

        });
    }

}
