package com.ionicframework.penchoyaida233650.Fotos.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.util.Log;
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
 * Created by Santiago on 14/07/2016.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private int itemBackground;
    Cursor cursor;
    DisplayImageOptions options;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    public ImageAdapter(Context c,Cursor cursor)
    {
        this.cursor = cursor;
        context = c;
        imageLoader.init(ImageLoaderConfiguration.createDefault(c));
        options = new DisplayImageOptions.Builder()
                .cacheInMemory().cacheOnDisc()
                .build();
        // sets a grey background; wraps around the images
        TypedArray a =c.obtainStyledAttributes(R.styleable.MyGallery);
        itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
        a.recycle();
    }
    // returns the number of images
    public int getCount() {
        return cursor.getCount();
    }
    // returns the ID of an item
    public Object getItem(int position) {
        return position;
    }
    // returns the ID of an item
    public long getItemId(int position) {
        return position;
    }
    // returns an ImageView view
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = new ImageView(context);
        cursor.moveToPosition(position);
        imageLoader.displayImage(cursor.getString(3), imageView, options);
        //imageView.setImageResource(imageIDs[position]);
        Log.i("imagenes",cursor.getString(3));
        imageView.setLayoutParams(new Gallery.LayoutParams(100, 100));
        imageView.setBackgroundResource(itemBackground);
        return imageView;
    }
}