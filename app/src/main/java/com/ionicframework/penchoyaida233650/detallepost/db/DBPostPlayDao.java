package com.ionicframework.penchoyaida233650.detallepost.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import com.ionicframework.penchoyaida233650.db.DaoSession;

/**
 * Created by Santiago on 13/05/2016.
 */
public class DBPostPlayDao extends AbstractDao<DBPostPlay, Long> {

    public static final String TABLENAME = "POSTPLAY";
    private Object object;



    public static class Properties {
        public final static Property id                               = new Property(0, Long.class,   "id", true, "id");
        public final static Property titulo                           = new Property(1, String.class, "titulo", true, "titulo");
        public final static Property link                             = new Property(2, String.class, "link", true, "link");
        public final static Property imagen                           = new Property(3, String.class, "imagen", true, "imagen");
        public final static Property artista                          = new Property(4, String.class, "artista", true, "artista");
        public final static Property subtitle                         = new Property(5, String.class, "subtitle", true, "subtitle");

    }

    public DBPostPlayDao(DaoConfig config) {
        super(config);
    }

    public DBPostPlayDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }


    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'POSTPLAY' (" + //
                "'id' INTEGER ," +
                "'titulo' TEXT ," +
                "'link' TEXT ," +
                "'imagen' TEXT ," +
                "'artista' TEXT ," +
                "'subtitle' TEXT);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'POSTPLAY'";
        db.execSQL(sql);
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, DBPostPlay entity) {
        stmt.clearBindings();

        Long id1 = entity.getId();
        if (id1 != null) {
            stmt.bindLong(1, id1);
        }
        stmt.bindString(2,entity.getTitulo());
        stmt.bindString(3,entity.getLink());
        stmt.bindString(4,entity.getImagen());
        stmt.bindString(5,entity.getArtista());
        stmt.bindString(6,entity.getSubtitle());

    }



    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /**
     * @inheritdoc
     */
    @Override
    public DBPostPlay readEntity(Cursor cursor, int offset) {
        DBPostPlay entity = new DBPostPlay( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getString(offset + 1),
                cursor.getString(offset + 2),
                cursor.getString(offset + 3),
                cursor.getString(offset + 4),
                cursor.getString(offset + 5)

        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, DBPostPlay entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitulo(cursor.getString(offset + 1));
        entity.setLink(cursor.getString(offset + 2));
        entity.setImagen(cursor.getString(offset + 3));
        entity.setArtista(cursor.getString(offset + 4));
        entity.setSubtitle(cursor.getString(offset + 5));
    }



    /**
     * @inheritdoc
     */
    @Override
    protected Long updateKeyAfterInsert(DBPostPlay entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long getKey(DBPostPlay entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }
}
