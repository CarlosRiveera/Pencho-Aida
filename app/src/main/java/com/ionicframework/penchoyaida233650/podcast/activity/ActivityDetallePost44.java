package com.ionicframework.penchoyaida233650.podcast.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.ionicframework.penchoyaida233650.MainActivity;
import com.ionicframework.penchoyaida233650.R;
import com.ionicframework.penchoyaida233650.detallepost.db.DBDuracion;
import com.ionicframework.penchoyaida233650.detallepost.db.DBPostPlay;
import com.ionicframework.penchoyaida233650.detallepost.db.DaoAccessDetalle;
import com.ionicframework.penchoyaida233650.detallepost.util.Global;
import com.ionicframework.penchoyaida233650.detallepost.util.Utilities;
import com.ionicframework.penchoyaida233650.play.db.DBEstado;
import com.ionicframework.penchoyaida233650.play.util.PausaPlay;
import com.ionicframework.penchoyaida233650.play.ws.GET_Portada;

/**
 * Created by Santiago on 13/05/2016.
 */
public class ActivityDetallePost44 extends ActionBarActivity {

    Cursor cursor, cursorEstado, cursorpost, cursorduracion, cursorduracion2;
    String posicion,valor;
    int valores = 0, progreso = 0;
    int iniciador = 0, servidor = 0;
    DaoAccessDetalle daoAccessDetalle;
    ImageView imagenpri;
    Global global;
    CountDownTimer timer1, timer2;
    PausaPlay pausaPlay;
    int ban = 1;
    Bitmap bm = null;
    private long startTime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private Handler customHandler = new Handler();
    private AudioManager audioManager = null;
    SeekBar progress1, progress2 = null;
    TextView textinicio, textTitulo, textfin, textdetalle;
    ImageView btnquinmenos, btnunomenos, btnplay, btnunomas, btnquincemas, btncompartir, btnmin, btnmax;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    URL url = null;
    Utilities utils;
    int totalDuration, currentDuration;
    ArrayList<String> arrlist = new ArrayList<String>(20);
    private Handler seekHandler = new Handler();
    MediaPlayer player;
    Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallepost);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        pausaPlay = new PausaPlay();

        daoAccessDetalle = new DaoAccessDetalle();
        daoAccessDetalle.Instantiate(this);

        daoAccessDetalle.estadoDao.deleteAll();
        DBEstado estado = new DBEstado(Long.parseLong("0"), "0", "");
        daoAccessDetalle.estadoDao.insert(estado);
        utils = new Utilities();
        imagenpri = (ImageView) findViewById(R.id.imagenpri);
        btnquinmenos = (ImageView) findViewById(R.id.btnquinmenos);
        btnunomenos = (ImageView) findViewById(R.id.btnunomenos);
        btnplay = (ImageView) findViewById(R.id.btnplay);
        btnunomas = (ImageView) findViewById(R.id.btnunomas);
        btnquincemas = (ImageView) findViewById(R.id.btnquincemas);
        btncompartir = (ImageView) findViewById(R.id.btncompartir);
        btnmin = (ImageView) findViewById(R.id.btnmin);
        btnmax = (ImageView) findViewById(R.id.btnmax);
        cursorpost = daoAccessDetalle.GetAllPostPlay();
        cursorpost.moveToPosition(0);


        progress1 = (SeekBar) findViewById(R.id.progress1);
        progress2 = (SeekBar) findViewById(R.id.progress2);

        textinicio = (TextView) findViewById(R.id.textinicio);
        textTitulo = (TextView) findViewById(R.id.textTitulo);
        textfin = (TextView) findViewById(R.id.textfin);
        textdetalle = (TextView) findViewById(R.id.textdetalle);


        Bundle extra = ActivityDetallePost44.this.getIntent().getExtras();
        posicion = (String) extra.get("posicion");
        valor = (String) extra.get("valor");

        imageLoader.init(ImageLoaderConfiguration.createDefault(ActivityDetallePost44.this));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.fondo_pencho)
                .showImageForEmptyUri(R.mipmap.fondo_pencho)
                .cacheInMemory().cacheOnDisc()
                .build();

        new EspecialTask().execute();

        getInit();

        cursor = daoAccessDetalle.GetAllBusque();
        cursor.moveToPosition(Integer.parseInt(posicion));
        try {
            imageLoader.displayImage(cursor.getString(4), imagenpri, options);
            textinicio.setText("0:00");
            textTitulo.setText(cursor.getString(1));
            // textfin.setText(cursor.getString(10));
            textdetalle.setText(cursor.getString(2));
        } catch (Exception ex) {

        }
        initControls();
        btnmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress2.setProgress(progress2.getProgress() - 1);
            }
        });


        btnquinmenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.seekTo(player.getCurrentPosition() - 15000);
            }
        });

        btnquincemas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.seekTo(player.getCurrentPosition() + 15000);
            }
        });


        btnunomenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.seekTo(player.getCurrentPosition() - 60000);
            }
        });

        btnunomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                player.seekTo(player.getCurrentPosition() + 60000);
            }
        });

        btncompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap icon = bm;
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");

                try {
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String shareBody = "Estoy escuchando pencho y Aída FM http://penchoyaida.fm/";
                share.putExtra(Intent.EXTRA_TEXT, shareBody);
                share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporary_file.jpg"));
                startActivity(Intent.createChooser(share, "Share Image"));
            }
        });

        progress1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                progress1.setProgress(progress);
                progreso = progress;
            }

            /**
             * When user starts moving the progress handler
             * */
            public void onStartTrackingTouch(SeekBar seekBar) {
                // remove message Handler from updating progress bar
                mHandler.removeCallbacks(mUpdateTimeTask);
            }

            /**
             * When user stops moving the progress hanlder
             * */
            public void onStopTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mUpdateTimeTask);
                int totalDuration = player.getDuration();
                int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);
                // forward or backward to certain seconds
                player.seekTo(progreso);
                daoAccessDetalle.UpdateDuracion(progreso + "");
                // update timer progress again
                updateProgressBar();
            }

        });

        btnmax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress2.setProgress(progress2.getProgress() + 1);
            }
        });

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (servidor == 0) {

                    pausaPlay.pausa4(ActivityDetallePost44.this);
                    pausaPlay.pausa(ActivityDetallePost44.this);
                    pausaPlay.inicio(ActivityDetallePost44.this);
                    servidor = 1;
                }
                cursorEstado = daoAccessDetalle.GetAllEstado();
                cursorEstado.moveToPosition(0);
                if (cursorEstado.getString(1).equals("0")) {
                    btnplay.setImageDrawable(getResources().getDrawable(R.mipmap.icon_pausa));
                    daoAccessDetalle.UpdateEstado("1");

                    updateProgressBar();
                    player.start();
                    iniciador = 1;
                } else {
                    iniciador = 0;
                    btnplay.setImageDrawable(getResources().getDrawable(R.mipmap.icon_play));
                    daoAccessDetalle.UpdateEstado("0");
                    //  pausaPlay.pausa2(ActivityDetallePost2.this);
                    daoAccessDetalle.UpdateDuracion(player.getCurrentPosition() + "");
                    player.pause();
                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(iniciador==0){
                    player.stop();
                }
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void getInit() {
        try {

            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(cursor.getString(3));
            player.prepare();
            player.setLooping(true); // Set looping
            player.setVolume(100, 100);
            progress1.setMax((player.getCurrentPosition() * 100) / player.getDuration());
            progress1.setMax(player.getDuration());
            cursorduracion = daoAccessDetalle.GetAllDuracion();
            if(valor.equals("0")) {
                Log.i("errrrrrrrrror", "" + 0);
                daoAccessDetalle.duracionDao.deleteAll();
                DBDuracion duracion = new DBDuracion(
                        Long.parseLong("2"),
                        player.getDuration() + "",
                        player.getCurrentPosition() + "",
                        player.getCurrentPosition() + "", posicion
                );
                daoAccessDetalle.duracionDao.insert(duracion);
            }else{

                player.seekTo( Integer.parseInt(cursorduracion.getString(2)));
                player.start();
            }
            totalDuration = player.getDuration();
            currentDuration = player.getCurrentPosition() / 1000;
            textfin.setText("" + milliSecondsToTimer(totalDuration));
            textinicio.setText("" + milliSecondsToTimer(currentDuration));
        } catch (Exception ex) {
            Log.i("errrrrrrrrror", "" + ex);
        }
    }

    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        Log.i("progreso", "" + currentDuration);
        return currentDuration * 1000;
    }


    public int getProgressPercentage(long currentDuration1, long totalDuration1) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration1 / 1000);
        long totalSeconds = (int) (totalDuration1 / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }


    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        //  Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }


    public class EspecialTask extends AsyncTask<Void, Void, Void> {

        int error;
        MainActivity manito;
        URL url = null;
        Context context;
        String lati, longi, detalle, titulo;
        GET_Portada get_portada;


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
                cursor = daoAccessDetalle.GetAllBusque();
                cursor.moveToPosition(Integer.parseInt(posicion));

                daoAccessDetalle.postPlayDao.deleteAll();
                url = new URL("http://penchoyaida.fm/vivo/cover.jpg");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bm = BitmapFactory.decodeStream(input);

                DBPostPlay postPlay = new DBPostPlay(
                        Long.parseLong(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(3),
                        encodeTobase64(bm),
                        cursor.getString(2),
                        cursor.getString(4)

                );

                daoAccessDetalle.postPlayDao.insert(postPlay);

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

            } catch (Exception ex) {

            }


        }
    }

    private void initControls() {
        try {

            audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
            progress2.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            progress2.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            progress2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    /**
     * Background Runnable thread
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = player.getDuration();
            long currentDuration = player.getCurrentPosition();
            long falta = player.getDuration() - player.getCurrentPosition();


            // Displaying Total Duration time
            textfin.setText("" + utils.milliSecondsToTimer(falta));

            // Displaying time completed playing

            textinicio.setText("" + utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = player.getCurrentPosition();

            progress1.setProgress(progress);
            daoAccessDetalle.UpdateDuracion(progress + "");
            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };

    /**
     *
     * */

    @Override
    protected void onPause() {
        Log.i("curosr", "----" + iniciador);

        if (iniciador == 1) {
            daoAccessDetalle.UpdateDuracion(player.getCurrentPosition() + "");
            cursorduracion = daoAccessDetalle.GetAllDuracion();
            cursorduracion.moveToPosition(0);
            player.pause();
            pausaPlay.play2(ActivityDetallePost44.this);
        } else {

        }
        super.onPause();
    }


    @Override
    public void onResume() {
        try {
            if (iniciador == 1) {
                pausaPlay.pausa4(ActivityDetallePost44.this);
                cursorduracion = daoAccessDetalle.GetAllDuracion();
                cursorduracion.moveToPosition(0);
                player.seekTo(Integer.parseInt(cursorduracion.getString(2)));
                player.start();
               // pausaPlay.inicio(ActivityDetallePost2.this);
            } else {

            }
            if(valor.equals("1")) {
                if (cursorduracion.getCount() != 0) {
                    cursorduracion.moveToPosition(0);
                    Log.i("duracion", "" + cursorduracion.getString(2));
                    player.seekTo(Integer.parseInt(cursorduracion.getString(2)));
                    player.start();
                    //  player.seekTo(Integer.parseInt(cursorduracion.getString(2)));
                }
            }


            cursorEstado = daoAccessDetalle.GetAllEstado();
            cursorEstado.moveToPosition(0);
            if (cursorEstado.getString(1).equals("1")) {
                btnplay.setImageDrawable(getResources().getDrawable(R.mipmap.icon_pausa));
                daoAccessDetalle.UpdateEstado("1");
                iniciador = 1;
            } else {
                iniciador = 0;
                btnplay.setImageDrawable(getResources().getDrawable(R.mipmap.icon_play));
                daoAccessDetalle.UpdateEstado("0");
            }
        } catch (Exception ex) {

        }
        super.onResume();
    }


}
