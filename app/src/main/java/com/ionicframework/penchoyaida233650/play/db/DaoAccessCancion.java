package com.ionicframework.penchoyaida233650.play.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ionicframework.penchoyaida233650.db.DaoMaster;
import com.ionicframework.penchoyaida233650.db.DaoSession;
import com.ionicframework.penchoyaida233650.detallepost.db.DBDuracionDao;
import com.ionicframework.penchoyaida233650.detallepost.db.DBPostPlayDao;

/**
 * Created by Santiago on 11/05/2016.
 */
public class DaoAccessCancion {

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    public DBCancionDao cancionDao;
    public DBEstadoDao estadoDao;
    public DBPostPlayDao postPlayDao;
    public DBDuracionDao duracionDao;
    Cursor cursor;


    public void Instantiate(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "penchoaida.sqlite", null);
        db              = helper.getWritableDatabase();
        daoMaster       = new DaoMaster(db);
        daoSession      = daoMaster.newSession();
        cancionDao      = daoSession.getCancionDao();
        estadoDao       = daoSession.getEstadoDao();
        postPlayDao     = daoSession.getPostPlayDao();
        duracionDao     = daoSession.getDuracionDao();
    }


    public Cursor GetAllPostPlay(){
        cursor = db.query(postPlayDao.getTablename(), postPlayDao.getAllColumns(), null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor GetAllCancion(){
        cursor = db.query(cancionDao.getTablename(), cancionDao.getAllColumns(), null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor GetAllDuracion(){
        cursor = db.query(duracionDao.getTablename(), duracionDao.getAllColumns(), null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor GetAllEstado(){
        cursor = db.query(estadoDao.getTablename(), estadoDao.getAllColumns(), null, null, null, null, null);
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
    public void UpdateEstado2(String estado,String detalle){
        ContentValues valor = new ContentValues();
        valor.put("estado",            estado);
        valor.put("detalle",           detalle);
        db.update(estadoDao.getTablename(), valor, null, null);
    }

}
