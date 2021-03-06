package com.ionicframework.penchoyaida233650.Fotos.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import com.ionicframework.penchoyaida233650.db.DaoSession;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table PHOTO_DETALLE.
*/
public class PhotoDetalleDao extends AbstractDao<PhotoDetalle, Long> {

    public static final String TABLENAME = "PHOTO_DETALLE";

    /**
     * Properties of entity PhotoDetalle.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property IdPhoto = new Property(1, String.class, "idPhoto", false, "ID_PHOTO");
        public final static Property Secret = new Property(2, String.class, "secret", false, "SECRET");
        public final static Property Server = new Property(3, String.class, "server", false, "SERVER");
        public final static Property Farm = new Property(4, String.class, "farm", false, "FARM");
        public final static Property Dateuploaded = new Property(5, String.class, "dateuploaded", false, "DATEUPLOADED");
        public final static Property Originalsecret = new Property(6, String.class, "originalsecret", false, "ORIGINALSECRET");
        public final static Property Originalformat = new Property(7, String.class, "originalformat", false, "ORIGINALFORMAT");
        public final static Property Title = new Property(8, String.class, "title", false, "TITLE");
        public final static Property Description = new Property(9, String.class, "description", false, "DESCRIPTION");
        public final static Property Posted = new Property(10, String.class, "posted", false, "POSTED");
        public final static Property Taken = new Property(11, String.class, "taken", false, "TAKEN");
        public final static Property Lastupdate = new Property(12, String.class, "lastupdate", false, "LASTUPDATE");
        public final static Property Url = new Property(13, String.class, "url", false, "URL");
    };


    public PhotoDetalleDao(DaoConfig config) {
        super(config);
    }
    
    public PhotoDetalleDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PHOTO_DETALLE' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'ID_PHOTO' TEXT," + // 1: idPhoto
                "'SECRET' TEXT," + // 2: secret
                "'SERVER' TEXT," + // 3: server
                "'FARM' TEXT," + // 4: farm
                "'DATEUPLOADED' TEXT," + // 5: dateuploaded
                "'ORIGINALSECRET' TEXT," + // 6: originalsecret
                "'ORIGINALFORMAT' TEXT," + // 7: originalformat
                "'TITLE' TEXT," + // 8: title
                "'DESCRIPTION' TEXT," + // 9: description
                "'POSTED' TEXT," + // 10: posted
                "'TAKEN' TEXT," + // 11: taken
                "'LASTUPDATE' TEXT," + // 12: lastupdate
                "'URL' TEXT);"); // 13: url
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PHOTO_DETALLE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, PhotoDetalle entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String idPhoto = entity.getIdPhoto();
        if (idPhoto != null) {
            stmt.bindString(2, idPhoto);
        }
 
        String secret = entity.getSecret();
        if (secret != null) {
            stmt.bindString(3, secret);
        }
 
        String server = entity.getServer();
        if (server != null) {
            stmt.bindString(4, server);
        }
 
        String farm = entity.getFarm();
        if (farm != null) {
            stmt.bindString(5, farm);
        }
 
        String dateuploaded = entity.getDateuploaded();
        if (dateuploaded != null) {
            stmt.bindString(6, dateuploaded);
        }
 
        String originalsecret = entity.getOriginalsecret();
        if (originalsecret != null) {
            stmt.bindString(7, originalsecret);
        }
 
        String originalformat = entity.getOriginalformat();
        if (originalformat != null) {
            stmt.bindString(8, originalformat);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(9, title);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(10, description);
        }
 
        String posted = entity.getPosted();
        if (posted != null) {
            stmt.bindString(11, posted);
        }
 
        String taken = entity.getTaken();
        if (taken != null) {
            stmt.bindString(12, taken);
        }
 
        String lastupdate = entity.getLastupdate();
        if (lastupdate != null) {
            stmt.bindString(13, lastupdate);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(14, url);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public PhotoDetalle readEntity(Cursor cursor, int offset) {
        PhotoDetalle entity = new PhotoDetalle( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // idPhoto
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // secret
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // server
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // farm
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // dateuploaded
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // originalsecret
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // originalformat
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // title
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // description
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // posted
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // taken
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // lastupdate
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13) // url
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, PhotoDetalle entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIdPhoto(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSecret(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setServer(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setFarm(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDateuploaded(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setOriginalsecret(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setOriginalformat(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setTitle(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setDescription(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setPosted(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setTaken(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setLastupdate(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setUrl(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(PhotoDetalle entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(PhotoDetalle entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
