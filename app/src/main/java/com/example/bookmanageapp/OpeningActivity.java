package com.example.bookmanageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class OpeningActivity extends AppCompatActivity {

    private ImageView mOpenScreenImg;
    private AnimationDrawable mAD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        mOpenScreenImg = findViewById(R.id.open_screen_img);
        mOpenScreenImg.setBackgroundResource(R.drawable.open_animation);
        mAD = (AnimationDrawable) mOpenScreenImg.getBackground();
        mAD.start();

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        };

        Timer opening = new Timer();
        opening.schedule(tt,5000);
    }
}