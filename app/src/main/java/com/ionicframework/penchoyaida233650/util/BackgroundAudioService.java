package com.ionicframework.penchoyaida233650.util;

/**
 * Created by santiago on 11/05/2016.
 */

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;

import java.io.IOException;

import com.ionicframework.penchoyaida233650.MainActivity;
import com.ionicframework.penchoyaida233650.NotificationService;
import com.ionicframework.penchoyaida233650.R;
import com.ionicframework.penchoyaida233650.newplay.util.Constants;

public class BackgroundAudioService extends Service implements OnCompletionListener {
    MediaPlayer mediaPlayer;
    Notification status;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mediaPlayer.setDataSource("http://188.165.240.90:8466/stream");

            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        showNotification();
        return START_STICKY;
    }

    public void onDestroy() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }

    public void onCompletion(MediaPlayer _mediaPlayer) {
        stopSelf();
    }


    public void showNotification() {

        RemoteViews views = new RemoteViews(getPackageName(),
                R.layout.status_bar);
        RemoteViews bigViews = new RemoteViews(getPackageName(),
                R.layout.status_bar_expanded);

        views.setViewVisibility(R.id.status_bar_icon, View.VISIBLE);
        views.setViewVisibility(R.id.status_bar_album_art, View.GONE);
        bigViews.setImageViewBitmap(R.id.status_bar_album_art,
                Constants.getDefaultAlbumArt(this, ""));

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Intent previousIntent = new Intent(this, NotificationService.class);
        previousIntent.setAction(Constants.ACTION.PREV_ACTION);
        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                previousIntent, 0);

        Intent initIntent = new Intent(this, NotificationService.class);
        initIntent.setAction(Constants.ACTION.INIT_ACTION);
        PendingIntent iinitIntent = PendingIntent.getService(this, 0,
                initIntent, 0);


        Intent playIntent = new Intent(this, NotificationService.class);
        playIntent.setAction(Constants.ACTION.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                playIntent, 0);

        Intent nextIntent = new Intent(this, NotificationService.class);
        nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
        PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                nextIntent, 0);

        Intent closeIntent = new Intent(this, NotificationService.class);
        closeIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        PendingIntent pcloseIntent = PendingIntent.getService(this, 0,
                closeIntent, 0);

        views.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);

        views.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);

        views.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);

        views.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);


        views.setImageViewResource(R.id.status_bar_play,
                R.drawable.apollo_holo_dark_pause);
        bigViews.setImageViewResource(R.id.status_bar_play,
                R.drawable.apollo_holo_dark_pause);

        views.setTextViewText(R.id.status_bar_track_name, "");
        bigViews.setTextViewText(R.id.status_bar_track_name, "");

        views.setTextViewText(R.id.status_bar_artist_name, "");
        bigViews.setTextViewText(R.id.status_bar_artist_name, "");

        bigViews.setTextViewText(R.id.status_bar_album_name, "");

        status = new Notification.Builder(this).build();
        status.contentView = views;
        status.bigContentView = bigViews;
        status.flags = Notification.FLAG_ONGOING_EVENT;
        status.icon = R.mipmap.ic_launcher;
        status.contentIntent = pendingIntent;
        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);

    }
}

class Test extends Activity implements OnClickListener {

    Button startPlaybackButton, stopPlaybackButton;
    Intent playbackServiceIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        startPlaybackButton = (Button) this.findViewById(R.id.StartPlaybackButton);
        stopPlaybackButton = (Button) this.findViewById(R.id.StopPlaybackButton);

        startPlaybackButton.setOnClickListener(this);
        stopPlaybackButton.setOnClickListener(this);

        playbackServiceIntent = new Intent(this, BackgroundAudioService.class);
    }

    public void onClick(View v) {
        if (v == startPlaybackButton) {
            startService(playbackServiceIntent);
            finish();
        } else if (v == stopPlaybackButton) {
            stopService(playbackServiceIntent);
            finish();
        }
    }
}