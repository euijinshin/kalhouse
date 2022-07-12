package com.example.myapplication.activities.leave;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.main.JsonPlaceHolderApi;
import com.example.myapplication.main.MainActivity;
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

public class MyRoomActivity extends AppCompatActivity {

    ArrayList<JoinMember> list;
    MyRoomAdapter adapter;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    Integer user_id, other_id, room_id;
    List<RoomData> qqlist;
    String roomName, imgUrl, roomDetails, other_gender, other_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_room);


        Intent secondIntent = getIntent();
        user_id = secondIntent.getIntExtra("user_id", 0);

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.158:443/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        jsonPlaceHolderApi = retrofit.create(com.example.myapplication.main.JsonPlaceHolderApi.class);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        LinearLayout yesno = findViewById(R.id.yesno);

        TextView reject = findViewById(R.id.reject);

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set에서 user_id를 취소할 수 있게
                postOtherId();
                finish();
            }
        });

        getOtherId();
    }

    private void getOtherId() {
        jsonPlaceHolderApi.getOtherId(user_id).enqueue(new Callback<List<RoomData>>() {
            @Override
            public void onResponse(Call<List<RoomData>> call, Response<List<RoomData>> response) {
                qqlist = response.body();

                if (qqlist.size() == 0) {
//                    other_id = -1;

                }
                else{
                    roomName = qqlist.get(0).getRoomName();
                    room_id = qqlist.get(0).getRoomId();
                    roomDetails = qqlist.get(0).getRoomDetails();
                    other_id = qqlist.get(0).getOtherId();
                    other_gender = qqlist.get(0).getHostGender();
                    other_name = qqlist.get(0).getHostName();
                    imgUrl = qqlist.get(0).getHostImg();

                    TextView details = findViewById(R.id.myroomDetails);
                    details.setText(roomDetails);

                    TextView roomname = findViewById(R.id.myroomName);
                    roomname.setText(roomName);

                    ImageView imageView = findViewById(R.id.people_img);
                    Glide.with(MyRoomActivity.this).load(imgUrl).into(imageView);

                    TextView joinname = findViewById(R.id.people_name);
                    joinname.setText(other_name);

                    TextView joingender = findViewById(R.id.people_detail);
                    joingender.setText(other_gender);

                    TextView reject = findViewById(R.id.reject);
                    reject.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onFailure(Call<List<RoomData>> call, Throwable t) {
                Log.d("CCCCCCCCCC", t.getMessage());
            }
        });
    }

    private  void postOtherId(){

        jsonPlaceHolderApi.postOtherId(null, room_id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }

        });
    }
}