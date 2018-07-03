package com.ionicframework.penchoyaida233650;

/**
 * Created by Santiago on 11/05/2016.
 */

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.RemoteControlClient;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.hugomatilla.audioplayerview.AudioPlayerView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import com.ionicframework.penchoyaida233650.newplay.util.Constants;
import com.ionicframework.penchoyaida233650.newplay.util.util.receiver.NotificationBroadcast;
import com.ionicframework.penchoyaida233650.play.db.DBCancion;
import com.ionicframework.penchoyaida233650.play.db.DaoAccessCancion;
import com.ionicframework.penchoyaida233650.play.util.PausaPlay;
import com.ionicframework.penchoyaida233650.play.ws.GET_Portada;
import com.ionicframework.penchoyaida233650.util.MediaButtonHelper;
import com.ionicframework.penchoyaida233650.util.RemoteControlClientCompat;
import com.ionicframework.penchoyaida233650.util.RemoteControlHelper;


public class NotificationService extends Service {

    Notification status;
    Bitmap bm = null;
    Cursor cursor, cursorestado;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DaoAccessCancion daoAccessCancion;
    private static boolean currentVersionSupportBigNotification = false;
    private static boolean currentVersionSupportLockScreenControls = false;
    public boolean valor = true;
    AudioManager myAudioManager;
    Intent mediaButtonIntent;
    PausaPlay pausaPlay;
    RemoteControlClientCompat mRemoteControlClientCompat;
    CountDownTimer timer1;
    int valores =0;

    private Socket socket;

    {
        try {
            socket = IO.socket("http://penchoyaida.fm:3000");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
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
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource("http://188.165.240.90:8466/stream");
            player.prepare();
            player.setLooping(true);
            player.setVolume(100, 100);
            daoAccessCancion = new DaoAccessCancion();
            daoAccessCancion.Instantiate(NotificationService.this);
            pausaPlay = new PausaPlay();
           // repetir();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @SuppressLint("NewApi")
    private void RegisterRemoteClient() {
        ComponentName myEventReceiver = new ComponentName(getPackageName(), NotificationService.class.getName());
        myAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        myAudioManager.registerMediaButtonEventReceiver(myEventReceiver);
        mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        mediaButtonIntent.setComponent(myEventReceiver);
        PendingIntent mediaPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, mediaButtonIntent, 0);
        RemoteControlClient myRemoteControlClient = new RemoteControlClient(mediaPendingIntent);
        myAudioManager.registerRemoteControlClient(myRemoteControlClient);
        //
    }

    protected void setColorWallpaper() {
        // Create small solid color bitmap.
        int a = R.drawable.aida_perfil;
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.pencho_podcast);
        DisplayMetrics metrics = new DisplayMetrics();
        // getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // get the height and width of screen
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        try {
            wallpaperManager.setBitmap(bitmap);

            wallpaperManager.suggestDesiredDimensions(width, height);
            Toast.makeText(this, "Wallpaper Set", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {


            if (currentVersionSupportLockScreenControls) {
                RegisterRemoteClient();
            }
            if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
                showNotification();
                socket.connect();
                socket.on("song", handleIncomingMessages);
               // repetir();
            } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
                player.start();
            } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
                cursorestado = daoAccessCancion.GetAllEstado();
                cursorestado.moveToPosition(0);
                if (cursorestado.getString(1).equals("0")) {
                    player = new MediaPlayer();
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setDataSource("http://188.165.240.90:8466/stream");
                    player.prepare();
                    player.setLooping(true);
                    player.setVolume(100, 100);
                    daoAccessCancion = new DaoAccessCancion();
                    daoAccessCancion.Instantiate(NotificationService.this);
                    player.start();
                    daoAccessCancion.UpdateEstado("1");
                    showNotification();
                } else {
                    player.reset();
                    daoAccessCancion.UpdateEstado("0");
                    showNotification();
                }
            } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
                Log.i("play2","pausa");
                player.pause();
                player.seekTo(0);
                Log.i("posicion1",player.getCurrentPosition()+"");
                stopForeground(true);
                stopSelf();
                timer1.cancel();
            }else if (intent.getAction().equals(Constants.ACTION.INIT_ACTION)) {
                stopForeground(true);
                socket.close();
            } else if (intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION)) {
                daoAccessCancion.UpdateEstado2("0","0");
                player.stop();
                socket.close();
                stopForeground(true);
                stopSelf();
            }
        } catch (Exception ex) {

        }
        return START_STICKY;

    }


    private void showNotification() {
        try {
            daoAccessCancion = new DaoAccessCancion();
            daoAccessCancion.Instantiate(NotificationService.this);
            cursor = daoAccessCancion.GetAllCancion();
            cursor.moveToPosition(0);
            RemoteViews views = new RemoteViews(getPackageName(),
                    R.layout.status_bar);
            RemoteViews bigViews = new RemoteViews(getPackageName(),
                    R.layout.status_bar_expanded);
            views.setViewVisibility(R.id.status_bar_icon, View.VISIBLE);
            views.setImageViewBitmap(R.id.status_bar_icon,
                    Constants.getDefaultAlbumArt(this, cursor.getString(4)));
            views.setViewVisibility(R.id.status_bar_album_art, View.GONE);
            bigViews.setImageViewBitmap(R.id.status_bar_album_art,
                    Constants.getDefaultAlbumArt(this, cursor.getString(4)));

            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.putExtra("valor", "1");
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

            cursorestado = daoAccessCancion.GetAllEstado();
            cursorestado.moveToPosition(0);
            if (cursorestado.getString(1).equals("1")) {
                views.setImageViewResource(R.id.status_bar_play,
                        R.drawable.apollo_holo_dark_pause);
                bigViews.setImageViewResource(R.id.status_bar_play,
                        R.drawable.apollo_holo_dark_pause);
              //  player.seekTo(0);
            } else {
                views.setImageViewResource(R.id.status_bar_play,
                        R.drawable.apollo_holo_dark_play);
                bigViews.setImageViewResource(R.id.status_bar_play,
                        R.drawable.apollo_holo_dark_play);
              //  player.seekTo(0);
            }
            Log.i("repiteeeeeeeee","vueltasssssssssss");

            views.setTextViewText(R.id.status_bar_track_name, cursor.getString(1));
            bigViews.setTextViewText(R.id.status_bar_track_name, cursor.getString(1));
            views.setTextViewText(R.id.status_bar_artist_name, cursor.getString(2));
            bigViews.setTextViewText(R.id.status_bar_artist_name, cursor.getString(2));
            bigViews.setTextViewText(R.id.status_bar_album_name, cursor.getString(3));
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




    public class EspecialTask extends AsyncTask<Void, Void, Void> {
        int error;
        MainActivity manito;
        URL url = null;
        String song,artist,album,albumart;
        GET_Portada get_portada;

        public EspecialTask(int error, String song, String artist, String album, String albumart) {
            this.error = error;
            this.song = song;
            this.artist = artist;
            this.album = album;
            this.albumart = albumart;
        }

        @Override
        protected void onPreExecute() {
            try {
                manito = new MainActivity();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                if (error == 1) {
                    url = new URL(albumart);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    bm = BitmapFactory.decodeStream(input);

                    DBCancion cancion = new DBCancion(Long.parseLong("1"),
                            song,
                            artist,
                            album,
                            encodeTobase64(bm));
                    daoAccessCancion.cancionDao.insert(cancion);
                } else if (error == 2) {
                    url = new URL("http://penchoyaida.fm/vivo/cover.jpg");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    bm = BitmapFactory.decodeStream(input);

                    DBCancion cancion = new DBCancion(Long.parseLong("1"),
                            "Pencho & Aída",
                            "Pencho & Aída",
                            "En vivo",
                            encodeTobase64(bm));
                    daoAccessCancion.cancionDao.insert(cancion);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {

            try {
                        showNotification();

            } catch (Exception ex) {

            }

        }
    }

    private Emitter.Listener handleIncomingMessages = new Emitter.Listener() {
        Context context;


        @Override
        public void call(final Object... args) {
            NotificationService.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = null;
                        JSONObject data = (JSONObject) args[0];
                        String song,artist,album,albumart,message,cuna;
                        String imageText;
                        //try {
                        //message = data.getString("nombre").toString();
                        message = data + "";
                        Log.i("message",""+message);
                        JSONObject msgJson = data.getJSONObject("data");
                        daoAccessCancion.cancionDao.deleteAll();
                        cuna = msgJson.getString("song");
                        if (!cuna.equals("")) {
                            song = msgJson.getString("song");
                            artist = msgJson.getString("artist");
                            album = msgJson.getString("album");
                            albumart = msgJson.getString("albumArt");
                            new EspecialTask(1,song,artist,album,albumart).execute();
                        }else{
                           // new EspecialTask(2,"","","","").execute();
                        }



                   /* } catch (JSONException e) {
                        // return;
                        Log.i("Exception",""+e);
                    }*/

                    } catch (JSONException e) {
                        new EspecialTask(2,"","","","").execute();
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private void runOnUiThread(Runnable runnable) {
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }



   /* public void repetir() {
        timer1 = new CountDownTimer(5000, 1000) {
            @SuppressLint("NewApi")
            public void onTick(long millisUntilFinished) {
                Log.i("timer", "seconds remaining: " + millisUntilFinished
                        / 1000);
            }
            public void onFinish() {

                    new EspecialTask().execute();

            }
        }.start();
    }*/

}