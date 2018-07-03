package com.ionicframework.penchoyaida233650.play.fragment;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import com.ionicframework.penchoyaida233650.MainActivity;

import com.ionicframework.penchoyaida233650.R;


import com.ionicframework.penchoyaida233650.play.db.DBCancion;

import com.ionicframework.penchoyaida233650.play.db.DaoAccessCancion;
import com.ionicframework.penchoyaida233650.play.util.PausaPlay;
import com.ionicframework.penchoyaida233650.play.ws.GET_Portada;

/**
 * Created by Santiago on 09/05/2016.
 */
public class FragmentPrincipal extends Fragment {
    View rootView;
    int valores = 0;
    Bitmap bm = null;
    private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;
    TextView textTitle, textSub;
    ImageView imagen, btncompartir;
    CountDownTimer timer1;
    DisplayImageOptions options;
    DaoAccessCancion daoAccessCancion;
    Cursor cursor, cursorestado;
    int valor = 0, var2 = 0;
    String cuna;
    PausaPlay pausaPlay;
    ImageView btnplay, btnmin, btnmax;
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    private Socket socket;

    {
        try {
            //184.72.97.80
            //penchoyaida.fm
            socket = IO.socket("http://184.72.97.80:3000");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_principal, container, false);
        daoAccessCancion = new DaoAccessCancion();
        daoAccessCancion.Instantiate(getActivity());
        cursorestado = daoAccessCancion.GetAllEstado();
        cursorestado.moveToPosition(0);
        getActivity().setTitle("");
        pausaPlay = new PausaPlay();

        textTitle = (TextView) rootView.findViewById(R.id.textTitle);
        textSub = (TextView) rootView.findViewById(R.id.textSub);
        imagen = (ImageView) rootView.findViewById(R.id.imagen);
        btnplay = (ImageView) rootView.findViewById(R.id.btnplay);
        btnmin = (ImageView) rootView.findViewById(R.id.btnmin);
        btnmax = (ImageView) rootView.findViewById(R.id.btnmax);
        btncompartir = (ImageView) rootView.findViewById(R.id.btncompartir);
        volumeSeekbar = (SeekBar) rootView.findViewById(R.id.progress);
        if (cursorestado.getString(2).equals("")) {
            btnplay.setImageDrawable(getResources().getDrawable(R.drawable.btnplay));
            daoAccessCancion.UpdateEstado("0");
        } else {
            if (cursorestado.getString(1).equals("1")) {
                btnplay.setImageDrawable(getResources().getDrawable(R.drawable.btnpause));
                daoAccessCancion.UpdateEstado("1");
                pausaPlay.play(getActivity());
                valores = 1;
            } else {
                btnplay.setImageDrawable(getResources().getDrawable(R.drawable.btnplay));
                daoAccessCancion.UpdateEstado("0");
                valores = 0;
            }
        }

        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.fondo_pencho)
                .showImageForEmptyUri(R.mipmap.fondo_pencho)
                .cacheInMemory().cacheOnDisc()
                .build();
        //http://penchoyaida.fm/vivo/cover.jpg
        imageLoader.displayImage("http://penchoyaida.fm/vivo/cover.jpg", imagen, options);


        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valor == 0) {
                    pausaPlay.pausa4(getActivity());
                    valor = 1;
                }
                cursorestado = daoAccessCancion.GetAllEstado();
                cursorestado.moveToPosition(0);
                if (cursorestado.getString(1).equals("0")) {
                    btnplay.setImageDrawable(getResources().getDrawable(R.drawable.btnpause));
                    daoAccessCancion.UpdateEstado2("1", "3");
                    pausaPlay.play(getActivity());
                    valores = 1;
                    socket.connect();
                    socket.on("song", handleIncomingMessages);
                } else {
                    btnplay.setImageDrawable(getResources().getDrawable(R.drawable.btnplay));
                    daoAccessCancion.UpdateEstado2("0", "1");
                    pausaPlay.pausa10(getActivity());
                    valores = 0;
                    socket.close();
                    //socket.on("song", handleIncomingMessages);
                }
            }
        });


        btnmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volumeSeekbar.setProgress(volumeSeekbar.getProgress() - 1);
            }
        });

        btnmax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volumeSeekbar.setProgress(volumeSeekbar.getProgress() + 1);
            }
        });

        btncompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        initControls();


        return rootView;
    }

    private Emitter.Listener handleIncomingMessages = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            try {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = null;
                            JSONObject data = (JSONObject) args[0];
                            String song, artist, album, albumart, message;
                            String imageText;
                            //try {
                            //message = data.getString("nombre").toString();
                            message = data + "";
                            JSONObject msgJson = data.getJSONObject("data");
                            Log.i("message", "" + message);
                            daoAccessCancion.cancionDao.deleteAll();
                            cuna = msgJson.getString("song");
                            if (!cuna.equals("")) {
                                song = msgJson.getString("song");
                                artist = msgJson.getString("artist");
                                album = msgJson.getString("album");
                                albumart = msgJson.getString("albumArt");
                                new EspecialTask(1, song, artist, album, albumart).execute();
                            } else {

                            }



                   /* } catch (JSONException e) {
                        // return;
                        Log.i("Exception",""+e);
                    }*/

                        } catch (JSONException e) {
                            new EspecialTask(2, "", "", "", "").execute();
                            e.printStackTrace();
                        }
                    }
                });
            }catch (Exception ex){

            }
        }
    };


    @Override
    public void onPause() {
        // timer1.cancel();
        var2 = 1;
        if (valores == 1) {
            pausaPlay.inicios(getActivity());
        }
        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_favorito2, menu);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.btnFavorito:
                if (bm != null) {
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
                    String shareBody = "Estoy escuchando: " + textTitle.getText().toString() + " en #PenchoyAidaFM http://www.penchoyaida.fm";
                    share.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporary_file.jpg"));
                    startActivity(Intent.createChooser(share, "Share Image"));
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onResume() {
        var2 = 0;
        //  repetir();
        pausaPlay.pausa22(getActivity());
        cursorestado = daoAccessCancion.GetAllEstado();
        cursorestado.moveToPosition(0);
        if (cursorestado.getString(0).equals("0")) {
            if (cursorestado.getString(1).equals("1")) {
                btnplay.setImageDrawable(getResources().getDrawable(R.drawable.btnpause));
                daoAccessCancion.UpdateEstado("1");
                pausaPlay.play(getActivity());
                valores = 1;
            } else {
                btnplay.setImageDrawable(getResources().getDrawable(R.drawable.btnplay));
                daoAccessCancion.UpdateEstado("0");
                valores = 0;
            }
        } else {
            if (cursorestado.getString(1).equals("1")) {
                btnplay.setImageDrawable(getResources().getDrawable(R.drawable.btnpause));
                daoAccessCancion.UpdateEstado("1");

                valores = 1;
            } else {
                btnplay.setImageDrawable(getResources().getDrawable(R.drawable.btnplay));
                daoAccessCancion.UpdateEstado("0");
                valores = 0;
            }
        }
        super.onResume();
    }

   /* public void repetir() {
        timer1 = new CountDownTimer(5000, 1000) {
            @SuppressLint("NewApi")
            public void onTick(long millisUntilFinished) {
                Log.i("timer", "seconds remaining: " + millisUntilFinished
                        / 1000);
            }
            public void onFinish() {
                try {
                    cursorestado = daoAccessCancion.GetAllEstado();
                    cursorestado.moveToPosition(0);
                        if (cursorestado.getString(1).equals("1")) {
                            btnplay.setImageDrawable(getResources().getDrawable(R.drawable.btnpause));
                            daoAccessCancion.UpdateEstado("1");
                        } else {
                            btnplay.setImageDrawable(getResources().getDrawable(R.drawable.btnplay));
                            daoAccessCancion.UpdateEstado("0");
                        }
                }catch (Exception ex){
                    timer1.cancel();
                }
                if(var2==0) {
                    new EspecialTask().execute();
                }
            }
        }.start();
    }*/


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
            daoAccessCancion.cancionDao.deleteAll();
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
            // repetir();
            try {
                if (error != 4) {
                    if (valores == 1) {
                        cursor = daoAccessCancion.GetAllCancion();
                        cursor.moveToPosition(0);
                        textTitle.setText(cursor.getString(1));
                        textSub.setText(cursor.getString(2));
                        imagen.setImageBitmap(decodeBase64(cursor.getString(4)));
                    }
                    //  pausaPlay.inicios(getActivity());
                }
            } catch (Exception ex) {

            }

        }
    }


    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    private void initControls() {
        try {
            audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
}
