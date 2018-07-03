package com.ionicframework.penchoyaida233650.Fotos.fragment;



import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.io.FileOutputStream;

import com.ionicframework.penchoyaida233650.R;
import com.ionicframework.penchoyaida233650.util.ImageviewAdapter;
import com.ionicframework.penchoyaida233650.util.TouchImageView;


/**
 * Created by Alex on 18/05/2016.
 */
public class CustomDialogClassFotoDetalle extends Dialog implements
            View.OnClickListener {

        public Activity c;
        public Dialog d;
        public String urlImagenCabecera, idFoto,Descripcion, Link ;
        public Button yes,cerrar;
        public ImageView compartir;
        public TouchImageView ImagenCompartir;
        public TextView TextoDescripcionImagen;
        DisplayImageOptions options;
        protected ImageLoader imageLoader = ImageLoader.getInstance();
        public CustomDialogClassFotoDetalle(Activity a, String urlImagen, String idFoto, String Descripcion, String Link ) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
            this.urlImagenCabecera =urlImagen;
            this.idFoto = idFoto;
            this.Descripcion = Descripcion;
            this.Link = Link;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_descripcion_foto);
            TextoDescripcionImagen = (TextView) findViewById(R.id.TextoDescripcionImagen);

            String urlImagenDetallePodcast = Descripcion.replace("&quot;","'");
            TextoDescripcionImagen.setText(urlImagenDetallePodcast);
            ImagenCompartir = (TouchImageView) findViewById(R.id.ImagenCompartir);
            compartir = (ImageView) findViewById(R.id.compartirFoto);
            cerrar = (Button) findViewById(R.id.btnCerrar);
            imageLoader.init(ImageLoaderConfiguration.createDefault(c));
            options = new DisplayImageOptions.Builder()
                    .cacheInMemory().cacheOnDisc()
                    .build();
            imageLoader.displayImage(urlImagenCabecera, ImagenCompartir, options, new ImageLoadingListener(){
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

            cerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            compartir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("compartir touch executed");
                    ImagenCompartir.buildDrawingCache();
                    Bitmap bitmap = ImagenCompartir.getDrawingCache();
                    try {
                        File file = new File(ImagenCompartir.getContext().getCacheDir(), bitmap + ".jpg");
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
                        intent.putExtra(Intent.EXTRA_TEXT, "De la galería de Pencho y Aída #fotos  "+Link );
                        c.startActivity(Intent.createChooser(intent, "Compartir.."));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            dismiss();
        }
    }
