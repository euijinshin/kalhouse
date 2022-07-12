package com.example.myapplication.activities.leave;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.main.JsonPlaceHolderApi;
import com.example.myapplication.main.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WaitRoomActivity extends AppCompatActivity {

    private FloatingActionButton add_btn;
    Button myroom;
    String strUrl, strName, strDetails;
    ArrayList<RoomData> list;
    WaitRoomAdapter adapter;
    RecyclerView recyclerView;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    List<RoomData> qqlist;

    public static Context mContext;

//    List<Integer> otherList;


    public int user_id;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_room);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        list = new ArrayList<>();
        initView();
        initIntent();

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.158:443/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        jsonPlaceHolderApi = retrofit.create(com.example.myapplication.main.JsonPlaceHolderApi.class);

        setValue();
        gets();

        mContext = this;


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            strUrl = intent.getStringExtra("image");
            strName = intent.getStringExtra("roomName");
            strDetails = intent.getStringExtra("details");

            if (intent != null) {
                RoomData roomData2 = new RoomData(strUrl, strName, strDetails, user_id);
                list.add(roomData2);
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void initView() {
        recyclerView = findViewById(R.id.rvWaitRoom);
        add_btn = (FloatingActionButton) findViewById(R.id.create_btn);
        myroom = findViewById(R.id.myroom);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
    }

    public void setValue() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new WaitRoomAdapter(WaitRoomActivity.this, list);
        recyclerView.setAdapter(adapter);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WaitRoomActivity.this, CreateRoomActivity.class);
                intent.putExtra("user_id", user_id);
                startActivityForResult(intent, 1234);
            }
        });

        myroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WaitRoomActivity.this, MyRoomActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                if (!qqlist.isEmpty()) list = (ArrayList<RoomData>) qqlist;
                gets();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void initIntent() {
        Intent intent = getIntent();
        user_id =  intent.getIntExtra("user_id", 0);
    }

    private void gets() {
        jsonPlaceHolderApi.gets(user_id).enqueue(new Callback<List<RoomData>>() {
            @Override
            public void onResponse(Call<List<RoomData>> call, Response<List<RoomData>> response) {
                if(response.isSuccessful()) {
                   Log.d("ddddd", "ddd");
                   qqlist = response.body();
                   adapter.setItems(response.body());
                   //other_id 를 넣는 변수

                }
                else {
                    Log.d("sss", "sss");
                }
            }

            @Override
            public void onFailure(Call<List<RoomData>> call, Throwable t) {
                Log.d("CCCCCCCCCC", t.getMessage());
            }
        });
    }

    //그 변수에다가 참가자의 id를 넣고 이걸 post 보내자
    //보낼때 서버에서 컬럼 데이터 변경을 해서 넣자
}