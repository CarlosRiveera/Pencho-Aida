package com.ionicframework.penchoyaida233650.podacast_exclusivos.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

import com.ionicframework.penchoyaida233650.R;
import com.ionicframework.penchoyaida233650.db.BusquedasTemporales;
import com.ionicframework.penchoyaida233650.db.BusquedasTemporalesDao;
import com.ionicframework.penchoyaida233650.db.DaoMaster;
import com.ionicframework.penchoyaida233650.db.DaoSession;
import com.ionicframework.penchoyaida233650.detallepost.activity.ActivityDetallePost2;
import com.ionicframework.penchoyaida233650.podacast_exclusivos.adapter.adapter_PodcastExclusivos;
import com.ionicframework.penchoyaida233650.podacast_exclusivos.ws.Get_PostcatBuscados;


public class buscador_podcastExclusivos extends ActionBarActivity {
    adapter_PodcastExclusivos adaptador;
    Cursor cursor;
    public String Texto = "";
    ListView ListadoBusquedas;
    Vector<String> title;
    TextView texterror;
    public ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Podcast");
        Bundle extra = buscador_podcastExclusivos.this.getIntent().getExtras();
        Texto = (String) extra.get("Texto");
        texterror = (TextView) findViewById(R.id.texterror);
        ListadoBusquedas = (ListView) findViewById(R.id.ListadoBusquedas);
        new GetBuscador(buscador_podcastExclusivos.this, Texto).execute();

        ListadoBusquedas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent a = new Intent(buscador_podcastExclusivos.this, ActivityDetallePost22.class);
                a.putExtra("posicion", position + "");
                a.putExtra("valor", "0");
                startActivity(a);
            }
        });
    }


    private class GetBuscador extends AsyncTask<Void, Void, Void> {
        public int errorObtenido;
        public Vector<String> title;
        private Vector<String> link;
        private Vector<String> pubDate;
        private Vector<String> contenido;
        Context context;
        String textoaBuscar;

        public GetBuscador(Context contexto, String texto) {
            this.context = contexto;
            this.textoaBuscar = texto;
        }

        @Override
        protected void onPreExecute() {
            try {
                loading = new ProgressDialog(buscador_podcastExclusivos.this);
                loading.setMessage("Cargando");
                loading.setCancelable(false);
                loading.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            Get_PostcatBuscados a = new Get_PostcatBuscados(context, textoaBuscar);
            a.Get_buscar(buscador_podcastExclusivos.this);
            errorObtenido = a.error;
            System.out.println("Error obtenido " + errorObtenido);
            if (errorObtenido == 3) {
                title = a.title;
                link = a.link;
                pubDate = a.pubDate;
                contenido = a.content;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (errorObtenido == 3) {
                DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(buscador_podcastExclusivos.this, "penchoaida.sqlite", null);
                SQLiteDatabase db = helper.getWritableDatabase();
                DaoMaster daoMaster = new DaoMaster(db);
                DaoSession daoSession = daoMaster.newSession();
                BusquedasTemporalesDao busquedasTemporalesDao = daoSession.getBusquedasTemporalesDao();
                busquedasTemporalesDao.deleteAll();
                for (int j = 0; j < title.size(); j++) {
                    BusquedasTemporales busquedas = new BusquedasTemporales();
                    busquedas.setTitulo(title.get(j));
                    busquedas.setContent(contenido.get(j));
                    busquedas.setLink(link.get(j));
                    busquedas.setPubdate(pubDate.get(j));
                    busquedasTemporalesDao.insert(busquedas);
                }

                cursor = db.query(busquedasTemporalesDao.getTablename(), busquedasTemporalesDao.getAllColumns(), null, null, null, null, null);
                if (cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        adaptador = new adapter_PodcastExclusivos(buscador_podcastExclusivos.this, cursor);
                        ListadoBusquedas.setAdapter(adaptador);
                    }
                }
            } else if (errorObtenido == 1) {
                ListadoBusquedas.setVisibility(View.GONE);
                texterror.setVisibility(View.VISIBLE);
                Toast.makeText(buscador_podcastExclusivos.this, R.string.NoResultados, Toast.LENGTH_LONG).show();
            }
            loading.dismiss();
        }
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

}
