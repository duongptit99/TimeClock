package com.example.timeclock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btnStart, btnPause, btnStop;
    private TextView txtTime;
    private long timeStart, timePause, timeSystem = 0;
    private boolean check;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            timeSystem = SystemClock.uptimeMillis() - timeStart;
            long timeUpdate = timePause + timeSystem;
            long secs = (long) timeUpdate / 1000;
            long mins = secs / 60;
            secs = secs % 60;
            long milliseconds = (long) timeUpdate % 1000;
            txtTime.setText("" + String.format("%02d", mins) + ":" + String.format("%02d", secs) + ":" + String.format("%02d", milliseconds));
            handler.postDelayed(this, 0);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnStart.setOnClickListener(v -> {
            clickStart();
        });
        btnPause.setOnClickListener(v -> {
            clickPause();
        });
        btnStop.setOnClickListener(v -> {
            clickStop();
        });


    }

    public void init() {
        btnStart = findViewById(R.id.btn_start);
        btnPause = findViewById(R.id.btn_pause);
        btnStop = findViewById(R.id.btn_stop);

        txtTime = findViewById(R.id.txt_time);
    }

    public void clickStart() {
        if (check) return;
        check = true;
        timeStart = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);
    }

    public void clickStop() {
        if (!check) {
            txtTime.setText("00:00:00");
            timePause = 0;
            return ;
        }
        check = false;
        timePause = 0;
        txtTime.setText("00:00:00");
        handler.removeCallbacks(runnable);

    }

    public void clickPause() {

        if (!check) return;
        check = false;
        timePause += timeSystem;
        handler.removeCallbacks(runnable);

    }
}