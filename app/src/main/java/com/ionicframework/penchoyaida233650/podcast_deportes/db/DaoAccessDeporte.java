package com.ionicframework.penchoyaida233650.podcast_deportes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ionicframework.penchoyaida233650.db.DaoMaster;
import com.ionicframework.penchoyaida233650.db.DaoSession;
import com.ionicframework.penchoyaida233650.db.PostcastDao;
import com.ionicframework.penchoyaida233650.detallepost.db.DBDuracionDao;
import com.ionicframework.penchoyaida233650.detallepost.db.DBPostPlayDao;
import com.ionicframework.penchoyaida233650.play.db.DBCancionDao;
import com.ionicframework.penchoyaida233650.play.db.DBEstadoDao;

/**
 * Created by Santiago on 17/05/2016.
 */
public class DaoAccessDeporte {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    public DBCancionDao cancionDao;
    public DBEstadoDao estadoDao;
    public PostcastDao postcastDao;
    public DBPostPlayDao postPlayDao;
    public DBDuracionDao duracionDao;
    public PostcadDeportesDao postcadDeportesDao;
    public DetallePodcastPrincipalDeportesDao detallePodcastPrincipalDeportesDao;
    Cursor cursor;


    public void Instantiate(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "penchoaida.sqlite", null);
        db              = helper.getWritableDatabase();
        daoMaster       = new DaoMaster(db);
        daoSession      = daoMaster.newSession();
        cancionDao      = daoSession.getCancionDao();
        estadoDao       = daoSession.getEstadoDao();
        postcastDao     = daoSession.getPostcastDao();
        postPlayDao     = daoSession.getPostPlayDao();
        duracionDao     = daoSession.getDuracionDao();
        detallePodcastPrincipalDeportesDao  = daoSession.getDetallePodcastPrincipalDeportesDao();
        postcadDeportesDao = daoSession.getPostcadDeportesDao();
    }

    public Cursor GetAllPost(){
        cursor = db.query(postcastDao.getTablename(), postcastDao.getAllColumns(), null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }


    public Cursor GetAlldetalle(){
        cursor = db.query(postcadDeportesDao.getTablename(), postcadDeportesDao.getAllColumns(), null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor GetAllDuracion(){
        cursor = db.query(duracionDao.getTablename(), duracionDao.getAllColumns(), null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor GetAllPostPlay(){
        cursor = db.query(postPlayDao.getTablename(), postPlayDao.getAllColumns(), null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }


    public Cursor GetPostPlay(String valor){
        cursor = db.rawQuery("SELECT * FROM " + postPlayDao.getTablename() + " where id = " + valor, null);
        cursor.moveToFirst();
        return cursor;
    }


    public void UpdateDuracion(String estado){
        ContentValues valor = new ContentValues();
        valor.put("duration",            estado);
        db.update(duracionDao.getTablename(), valor, null, null);
    }

    public void UpdateEstado(String estado){
        ContentValues valor = new ContentValues();
        valor.put("estado",            estado);
        db.update(estadoDao.getTablename(), valor, null, null);
    }

    public Cursor GetAllEstado(){
        cursor = db.query(estadoDao.getTablename(), estadoDao.getAllColumns(), null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

}

