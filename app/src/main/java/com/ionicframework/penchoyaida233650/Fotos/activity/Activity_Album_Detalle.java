package com.ionicframework.penchoyaida233650.Fotos.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.etsy.android.grid.StaggeredGridView;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightGridView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import com.ionicframework.penchoyaida233650.Fotos.adapter.GalleryAdapter;
import com.ionicframework.penchoyaida233650.Fotos.adapter.ImageAdapter;
import com.ionicframework.penchoyaida233650.Fotos.adapter.fotosAlbum_Adapter;
import com.ionicframework.penchoyaida233650.Fotos.adapter.imageAdapter2;
import com.ionicframework.penchoyaida233650.Fotos.db.PhotoAlbumDao;
import com.ionicframework.penchoyaida233650.Fotos.db.PhotoDetalleDao;
import com.ionicframework.penchoyaida233650.Fotos.db.PhotosetDao;
import com.ionicframework.penchoyaida233650.Fotos.fragment.CustomDialogClassFotoDetalle;
import com.ionicframework.penchoyaida233650.Fotos.fragment.CustomDialogClassFotoDetalle2;
import com.ionicframework.penchoyaida233650.Fotos.ws.Get_DescripcionFoto;
import com.ionicframework.penchoyaida233650.Fotos.ws.Get_FotosAlbum;
import com.ionicframework.penchoyaida233650.R;
import com.ionicframework.penchoyaida233650.db.DaoMaster;
import com.ionicframework.penchoyaida233650.db.DaoSession;
import com.ionicframework.penchoyaida233650.util.ImageviewAdapter;


/**
 * Created by Alex on 17/05/2016.
 */
public class Activity_Album_Detalle extends ActionBarActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    int error;
    String titu;
    Cursor cursor;
    private final int RESULT_CODE_ALERT = 1;
    GalleryAdapter galleryadapter;
    DisplayImageOptions options;
    TextSliderView textSliderView;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    public ProgressDialog loading;
    public String idAlbum, urlImagenCabecera, urlImagenSeleccionada, idFoto;
    fotosAlbum_Adapter FotosAlbum_Adapter;
    Cursor cursorFotosPrincipal, cursorFotosDetalles;
    ImageviewAdapter ImagenCabecera;
    ExpandableHeightGridView GridViewAlbum;
    TextView TextoCabecera, textoMejorDelDia;
    imageAdapter2 imanita;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_fotos2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Bundle extra = Activity_Album_Detalle.this.getIntent().getExtras();
        imageLoader.init(ImageLoaderConfiguration.createDefault(Activity_Album_Detalle.this));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.icono_pencho_p)
                .showImageForEmptyUri(R.mipmap.icono_pencho_p)
                .cacheInMemory().cacheOnDisc()
                .build();

        idAlbum = (String) extra.get("idAlbum");
        urlImagenCabecera = (String) extra.get("urlImagenCabecera");
        titu = (String) extra.get("titu");
        getSupportActionBar().setTitle(titu);

        ImagenCabecera = (ImageviewAdapter) findViewById(R.id.ImagenCabecera);
        TextoCabecera = (TextView) findViewById(R.id.TextoCabecera);
        textoMejorDelDia = (TextView) findViewById(R.id.textoMejorDelDia);
        if (textoMejorDelDia != null) {
            textoMejorDelDia.setVisibility(View.GONE);
        }
       textSliderView = new TextSliderView(this);
        TextoCabecera.setText(titu);
       // TextoCabecera.setVisibility(View.GONE);
        GridViewAlbum = (ExpandableHeightGridView) findViewById(R.id.GridViewAlbum);
        imageLoader.displayImage(urlImagenCabecera, ImagenCabecera, options, new ImageLoadingListener() {
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
        new GetFotos().execute();

        GridViewAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(Activity_Album_Detalle.this, "penchoaida.sqlite", null);
                SQLiteDatabase db = helper.getWritableDatabase();
                DaoMaster daoMaster = new DaoMaster(db);
                DaoSession daoSession = daoMaster.newSession();
                PhotoAlbumDao photoAlbumDao = daoSession.getPhotoAlbumDao();
                cursorFotosPrincipal = db.query(photoAlbumDao.getTablename(), photoAlbumDao.getAllColumns(), null, null, null, null, null);
                if (cursorFotosPrincipal.moveToFirst()) {
                    cursorFotosPrincipal.moveToPosition(position);
                    idFoto = cursorFotosPrincipal.getString(1);
                    urlImagenSeleccionada = cursorFotosPrincipal.getString(8);
                    new GetDescripcionFotos().execute();
                }
            }
        });

        ImagenCabecera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogClassFotoDetalle2 cdd = new CustomDialogClassFotoDetalle2(Activity_Album_Detalle.this);
                cdd.show();
            }
        });

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(Activity_Album_Detalle.this, "penchoaida.sqlite", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        PhotoDetalleDao photoDetalleDao = daoSession.getPhotoDetalleDao();
        cursorFotosDetalles = db.query(photoDetalleDao.getTablename(), photoDetalleDao.getAllColumns(), null, null, null, null, null);
        imanita = new imageAdapter2(Activity_Album_Detalle.this,cursorFotosDetalles);
        Log.i("imagenes",galleryadapter+"----------");
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

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    private class GetFotos extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            try {
                loading = new ProgressDialog(Activity_Album_Detalle.this);
                loading.setMessage(getResources().getString(R.string.cargando));
                loading.setCancelable(false);
                loading.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            Get_FotosAlbum get_fotosAlbum = new Get_FotosAlbum();
            get_fotosAlbum.Get_FotosAlbum(Activity_Album_Detalle.this, idAlbum);
            error = get_fotosAlbum.error;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                if (error == 3) {
                    DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(Activity_Album_Detalle.this, "penchoaida.sqlite", null);
                    SQLiteDatabase db = helper.getWritableDatabase();
                    DaoMaster daoMaster = new DaoMaster(db);
                    DaoSession daoSession = daoMaster.newSession();
                    PhotoAlbumDao photoAlbumDao = daoSession.getPhotoAlbumDao();
                    cursorFotosPrincipal = db.query(photoAlbumDao.getTablename(), photoAlbumDao.getAllColumns(), null, null, null, null, null);
                    if (cursorFotosPrincipal.moveToFirst()) {
                        cursorFotosPrincipal.moveToNext();
                        FotosAlbum_Adapter = new fotosAlbum_Adapter(Activity_Album_Detalle.this, cursorFotosPrincipal);
                        GridViewAlbum.setAdapter(FotosAlbum_Adapter);
                        GridViewAlbum.setExpanded(true);
                    }
                }
                loading.dismiss();
            } catch (Exception e) {

            }
        }
    }


    private class GetDescripcionFotos extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            try {
                loading = new ProgressDialog(Activity_Album_Detalle.this);
                loading.setMessage(getResources().getString(R.string.cargando));
                loading.setCancelable(false);
                loading.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            Get_DescripcionFoto get_descripcionFoto = new Get_DescripcionFoto();
            get_descripcionFoto.Get_DescripcionFoto(Activity_Album_Detalle.this, idFoto);
            error = get_descripcionFoto.error;
            if (error == 3) {
                System.out.println("la descripcion obtenida es " + get_descripcionFoto);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(Activity_Album_Detalle.this, "penchoaida.sqlite", null);
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            DaoSession daoSession = daoMaster.newSession();
            PhotoDetalleDao photoDetalleDao = daoSession.getPhotoDetalleDao();
            cursorFotosDetalles = db.query(photoDetalleDao.getTablename(), photoDetalleDao.getAllColumns(), null, null, null, null, null);
            if (cursorFotosDetalles.moveToFirst()) {
                CustomDialogClassFotoDetalle cdd = new CustomDialogClassFotoDetalle(Activity_Album_Detalle.this, urlImagenSeleccionada, cursorFotosDetalles.getString(1), cursorFotosDetalles.getString(9), cursorFotosDetalles.getString(13));
                cdd.show();
            }
            loading.dismiss();
        }
    }


    public void AlertInfo(Context context) {
        AlertDialog.Builder builder;
        final AlertDialog alertDialog;
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        builder = new AlertDialog.Builder(context);
        View layout = inflater.inflate(R.layout.layout_gallery, (ViewGroup) findViewById(R.id.layout_root));
        layout.setMinimumWidth(500);
        layout.setMinimumHeight(700);
        builder.setView(layout);
        //final Button ok = (Button) layout.findViewById(R.id.btnok);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        getWindow().setLayout(metrics.widthPixels, metrics.heightPixels);
        alertDialog = builder.create();
        alertDialog.show();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(Activity_Album_Detalle.this, "penchoaida.sqlite", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        PhotoDetalleDao photoDetalleDao = daoSession.getPhotoDetalleDao();
        PhotoAlbumDao photoAlbumDao = daoSession.getPhotoAlbumDao();
        cursorFotosDetalles = db.query(photoAlbumDao.getTablename(), photoAlbumDao.getAllColumns(), null, null, null, null, null);
      /*  ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });*/

        /*final Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        galleryadapter = new GalleryAdapter(this, cursorFotosDetalles);
        gallery.setAdapter(galleryadapter);
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position,long id)
            {
                Toast.makeText(getBaseContext(),"pic" + (position + 1) + " selected",
                        Toast.LENGTH_SHORT).show();
                // display the images selected
                ImageView imageView = (ImageView) findViewById(R.id.image1);
                cursorFotosDetalles.moveToPosition(position);
                Log.i("cursor",""+cursorFotosDetalles.getString(8));
                imageLoader.displayImage(cursorFotosDetalles.getString(8), imageView, options);
               // imageView.setImageResource(imageIDs[position]);
            }
        });*/
        final DisplayImageOptions options;
        final ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(Activity_Album_Detalle.this));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.icono_pencho_p)
                .showImageForEmptyUri(R.mipmap.icono_pencho_p)
                .cacheInMemory().cacheOnDisc()
                //.displayer(new RoundedBitmapDisplayer(50))
                .build();
        final ImageView imageView = (ImageView) findViewById(R.id.image1);


        cursor = db.query(photoAlbumDao.getTablename(), photoAlbumDao.getAllColumns(), null, null, null, null, null);
        cursor.moveToPosition(0);
        //Log.i("cursor1",""+cursor.getString(8));
       // imageLoader.displayImage(cursor.getString(8), imageView, options);

        // Note that Gallery view is deprecated in Android 4.1---
        final Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        gallery.setAdapter(new ImageAdapters(Activity_Album_Detalle.this));
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {

                // display the images selected

                cursor.moveToPosition(position);
                Log.i("cursor",""+cursor.getString(8));
                imageLoader.displayImage(cursor.getString(8), imageView, options);
                //imageView.setImageResource(imageIDs[position]);
            }
        });

        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView = (ImageView) findViewById(R.id.image1);
                cursor.moveToPosition(position);
                Log.i("cursor",""+cursor.getString(8));
                imageLoader.displayImage(cursor.getString(8), imageView, options);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    public class ImageAdapters extends BaseAdapter {
        private Context context;
        private int itemBackground;
        public ImageAdapters(Context c)
        {
            context = c;
            // sets a grey background; wraps around the images
            TypedArray a =obtainStyledAttributes(R.styleable.MyGallery);
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
            imageLoader.displayImage(cursor.getString(8), imageView, options);
            imageView.setLayoutParams(new Gallery.LayoutParams(100, 100));
            imageView.setBackgroundResource(itemBackground);
            return imageView;
        }
    }


}
