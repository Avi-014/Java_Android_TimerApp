package com.example.timerapp;

import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView OutputTimer;
    EditText InputTimer;
    boolean Active=false;
    Button StartButton;
    CountDownTimer countDownTimer;
    public void reset(){
        OutputTimer.setText("00:00");
        countDownTimer.cancel();
        StartButton.setText("START");
        Active=false;
    }

    public void Start(View view) {
        if (Active) {
            reset();
        } else {
            Active=true;
            StartButton.setText("RESET");
            countDownTimer = new CountDownTimer(Integer.parseInt(InputTimer.getText().toString()) * 1000L, 1000) {
                @Override
                public void onTick(long l) {
                    UpdateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    ImageView horn = findViewById(R.id.Horn);
                    horn.setVisibility(View.VISIBLE);
                    ObjectAnimator rotate = ObjectAnimator.ofFloat(horn, "Rotation", 0f, 20f, 0f, -20f, 0f);
                    rotate.setRepeatCount(20);
                    rotate.setDuration(100);
                    rotate.start();

                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mediaPlayer.start();
                    reset();
                }
            }.start();
        }
    }
    public void UpdateTimer(int SecondsLeft){
        int min = SecondsLeft/60;
        int sec = SecondsLeft - (min*60);
        String Seconds = Integer.toString(sec);
        String Minutes = Integer.toString(min);
        if (sec<=9){
            Seconds = "0"+Seconds;
        }
        if (min<=9){
            Minutes = "0"+Minutes;
        }
        OutputTimer.setText(Minutes + ":" + Seconds);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InputTimer = findViewById(R.id.Input);
        StartButton= findViewById(R.id.StartButton);
        OutputTimer = findViewById(R.id.Output);
    }
}