package com.ionicframework.penchoyaida233650.play.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.hugomatilla.audioplayerview.AudioPlayerView;

import com.ionicframework.penchoyaida233650.R;

/**
 * Created by Santiago on 09/05/2016.
 */
public class MyNotification extends Notification {

    private Context ctx;
    private NotificationManager mNotificationManager;

    @SuppressLint("NewApi")
    public MyNotification(Context ctx, AudioPlayerView audioPlayerView){
        super();
        this.ctx=ctx;
        String ns = Context.NOTIFICATION_SERVICE;
        mNotificationManager = (NotificationManager) ctx.getSystemService(ns);
        CharSequence tickerText = "Shortcuts";
        long when = System.currentTimeMillis();
        Notification.Builder builder = new Notification.Builder(ctx);
        @SuppressWarnings("deprecation")
        Notification notification=builder.getNotification();
        notification.when=when;
        notification.tickerText=tickerText;
        notification.icon=R.drawable.ic_launcher;

        RemoteViews contentView=new RemoteViews(ctx.getPackageName(), R.layout.messageview);

        //set the button listeners
        setListeners(contentView,audioPlayerView);

        notification.contentView = contentView;
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        CharSequence contentTitle = "From Shortcuts";
        mNotificationManager.notify(548853, notification);
    }

    public void setListeners(RemoteViews view,AudioPlayerView audioPlayerView){



        //radio listener
        Intent radio = new Intent(ctx,HelperActivity.class);
        radio.putExtra("DO", "play");
        PendingIntent pRadio = PendingIntent.getActivity(ctx, 0, radio, 0);
        view.setOnClickPendingIntent(R.id.btnplay, pRadio);

        //volume listener
        Intent volume=new Intent(ctx, HelperActivity.class);
        volume.putExtra("DO", "volume");
        PendingIntent pVolume = PendingIntent.getActivity(ctx, 1, volume, 0);
        view.setOnClickPendingIntent(R.id.btnvolume, pVolume);

        //reboot listener
        Intent reboot=new Intent(ctx, HelperActivity.class);
        reboot.putExtra("DO", "stop");
        PendingIntent pReboot = PendingIntent.getActivity(ctx, 5, reboot, 0);
        view.setOnClickPendingIntent(R.id.btnstop, pReboot);

    }

}