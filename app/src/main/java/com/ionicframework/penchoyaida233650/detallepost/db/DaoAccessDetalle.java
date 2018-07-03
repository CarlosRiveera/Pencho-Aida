package com.ionicframework.penchoyaida233650.detallepost.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ionicframework.penchoyaida233650.db.BusquedasTemporalesDao;
import com.ionicframework.penchoyaida233650.db.DaoMaster;
import com.ionicframework.penchoyaida233650.db.DaoSession;
import com.ionicframework.penchoyaida233650.db.PostcastDao;
import com.ionicframework.penchoyaida233650.db.Postcast_PrincipalesDao;
import com.ionicframework.penchoyaida233650.play.db.DBCancionDao;
import com.ionicframework.penchoyaida233650.play.db.DBEstadoDao;

/**
 * Created by Santiago on 13/05/2016.
 */
public class DaoAccessDetalle {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    public DBCancionDao cancionDao;
    public DBEstadoDao estadoDao;
    public PostcastDao postcastDao;
    public DBPostPlayDao postPlayDao;
    public DBDuracionDao duracionDao;
    public Postcast_PrincipalesDao postcast_principalesDao;
    public BusquedasTemporalesDao busquedasTemporalesDao;
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
        busquedasTemporalesDao = daoSession.getBusquedasTemporalesDao();
        postcast_principalesDao = daoSession.getPostcast_PrincipalesDao();
    }


    public Cursor GetAllBusque(){
        cursor = db.query(busquedasTemporalesDao.getTablename(), busquedasTemporalesDao.getAllColumns(), null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor GetAllPost(){
        cursor = db.query(postcastDao.getTablename(), postcastDao.getAllColumns(), null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor GetAllPost_principal(){
        cursor = db.query(postcast_principalesDao.getTablename(), postcast_principalesDao.getAllColumns(), null, null, null, null, null);
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
        Log.i("db", "---" + valor);
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
