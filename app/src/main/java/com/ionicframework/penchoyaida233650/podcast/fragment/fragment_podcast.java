package com.ionicframework.penchoyaida233650.podcast.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.ionicframework.penchoyaida233650.db.DetallePodcastDao;
import com.ionicframework.penchoyaida233650.db.DetallePodcastPrincipalDao;
import com.ionicframework.penchoyaida233650.db.PostcastDao;
import com.ionicframework.penchoyaida233650.db.Postcast_PrincipalesDao;
import com.ionicframework.penchoyaida233650.podacast_exclusivos.activity.buscador_podcastExclusivos;
import com.ionicframework.penchoyaida233650.podacast_exclusivos.adapter.adapter_PodcastExclusivos;
import com.ionicframework.penchoyaida233650.podcast.activity.ActivityDetallePost4;
import com.ionicframework.penchoyaida233650.podcast.activity.buscador_podcastPrincipal;
import com.ionicframework.penchoyaida233650.podcast.ws.GET_podcast;
import com.ionicframework.penchoyaida233650.podcast.ws.Get_PostCastJ;
import com.ionicframework.penchoyaida233650.util.XMlParser;
import me.biubiubiu.justifytext.library.JustifyTextView;

/**
 * Created by Alex on 12/05/2016.
 */
public class fragment_podcast extends Fragment {
    public ProgressDialog loading;
    int posiciones = 0;
    TextView TituloGeneral, TituloSecundario, Pagination;
    JustifyTextView DescripcionGeneral;
    ImageView ImagenGeneral;
    ExpandableHeightListView ListadoPodcast;
    Button btnBuscar, more, back;
    EditText textBuscar;

    LinearLayout contenedor;
    adapter_PodcastExclusivos adaptador;
    DaoMaster.DevOpenHelper helper;
    SQLiteDatabase db;
    DaoMaster daoMaster;
    DaoSession daoSession;
    PostcastDao postcastDao;
    Postcast_PrincipalesDao postcast_principalesDao;
    DetallePodcastDao detallePodcastDao;
    DetallePodcastPrincipalDao detallePodcastPrincipalDao;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
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
        contenedor = (LinearLayout) rootView.findViewById(R.id.contenedor);
        TituloGeneral = (TextView) rootView.findViewById(R.id.TituloPrincipal);
        TituloSecundario = (TextView) rootView.findViewById(R.id.TituloSecundario);
        Pagination = (TextView) rootView.findViewById(R.id.Pagination);
        DescripcionGeneral = (JustifyTextView) rootView.findViewById(R.id.DescripcionGeneralPodcast);
        ImagenGeneral = (ImageView) rootView.findViewById(R.id.ImagenPodcastGeneral);
        ListadoPodcast = (ExpandableHeightListView) rootView.findViewById(R.id.ListadoPodcast);
        btnBuscar = (Button) rootView.findViewById(R.id.buscadorBTN);
        more = (Button) rootView.findViewById(R.id.more);
        back = (Button) rootView.findViewById(R.id.back);
        textBuscar = (EditText) rootView.findViewById(R.id.buscador);

        helper = new DaoMaster.DevOpenHelper(getActivity(), "penchoaida.sqlite", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        postcastDao = daoSession.getPostcastDao();
        postcast_principalesDao = daoSession.getPostcast_PrincipalesDao();
        detallePodcastDao = daoSession.getDetallePodcastDao();
        detallePodcastPrincipalDao = daoSession.getDetallePodcastPrincipalDao();
        getActivity().setTitle("");
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

        textBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contenedor.setVisibility(View.GONE);
            }
        });

        ListadoPodcast.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("entra", "ooo" + position);
                Intent a = new Intent(getActivity(), ActivityDetallePost4.class);
                a.putExtra("posicion", position + "");
                a.putExtra("valor","0");
                startActivity(a);
                posiciones = position;
            }
        });

        ListadoPodcast.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posiciones = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ListadoPodcast.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
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
                CursorListado = db.query(postcast_principalesDao.getTablename(), postcast_principalesDao.getAllColumns(), null, null, null, null, null);
                total_paginas = Math.ceil(CursorListado.getCount() / cantidad_resultados_por_pagina);
                Log.i("paginas","paginas: "+ total_paginas);

                if(pagina>0){
                    more.setVisibility(View.VISIBLE);
                    pagina = pagina -1;
                    total_paginas = Math.ceil(CursorListado.getCount() / cantidad_resultados_por_pagina);
                    empezar_desde = pagina * cantidad_resultados_por_pagina;
                    String cadena1=   empezar_desde+", "+ cantidad_resultados_por_pagina;
                    CursorListado = db.query(postcast_principalesDao.getTablename(), postcast_principalesDao.getAllColumns(), null, null, null, null, null, cadena1+"");
                    int paginaActual = pagina + 1;
                    int paginaTotal = (int) total_paginas;
                    if (CursorListado.getCount() != 0) {

                        Log.i("cantidadpagina","cantidadpagina: "+total_paginas);
                        Log.i("contador","count: "+ CursorListado.getCount());
                        Log.i("cadena","count: "+ cadena1);


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

                CursorListado = db.query(postcast_principalesDao.getTablename(), postcast_principalesDao.getAllColumns(), null, null, null, null, null);
                total_paginas = Math.ceil(CursorListado.getCount() / cantidad_resultados_por_pagina);
                Log.i("paginas","paginas: "+ total_paginas);
                if(pagina<total_paginas){
                    back.setVisibility(View.VISIBLE);
                    pagina = pagina +1;
                    total_paginas = Math.ceil(CursorListado.getCount() / cantidad_resultados_por_pagina);
                    empezar_desde = pagina * cantidad_resultados_por_pagina;
                    String cadena1=   empezar_desde+", "+ cantidad_resultados_por_pagina;
                    CursorListado = db.query(postcast_principalesDao.getTablename(), postcast_principalesDao.getAllColumns(), null, null, null, null, null, cadena1+"");
                    int paginaActual = pagina + 1;
                    int paginaTotal = (int) total_paginas;
                    if (CursorListado.getCount() != 0) {

                        Log.i("cantidadpagina","cantidadpagina: "+total_paginas);
                        Log.i("contador","count: "+ CursorListado.getCount());
                        Log.i("cadena","count: "+ cadena1);

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
        int resultado;
        String myData;
        String htmlText;
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
            Get_PostCastJ convertidor = new Get_PostCastJ();
            convertidor.Get_PostCastJ(getActivity());
            resultado = convertidor.error;
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            if (resultado == 0) {
                cursorPrincipal = db.query(detallePodcastPrincipalDao.getTablename(), detallePodcastPrincipalDao.getAllColumns(), null, null, null, null, null);
                if (cursorPrincipal.getCount() != 0) {

                    cursorPrincipal.moveToPosition(0);
                    TituloGeneral.setText(getActivity().getResources().getString(R.string.pencho));
                    TituloSecundario.setText("Noticias y Politica");
                    imageLoader.displayImage(cursorPrincipal.getString(3), ImagenGeneral, options);
                    Log.i("imageprincipal","imagen"+cursorPrincipal.getString(3));
                    DescripcionGeneral.setText(cursorPrincipal.getString(4)+"\n");

                }
                String cadena1=   empezar_desde+", "+ cantidad_resultados_por_pagina;
                CursorListado = db.query(postcast_principalesDao.getTablename(), postcast_principalesDao.getAllColumns(), null, null, null, null, null);
                Log.i("contador","count: "+ CursorListado.getCount());
                total_paginas = Math.ceil(CursorListado.getCount() / cantidad_resultados_por_pagina);
                if (CursorListado.getCount() != 0) {

                    CursorListado = db.query(postcast_principalesDao.getTablename(), postcast_principalesDao.getAllColumns(), null, null, null, null, null, cadena1+"");

                    empezar_desde = pagina * cantidad_resultados_por_pagina;

                    Log.i("cantidadpagina","cantidadpagina: "+total_paginas);
                    Log.i("contador","count: "+ CursorListado.getCount());
                    Log.i("cadena","count: "+ cadena1);
                    Log.i("paginas","paginas: "+ pagina);

                    int paginaActual = pagina + 1;
                    int paginaTotal = (int) total_paginas;
                    Pagination.setText("Pagina "+ paginaActual + " de " + paginaTotal);

                    adaptador = new adapter_PodcastExclusivos(getActivity(), CursorListado);
                    ListadoPodcast.setAdapter(adaptador);
                    ListadoPodcast.setExpanded(true);

                    back.setVisibility(View.GONE);

                    if (paginaActual==total_paginas)
                    {
                        more.setVisibility(View.GONE);
                        back.setVisibility(View.GONE);
                    }else
                    {

                    }
                }
            }
            loading.dismiss();
        }
    }

}
