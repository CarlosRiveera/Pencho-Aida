package com.ionicframework.penchoyaida233650.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table POSTCAST.
*/
public class PostcastDao extends AbstractDao<Postcast, Long> {

    public static final String TABLENAME = "POSTCAST";

    /**
     * Properties of entity Postcast.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id             = new Property(0, Long.class, "id", true, "_id");
        public final static Property Title          = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Description    = new Property(2, String.class, "description", false, "DESCRIPTION");
        public final static Property Link           = new Property(3, String.class, "link", false, "LINK");
        public final static Property Imagen         = new Property(4, String.class, "imagen", false, "IMAGEN");
        public final static Property Enclosure      = new Property(5, String.class, "enclosure", false, "ENCLOSURE");
        public final static Property Guid           = new Property(6, String.class, "guid", false, "GUID");
        public final static Property PubDate        = new Property(7, String.class, "pubDate", false, "PUB_DATE");
        public final static Property Subtitle       = new Property(8, String.class, "subtitle", false, "SUBTITLE");
        public final static Property Summary        = new Property(9, String.class, "summary", false, "SUMMARY");
        public final static Property Duration       = new Property(10, String.class, "duration", false, "DURATION");
        public final static Property Author         = new Property(11, String.class, "author", false, "AUTHOR");
        public final static Property Keywords       = new Property(12, String.class, "keywords", false, "KEYWORDS");
        public final static Property Explicit       = new Property(13, String.class, "explicit", false, "EXPLICIT");
        public final static Property Block          = new Property(14, String.class, "block", false, "BLOCK");
    };


    public PostcastDao(DaoConfig config) {
        super(config);
    }
    
    public PostcastDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'POSTCAST' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'TITLE' TEXT," + // 1: title
                "'DESCRIPTION' TEXT," + // 2: description
                "'LINK' TEXT," + // 3: link
                "'IMAGEN' TEXT," + // 4: imagen
                "'ENCLOSURE' TEXT," + // 5: enclosure
                "'GUID' TEXT," + // 6: guid
                "'PUB_DATE' TEXT," + // 7: pubDate
                "'SUBTITLE' TEXT," + // 8: subtitle
                "'SUMMARY' TEXT," + // 9: summary
                "'DURATION' TEXT," + // 10: duration
                "'AUTHOR' TEXT," + // 11: author
                "'KEYWORDS' TEXT," + // 12: keywords
                "'EXPLICIT' TEXT," + // 13: explicit
                "'BLOCK' TEXT);"); // 14: block
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'POSTCAST'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Postcast entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(3, description);
        }
 
        String link = entity.getLink();
        if (link != null) {
            stmt.bindString(4, link);
        }
 
        String imagen = entity.getImagen();
        if (imagen != null) {
            stmt.bindString(5, imagen);
        }
 
        String enclosure = entity.getEnclosure();
        if (enclosure != null) {
            stmt.bindString(6, enclosure);
        }
 
        String guid = entity.getGuid();
        if (guid != null) {
            stmt.bindString(7, guid);
        }
 
        String pubDate = entity.getPubDate();
        if (pubDate != null) {
            stmt.bindString(8, pubDate);
        }
 
        String subtitle = entity.getSubtitle();
        if (subtitle != null) {
            stmt.bindString(9, subtitle);
        }
 
        String summary = entity.getSummary();
        if (summary != null) {
            stmt.bindString(10, summary);
        }
 
        String duration = entity.getDuration();
        if (duration != null) {
            stmt.bindString(11, duration);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(12, author);
        }
 
        String keywords = entity.getKeywords();
        if (keywords != null) {
            stmt.bindString(13, keywords);
        }
 
        String explicit = entity.getExplicit();
        if (explicit != null) {
            stmt.bindString(14, explicit);
        }
 
        String block = entity.getBlock();
        if (block != null) {
            stmt.bindString(15, block);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Postcast readEntity(Cursor cursor, int offset) {
        Postcast entity = new Postcast( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // description
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // link
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // imagen
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // enclosure
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // guid
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // pubDate
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // subtitle
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // summary
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // duration
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // author
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // keywords
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // explicit
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14) // block
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Postcast entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDescription(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLink(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setImagen(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setEnclosure(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setGuid(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setPubDate(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSubtitle(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setSummary(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setDuration(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setAuthor(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setKeywords(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setExplicit(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setBlock(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Postcast entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Postcast entity) {
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
