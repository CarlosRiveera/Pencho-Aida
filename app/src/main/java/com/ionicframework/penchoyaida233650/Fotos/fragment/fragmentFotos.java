package com.ionicframework.penchoyaida233650.Fotos.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightGridView;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import com.ionicframework.penchoyaida233650.Fotos.activity.Activity_Album_Detalle;
import com.ionicframework.penchoyaida233650.Fotos.adapter.fotosPrincipal_Adapter;
import com.ionicframework.penchoyaida233650.Fotos.db.PhotosetDao;
import com.ionicframework.penchoyaida233650.Fotos.ws.Get_photoset;
import com.ionicframework.penchoyaida233650.R;
import com.ionicframework.penchoyaida233650.db.DaoMaster;
import com.ionicframework.penchoyaida233650.db.DaoSession;


/**
 * Created by Alex on 17/05/2016.
 */
public class fragmentFotos extends Fragment {
    public ProgressDialog loading;
    int error;
    View rootView;
    TextView TextoCabecera;
    DisplayImageOptions options;
    public ExpandableHeightGridView gridView;
    public ImageView ImagenPrincipal;
    fotosPrincipal_Adapter fotosPrincipal_adapterV;
    Cursor cursorFotosPrincipal;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    Cursor cursorPrincipal, CursorListado;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_fotos, container, false);

        try {
            getActivity().setTitle("");
            imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
            options = new DisplayImageOptions.Builder()
                    .cacheInMemory().cacheOnDisc()
                    .build();
            ImagenPrincipal = (ImageView) rootView.findViewById(R.id.ImagenCabecera);
            TextoCabecera = (TextView) rootView.findViewById(R.id.TextoCabecera);
            gridView = (ExpandableHeightGridView) rootView.findViewById(R.id.GridViewAlbum);
            new GetFotos().execute();
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(), "penchoaida.sqlite", null);
            final SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            DaoSession daoSession = daoMaster.newSession();
            final PhotosetDao photosetDao = daoSession.getPhotosetDao();
           /* cursorFotosPrincipal = db.query(photosetDao.getTablename(), photosetDao.getAllColumns(), null, null, null, null, null);
            cursorFotosPrincipal.moveToFirst();
            imageLoader.displayImage(cursorFotosPrincipal.getString(11), ImagenPrincipal, options, new ImageLoadingListener() {
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


            });*/
            ImagenPrincipal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cursorFotosPrincipal = db.query(photosetDao.getTablename(), photosetDao.getAllColumns(), null, null, null, null, null);
                    if (cursorFotosPrincipal.moveToFirst()) {
                        Intent a = new Intent(getActivity(), Activity_Album_Detalle.class);
                        a.putExtra("idAlbum", cursorFotosPrincipal.getString(1));
                        a.putExtra("urlImagenCabecera", cursorFotosPrincipal.getString(11));
                        a.putExtra("titu", cursorFotosPrincipal.getString(7));
                        startActivity(a);
                    }
                }
            });


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    cursorFotosPrincipal = db.query(photosetDao.getTablename(), photosetDao.getAllColumns(), null , null, null, null, null);
                    if (cursorFotosPrincipal.moveToFirst()) {
                        cursorFotosPrincipal.moveToPosition(position);
                        Intent a = new Intent(getActivity(), Activity_Album_Detalle.class);
                        a.putExtra("idAlbum", cursorFotosPrincipal.getString(1));
                        a.putExtra("urlImagenCabecera", cursorFotosPrincipal.getString(11));
                        a.putExtra("titu", cursorFotosPrincipal.getString(7));
                        startActivity(a);
                    }
                }
            });
        } catch (Exception ex) {

        }
        return rootView;
    }


    private class GetFotos extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            try {
                loading = new ProgressDialog(getActivity());
                loading.setMessage("Cargando");
                loading.setCancelable(false);
                loading.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            Get_photoset get_photoset = new Get_photoset();
            get_photoset.Get_photoset(getActivity());
            error = get_photoset.error;
            System.out.println("el error obtenido es " + error);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(), "penchoaida.sqlite", null);
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            DaoSession daoSession = daoMaster.newSession();
            PhotosetDao photosetDao = daoSession.getPhotosetDao();
            cursorFotosPrincipal = db.query(photosetDao.getTablename(), photosetDao.getAllColumns(), null, null, null, null, null);
            if (cursorFotosPrincipal.moveToFirst()) {
                cursorFotosPrincipal.moveToNext();
                fotosPrincipal_adapterV = new fotosPrincipal_Adapter(getActivity(), cursorFotosPrincipal);
                gridView.setAdapter(fotosPrincipal_adapterV);
                gridView.setExpanded(true);
            }

            if (cursorFotosPrincipal.moveToFirst()) {
                cursorFotosPrincipal.moveToPosition(0);
                TextoCabecera.setText(cursorFotosPrincipal.getString(7));

            }


            cursorFotosPrincipal = db.query(photosetDao.getTablename(), photosetDao.getAllColumns(), null, null, null, null, null);
            cursorFotosPrincipal.moveToFirst();
            imageLoader.displayImage(cursorFotosPrincipal.getString(11), ImagenPrincipal, options, new ImageLoadingListener() {
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
            loading.dismiss();
        }
    }
}