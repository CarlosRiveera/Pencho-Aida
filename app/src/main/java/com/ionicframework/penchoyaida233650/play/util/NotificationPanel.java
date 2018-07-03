package com.ionicframework.penchoyaida233650.play.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.hugomatilla.audioplayerview.AudioPlayerView;

import com.ionicframework.penchoyaida233650.R;
import com.ionicframework.penchoyaida233650.play.activity.NotificationReturnSlot;

/**
 * Created by Santiago on 09/05/2016.
 */
public class NotificationPanel {

    private Context parent;
    private NotificationManager nManager;
    private NotificationCompat.Builder nBuilder;
    private RemoteViews remoteView;
    AudioPlayerView audioPlayerView;

    public NotificationPanel(final Context parent, AudioPlayerView audioPlayerView) {
        // TODO Auto-generated constructor stub
        this.parent = parent;
        this.audioPlayerView = audioPlayerView;
        nBuilder = new NotificationCompat.Builder(parent)
                .setContentTitle("Parking Meter")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true);

        remoteView = new RemoteViews(parent.getPackageName(), R.layout.notificationview);

        //set the button listeners

        audioPlayerView.setOnAudioPlayerViewListener(new AudioPlayerView.OnAudioPlayerViewListener() {
            @Override
            public void onAudioPreparing() {
                Toast.makeText(parent, "Audio is loading callback", Toast.LENGTH_SHORT).show();
                // spinner.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAudioReady() {
                Toast.makeText(parent, "Audio is ready callback", Toast.LENGTH_SHORT).show();
                // spinner.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAudioFinished() {
                Toast.makeText(parent, "Audio finished callback", Toast.LENGTH_SHORT).show();
                notificationCancel();
            }
        });

        setListeners(remoteView);
        nBuilder.setContent(remoteView);

        nManager = (NotificationManager) parent.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(2, nBuilder.build());
    }

    public void setListeners(RemoteViews view){

        Toast.makeText(parent, "volume-----------------", Toast.LENGTH_SHORT).show();
        //listener 1
        Log.i("okkkkkkk","------------------");
        Intent play = new Intent(parent,NotificationReturnSlot.class);
        play.putExtra("DO", "play");
        PendingIntent btn3 = PendingIntent.getActivity(parent, 0, play, 0);
        view.setOnClickPendingIntent(R.id.btn3, btn3);


        //listener 1
        Intent volume = new Intent(parent,NotificationReturnSlot.class);
        volume.putExtra("DO", "volume");
        PendingIntent btn1 = PendingIntent.getActivity(parent, 0, volume, 0);
        view.setOnClickPendingIntent(R.id.btn1, btn1);

        //listener 2
        Intent stop = new Intent(parent, NotificationReturnSlot.class);
        stop.putExtra("DO", "stop");
        PendingIntent btn2 = PendingIntent.getActivity(parent, 1, stop, 0);
        view.setOnClickPendingIntent(R.id.btn2, btn2);
    }

    public void notificationCancel() {
        nManager.cancel(2);
    }
}