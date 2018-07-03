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
public class DBDuracionDao extends AbstractDao<DBDuracion, Long> {

    public static final String TABLENAME = "DURACION";
    private Object object;



    public static class Properties {
        public final static Property id                               = new Property(0, Long.class,   "id", true, "id");
        public final static Property current                           = new Property(1, String.class, "current", true, "current");
        public final static Property duration                             = new Property(2, String.class, "duration", true, "duration");
        public final static Property progreso                           = new Property(3, String.class, "progreso", true, "progreso");
        public final static Property detalle                          = new Property(4, String.class, "detalle", true, "detalle");

    }

    public DBDuracionDao(DaoConfig config) {
        super(config);
    }

    public DBDuracionDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }


    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'DURACION' (" + //
                "'id' INTEGER ," +
                "'current' TEXT ," +
                "'duration' TEXT ," +
                "'progreso' TEXT ," +
                "'detalle' TEXT);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'DURACION'";
        db.execSQL(sql);
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, DBDuracion entity) {
        stmt.clearBindings();

        Long id1 = entity.getId();
        if (id1 != null) {
            stmt.bindLong(1, id1);
        }
        stmt.bindString(2,entity.getCurrent());
        stmt.bindString(3,entity.getDuration());
        stmt.bindString(4,entity.getProgreso());
        stmt.bindString(5,entity.getDetalle());
    }



    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /**
     * @inheritdoc
     */
    @Override
    public DBDuracion readEntity(Cursor cursor, int offset) {
        DBDuracion entity = new DBDuracion( //
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
    public void readEntity(Cursor cursor, DBDuracion entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCurrent(cursor.getString(offset + 1));
        entity.setDuration(cursor.getString(offset + 2));
        entity.setProgreso(cursor.getString(offset + 3));
        entity.setDetalle(cursor.getString(offset + 4));

    }



    /**
     * @inheritdoc
     */
    @Override
    protected Long updateKeyAfterInsert(DBDuracion entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long getKey(DBDuracion entity) {
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
