package com.ionicframework.penchoyaida233650.videos.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.L;

import com.ionicframework.penchoyaida233650.Fotos.adapter.fotosPrincipal_Adapter;
import com.ionicframework.penchoyaida233650.R;
import com.ionicframework.penchoyaida233650.db.DaoMaster;
import com.ionicframework.penchoyaida233650.db.DaoSession;
import com.ionicframework.penchoyaida233650.util.ImageviewAdapter;
import com.ionicframework.penchoyaida233650.videos.activity.Activity_Detalle_videos;
import com.ionicframework.penchoyaida233650.videos.adapter.adapterVideos_Listado;
import com.ionicframework.penchoyaida233650.videos.db.DetalleVideoDao;
import com.ionicframework.penchoyaida233650.videos.ws.Get_Videos;


/**
 * Created by Alex on 18/05/2016.
 */
public class fragment_videos extends Fragment {
    public ProgressDialog loading;
    int error;
    adapterVideos_Listado AdapterVideos_Listado;
    ExpandableHeightListView ListadoVideos;
    ImageviewAdapter ImagenCabecera;
    TextView TextoCabecera,texttitulo;
    TextView textoMejorDelDia;
    DisplayImageOptions options;
    public GridView gridView;
    public ImageView ImagenPrincipal;
    fotosPrincipal_Adapter fotosPrincipal_adapterV;
    Cursor cursorVideoPrincipal, cursorVideosListados;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    Cursor cursorPrincipal, CursorListado;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        new GETVideos().execute();
        View rootView = inflater.inflate(R.layout.fragmet_videos, container, false);
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(), "penchoaida.sqlite", null);
        SQLiteDatabase db  = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        DetalleVideoDao detalleVideoDao = daoSession.getDetalleVideoDao();

        textoMejorDelDia= (TextView) rootView.findViewById(R.id.textoMejorDelDia);

        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        options = new DisplayImageOptions.Builder()
                .cacheInMemory().cacheOnDisc()
                .build();
        ImagenCabecera = (ImageviewAdapter) rootView.findViewById(R.id.ImagenCabecera);
        ListadoVideos = (ExpandableHeightListView) rootView.findViewById(R.id.ListadoVideos);


        ImagenCabecera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursorVideoPrincipal.moveToFirst();
                Intent detalle = new Intent(getActivity(), Activity_Detalle_videos.class);
                detalle.putExtra("idVideo",cursorVideoPrincipal.getString(2));
                detalle.putExtra("urlImagen",cursorVideoPrincipal.getString(9));
                detalle.putExtra("Titulo",cursorVideoPrincipal.getString(5));
                detalle.putExtra("Descripcion",cursorVideoPrincipal.getString(6));
                startActivity(detalle);
            }
        });


        ListadoVideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursorVideosListados.moveToPosition(position);
                Intent detalle = new Intent(getActivity(), Activity_Detalle_videos.class);
                detalle.putExtra("idVideo",cursorVideosListados.getString(2));
                detalle.putExtra("urlImagen",cursorVideosListados.getString(9));
                detalle.putExtra("Titulo",cursorVideosListados.getString(5));
                detalle.putExtra("Descripcion",cursorVideosListados.getString(6));
               startActivity(detalle);
            }
        });

        return rootView;
    }



    private class GETVideos extends AsyncTask<Void, Void, Void> {


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
            Get_Videos get_videos = new Get_Videos();
            get_videos.Get_photoset(getActivity());
            error = get_videos.error;
            System.out.println("el error obtenido es "+error);

            return null;

        }



        @Override
        protected void onPostExecute(Void result) {

            try{

                DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(), "penchoaida.sqlite", null);
                SQLiteDatabase db  = helper.getWritableDatabase();
                DaoMaster daoMaster = new DaoMaster(db);
                DaoSession daoSession = daoMaster.newSession();
                DetalleVideoDao detalleVideoDao = daoSession.getDetalleVideoDao();
                cursorVideosListados = db.query(detalleVideoDao.getTablename(),detalleVideoDao.getAllColumns(),"_id!=1",null,null,null,null);




                if(cursorVideosListados.moveToFirst()){
                    AdapterVideos_Listado = new adapterVideos_Listado(getActivity(), cursorVideosListados);
                    ListadoVideos.setAdapter(AdapterVideos_Listado);
                    ListadoVideos.setExpanded(true);
                }
                cursorVideoPrincipal = db.query(detalleVideoDao.getTablename(),detalleVideoDao.getAllColumns(),"_id=1",null,null,null,null,null);
                if(cursorVideoPrincipal.moveToFirst()){
                    textoMejorDelDia.setText(cursorVideoPrincipal.getString(5));
                    getActivity().setTitle(cursorVideoPrincipal.getString(5));
                    imageLoader.displayImage(cursorVideoPrincipal.getString(9), ImagenCabecera, options, new ImageLoadingListener(){
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
                }
                loading.dismiss();
            }catch (Exception e){

            }


        }

    }
}
