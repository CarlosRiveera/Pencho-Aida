package com.ionicframework.penchoyaida233650.podcast_deportes.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;

import com.ionicframework.penchoyaida233650.R;
import com.ionicframework.penchoyaida233650.db.DaoMaster;
import com.ionicframework.penchoyaida233650.db.DaoSession;
import com.ionicframework.penchoyaida233650.podcast_deportes.activity.ActivityDetallePost3;
import com.ionicframework.penchoyaida233650.podcast_deportes.db.DetallePodcastPrincipalDeportesDao;
import com.ionicframework.penchoyaida233650.podacast_exclusivos.adapter.adapter_PodcastExclusivos;
import com.ionicframework.penchoyaida233650.podcast.activity.buscador_podcastPrincipal;
import com.ionicframework.penchoyaida233650.podcast_deportes.adapter.adapter_PodcastDeportes;
import com.ionicframework.penchoyaida233650.podcast_deportes.db.PostcadDeportesDao;
import com.ionicframework.penchoyaida233650.podcast_deportes.ws.xml_podcastDeportes;
import com.ionicframework.penchoyaida233650.util.XMlParser;
import me.biubiubiu.justifytext.library.JustifyTextView;

/**
 * Created by Alex on 16/05/2016.
 */
public class fragment_podcast_deportes  extends Fragment {
    public int errorObtenido;
    public ProgressDialog loading;
    String URL =
            "http://penchoyaida.com/podcast/rss_itunes_deportes_pya.xml";
    String a;


    TextView TituloGeneral, TituloSecundario, Pagination;
    JustifyTextView DescripcionGeneral;
    ImageView ImagenGeneral;
    ExpandableHeightListView ListadoPodcast;
    Button btnBuscar, more, back;
    EditText textBuscar;
    DisplayImageOptions options;
    adapter_PodcastExclusivos adaptador;
    adapter_PodcastDeportes adapter_podcastDeportes;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    Cursor cursorPrincipal, CursorListado;
    int pagina = 0;
    int cantidad_resultados_por_pagina = 5;
    double total_paginas = 0;
    int empezar_desde = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        new TareaDescargaXml().execute();
        View rootView = inflater.inflate(R.layout.podcast_exclusivo_fragment2, container, false);
        TituloGeneral = (TextView) rootView.findViewById(R.id.TituloPrincipal);
        TituloSecundario =(TextView) rootView.findViewById(R.id.TituloSecundario);
        Pagination = (TextView) rootView.findViewById(R.id.Pagination);
        DescripcionGeneral = (JustifyTextView) rootView.findViewById(R.id.DescripcionGeneralPodcast);
        ImagenGeneral = (ImageView) rootView.findViewById(R.id.ImagenPodcastGeneral);
        ListadoPodcast = (ExpandableHeightListView) rootView.findViewById(R.id.ListadoPodcast);
        btnBuscar = (Button) rootView.findViewById(R.id.buscadorBTN);
        textBuscar = (EditText) rootView.findViewById(R.id.buscador);
        more = (Button) rootView.findViewById(R.id.more);
        back = (Button) rootView.findViewById(R.id.back);
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(), "penchoaida.sqlite", null);
        SQLiteDatabase db  = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        getActivity().setTitle("");
        PostcadDeportesDao postcadDeportesDao = daoSession.getPostcadDeportesDao();

        DetallePodcastPrincipalDeportesDao detallePodcastPrincipalDeportesDao = daoSession.getDetallePodcastPrincipalDeportesDao();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        options = new DisplayImageOptions.Builder()
                .cacheInMemory().cacheOnDisc()
                .build();


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(getActivity(), buscador_podcastPrincipal.class);
                String Texto = String.valueOf(textBuscar.getText());
                c.putExtra("Texto", Texto);
                startActivity(c);
            }
        });

        ListadoPodcast.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent a = new Intent(getActivity(), ActivityDetallePost3.class);
                a.putExtra("posicion", position+"");
                a.putExtra("valor","0");
                startActivity(a);
                getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });

        textBuscar.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            Intent c = new Intent(getActivity(), buscador_podcastPrincipal.class);
                            String Texto = String.valueOf(textBuscar.getText());
                            c.putExtra("Texto", Texto);
                            startActivity(c);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(), "penchoaida.sqlite", null);
                SQLiteDatabase db  = helper.getWritableDatabase();
                DaoMaster daoMaster = new DaoMaster(db);
                DaoSession daoSession = daoMaster.newSession();
                PostcadDeportesDao postcadDeportesDao = daoSession.getPostcadDeportesDao();
                DetallePodcastPrincipalDeportesDao detallePodcastPrincipalDeportesDao = daoSession.getDetallePodcastPrincipalDeportesDao();
                CursorListado = db.query(postcadDeportesDao.getTablename(),postcadDeportesDao.getAllColumns(), null, null, null, null, null);
                total_paginas = Math.ceil(CursorListado.getCount() / cantidad_resultados_por_pagina);
                Log.i("paginas","paginas: "+ total_paginas);
                if(pagina>0){
                    more.setVisibility(View.VISIBLE);
                    pagina = pagina -1;
                    total_paginas = Math.ceil(CursorListado.getCount() / cantidad_resultados_por_pagina);
                    empezar_desde = pagina * cantidad_resultados_por_pagina;
                    String cadena1=   empezar_desde+", "+ cantidad_resultados_por_pagina;
                    CursorListado = db.query(postcadDeportesDao.getTablename(),postcadDeportesDao.getAllColumns(), null, null, null, null, null, cadena1+"");
                    int paginaActual = pagina + 1;
                    int paginaTotal = (int) total_paginas;
                    if (CursorListado.getCount() != 0) {

                        Log.i("cantidadpagina","cantidadpagina: "+total_paginas);
                        Log.i("contador","count: "+ CursorListado.getCount());
                        Log.i("cadena","count: "+ cadena1);
                        Log.i("pagina actual","count: "+ paginaActual);
                        Pagination.setText("Pagina "+ paginaActual + " de " + paginaTotal);
                        adaptador = new adapter_PodcastExclusivos(getActivity(), CursorListado);
                        ListadoPodcast.setAdapter(adaptador);
                        ListadoPodcast.setExpanded(true);

                    }else{

                    }
                    if (paginaActual==1){
                        back.setVisibility(View.GONE);
                    }else{

                    }
                }
                else{
                    back.setVisibility(View.GONE);
                }
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(), "penchoaida.sqlite", null);
                SQLiteDatabase db  = helper.getWritableDatabase();
                DaoMaster daoMaster = new DaoMaster(db);
                DaoSession daoSession = daoMaster.newSession();
                PostcadDeportesDao postcadDeportesDao = daoSession.getPostcadDeportesDao();
                DetallePodcastPrincipalDeportesDao detallePodcastPrincipalDeportesDao = daoSession.getDetallePodcastPrincipalDeportesDao();
                CursorListado = db.query(postcadDeportesDao.getTablename(),postcadDeportesDao.getAllColumns(), null, null, null, null, null);
                total_paginas = Math.ceil(CursorListado.getCount() / cantidad_resultados_por_pagina);
                Log.i("paginas","paginas: "+ total_paginas);
                if(pagina<total_paginas){
                    back.setVisibility(View.VISIBLE);
                    pagina = pagina +1;
                    total_paginas = Math.ceil(CursorListado.getCount() / cantidad_resultados_por_pagina);
                    empezar_desde = pagina * cantidad_resultados_por_pagina;
                    String cadena1=   empezar_desde+", "+ cantidad_resultados_por_pagina;
                    CursorListado = db.query(postcadDeportesDao.getTablename(),postcadDeportesDao.getAllColumns(), null, null, null, null, null, cadena1+"");
                    int paginaActual = pagina + 1;
                    int paginaTotal = (int) total_paginas;
                    if (CursorListado.getCount() != 0) {

                        Log.i("cantidadpagina","cantidadpagina: "+total_paginas);
                        Log.i("contador","count: "+ CursorListado.getCount());
                        Log.i("cadena","count: "+ cadena1);
                        Log.i("pagina actual","count: "+ paginaActual);
                        Pagination.setText("Pagina "+ paginaActual + " de " + paginaTotal);
                        adaptador = new adapter_PodcastExclusivos(getActivity(), CursorListado);
                        ListadoPodcast.setAdapter(adaptador);
                        ListadoPodcast.setExpanded(true);

                    }else{

                    }
                    if (paginaActual==total_paginas){
                        more.setVisibility(View.GONE);
                    }else{

                    }
                }
                else{
                    more.setVisibility(View.GONE);
                }
            }
        });
        return rootView;
    }





    private class TareaDescargaXml extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            try {
                loading = new ProgressDialog(getActivity());
                loading.setMessage(getResources().getString(R.string.cargando));
                loading.setCancelable(false);
                loading.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            xml_podcastDeportes traerPodcast = new xml_podcastDeportes(getActivity());
            traerPodcast.Get_buscar(getActivity());
            errorObtenido = traerPodcast.error;
            return null;

        }


        @Override
        protected void onPostExecute(Void result) {
            try{

                if (errorObtenido==3){
                    DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(), "penchoaida.sqlite", null);
                    SQLiteDatabase db  = helper.getWritableDatabase();
                    DaoMaster daoMaster = new DaoMaster(db);
                    DaoSession daoSession = daoMaster.newSession();
                    PostcadDeportesDao postcadDeportesDao = daoSession.getPostcadDeportesDao();
                    DetallePodcastPrincipalDeportesDao detallePodcastPrincipalDeportesDao = daoSession.getDetallePodcastPrincipalDeportesDao();
                    cursorPrincipal = db.query(detallePodcastPrincipalDeportesDao.getTablename(), detallePodcastPrincipalDeportesDao.getAllColumns(),null,null,null,null,null);
                    if (cursorPrincipal.getCount()>0){
                        if(cursorPrincipal.moveToFirst()){
                            TituloGeneral.setText("Fernando Palomo");

                            imageLoader.displayImage(cursorPrincipal.getString(3),ImagenGeneral, options);
                            DescripcionGeneral.setText(cursorPrincipal.getString(4)+"\n");
                        }
                    }
                    String cadena1=   empezar_desde+", "+ cantidad_resultados_por_pagina;
                    CursorListado = db.query(postcadDeportesDao.getTablename(),postcadDeportesDao.getAllColumns(), null, null, null, null, null);
                    total_paginas = Math.ceil(CursorListado.getCount() / cantidad_resultados_por_pagina);
                    if (CursorListado.getCount()>0){
                        CursorListado = db.query(postcadDeportesDao.getTablename(),postcadDeportesDao.getAllColumns(), null, null, null, null, null, cadena1+"");

                        if(CursorListado.moveToFirst()){
                            adapter_podcastDeportes = new adapter_PodcastDeportes(getActivity(), CursorListado);
                            ListadoPodcast.setAdapter(adapter_podcastDeportes);
                            ListadoPodcast.setExpanded(true);
                            Log.i("cantidadpagina","cantidadpagina: "+total_paginas);
                            Log.i("contador","count: "+ CursorListado.getCount());
                            Log.i("cadena","count: "+ cadena1);
                            Log.i("paginas","paginas: "+ pagina);
                        }
                        empezar_desde = pagina * cantidad_resultados_por_pagina;
                        int paginaActual = pagina + 1;
                        int paginaTotal = (int) total_paginas;
                        Pagination.setText("Pagina "+ paginaActual + " de " + paginaTotal);
                        back.setVisibility(View.GONE);
                        if (paginaActual==total_paginas)
                        {
                            more.setVisibility(View.GONE);
                            back.setVisibility(View.GONE);
                        }else
                        {

                        }
                    }
                    TituloSecundario.setText("Deportes");
                }else{

                }
            }catch (Exception ex){

            }
            loading.dismiss();
        }
    }
}
