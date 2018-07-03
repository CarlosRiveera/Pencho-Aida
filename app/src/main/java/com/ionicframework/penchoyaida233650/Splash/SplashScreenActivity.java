package com.ionicframework.penchoyaida233650.Splash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import com.ionicframework.penchoyaida233650.MainActivity;
import com.ionicframework.penchoyaida233650.R;
import com.ionicframework.penchoyaida233650.play.util.PausaPlay;
import tyrantgit.explosionfield.ExplosionField;

/**
 * Created by Santiago on 19/05/2016.
 */
public class SplashScreenActivity extends ActionBarActivity {

    private long splashDelay = 5000; //2 segundos
    DilatingDotsProgressBar mDilatingDotsProgressBar;
    PausaPlay pausaPlay;
    ImageView iv;
    CountDownTimer timer1;
    //View v;
   // private ExplosionField mExplosionField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mDilatingDotsProgressBar = (DilatingDotsProgressBar) findViewById(R.id.progress);
        StartAnimations();
        //setupWindowAnimations();
        pausaPlay = new PausaPlay();
        //mExplosionField = ExplosionField.attach2Window(this);
      //   pausaPlay.pausa4(SplashScreenActivity.this);
       iv = (ImageView) findViewById(R.id.logo);
       // repetir();
       /* iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mExplosionField.explode(v);
                return false;
            }
        });
         iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExplosionField.explode(v);
            }
        });*/




        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //mExplosionField.explode(v);
                VerifyUserData(SplashScreenActivity.this);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, splashDelay);
    }

    public void VerifyUserData(Context context) {


        Intent i = new Intent(context, MainActivity.class);
        i.putExtra("valor", "0");
        startActivity(i);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        finish();
    }

    private void StartAnimations() {

        mDilatingDotsProgressBar.showNow();
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        RelativeLayout l = (RelativeLayout) findViewById(R.id.relative);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(anim);



    }


    public void repetir() {
        timer1 = new CountDownTimer(7000, 1000) {
            @SuppressLint("NewApi")
            public void onTick(long millisUntilFinished) {
                Log.i("timer", "seconds remaining: " + millisUntilFinished
                        / 1000);
                if(millisUntilFinished/1000==1){
                    iv.setImageResource(R.mipmap.splash_pencho);
                }else if(millisUntilFinished/1000==2){
                    iv.setImageResource(R.mipmap.splash_pencho2);
                }else if(millisUntilFinished/1000==3){
                    iv.setImageResource(R.mipmap.splash_pencho1);
                }else if(millisUntilFinished/1000==4){
                    iv.setImageResource(R.mipmap.splash_pencho3);
                }else if(millisUntilFinished/1000==5){
                    iv.setImageResource(R.mipmap.splash_pencho);
                }else if(millisUntilFinished/1000==6){
                    iv.setImageResource(R.mipmap.splash_pencho2);
                }else if(millisUntilFinished/1000==7){
                    iv.setImageResource(R.mipmap.splash_pencho1);
                }else{
                    iv.setImageResource(R.mipmap.splash_pencho);
                }

            }
            public void onFinish() {

            }
        }.start();
    }


}

