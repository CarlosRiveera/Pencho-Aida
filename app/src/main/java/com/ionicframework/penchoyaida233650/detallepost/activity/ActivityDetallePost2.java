package com.ionicframework.penchoyaida233650.detallepost.activity;

import android.app.ProgressDialog;
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
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Timer;
import java.util.TimerTask;

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
import com.ionicframework.penchoyaida233650.util.Milisegundos;
import me.biubiubiu.justifytext.library.JustifyTextView;

/**
 * Created by Santiago on 13/05/2016.
 */
public class ActivityDetallePost2 extends ActionBarActivity {

    Cursor cursor, cursorEstado, cursorpost, cursorduracion, cursorvalidacion;
    String posicion, valor, cambio;
    int progreso = 0;
    int a = 0;
    int iniciador = 0, servidor = 0;
    DaoAccessDetalle daoAccessDetalle;
    ImageView imagenpri,btnback;
    PausaPlay pausaPlay;
    Boolean dado = true;
    Bitmap bm = null;
    Milisegundos milisegundos;
    private AudioManager audioManager = null;
    SeekBar progress1, progress2 = null;
    JustifyTextView textdetalle;
    TextView textinicio, textTitulo, textfin;
    ImageView btnquinmenos, btnunomenos, btnplay, btnunomas, btnquincemas, btncompartir, btnmin, btnmax;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    URL url = null;
    Utilities utils;
    int totalDuration, currentDuration;
    MediaPlayer player;
    Handler mHandler = new Handler();
    private long splashDelay = 3500;
    private long splashDelay2 = 2000;
    Long vi;
    String va1 = "", var2, var3, var4;
    ProgressDialog loading;
    Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallepost);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_imagen);
        pausaPlay = new PausaPlay();

        daoAccessDetalle = new DaoAccessDetalle();
        daoAccessDetalle.Instantiate(this);
       // getSupportActionBar().setTitle(getResources().getString(R.string.podcaste));
       // getSupportActionBar().setIcon(R.mipmap.banner_aida);
       // getSupportActionBar().setLogo(R.mipmap.banner_aida);
        cursorvalidacion = daoAccessDetalle.GetAllEstado();

       /* View view = getLayoutInflater().inflate(R.layout.layout_imagen, null);
        ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT);
        getSupportActionBar().setCustomView(view, layout);*/

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
        progress1 = (SeekBar) findViewById(R.id.progress1);
        progress2 = (SeekBar) findViewById(R.id.progress2);
        textinicio = (TextView) findViewById(R.id.textinicio);
        textTitulo = (TextView) findViewById(R.id.textTitulo);
        textfin = (TextView) findViewById(R.id.textfin);
        btnback = (ImageView) findViewById(R.id.btnback);
        textdetalle = (JustifyTextView) findViewById(R.id.textdetalle);

        Bundle extra = ActivityDetallePost2.this.getIntent().getExtras();
        posicion = (String) extra.get("posicion");
        valor = (String) extra.get("valor");

        milisegundos = new Milisegundos();

        imageLoader.init(ImageLoaderConfiguration.createDefault(ActivityDetallePost2.this));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.fondo_pencho)
                .showImageForEmptyUri(R.mipmap.fondo_pencho)
                .cacheInMemory().cacheOnDisc()
                .build();
        cursorduracion = daoAccessDetalle.GetAllDuracion();

        new EspecialTask().execute();
        try {
            cursor = daoAccessDetalle.GetAllPost();
            cursor.moveToPosition(Integer.parseInt(posicion));
            imageLoader.displayImage(cursor.getString(4), imagenpri, options);
            textinicio.setText("0:00");
            textTitulo.setText(cursor.getString(1));
            textdetalle.setText("    "+cursor.getString(2)+"\n");
        } catch (Exception ex) {
            finish();
            Toast.makeText(ActivityDetallePost2.this,getResources().getString(R.string.errortrate),Toast.LENGTH_SHORT).show();
            Log.i("error", ""+ex);
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

            }
        });

        progress1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                progress1.setProgress(progress);
                progreso = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mUpdateTimeTask);
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mUpdateTimeTask);
                player.seekTo(progreso);
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
                    pausaPlay.pausa4(ActivityDetallePost2.this);
                    pausaPlay.pausa10(ActivityDetallePost2.this);
                    servidor = 1;
                    new EspecialTask2(0).execute();
                }
                cursorEstado = daoAccessDetalle.GetAllEstado();
                cursorEstado.moveToPosition(0);
                if (cursorEstado.getString(1).equals("0")) {
                    btnplay.setImageDrawable(getResources().getDrawable(R.drawable.btnpause));
                    daoAccessDetalle.UpdateEstado("1");
                    updateProgressBar();
                    player.start();
                    iniciador = 1;
                } else {
                    iniciador = 0;
                    btnplay.setImageDrawable(getResources().getDrawable(R.drawable.btnplay));
                    daoAccessDetalle.UpdateEstado("0");
                    daoAccessDetalle.UpdateDuracion(player.getCurrentPosition() + "");
                    player.pause();
                }
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_favorito2, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.btnFavorito:
                imagenpri.buildDrawingCache();
                imagenpri.buildDrawingCache();
                Bitmap bitmap = imagenpri.getDrawingCache();
                try {
                    File file = new File(imagenpri.getContext().getCacheDir(), bitmap + ".jpg");
                    FileOutputStream fOut = null;
                    fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true, false);
                    final Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    intent.setType("image/png");
                    cursorpost = daoAccessDetalle.GetAllPostPlay();
                    cursorpost.moveToPosition(0);
                    intent.putExtra(Intent.EXTRA_TEXT, "Escuchando en Pencho y Aída:" + String.valueOf(textTitulo.getText()) + " " + cursorpost.getString(2));
                    startActivity(Intent.createChooser(intent, "Compartir.."));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getInit() {
        try {
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(cursorpost.getString(2));
            player.prepare();
            player.setLooping(true); // Set looping
            player.setVolume(100, 100);
            progress1.setMax((player.getCurrentPosition() * 100) / player.getDuration());
            progress1.setMax(player.getDuration());
            cursorduracion = daoAccessDetalle.GetAllDuracion();
            if (valor.equals("0")) {
                if (cursorduracion.getCount() != 0) {
                    cursorduracion.moveToPosition(0);
                    if (cursorduracion.getString(4).equals(posicion) && cursorduracion.getString(0).equals("2")) {
                        iniciador = 1;
                        pausaPlay.pausa(ActivityDetallePost2.this);
                        pausaPlay.pausa3(ActivityDetallePost2.this);
                        btnplay.setImageDrawable(getResources().getDrawable(R.mipmap.icon_pausa));
                        daoAccessDetalle.UpdateEstado("1");
                        updateProgressBar();

                        new EspecialTask2(1).execute();
                    } else {
                        cursorduracion.moveToPosition(0);
                        vi = Long.parseLong("2");
                        va1 = cursorduracion.getString(1);
                        var2 = cursorduracion.getString(2);
                        var3 = cursorduracion.getString(3);
                        var4 = cursorduracion.getString(4);
                        daoAccessDetalle.duracionDao.deleteAll();
                        DBDuracion duracion = new DBDuracion(
                                Long.parseLong("2"),
                                player.getDuration() + "",
                                player.getCurrentPosition() + "",
                                player.getCurrentPosition() + "", posicion
                        );
                        daoAccessDetalle.duracionDao.insert(duracion);
                    }
                } else {
                    daoAccessDetalle.duracionDao.deleteAll();
                    DBDuracion duracion = new DBDuracion(
                            Long.parseLong("2"),
                            player.getDuration() + "",
                            player.getCurrentPosition() + "",
                            player.getCurrentPosition() + "", posicion
                    );
                    daoAccessDetalle.duracionDao.insert(duracion);
                }
            } else {
                cursorduracion.moveToPosition(0);
                btnplay.setImageDrawable(getResources().getDrawable(R.mipmap.icon_pausa));
                daoAccessDetalle.UpdateEstado("1");
                updateProgressBar();
                progress1.setProgress(Integer.parseInt(cursorduracion.getString(2)));
                player.seekTo(Integer.parseInt(cursorduracion.getString(2)));
            }
            totalDuration = player.getDuration();
            currentDuration = player.getCurrentPosition() / 1000;
            textfin.setText("" + milisegundos.milliSecondsToTimer(totalDuration));
            textinicio.setText("" + milisegundos.milliSecondsToTimer(currentDuration));
        } catch (Exception ex) {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            progress2.setProgress(progress2.getProgress() - 1);
            return true;
        } else if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
            progress2.setProgress(progress2.getProgress() + 1);
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }

    public class EspecialTask extends AsyncTask<Void, Void, Void> {

        URL url = null;

        @Override
        protected void onPreExecute() {
            try {
                loading = new ProgressDialog(ActivityDetallePost2.this);
                loading.setMessage(getResources().getString(R.string.cargando));
                loading.setCancelable(false);
                loading.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                cursor = daoAccessDetalle.GetAllPost();
                if (cursor.getCount() != 0) {
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
                            utils.encodeTobase64(bm),
                            cursor.getString(11),
                            cursor.getString(2)
                    );
                    daoAccessDetalle.postPlayDao.insert(postPlay);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalStateException ex){
                finish();
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                finish();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            try {
                cursorpost = daoAccessDetalle.GetAllPostPlay();
                cursorpost.moveToPosition(0);
                getInit();
                loading.dismiss();
            } catch (Exception ex) {
                Log.i("valor","val  "+ex);
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


    public class EspecialTask2 extends AsyncTask<Void, Void, Void> {
        ProgressDialog loading;
        int bandera;

        public EspecialTask2(int bandera) {
            this.bandera = bandera;
        }

        @Override
        protected void onPreExecute() {
            try {
                loading = new ProgressDialog(ActivityDetallePost2.this);
                loading.setMessage(getResources().getString(R.string.cargando));
                loading.setCancelable(false);
                loading.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (bandera == 0) {
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        pausaPlay.inicio(ActivityDetallePost2.this);
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, splashDelay);
            } else {
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        cursorduracion = daoAccessDetalle.GetAllDuracion();
                        cursorduracion.moveToPosition(0);
                        progress1.setProgress(Integer.parseInt(cursorduracion.getString(2)));
                        player.seekTo(Integer.parseInt(cursorduracion.getString(2)));
                        player.start();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, splashDelay);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            loading.dismiss();
        }
    }

    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long currentDuration = player.getCurrentPosition();
            long falta = player.getDuration() - player.getCurrentPosition();
            textfin.setText("" + utils.milliSecondsToTimer(falta));
            textinicio.setText("" + utils.milliSecondsToTimer(currentDuration));
            int progress = player.getCurrentPosition();
            progress1.setProgress(progress);
            mHandler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onPause() {
        a = 1;
        cursorEstado = daoAccessDetalle.GetAllEstado();
        cursorEstado.moveToPosition(0);
        if (cursorEstado.getString(1).equals("1")) {
            iniciador = 1;
        } else {
            iniciador = 0;
        }
        if (iniciador == 1) {
            dado = true;
            cambio = player.getCurrentPosition() + "";
            daoAccessDetalle.UpdateDuracion(cambio + "");
            cursorduracion = daoAccessDetalle.GetAllDuracion();
            cursorduracion.moveToPosition(0);
            new EspecialTask4().execute();
          /*  pausaPlay.play2(ActivityDetallePost2.this);
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    player.pause();
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, splashDelay2);*/
           // player.pause();
        } else {
            player.pause();
            if (!va1.equals("")) {
                daoAccessDetalle.duracionDao.deleteAll();
                DBDuracion duracion = new DBDuracion(
                        vi,
                        va1 + "",
                        var2 + "",
                        var3 + "", var4
                );
                daoAccessDetalle.duracionDao.insert(duracion);
            }
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        a = 0;
        try {
            if (iniciador == 1) {

                new EspecialTask3().execute();
            }
            if (valor.equals("1")) {
                if (cursorduracion.getCount() != 0) {
                    cursorduracion.moveToPosition(0);
                    player.seekTo(Integer.parseInt(cursorduracion.getString(2)));
                    player.start();
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

    public class EspecialTask3 extends AsyncTask<Void, Void, Void> {
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            try {
                loading = new ProgressDialog(ActivityDetallePost2.this);
                loading.setMessage(getResources().getString(R.string.cargando));
                loading.setCancelable(false);
                loading.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
                    pausaPlay.pausa3(ActivityDetallePost2.this);

            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            if (a == 0) {
                cursorduracion = daoAccessDetalle.GetAllDuracion();
                cursorduracion.moveToPosition(0);
                player.seekTo(Integer.parseInt(cursorduracion.getString(2)));
                player.start();
            }
            loading.dismiss();
        }
    }


    public class EspecialTask4 extends AsyncTask<Void, Void, Void> {
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            try {
                loading = new ProgressDialog(ActivityDetallePost2.this);
                loading.setMessage(getResources().getString(R.string.cargando));
                loading.setCancelable(false);
                loading.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            pausaPlay.play2(ActivityDetallePost2.this);

            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            player.pause();
            loading.dismiss();
        }
    }
}
