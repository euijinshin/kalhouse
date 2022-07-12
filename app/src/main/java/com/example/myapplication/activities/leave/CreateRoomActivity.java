package com.example.myapplication.activities.leave;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.main.JsonPlaceHolderApi;
import com.example.myapplication.main.Post;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CreateRoomActivity extends AppCompatActivity {

    ImageView create_profile;
    TextView create_name, create_details; // user name and details
    TextView roomName, exitTime, fullMember; // room details
    TextView createConfirm, createCancel; // buttons

    int user_id;

    public static RoomData roomData;

    String user_name, user_img;

    private JsonPlaceHolderApi jsonPlaceHolderApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.158:443/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        jsonPlaceHolderApi = retrofit.create(com.example.myapplication.main.JsonPlaceHolderApi.class);

        initView();
        initIntent();
        getByUserId();

        if (user_id <= -1) Toast.makeText(this, "userid not passed", Toast.LENGTH_SHORT);

        Toast.makeText(this, Integer.toString(user_id), Toast.LENGTH_SHORT);
//        create_profile.setImageResource();
//        create_name.setText();
//        create_details.setText();

        createConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posts();
                String details = exitTime.getText().toString();
//                roomData = new RoomData("url", roomName.getText().toString(), details);
                Intent intent = new Intent();
//                intent.putExtra("roomData", roomData);
                intent.putExtra("image", "url");
                intent.putExtra("roomName", roomName.getText().toString());
                intent.putExtra("details", details);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        createCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void initView() {
        create_profile = findViewById(R.id.create_profile);
        create_name = findViewById(R.id.create_name);
//        create_details = findViewById(R.id.create_details);

        roomName = findViewById(R.id.roomName);
        exitTime = findViewById(R.id.exitTime);
        fullMember = findViewById(R.id.fullMember);

        createConfirm = findViewById(R.id.createConfirm);
        createCancel = findViewById(R.id.createCancel);
    }

    public void initIntent() {
        Intent intent = getIntent();
        user_id =  intent.getIntExtra("user_id", -1);
    }

    private void posts() {

        RoomData roomData = new RoomData(user_img, roomName.getText().toString(), exitTime.getText().toString(), user_id);

        Call<String> call = jsonPlaceHolderApi.posts(roomData);

        Log.d("lllllll", user_img);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d("SSSSSSSS", "Success");
                }
                if(response.body() == null)
                    return;
            }
            @Override
            public void onFailure(Call<String> call, Throwable t){
                Log.d("SSSSSSSS", "Failure");
            }
        });
    }

    private void getByUserId() {
        jsonPlaceHolderApi.getId(user_id).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful()) {
                    List<String> qqlist = response.body();
                      user_img = qqlist.get(0);
                      user_name = qqlist.get(1);

                    // 닉네임, email, details set
                    create_name.setText(user_name);

                    //프로필 이미지 사진 set
                    Glide.with(CreateRoomActivity.this).load(user_img).into(create_profile);
                }
                else {
                    Toast.makeText(CreateRoomActivity.this, "fuck you", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.d("CCCCCCCCCC", t.getMessage());
            }
        });
    }
}