package com.example.singh.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SeekBar timerSeekBar;
    private TextView timertextView;
    private boolean counterIsActive;
    private Button controllerButton;
    private CountDownTimer countDownTimer;

    public void resetTimer(){
        timertextView.setText("0:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        controllerButton.setText("GO!");
        timerSeekBar.setEnabled(true);
        counterIsActive = false;
    }
     public void updateTimer(int progress){
         int minutes = (int) progress/60;
         int secondsLeft = progress - (minutes*60);
         String secondString = Integer.toString(secondsLeft);
         if( secondsLeft <=9){
             secondString = "0"+secondString;
         }
         timertextView.setText(Integer.toString(minutes)+" : "+secondString);
     }
    public void controlTimer(View view) {
         if(counterIsActive == false) {
             counterIsActive = true;
             timerSeekBar.setEnabled(false);
             controllerButton.setText("Stop");

             countDownTimer = new CountDownTimer((timerSeekBar.getProgress() * 1000) + 100, 1000) {

                 @Override
                 public void onTick(long millisUntilFinished) {
                     updateTimer((int) millisUntilFinished / 1000);
                 }

                 @Override
                 public void onFinish() {
                     MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                     mediaPlayer.start();
                     resetTimer();
                 }
             }.start();
         } else {
             resetTimer();
         }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        timertextView = (TextView)findViewById(R.id.timerTextView);
        controllerButton = (Button)findViewById(R.id.controllerButton);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
               updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
