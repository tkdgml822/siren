package com.cookandroid.siren;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView timerTextView;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);

        // 클릭 이벤트
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                startButton.setEnabled(false);
            }
        });
    }

    private void startTimer() {
        // new CountDownTimer(타이머 총 시간, 간격)
        new CountDownTimer(30000, 1000) {
            // onTick() :  간격마다 호출됨
            @Override
            public void onTick(long millisUntilFinished) {
                // 남은 초 구하기
                long secondsRemaining = millisUntilFinished / 1000;
                timerTextView.setText(String.valueOf(secondsRemaining));
            }

            // 타이머 종료 이벤트
            @Override
            public void onFinish() {
                timerTextView.setText("0");
                startButton.setEnabled(true);
            }
        }.start();
    }
}