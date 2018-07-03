package com.ionicframework.penchoyaida233650.Fotos.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.io.FileOutputStream;

import com.ionicframework.penchoyaida233650.Fotos.db.PhotoAlbumDao;
import com.ionicframework.penchoyaida233650.Fotos.db.PhotoDetalleDao;
import com.ionicframework.penchoyaida233650.R;
import com.ionicframework.penchoyaida233650.db.DaoMaster;
import com.ionicframework.penchoyaida233650.db.DaoSession;
import com.ionicframework.penchoyaida233650.util.TouchImageView;

/**
 * Created by Santiago on 14/07/2016.
 */
@SuppressWarnings("deprecation")
public class ActivityGallery extends ActionBarActivity {
    DisplayImageOptions options2;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    //the images to display
    int posicion=0,ultimo=0;
    Cursor cursor;
    TouchImageView imageView;
    Integer[] imageIDs = {

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        imageView = (TouchImageView) findViewById(R.id.image1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        imageLoader.init(ImageLoaderConfiguration.createDefault(ActivityGallery.this));
        options2 = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.icono_pencho_p)
                .showImageForEmptyUri(R.mipmap.icono_pencho_p)
                .cacheInMemory().cacheOnDisc()
                //.displayer(new RoundedBitmapDisplayer(50))
                .build();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(ActivityGallery.this, "penchoaida.sqlite", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        PhotoDetalleDao photoDetalleDao = daoSession.getPhotoDetalleDao();
        PhotoAlbumDao photoAlbumDao = daoSession.getPhotoAlbumDao();
        cursor = db.query(photoAlbumDao.getTablename(), photoAlbumDao.getAllColumns(), null, null, null, null, null);
        cursor.moveToPosition(0);
        imageLoader.displayImage(cursor.getString(8), imageView, options2);
        ultimo =cursor.getCount();
        // Note that Gallery view is deprecated in Android 4.1---
        final Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        gallery.setAdapter(new ImageAdapters(this));
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {

                // display the images selected
                posicion = position;
                cursor.moveToPosition(position);
                Log.i("cursor",""+cursor.getString(8));
                imageLoader.displayImage(cursor.getString(8), imageView, options2);
                //imageView.setImageResource(imageIDs[position]);
            }
        });

        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicion = position;
                cursor.moveToPosition(position);
                Log.i("cursor",""+cursor.getString(8));
                imageLoader.displayImage(cursor.getString(8), imageView, options2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(posicion+1<ultimo) {
                    gallery.setSelection(posicion + 1);
                }
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

    public class ImageAdapters extends BaseAdapter {
        private Context context;
        private int itemBackground;
        public ImageAdapters(Context c)
        {
            context = c;
            // sets a grey background; wraps around the images
            TypedArray a =obtainStyledAttributes(R.styleable.PicGallery);
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
            //imageView.setImageResource(imageIDs[position]);
            cursor.moveToPosition(position);
            Log.i("imagenes","---------"+cursor.getString(8));
            imageLoader.displayImage(cursor.getString(8), imageView, options2);
            imageView.setLayoutParams(new Gallery.LayoutParams(100, 100));
            imageView.setBackgroundResource(itemBackground);
            return imageView;
        }
    }
}
