package com.ionicframework.penchoyaida233650.Fotos.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import com.ionicframework.penchoyaida233650.R;
import com.ionicframework.penchoyaida233650.util.ImageviewAdapter;


/**
 * Created by Alex on 17/05/2016.
 */
public class imageAdapter2 extends BaseAdapter {

    private LayoutInflater inflater;
    private final Context contexto;
    private int itemBackground;
    Cursor cursor;
    DisplayImageOptions options;
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    public imageAdapter2(Context context, Cursor cursor) {
        this.contexto = context;
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .cacheInMemory().cacheOnDisc()
                .build();
        this.inflater = LayoutInflater.from(context);
        this.cursor = cursor;
        TypedArray a =context.obtainStyledAttributes(R.styleable.MyGallery);
        itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
        a.recycle();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (convertView == null) {
            view = inflater.inflate(R.layout.item_fotos, null);
            holder = new ViewHolder();
            holder.imagenes = (ImageView) view.findViewById(R.id.imagenes);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        cursor.moveToPosition(position);
        Log.i("imagenes",cursor.getString(3));
        imageLoader.displayImage(cursor.getString(3), holder.imagenes, options, new ImageLoadingListener(){
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
        return view;
    }

    private class ViewHolder {

        public ImageView imagenes;
    }
}
