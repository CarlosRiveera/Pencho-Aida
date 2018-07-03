package com.ionicframework.penchoyaida233650.podacast_exclusivos.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Alex on 11/05/2016.
 */
public class reproductor_podcastExclusivos extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Podcast Exclusivos");
    }

}
