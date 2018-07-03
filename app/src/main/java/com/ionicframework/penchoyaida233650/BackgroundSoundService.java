package com.ionicframework.penchoyaida233650;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RemoteControlClient;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import java.io.IOException;

import com.ionicframework.penchoyaida233650.newplay.util.util.PlayerConstants;
import com.ionicframework.penchoyaida233650.newplay.util.util.receiver.NotificationBroadcast;


/**
 * Created by Santiago on 11/05/2016.
 */
public class BackgroundSoundService extends Service {
    private static final String TAG = null;
    MediaPlayer player;

    Notification status;
    private ComponentName remoteComponentName;
    private RemoteControlClient remoteControlClient;
    AudioManager audioManager;

    private static boolean currentVersionSupportBigNotification = false;
    private static boolean currentVersionSupportLockScreenControls = false;

    public static final String NOTIFY_PREVIOUS = "imoves.com.panchoaida.previous";
    public static final String NOTIFY_DELETE = "imoves.com.panchoaida.delete";
    public static final String NOTIFY_PAUSE = "imoves.com.panchoaida.pause";
    public static final String NOTIFY_PLAY = "imoves.com.panchoaida.play";
    public static final String NOTIFY_NEXT = "imoves.com.panchoaida.next";

    public IBinder onBind(Intent arg0) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            player = new MediaPlayer();
            audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);

            player.setDataSource("http://188.165.240.90:8466/stream");
            player.prepare();
            player.setLooping(true); // Set looping
            player.setVolume(100, 100);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();

        if(currentVersionSupportLockScreenControls){
            RegisterRemoteClient();
        }

        //showNotification();
        newNotification();

        PlayerConstants.PLAY_PAUSE_HANDLER = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                String message = (String)msg.obj;
                if(player == null)
                    return false;
                if(message.equalsIgnoreCase("play")){
                    PlayerConstants.SONG_PAUSED = false;
                    Log.d("TAG", "presiono**" + message);
                    if(currentVersionSupportLockScreenControls){
                        remoteControlClient.setPlaybackState(RemoteControlClient.PLAYSTATE_PLAYING);
                    }

                    player.start();
                }else if(message.equalsIgnoreCase("pause")){
                    PlayerConstants.SONG_PAUSED = true;
                    if(currentVersionSupportLockScreenControls){
                        remoteControlClient.setPlaybackState(RemoteControlClient.PLAYSTATE_PAUSED);
                    }
                    player.stop();
                }
                newNotification();
                try{
                    //MainActivity.changeButton();
                    AudioPlayerActivity.changeButton();
                }catch(Exception e){}
                Log.d("TAG", "TAG Pressed: " + message);
                return false;
            }
        });

        return START_STICKY;
    }

    @SuppressLint("NewApi")
    private void RegisterRemoteClient(){
        remoteComponentName = new ComponentName(getApplicationContext(), new NotificationBroadcast().ComponentName());
        try {
            if(remoteControlClient == null) {
                audioManager.registerMediaButtonEventReceiver(remoteComponentName);
                Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
                mediaButtonIntent.setComponent(remoteComponentName);
                PendingIntent mediaPendingIntent = PendingIntent.getBroadcast(this, 0, mediaButtonIntent, 0);
                remoteControlClient = new RemoteControlClient(mediaPendingIntent);
                audioManager.registerRemoteControlClient(remoteControlClient);
            }
            remoteControlClient.setTransportControlFlags(
                    RemoteControlClient.FLAG_KEY_MEDIA_PLAY |
                            RemoteControlClient.FLAG_KEY_MEDIA_PAUSE |
                            RemoteControlClient.FLAG_KEY_MEDIA_PLAY_PAUSE |
                            RemoteControlClient.FLAG_KEY_MEDIA_STOP |
                            RemoteControlClient.FLAG_KEY_MEDIA_PREVIOUS |
                            RemoteControlClient.FLAG_KEY_MEDIA_NEXT);
        }catch(Exception ex) {
        }
    }

    public void onStart(Intent intent, int startId) {
        //player.start();

        // TO DO
    }

    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {
        player.stop();

    }

    public void onPause() {
        //showNotification();
    }

    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory() {

    }

    @SuppressLint("NewApi")
    private void newNotification() {
        //String songName = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getTitle();
       // String albumName = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getAlbum();
        RemoteViews simpleContentView = new RemoteViews(getApplicationContext().getPackageName(),R.layout.custom_notification);
        RemoteViews expandedView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.big_notification);

        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("algo").build();

        setListeners(simpleContentView);
        setListeners(expandedView);

        notification.contentView = simpleContentView;
        if(currentVersionSupportBigNotification){
            notification.bigContentView = expandedView;
        }

        try{
            //long albumId = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getAlbumId();
            //Bitmap albumArt = UtilFunctions.getAlbumart(getApplicationContext(), albumId);
            //if(albumArt != null){
                notification.contentView.setImageViewResource(R.id.imageViewAlbumArt, R.drawable.ic_launcher);
                if(currentVersionSupportBigNotification){
                    notification.bigContentView.setImageViewResource(R.id.imageViewAlbumArt, R.drawable.ic_launcher);
                }
            /*}else{
                notification.contentView.setImageViewResource(R.id.imageViewAlbumArt, R.drawable.default_album_art);
                if(currentVersionSupportBigNotification){
                    notification.bigContentView.setImageViewResource(R.id.imageViewAlbumArt, R.drawable.default_album_art);
                }
            }*/
        }catch(Exception e){
            e.printStackTrace();
        }
        if(PlayerConstants.SONG_PAUSED){
            notification.contentView.setViewVisibility(R.id.btnPause, View.GONE);
            notification.contentView.setViewVisibility(R.id.btnPlay, View.VISIBLE);

            if(currentVersionSupportBigNotification){
                notification.bigContentView.setViewVisibility(R.id.btnPause, View.GONE);
                notification.bigContentView.setViewVisibility(R.id.btnPlay, View.VISIBLE);
            }
        }else{
            notification.contentView.setViewVisibility(R.id.btnPause, View.VISIBLE);
            notification.contentView.setViewVisibility(R.id.btnPlay, View.GONE);

            if(currentVersionSupportBigNotification){
                notification.bigContentView.setViewVisibility(R.id.btnPause, View.VISIBLE);
                notification.bigContentView.setViewVisibility(R.id.btnPlay, View.GONE);
            }
        }

        notification.contentView.setTextViewText(R.id.textSongName, "algo");
        notification.contentView.setTextViewText(R.id.textAlbumName, "algo2");
        if(currentVersionSupportBigNotification){
            notification.bigContentView.setTextViewText(R.id.textSongName, "algo");
            notification.bigContentView.setTextViewText(R.id.textAlbumName, "algo2");
        }
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        startForeground(1111, notification);
    }

    public  void showNotification() {

      //  daoAccessCancion = new DaoAccessCancion();
     //   daoAccessCancion.Instantiate(BackgroundSoundService.this);
     //   cursor = daoAccessCancion.GetAllCancion();
     //   cursor.moveToPosition(0);
// Using RemoteViews to bind custom layouts into Notification
        RemoteViews views = new RemoteViews(getPackageName(),
                R.layout.status_bar);
        RemoteViews bigViews = new RemoteViews(getPackageName(),
                R.layout.status_bar_expanded);

        //setListeners(views);
       // setListeners(bigViews);

// showing default album image
        views.setViewVisibility(R.id.status_bar_icon, View.VISIBLE);
        views.setViewVisibility(R.id.status_bar_album_art, View.GONE);
        bigViews.setImageViewBitmap(R.id.status_bar_album_art,
                Constants.getDefaultAlbumArt(this));

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

    public void setListeners(RemoteViews view) {
        Intent previous = new Intent(NOTIFY_PREVIOUS);
        Intent delete = new Intent(NOTIFY_DELETE);
        Intent pause = new Intent(NOTIFY_PAUSE);
        Intent next = new Intent(NOTIFY_NEXT);
        Intent play = new Intent(NOTIFY_PLAY);

        /*PendingIntent pPrevious = PendingIntent.getBroadcast(getApplicationContext(), 0, previous, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnPrevious, pPrevious);

        PendingIntent pDelete = PendingIntent.getBroadcast(getApplicationContext(), 0, delete, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnDelete, pDelete);*/

        PendingIntent pPause = PendingIntent.getBroadcast(getApplicationContext(), 0, pause, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnPause, pPause);

        /*PendingIntent pNext = PendingIntent.getBroadcast(getApplicationContext(), 0, next, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnNext, pNext);*/

        PendingIntent pPlay = PendingIntent.getBroadcast(getApplicationContext(), 0, play, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnPlay, pPlay);

    }

}
