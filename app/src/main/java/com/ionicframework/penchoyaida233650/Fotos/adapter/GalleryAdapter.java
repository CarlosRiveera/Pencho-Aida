package com.ionicframework.penchoyaida233650.Fotos.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import com.ionicframework.penchoyaida233650.R;

/**
 * Created by PC3IMOVES on 06/01/2016.
 */
public class GalleryAdapter extends BaseAdapter {
    Context context;
    Cursor cursor;
    int background;
    //guardamos las imágenes reescaladas para mejorar el rendimiento ya que estas operaciones son costosas
    //se usa SparseArray siguiendo la recomendación de Android Lint
    SparseArray<Bitmap> imagenesEscaladas = new SparseArray<Bitmap>(7);
    DisplayImageOptions options2;
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    public GalleryAdapter(Context context, Cursor cursor) {
        super();
        this.cursor = cursor;
        this.context = context;
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options2 = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.icono_pencho_p)
                .showImageForEmptyUri(R.mipmap.icono_pencho_p)
                .cacheInMemory().cacheOnDisc()
                        //.displayer(new RoundedBitmapDisplayer(50))
                .build();

        //establecemos un marco para las imágenes (estilo por defecto proporcionado)
        //por android y definido en /values/attr.xml
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.PicGallery);
        background = typedArray.getResourceId(R.styleable.PicGallery_android_galleryItemBackground, 1);
        typedArray.recycle();
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //create the view
        ImageView imageView = new ImageView(context);
        //specify the bitmap at this position in the array
        cursor.moveToPosition(position);
        Log.i("imagenes",cursor.getString(8));
        imageLoader.displayImage(cursor.getString(8), imageView, options2);
       // imageView.setImageResource(imagenes[position]);
        //set layout options
        imageView.setLayoutParams(new Gallery.LayoutParams(160, 160));
        //scale type within view area
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //set default gallery item background
        imageView.setBackgroundResource(background);
        //return the view
        return imageView;
    }

}