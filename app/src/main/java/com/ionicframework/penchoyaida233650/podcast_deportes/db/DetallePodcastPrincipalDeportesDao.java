package com.ionicframework.penchoyaida233650.podcast_deportes.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import com.ionicframework.penchoyaida233650.db.DaoSession;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table DETALLE_PODCAST_PRINCIPAL_DEPORTES.
*/
public class DetallePodcastPrincipalDeportesDao extends AbstractDao<DetallePodcastPrincipalDeportes, Long> {

    public static final String TABLENAME = "DETALLE_PODCAST_PRINCIPAL_DEPORTES";

    /**
     * Properties of entity DetallePodcastPrincipalDeportes.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Titulo = new Property(1, String.class, "Titulo", false, "TITULO");
        public final static Property SegundoTitulo = new Property(2, String.class, "SegundoTitulo", false, "SEGUNDO_TITULO");
        public final static Property ImagenPodcast = new Property(3, String.class, "ImagenPodcast", false, "IMAGEN_PODCAST");
        public final static Property Descripcion = new Property(4, String.class, "Descripcion", false, "DESCRIPCION");
    };


    public DetallePodcastPrincipalDeportesDao(DaoConfig config) {
        super(config);
    }
    
    public DetallePodcastPrincipalDeportesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'DETALLE_PODCAST_PRINCIPAL_DEPORTES' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'TITULO' TEXT," + // 1: Titulo
                "'SEGUNDO_TITULO' TEXT," + // 2: SegundoTitulo
                "'IMAGEN_PODCAST' TEXT," + // 3: ImagenPodcast
                "'DESCRIPCION' TEXT);"); // 4: Descripcion
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'DETALLE_PODCAST_PRINCIPAL_DEPORTES'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, DetallePodcastPrincipalDeportes entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String Titulo = entity.getTitulo();
        if (Titulo != null) {
            stmt.bindString(2, Titulo);
        }
 
        String SegundoTitulo = entity.getSegundoTitulo();
        if (SegundoTitulo != null) {
            stmt.bindString(3, SegundoTitulo);
        }
 
        String ImagenPodcast = entity.getImagenPodcast();
        if (ImagenPodcast != null) {
            stmt.bindString(4, ImagenPodcast);
        }
 
        String Descripcion = entity.getDescripcion();
        if (Descripcion != null) {
            stmt.bindString(5, Descripcion);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public DetallePodcastPrincipalDeportes readEntity(Cursor cursor, int offset) {
        DetallePodcastPrincipalDeportes entity = new DetallePodcastPrincipalDeportes( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // Titulo
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // SegundoTitulo
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // ImagenPodcast
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // Descripcion
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, DetallePodcastPrincipalDeportes entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitulo(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSegundoTitulo(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setImagenPodcast(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDescripcion(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(DetallePodcastPrincipalDeportes entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(DetallePodcastPrincipalDeportes entity) {
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
