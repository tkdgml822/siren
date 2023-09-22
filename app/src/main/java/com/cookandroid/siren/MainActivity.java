package com.cookandroid.siren;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // 최대 시간 (30초)
    static final int MAXIMUM_SECONDS = 30000;
    private TextView timerTextView;
    private Button startButton, endButton;
    private MediaPlayer mediaPlayer;

    private CountDownTimer countDownTimer;

    long secondsRemaining;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        endButton = findViewById(R.id.endButton);
        mediaPlayer = MediaPlayer.create(this, R.raw.sound); // 사운드 변수

        // 클릭 이벤트
        startButton.setOnClickListener(view -> {
            startSound();
            startButton.setEnabled(false);
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }

                // 남은 시간 변수 초기화
                secondsRemaining = MAXIMUM_SECONDS;

                // 타이머 텍스트 초기화 (예: 다시 30초로 설정)
                timerTextView.setText(String.valueOf(MAXIMUM_SECONDS / 1000));

                mediaPlayer.setLooping(false);
                mediaPlayer.pause();

                // 시작 버튼 활성화
                startButton.setEnabled(true);
            }
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
        countDownTimer = new CountDownTimer(MAXIMUM_SECONDS, 1000) {
            // onTick() :  간격마다 호출됨
            @Override
            public void onTick(long millisUntilFinished) {
                // 남은 초 구하기
                secondsRemaining = millisUntilFinished / 1000;
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