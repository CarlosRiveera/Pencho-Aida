package com.ionicframework.penchoyaida233650.detallepost.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.ionicframework.penchoyaida233650.MainActivity;
import com.ionicframework.penchoyaida233650.R;
import com.ionicframework.penchoyaida233650.detallepost.db.DBPostPlay;
import com.ionicframework.penchoyaida233650.detallepost.db.DaoAccessDetalle;
import com.ionicframework.penchoyaida233650.detallepost.util.Global;
import com.ionicframework.penchoyaida233650.play.db.DBEstado;
import com.ionicframework.penchoyaida233650.play.util.PausaPlay;
import com.ionicframework.penchoyaida233650.play.ws.GET_Portada;

/**
 * Created by Santiago on 13/05/2016.
 */
public class ActivityDetallePost extends ActionBarActivity {

    Cursor cursor, cursorEstado, cursorpost, cursorduracion;
    String posicion;
    int valores,progreso=0;
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
    int totalDuration,currentDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallepost);

        pausaPlay = new PausaPlay();
        daoAccessDetalle = new DaoAccessDetalle();
        daoAccessDetalle.Instantiate(this);
        global = new Global();
        daoAccessDetalle.estadoDao.deleteAll();
        DBEstado estado = new DBEstado(Long.parseLong("0"), "0", "");
        daoAccessDetalle.estadoDao.insert(estado);

        imagenpri = (ImageView) findViewById(R.id.imagenpri);
        btnquinmenos = (ImageView) findViewById(R.id.btnquinmenos);
        btnunomenos = (ImageView) findViewById(R.id.btnunomenos);
        btnplay = (ImageView) findViewById(R.id.btnplay);
        btnunomas = (ImageView) findViewById(R.id.btnunomas);
        btnquincemas = (ImageView) findViewById(R.id.btnquincemas);
        btncompartir = (ImageView) findViewById(R.id.btncompartir);
        btnmin = (ImageView) findViewById(R.id.btnmin);
        btnmax = (ImageView) findViewById(R.id.btnmax);

        progress1 = (SeekBar) findViewById(R.id.progress1);
        progress2 = (SeekBar) findViewById(R.id.progress2);

        textinicio = (TextView) findViewById(R.id.textinicio);
        textTitulo = (TextView) findViewById(R.id.textTitulo);
        textfin = (TextView) findViewById(R.id.textfin);
        textdetalle = (TextView) findViewById(R.id.textdetalle);


        Bundle extra = ActivityDetallePost.this.getIntent().getExtras();
        posicion = (String) extra.get("posicion");

        imageLoader.init(ImageLoaderConfiguration.createDefault(ActivityDetallePost.this));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.fondo_pencho)
                .showImageForEmptyUri(R.mipmap.fondo_pencho)
                .cacheInMemory().cacheOnDisc()
                .build();
        new EspecialTask().execute();

        cursor = daoAccessDetalle.GetAllPost();
        cursor.moveToPosition(Integer.parseInt(posicion));
        try {


            imageLoader.displayImage(cursor.getString(4), imagenpri, options);
            textinicio.setText("0:00");
            textTitulo.setText(cursor.getString(1));
            textfin.setText(cursor.getString(10));
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

        btnmax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress2.setProgress(progress2.getProgress() + 1);
            }
        });

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursorEstado = daoAccessDetalle.GetAllEstado();
                cursorEstado.moveToPosition(0);
                if (cursorEstado.getString(1).equals("0")) {
                    btnplay.setImageDrawable(getResources().getDrawable(R.mipmap.icon_pausa));
                    daoAccessDetalle.UpdateEstado("1");
                    pausaPlay.play2(ActivityDetallePost.this);
                    //  progress1.setMax(pausaPlay.val());

                    repetir();
                } else {
                    progreso=0;
                    btnplay.setImageDrawable(getResources().getDrawable(R.mipmap.icon_play));
                    daoAccessDetalle.UpdateEstado("0");
                    pausaPlay.pausa2(ActivityDetallePost.this);
                }
            }
        });

      /*  startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);*/

        progress1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progreso=1;
                Log.i("timedsadasdr", progreso+"");
            }
        });




        progress1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @SuppressLint("NewApi")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressV,
                                          boolean fromUser) {

                        //p1.setRotation((float) (progressV * 1));
                        Log.i("u", "" + progressV);
                        cursorEstado = daoAccessDetalle.GetAllEstado();
                        cursorEstado.moveToPosition(0);
                        //p1.setRotation(f + 243);
                        if (ban == 0) {
                            daoAccessDetalle.UpdateDuracion(progressV + "");
                            pausaPlay.pausa3(ActivityDetallePost.this);
                        } else {
                            ban = 0;
                            btnplay.setImageDrawable(getResources().getDrawable(R.mipmap.icon_pausa));
                            daoAccessDetalle.UpdateEstado("1");
                            pausaPlay.play2(ActivityDetallePost.this);
                        }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });





        Runnable updateTimerThread = new Runnable() {

            public void run() {

                timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

                updatedTime = timeSwapBuff + timeInMilliseconds;

                int secs = (int) (updatedTime / 1000);
                int mins = secs / 60;
                secs = secs % 60;
                int milliseconds = (int) (updatedTime % 1000);
                textinicio.setText("" + mins + ":"
                        + String.format("%02d", secs));
                customHandler.postDelayed(this, 0);
            }

        };
    }





    public void repetir2() {


        timer2 = new CountDownTimer(1000, 1000) {

            @SuppressLint("NewApi")
            public void onTick(long millisUntilFinished) {
                Log.i("timer", "seconds remaining: " + millisUntilFinished
                        / 1000);
                valores = progress1.getProgress()+10000;
                Log.i("timer", valores+"");
            }

            public void onFinish() {

                    progress1.setProgress(valores);

                repetir2();
                //new PostDataTask(token, idtaxista, idsolicitud, "3").execute();
                //isRunning = false;
            }
        }.start();

    }


    public void repetir() {


        timer1 = new CountDownTimer(3000, 1000) {

            @SuppressLint("NewApi")
            public void onTick(long millisUntilFinished) {
                Log.i("timer", "seconds remaining: " + millisUntilFinished
                        / 1000);
            }

            public void onFinish() {
                Log.i("timer", "done!");
                cursorduracion = daoAccessDetalle.GetAllDuracion();
                cursorduracion.moveToPosition(0);
                if (cursorduracion.getCount() != 0) {
                    Log.i("timer", cursorduracion.getString(2));
                    progress1.setMax(Integer.parseInt(cursorduracion.getString(2)));
                    timer1.cancel();
                    progreso=1;
                    repetir2();
                } else {
                    repetir();
                }
                //new PostDataTask(token, idtaxista, idsolicitud, "3").execute();
                //isRunning = false;
            }
        }.start();

    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }


    public class EspecialTask extends AsyncTask<Void, Void, Void> {
        Bitmap bm = null;
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
            cursor = daoAccessDetalle.GetAllPost();
            cursor.moveToPosition(Integer.parseInt(posicion));

                daoAccessDetalle.postPlayDao.deleteAll();
                url = new URL(cursor.getString(4));
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
                        cursor.getString(11),
                        cursor.getString(8)

                );
                Log.i("ok", cursor.getString(3));
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

    @Override
    public void onResume() {
        cursorEstado = daoAccessDetalle.GetAllEstado();
        cursorEstado.moveToPosition(0);
        if (cursorEstado.getString(1).equals("1")) {
            btnplay.setImageDrawable(getResources().getDrawable(R.mipmap.icon_pausa));
            daoAccessDetalle.UpdateEstado("1");

        } else {

            btnplay.setImageDrawable(getResources().getDrawable(R.mipmap.icon_play));
            daoAccessDetalle.UpdateEstado("0");
        }
        super.onResume();
    }
}
