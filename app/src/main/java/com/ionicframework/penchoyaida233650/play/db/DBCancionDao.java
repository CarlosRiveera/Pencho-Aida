package com.ionicframework.penchoyaida233650.play.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import com.ionicframework.penchoyaida233650.db.DaoSession;


/**
 * Created by Santiago on 11/05/2016.
 */
public class DBCancionDao extends AbstractDao<DBCancion, Long> {

    public static final String TABLENAME = "CANCION";
    private Object object;



    public static class Properties {
        public final static Property id                                = new Property(0, Long.class,   "id", true, "id");
        public final static Property song                              = new Property(1, String.class, "song", true, "song");
        public final static Property artist                            = new Property(2, String.class, "artist", true, "artist");
        public final static Property album                             = new Property(3, String.class, "album", true, "album");
        public final static Property albumart                          = new Property(4, String.class, "albumart", true, "albumart");

    }

    public DBCancionDao(DaoConfig config) {
        super(config);
    }

    public DBCancionDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }


    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'CANCION' (" + //
                "'id' INTEGER ," +
                "'song' TEXT ," +
                "'artist' TEXT ," +
                "'album' TEXT ," +
                "'albumart' TEXT);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'CANCION'";
        db.execSQL(sql);
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, DBCancion entity) {
        stmt.clearBindings();

        Long id1 = entity.getId();
        if (id1 != null) {
            stmt.bindLong(1, id1);
        }
        stmt.bindString(2,entity.getSong());
        stmt.bindString(3,entity.getArtist());
        stmt.bindString(4,entity.getAlbum());
        stmt.bindString(5,entity.getAlbumart());

    }



    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /**
     * @inheritdoc
     */
    @Override
    public DBCancion readEntity(Cursor cursor, int offset) {
        DBCancion entity = new DBCancion( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getString(offset + 1),
                cursor.getString(offset + 2),
                cursor.getString(offset + 3),
                cursor.getString(offset + 4)

        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, DBCancion entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSong(cursor.getString(offset + 1));
        entity.setArtist(cursor.getString(offset + 2));
        entity.setAlbum(cursor.getString(offset + 3));
        entity.setAlbumart(cursor.getString(offset + 4));

    }



    /**
     * @inheritdoc
     */
    @Override
    protected Long updateKeyAfterInsert(DBCancion entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long getKey(DBCancion entity) {
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
