package com.example.myapplication.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.main.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RuntimeActivity extends AppCompatActivity {

    TextView startTimeSet, endTimeSet, work_text;

    long start, stop, savedStart;
    Date dateStart, dateStop;
    Thread t;
    Boolean running = true;
    Boolean isStart = false;
    Boolean isStop = false;
    Button Start, Stop;

    String strTime;

    long time = 0;

    SimpleDateFormat passFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        SharedPreferences sharedPreferences = getSharedPreferences("sFile", MODE_PRIVATE);
        savedStart = sharedPreferences.getLong("start", 0);

        initView();

        if (savedStart != 0) {
            Start.setVisibility(View.GONE);
            Stop.setVisibility(View.VISIBLE);
            start = savedStart;
            dateStart = new Date(start);

            work_text.setText("몰입 중");

            startTimeSet.setText(passFormat.format(dateStart));
        }

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start = System.currentTimeMillis();

                dateStart = new Date(start);

                running = true;
                isStart = true;

                work_text.setText("몰입 중");

                Stop.setVisibility(View.VISIBLE);
                Start.setVisibility(View.GONE);

                startTimeSet.setText(passFormat.format(dateStart));

            }
        });

        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = false;

                stop = System.currentTimeMillis();

                dateStop = new Date(stop);

                time = dateStop.getTime() - dateStart.getTime() - 32399999;

                Date pass = new Date(time);
                String getTime = passFormat.format(pass);

                TextView textView = findViewById(R.id.tv_timeCounter);
                textView.setText(getTime);

                start = Long.valueOf(0);
                isStop = true;

                Stop.setVisibility(View.GONE);
                String workTime = passFormat.format(dateStop);

                work_text.setText("퇴근!");

                endTimeSet.setText(workTime);
//                endTimeSet.setText(strTime);

                Intent intent = new Intent();
                intent.putExtra("isStopSW", isStop);
                intent.putExtra("today", getTime);

                setResult(RESULT_OK, intent);

            }
        });

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (isStart) {
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);

                    long pass = date.getTime() - dateStart.getTime() - 32399999;

//                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    strTime = passFormat.format(pass);

                    TextView textView = findViewById(R.id.tv_timeCounter);
                    textView.setText(strTime);

                }

                if (savedStart != 0) {
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);

                    Date startDate = new Date(savedStart);

                    long pass = date.getTime() - startDate.getTime() - 32399999;

//                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    strTime = passFormat.format(pass);

                    TextView textView = findViewById(R.id.tv_timeCounter);
                    textView.setText(strTime);
                }
            }
        } ;

        // 새로운 스레드 실행 코드. 1초 단위로 현재 시각 표시 요청.
        class NewRunnable implements Runnable {
            @Override
            public void run() {
                while (running) {

                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace() ;
                    }

                    // 메인 스레드에 runnable 전달.
                    runOnUiThread(runnable) ;
                }
            }
        }

        NewRunnable nr = new NewRunnable() ;
        t = new Thread(nr) ;
        t.start();

    }

    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getSharedPreferences("sFile",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long save = start;
        editor.putLong("start", save);
        editor.commit();

        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void initView() {
        startTimeSet = findViewById(R.id.startWork);

        Start = findViewById(R.id.SWPlay);
        Stop = findViewById(R.id.SWStop);

        endTimeSet = findViewById(R.id.endWork);
        work_text = findViewById(R.id.work_text);
    }
}