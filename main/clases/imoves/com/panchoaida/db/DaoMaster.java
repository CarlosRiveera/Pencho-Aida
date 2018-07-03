package com.ionicframework.penchoyaida233650.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

import com.ionicframework.penchoyaida233650.db.PortadaDao;
import com.ionicframework.penchoyaida233650.db.PostcastDao;
import com.ionicframework.penchoyaida233650.db.DetallePodcastDao;
import com.ionicframework.penchoyaida233650.db.DetallePodcastPrincipalDao;
import com.ionicframework.penchoyaida233650.db.BusquedasTemporalesDao;
import com.ionicframework.penchoyaida233650.db.Postcast_PrincipalesDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 1): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        PortadaDao.createTable(db, ifNotExists);
        PostcastDao.createTable(db, ifNotExists);
        DetallePodcastDao.createTable(db, ifNotExists);
        DetallePodcastPrincipalDao.createTable(db, ifNotExists);
        BusquedasTemporalesDao.createTable(db, ifNotExists);
        Postcast_PrincipalesDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        PortadaDao.dropTable(db, ifExists);
        PostcastDao.dropTable(db, ifExists);
        DetallePodcastDao.dropTable(db, ifExists);
        DetallePodcastPrincipalDao.dropTable(db, ifExists);
        BusquedasTemporalesDao.dropTable(db, ifExists);
        Postcast_PrincipalesDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(PortadaDao.class);
        registerDaoClass(PostcastDao.class);
        registerDaoClass(DetallePodcastDao.class);
        registerDaoClass(DetallePodcastPrincipalDao.class);
        registerDaoClass(BusquedasTemporalesDao.class);
        registerDaoClass(Postcast_PrincipalesDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}
