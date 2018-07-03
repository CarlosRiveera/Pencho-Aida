package com.ionicframework.penchoyaida233650;

/**
 * Created by Santiago on 11/05/2016.
 */

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RemoteControlClient;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;

import com.ionicframework.penchoyaida233650.detallepost.activity.ActivityDetallePost2;
import com.ionicframework.penchoyaida233650.detallepost.db.DaoAccessDetalle;
import com.ionicframework.penchoyaida233650.detallepost.util.Global;
import com.ionicframework.penchoyaida233650.newplay.util.Constants;
import com.ionicframework.penchoyaida233650.newplay.util.util.receiver.NotificationBroadcast;
import com.ionicframework.penchoyaida233650.play.db.DaoAccessCancion;
import com.ionicframework.penchoyaida233650.podacast_exclusivos.activity.ActivityDetallePost22;
import com.ionicframework.penchoyaida233650.podcast.activity.ActivityDetallePost4;
import com.ionicframework.penchoyaida233650.podcast_deportes.activity.ActivityDetallePost3;


public class NotificationService2new extends Service {

    Notification status;
    Cursor cursor, cursorestado, cursorpost, cursorduracion;
    public int progress;
    Global global;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DaoAccessCancion daoAccessCancion;
    DaoAccessDetalle daoAccessDetalle;
    private ComponentName remoteComponentName;
    private RemoteControlClient remoteControlClient;
    private static boolean currentVersionSupportBigNotification = false;
    private static boolean currentVersionSupportLockScreenControls = false;
    AudioManager audioManager;
    int duration = 0;
    Intent notificationIntent;
    int currentPosition = 0;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            daoAccessCancion = new DaoAccessCancion();
            daoAccessCancion.Instantiate(NotificationService2new.this);
            daoAccessDetalle = new DaoAccessDetalle();
            daoAccessDetalle.Instantiate(this);
            cursorpost = daoAccessCancion.GetAllPostPlay();
            cursorpost.moveToPosition(0);
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(cursorpost.getString(2));
            player.prepare();
            player.setLooping(true); // Set looping
            player.setVolume(100, 100);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
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
        try {
            if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
                showNotification();
            } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
                global.setIvar1(1);
                cursorduracion = daoAccessCancion.GetAllDuracion();
                cursorduracion.moveToPosition(0);
                if (cursorduracion.getCount() != 0) {
                    player.seekTo(Integer.parseInt(cursorduracion.getString(2)));
                }
                player.start();
                showNotification();
            } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
                cursorestado = daoAccessCancion.GetAllEstado();
                cursorestado.moveToPosition(0);
                if (cursorestado.getString(1).equals("0")) {
                    player.start();
                    daoAccessCancion.UpdateEstado("1");
                    showNotification();
                } else {
                    player.pause();
                    daoAccessCancion.UpdateEstado("0");
                    showNotification();
                }
            } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
                daoAccessDetalle.UpdateDuracion(player.getCurrentPosition() + "");
                cursorestado = daoAccessDetalle.GetAllDuracion();
                cursorestado.moveToPosition(0);
                player.pause();
                stopForeground(true);
            } else if (intent.getAction().equals(Constants.ACTION.INIT_ACTION)) {
                stopForeground(true);
                stopSelf();
            } else if (intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION)) {
                daoAccessCancion.UpdateDuracion(player.getCurrentPosition() + "");
                player.stop();
                stopForeground(true);
                stopSelf();
            }
        } catch (Exception ex) {
            Log.i("grave",""+ex);
        }

        return START_STICKY;
    }


    public int getDuration() {
        duration = player.getDuration();
        return duration;
    }

    public int getPresentDuration() {
        currentPosition = player.getCurrentPosition();
        return currentPosition;
    }

    private void showNotification() {
        try {
            cursor = daoAccessCancion.GetAllPostPlay();
            cursor.moveToPosition(0);
            RemoteViews views = new RemoteViews(getPackageName(),
                    R.layout.status_bar);
            RemoteViews bigViews = new RemoteViews(getPackageName(),
                    R.layout.status_bar_expanded);
            views.setViewVisibility(R.id.status_bar_icon, View.VISIBLE);
            views.setImageViewBitmap(R.id.status_bar_icon,
                    Constants.getDefaultAlbumArt(this, cursor.getString(3)));
            views.setViewVisibility(R.id.status_bar_album_art, View.GONE);
            bigViews.setImageViewBitmap(R.id.status_bar_album_art,
                    Constants.getDefaultAlbumArt(this, cursor.getString(3)));
            if (cursorduracion.getString(0).equals("2")) {
                notificationIntent = new Intent(this, ActivityDetallePost2.class);
            } else if (cursorduracion.getString(0).equals("3")) {
                notificationIntent = new Intent(this, ActivityDetallePost3.class);
            } else if (cursorduracion.getString(0).equals("4")) {
                notificationIntent = new Intent(this, ActivityDetallePost4.class);
            }else if (cursorduracion.getString(0).equals("22")) {
                notificationIntent = new Intent(this, ActivityDetallePost22.class);
            }
            notificationIntent.putExtra("posicion", cursorduracion.getString(4));
            notificationIntent.putExtra("valor", "1");
            notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, 0);

            Intent previousIntent = new Intent(this, NotificationService2new.class);
            previousIntent.setAction(Constants.ACTION.PREV_ACTION);
            PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                    previousIntent, 0);

            Intent initIntent = new Intent(this, NotificationService2new.class);
            initIntent.setAction(Constants.ACTION.INIT_ACTION);
            PendingIntent iinitIntent = PendingIntent.getService(this, 0,
                    initIntent, 0);


            Intent playIntent = new Intent(this, NotificationService2new.class);
            playIntent.setAction(Constants.ACTION.PLAY_ACTION);
            PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                    playIntent, 0);

            Intent nextIntent = new Intent(this, NotificationService2new.class);
            nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
            PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                    nextIntent, 0);

            Intent closeIntent = new Intent(this, NotificationService2new.class);
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

            views.setTextViewText(R.id.status_bar_artist_name, cursor.getString(4));
            bigViews.setTextViewText(R.id.status_bar_artist_name, cursor.getString(4));
            bigViews.setTextViewText(R.id.status_bar_album_name, cursor.getString(5));

            status = new Notification.Builder(this).build();
            status.contentView = views;
            status.bigContentView = bigViews;
            status.flags = Notification.FLAG_ONGOING_EVENT;
            status.icon = R.mipmap.logo_push;
            status.contentIntent = pendingIntent;
            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);
        } catch (Exception ex) {

        }
    }


}