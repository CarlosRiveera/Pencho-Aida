package com.ionicframework.penchoyaida233650;


import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;


import android.support.design.widget.NavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.ionicframework.penchoyaida233650.Fotos.fragment.fragmentFotos;
import com.ionicframework.penchoyaida233650.club.fragment.FragmentOyente;
import com.ionicframework.penchoyaida233650.nosotros.fragment.FragmentNosotros;
import com.ionicframework.penchoyaida233650.play.db.DBEstado;
import com.ionicframework.penchoyaida233650.play.db.DaoAccessCancion;
import com.ionicframework.penchoyaida233650.play.fragment.FragmentPrincipal;
import com.ionicframework.penchoyaida233650.podacast_exclusivos.fragment.fragment_podcastExclusivos;
import com.ionicframework.penchoyaida233650.podcast.fragment.fragment_podcast;

import com.ionicframework.penchoyaida233650.podcast_deportes.fragment.fragment_podcast_deportes;
import com.ionicframework.penchoyaida233650.videos.fragment.fragment_videos;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    int bandera=0;
    ImageView imagenpencho;
    TextView texttitulo;
    DaoAccessCancion daoAccessCancion;
    String valor="0";
    public ProgressDialog loading;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        daoAccessCancion = new DaoAccessCancion();
        daoAccessCancion.Instantiate(MainActivity.this);
        cursor = daoAccessCancion.GetAllEstado();

        imagenpencho = (ImageView) findViewById(R.id.imagenpencho);
        texttitulo   = (TextView)  findViewById(R.id.texttitulo);
        Bundle extra = MainActivity.this.getIntent().getExtras();
        try {
            valor = (String) extra.get("valor");
        }catch (NullPointerException ex){
            valor="1";
        }
        if(cursor.getCount()!=0) {
            cursor.moveToPosition(0);
            if (cursor.getString(2).equals("3")) {
                daoAccessCancion.estadoDao.deleteAll();
                DBEstado estado = new DBEstado(Long.parseLong("0"), "1", "3");
                daoAccessCancion.estadoDao.insert(estado);
            } else {
                if (valor.equals("0")) {
                    daoAccessCancion.estadoDao.deleteAll();
                    DBEstado estado = new DBEstado(Long.parseLong("1"), "0", "1");
                    daoAccessCancion.estadoDao.insert(estado);
                } else {
                    daoAccessCancion.estadoDao.deleteAll();
                    DBEstado estado = new DBEstado(Long.parseLong("1"), "1", "1");
                    daoAccessCancion.estadoDao.insert(estado);
                }
            }
        }else{
                daoAccessCancion.estadoDao.deleteAll();
                DBEstado estado = new DBEstado(Long.parseLong("1"), "0", "1");
                daoAccessCancion.estadoDao.insert(estado);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showFragment();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btnenvivo) {
            displayView(0);
            imagenpencho.setVisibility(View.VISIBLE);
            texttitulo.setVisibility(View.GONE);
        } else if (id == R.id.btnradio) {
            displayView(2);
            imagenpencho.setVisibility(View.GONE);
            texttitulo.setVisibility(View.VISIBLE);
            texttitulo.setText(getResources().getString(R.string.podcasts));
        } else if (id == R.id.btndeport) {
            displayView(3);
            imagenpencho.setVisibility(View.GONE);
            texttitulo.setVisibility(View.VISIBLE);
            texttitulo.setText(getResources().getString(R.string.podcasts));
        } else if (id == R.id.btnfoto) {
            displayView(4);
            imagenpencho.setVisibility(View.GONE);
            texttitulo.setVisibility(View.VISIBLE);
            texttitulo.setText(getResources().getString(R.string.fotos));
        } else if (id == R.id.btnvideo) {
            displayView(5);
            imagenpencho.setVisibility(View.GONE);
            texttitulo.setVisibility(View.VISIBLE);
            texttitulo.setText(getResources().getString(R.string.videos));
        } else if (id == R.id.btnnosotros) {
            displayView(6);
            imagenpencho.setVisibility(View.GONE);
            texttitulo.setVisibility(View.VISIBLE);
            texttitulo.setText(getResources().getString(R.string.nosotros));
        }else if(id == R.id.btnclub){
            displayView(7);
            imagenpencho.setVisibility(View.GONE);
            texttitulo.setVisibility(View.VISIBLE);
            texttitulo.setText(getResources().getString(R.string.club));
        }else if (id == R.id.PodcastExlusivos) {
            displayView(1);
            imagenpencho.setVisibility(View.GONE);
            texttitulo.setVisibility(View.VISIBLE);
            texttitulo.setText(getResources().getString(R.string.podcasts));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


        public void showFragment () {
            // Currently selected country
            if (bandera == 0) {
                FragmentPrincipal fragment = new FragmentPrincipal();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment).commit();
            }
        }

    public void displayView(int position) {
        Fragment fragment = null;
        String title = "";
        switch (position) {
            case 0:
                fragment = new FragmentPrincipal();
               // title = "Pencho y Aída FM(En vivo)";
                break;
            case 1:
                fragment = new fragment_podcastExclusivos();
             //   title = "Podcast exclusivo";
                break;
            case 2:
                fragment = new fragment_podcast();
             //   title = "Programas de radio";
                break;
            case 3:
                fragment = new fragment_podcast_deportes();
               // title = "Podcast deportivos";
                break;
            case 4:
                fragment = new fragmentFotos();
               // title = "Fotos";
                break;
            case 5:
                fragment = new fragment_videos();
               // title= "Videos";
                break;
            case 6:
                fragment = new FragmentNosotros();
             //   title= "Nosotros";
                break;
            case 7:
                fragment = new FragmentOyente();
             //   title= "Club de oyentes";
                break;
            default:
                break;
        }

       if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle(title);
        }
    }



}
