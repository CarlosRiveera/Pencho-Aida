package com.ionicframework.penchoyaida233650;

/**
 * Created by Santiago on 11/05/2016.
 */

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RemoteControlClient;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;

import com.ionicframework.penchoyaida233650.newplay.util.Constants;
import com.ionicframework.penchoyaida233650.newplay.util.util.receiver.NotificationBroadcast;
import com.ionicframework.penchoyaida233650.play.db.DaoAccessCancion;


public class NotificationService3 extends Service {

    String titulo = "", sub, imagentext;
    Notification status;
    Cursor cursor, cursorestado,cursorpost,cursorduracion;
    int a = 1;
    DisplayImageOptions options;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private final String LOG_TAG = "NotificationService";
    DaoAccessCancion daoAccessCancion;
    private ComponentName remoteComponentName;
    private RemoteControlClient remoteControlClient;
    private static boolean currentVersionSupportBigNotification = false;
    private static boolean currentVersionSupportLockScreenControls = false;
    AudioManager audioManager;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            daoAccessCancion = new DaoAccessCancion();
            daoAccessCancion.Instantiate(NotificationService3.this);
            cursorpost = daoAccessCancion.GetAllPostPlay();
            cursorpost.moveToPosition(0);
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);


            player.setDataSource(cursorpost.getString(2));
            //Toast.makeText(this, cursorpost.getString(2), Toast.LENGTH_SHORT).show();
            Log.i("servicio3", cursorpost.getString(2));
            player.prepare();
            player.setLooping(true); // Set looping
            player.setVolume(100, 100);

            daoAccessCancion = new DaoAccessCancion();
            daoAccessCancion.Instantiate(NotificationService3.this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("NewApi")
    private void RegisterRemoteClient() {
        remoteComponentName = new ComponentName(getApplicationContext(), new NotificationBroadcast().ComponentName());
        try {
            if (remoteControlClient == null) {
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
        } catch (Exception ex) {
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (currentVersionSupportLockScreenControls) {
            RegisterRemoteClient();
        }

        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
          //  showNotification();
          //     Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();

        } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
            cursorduracion = daoAccessCancion.GetAllDuracion();
            cursorduracion.moveToPosition(0);
            if (cursorduracion.getCount() != 0) {
                player.seekTo(Integer.parseInt(cursorduracion.getString(2)));
            }
            player.start();
            showNotification();
        } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
         //  Toast.makeText(this, "Clicked Play " + a, Toast.LENGTH_SHORT).show();
            cursorestado = daoAccessCancion.GetAllEstado();
            cursorestado.moveToPosition(0);
            //  Log.i(LOG_TAG, "Clicked Play" + cursorestado.getString(1));
            if (cursorestado.getString(1).equals("0")) {

                player.start();
                daoAccessCancion.UpdateEstado("1");
                showNotification();
            } else {

                player.pause();
                player.seekTo(0);
                daoAccessCancion.UpdateEstado("0");
                showNotification();
            }
        } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
            //  Toast.makeText(this, "Clicked Next", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Next");
        } else if (intent.getAction().equals(
                Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Stop Foreground Intent");
            //  Toast.makeText(this, "Service Stoped", Toast.LENGTH_SHORT).show();
            player.stop();
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }


    private void showNotification() {
        try {


            daoAccessCancion = new DaoAccessCancion();
            daoAccessCancion.Instantiate(NotificationService3.this);
            cursor = daoAccessCancion.GetAllCancion();
            cursor.moveToPosition(0);
// Using RemoteViews to bind custom layouts into Notification
            RemoteViews views = new RemoteViews(getPackageName(),
                    R.layout.status_bar);
            RemoteViews bigViews = new RemoteViews(getPackageName(),
                    R.layout.status_bar_expanded);

// showing default album image
            views.setViewVisibility(R.id.status_bar_icon, View.VISIBLE);
            views.setImageViewBitmap(R.id.status_bar_icon,
                    Constants.getDefaultAlbumArt(this, cursor.getString(4)));
            views.setViewVisibility(R.id.status_bar_album_art, View.GONE);
            bigViews.setImageViewBitmap(R.id.status_bar_album_art,
                    Constants.getDefaultAlbumArt(this, cursor.getString(4)));

            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, 0);

            Intent previousIntent = new Intent(this, NotificationService3.class);
            previousIntent.setAction(Constants.ACTION.PREV_ACTION);
            PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                    previousIntent, 0);

            Intent initIntent = new Intent(this, NotificationService3.class);
            initIntent.setAction(Constants.ACTION.INIT_ACTION);
            PendingIntent iinitIntent = PendingIntent.getService(this, 0,
                    initIntent, 0);


            Intent playIntent = new Intent(this, NotificationService3.class);
            playIntent.setAction(Constants.ACTION.PLAY_ACTION);
            PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                    playIntent, 0);

            Intent nextIntent = new Intent(this, NotificationService3.class);
            nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
            PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                    nextIntent, 0);

            Intent closeIntent = new Intent(this, NotificationService3.class);
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

            cursorestado = daoAccessCancion.GetAllEstado();
            cursorestado.moveToPosition(0);
            if (cursorestado.getString(1).equals("1")) {
                views.setImageViewResource(R.id.status_bar_play,
                        R.drawable.apollo_holo_dark_pause);
                bigViews.setImageViewResource(R.id.status_bar_play,
                        R.drawable.apollo_holo_dark_pause);
            } else {
                views.setImageViewResource(R.id.status_bar_play,
                        R.drawable.apollo_holo_dark_play);
                bigViews.setImageViewResource(R.id.status_bar_play,
                        R.drawable.apollo_holo_dark_play);
            }

            views.setTextViewText(R.id.status_bar_track_name, cursor.getString(1));
            bigViews.setTextViewText(R.id.status_bar_track_name, cursor.getString(1));

            views.setTextViewText(R.id.status_bar_artist_name, cursor.getString(2));
            bigViews.setTextViewText(R.id.status_bar_artist_name, cursor.getString(2));

            bigViews.setTextViewText(R.id.status_bar_album_name, cursor.getString(3));

            status = new Notification.Builder(this).build();
            status.contentView = views;
            status.bigContentView = bigViews;
            status.flags = Notification.FLAG_ONGOING_EVENT;
            status.icon = R.mipmap.pencho_icon;
            status.contentIntent = pendingIntent;
            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);
        } catch (Exception ex) {

        }
    }


}