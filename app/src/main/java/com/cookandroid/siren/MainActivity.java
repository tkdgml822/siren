package com.cookandroid.siren;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // 최대 시간 (30초)
    static final int MAXIMUM_SECONDS = 30000;
    private TextView timerTextView;
    private Button startButton;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        mediaPlayer = MediaPlayer.create(this, R.raw.sound);

        // 클릭 이벤트
        startButton.setOnClickListener(view -> {
            startSound();
            startButton.setEnabled(false);
        });
    }

    private void startSound() {
        // 무한 반복
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        // 30초 후 무한 재생 취소
        startButton.postDelayed(() -> {
            mediaPlayer.setLooping(false); // 무한 반복 해제
            mediaPlayer.pause(); // 사운드 재생 중지
            startButton.setEnabled(true);
        }, MAXIMUM_SECONDS);

        // 타이머 시작
        startTimer();
    }

    private void startTimer() {
        // new CountDownTimer(타이머 총 시간, 간격)
        new CountDownTimer(MAXIMUM_SECONDS, 1000) {
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

    // Activity가 호출하는 마지막 메소드(마지막에 실행하는 함수)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}