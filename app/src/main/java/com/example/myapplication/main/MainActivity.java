package com.example.myapplication.main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.activities.MapFragment;
import com.example.myapplication.activities.RuntimeActivity;
import com.example.myapplication.activities.leave.CreateRoomActivity;
import com.example.myapplication.activities.leave.LeaveActivity;
import com.example.myapplication.activities.leave.MyRoomActivity;
import com.example.myapplication.activities.leave.WaitRoomActivity;
import com.example.myapplication.activities.MapActivity;
import com.example.myapplication.R;
import com.example.myapplication.main.notifications.NotificationActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    private TextView text;


    private String strNick, strProfileImg, strEmail, strAge, strGender;
    TextView tv_maincounter, tv_nick, tv_email, tv_details;
    ImageView iv_profile;
    Button leave_btn, map_btn, notif_btn, time_btn, logout_btn;

    private int user_id;

    private boolean running = false;
    private boolean isrunning = false;
    private boolean unbind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initIntent();
        initView();

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.158:443/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        jsonPlaceHolderApi = retrofit.create(com.example.myapplication.main.JsonPlaceHolderApi.class);

        setValues();

        Fragment fragment = new MapFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, fragment).commit();


        // 닉네임, email, details set
        tv_nick.setText(strNick);
//        tv_email.setText(strEmail);
//        tv_details.setText(strAge + ", " + strGender);

        //프로필 이미지 사진 set
        Glide.with(this).load(strProfileImg).into(iv_profile);

        createPosts();

        getPosts();

    }

    // StopWatch function
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            Boolean isStop = intent.getBooleanExtra("isStopSW", false);
            String workTime = intent.getStringExtra("today");

            if (isStop) {
//                Date pass = new Date(time);
//                SimpleDateFormat passFormat = new SimpleDateFormat("hh:mm:ss");
//                String getTime = passFormat.format(pass);

                TextView textView = findViewById(R.id.tv_mainCounter);
                textView.setText(workTime);

//                LinearLayout linearLayout = findViewById(R.id.linear_worktime);
//                linearLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    public void initView() {
        // Buttons
        leave_btn = (Button) findViewById(R.id.leave_btn);
        map_btn = findViewById(R.id.map_btn);
        notif_btn = findViewById(R.id.notif_btn);
        time_btn = findViewById(R.id.work_btn);
        logout_btn = findViewById(R.id.logout_btn);
        //profile set
        tv_nick = findViewById(R.id.tv_nickname);
//        tv_email = findViewById(R.id.tv_email);
//        tv_details = findViewById(R.id.details);
        iv_profile = findViewById(R.id.iv_profile);
        tv_maincounter = findViewById(R.id.tv_mainCounter);
//        text = findViewById(R.id.text);
    }

    public void initIntent() {
        Intent intent = getIntent();
        // Profile get from kakao
        strNick = intent.getStringExtra("name");
        strProfileImg = intent.getStringExtra("profileImg");
//        strEmail = intent.getStringExtra("email");
        strGender = intent.getStringExtra("gender");
        strAge = "20";//intent.getStringExtra("age");

        if (strAge == null) strAge = "20";
    }

    public void setValues() {
        // set button OnClick
        leave_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), WaitRoomActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });

        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });

        notif_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });

        time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RuntimeActivity.class);
                intent.putExtra("user_id", user_id);
                startActivityForResult(intent, 1111);
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        //로그아웃 성공시 수행하는 지점
                        finish(); //현재 액티비티 종료
                    }
                });
            }
        });
    }

    private void createPosts() {
        Post post = new Post(strNick, strProfileImg, strGender);

        Call<String> call = jsonPlaceHolderApi.createPosts(post);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
//                text.setText("success");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                text.setText("실패");
                Log.d("AAAAAAAAAAAA ", t.getMessage());
//                Toast.makeText(this, "failed to get info", Toast.LENGTH_SHORT);
            }
        });
    }
//
//
    private void getPosts() {


        jsonPlaceHolderApi.getPosts(strNick).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()) {
                    List<Post> qqlist = response.body();
//                    Log.d("BBBBBBBBBBBBB", "BBBBBBBBBBBB");
//                    for(int i = 0; i < qqlist.size(); i++) {
//                        Log.d("AAAAAAAAA ", Integer.toString(qqlist.get(i).getId()));
//                    }
                    user_id = qqlist.get(0).getId();
                    strGender = qqlist.get(0).getGender();
//                    text.setText(Integer.toString(user_id));
                }
                else {
                    Toast.makeText(MainActivity.this, "not connected", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d("CCCCCCCCCC", t.getMessage());
            }
        });
    }
}

