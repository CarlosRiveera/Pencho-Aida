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
public class DBEstadoDao extends AbstractDao<DBEstado, Long> {

    public static final String TABLENAME = "ESTADO";
    private Object object;


    public static class Properties {
        public final static Property idestado                           = new Property(0, Long.class,   "idestado", true, "idestado");
        public final static Property estado                             = new Property(1, String.class, "estado", true, "estado");
        public final static Property detalle                            = new Property(2, String.class, "detalle", true, "detalle");

    }

    public DBEstadoDao(DaoConfig config) {
        super(config);
    }

    public DBEstadoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }


    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'ESTADO' (" + //
                "'idestado' INTEGER ," +
                "'estado' TEXT ," +
                "'detalle' TEXT);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ESTADO'";
        db.execSQL(sql);
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, DBEstado entity) {
        stmt.clearBindings();

        Long id1 = entity.getIdestado();
        if (id1 != null) {
            stmt.bindLong(1, id1);
        }
        stmt.bindString(2,entity.getEstado());
        stmt.bindString(3,entity.getDetalle());

    }



    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /**
     * @inheritdoc
     */
    @Override
    public DBEstado readEntity(Cursor cursor, int offset) {
        DBEstado entity = new DBEstado( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getString(offset + 1),
                cursor.getString(offset + 2)

        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, DBEstado entity, int offset) {
        entity.setIdestado(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setEstado(cursor.getString(offset + 1));
        entity.setDetalle(cursor.getString(offset + 2));
    }



    /**
     * @inheritdoc
     */
    @Override
    protected Long updateKeyAfterInsert(DBEstado entity, long rowId) {
        entity.setIdestado(rowId);
        return rowId;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long getKey(DBEstado entity) {
        if (entity != null) {
            return entity.getIdestado();
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
