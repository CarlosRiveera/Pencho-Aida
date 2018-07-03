package com.ionicframework.penchoyaida233650.videos.activity;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import com.ionicframework.penchoyaida233650.R;
import com.ionicframework.penchoyaida233650.play.util.PausaPlay;
import com.ionicframework.penchoyaida233650.util.ImageviewAdapter;
import com.ionicframework.penchoyaida233650.util.YouTubeUtils;


/**
 * Created by Alex on 18/05/2016.
 */
public class Activity_Detalle_videos  extends ActionBarActivity {


    int error;

    DisplayImageOptions options;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    public ProgressDialog loading;

    ImageviewAdapter ImagenCabecera;

    TextView TextoTitulo,TextoDescripcion;
    public String idVideo, Titulo,Descripcion,UrlImagen;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_video);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Videos");
        Bundle extra = Activity_Detalle_videos.this.getIntent().getExtras();
        ImagenCabecera =(ImageviewAdapter) findViewById(R.id.ImagenCabeceraDetalleVideo);
        TextoTitulo = (TextView) findViewById(R.id.TituloVideo);
        TextoDescripcion = (TextView) findViewById(R.id.DescipcionVideo);
        imageLoader.init(ImageLoaderConfiguration.createDefault(Activity_Detalle_videos.this));
        options = new DisplayImageOptions.Builder()
                .cacheInMemory().cacheOnDisc()
                .build();
        idVideo = (String) extra.get("idVideo");
         UrlImagen= (String) extra.get("urlImagen");
        Titulo = (String) extra.get("Titulo");
        Descripcion = (String) extra.get("Descripcion");
        imageLoader.displayImage(UrlImagen, ImagenCabecera, options, new ImageLoadingListener(){
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {

            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }


        });
        TextoTitulo.setText(Titulo);
        TextoDescripcion.setText(Descripcion);

        ImagenCabecera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PausaPlay pausaPlay = new PausaPlay();
                pausaPlay.pausa(Activity_Detalle_videos.this);
                pausaPlay.pausa4(Activity_Detalle_videos.this);
                YouTubeUtils.showYouTubeVideo(idVideo, Activity_Detalle_videos.this);
            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
