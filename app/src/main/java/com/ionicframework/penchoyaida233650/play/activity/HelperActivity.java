package com.ionicframework.penchoyaida233650.play.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

import com.hugomatilla.audioplayerview.AudioPlayerView;

import com.ionicframework.penchoyaida233650.play.fragment.FragmentPrincipal;

/**
 * Created by Santiago on 09/05/2016.
 */
public class HelperActivity extends Activity {

    private HelperActivity ctx;


    AudioPlayerView audioPlayerView;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        ctx = this;
        FragmentPrincipal fragmentPrincipal = new FragmentPrincipal();

        String action = (String) getIntent().getExtras().get("DO");
        if (action.equals("play")) {
            /*fragmentPrincipal.audioPlayerView = (AudioPlayerView) findViewById(R.id.play);

            fragmentPrincipal.audioPlayerView.destroy();*/
            //Your code
        } else if (action.equals("volume")) {
            //Your code
        } else if (action.equals("stop")) {
            Toast.makeText(HelperActivity.this, "Audio finished callback", Toast.LENGTH_SHORT).show();
            //Your code
        }

        if (!action.equals("reboot")){
            finish();
        }



    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
