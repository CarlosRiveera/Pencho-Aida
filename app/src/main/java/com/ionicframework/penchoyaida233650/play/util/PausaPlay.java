package com.ionicframework.penchoyaida233650.play.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ionicframework.penchoyaida233650.BackgroundSoundService;
import com.ionicframework.penchoyaida233650.NotificationService;
import com.ionicframework.penchoyaida233650.NotificationService2new;
import com.ionicframework.penchoyaida233650.NotificationService3;
import com.ionicframework.penchoyaida233650.detallepost.service.NotificationService2;
import com.ionicframework.penchoyaida233650.newplay.util.Constants;
import com.ionicframework.penchoyaida233650.newplay.util.util.PlayerConstants;
import com.ionicframework.penchoyaida233650.newplay.util.util.UtilFunctions;

/**
 * Created by Santiago on 12/05/2016.
 */
public class PausaPlay {
    boolean estados;
    public void play(Context context){
        Log.i("LOOK", "entra");
        Intent serviceIntent = new Intent(context, NotificationService.class);
        serviceIntent.setAction(Constants.ACTION.PREV_ACTION);
        context.startService(serviceIntent);
    }

    public boolean estado(){
        NotificationService noti = new NotificationService();
        estados= noti.valor;
        return estados;
    }

    public void inicio(Context context){
        Log.i("LOOK", "entra");
        Intent serviceIntent = new Intent(context, NotificationService2new.class);
        serviceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        context.startService(serviceIntent);
    }

    public void inicios(Context context){
        Log.i("LOOK", "entra");
        Intent serviceIntent = new Intent(context, NotificationService.class);
        serviceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        context.startService(serviceIntent);
    }

    public void play2(Context context){
        Log.i("LOOK", "entras");
        Intent serviceIntent = new Intent(context, NotificationService2new.class);
        serviceIntent.setAction(Constants.ACTION.PREV_ACTION);
        context.startService(serviceIntent);
    }

    public void pausa2(Context context){
        Log.i("LOOK", "entra");
        Intent serviceIntent = new Intent(context, NotificationService2new.class);
        serviceIntent.setAction(Constants.ACTION.INIT_ACTION);
        context.startService(serviceIntent);
    }

    public void pausa22(Context context){
        Log.i("LOOK", "entra");
        Intent serviceIntent = new Intent(context, NotificationService.class);
        serviceIntent.setAction(Constants.ACTION.INIT_ACTION);
        context.startService(serviceIntent);
    }

    public void pausa3(Context context){
        Log.i("LOOK", "entra");
        Intent serviceIntent = new Intent(context, NotificationService2new.class);
        serviceIntent.setAction(Constants.ACTION.NEXT_ACTION);
        context.startService(serviceIntent);
    }

    public void pausa4(Context context){
        Log.i("LOOK", "entra");
        Intent serviceIntent = new Intent(context, NotificationService2new.class);
        serviceIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        context.startService(serviceIntent);
    }

    public void pausa(Context context){
        Log.i("LOOK", "entra");
        Intent serviceIntent = new Intent(context, NotificationService.class);
        serviceIntent.setAction(Constants.ACTION.NEXT_ACTION);
        context.startService(serviceIntent);
    }

    public void pausa10(Context context){
        Log.i("LOOK", "entra");
        Intent serviceIntent = new Intent(context, NotificationService.class);
        serviceIntent.setAction(Constants.ACTION.NEXT_ACTION);
        context.startService(serviceIntent);
    }


}
