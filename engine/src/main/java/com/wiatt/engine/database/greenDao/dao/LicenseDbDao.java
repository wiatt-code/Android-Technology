package com.wiatt.engine.database.greenDao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.wiatt.engine.database.entity.LicenseDb;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LICENSE_DB".
*/
public class LicenseDbDao extends AbstractDao<LicenseDb, Long> {

    public static final String TABLENAME = "LICENSE_DB";

    /**
     * Properties of entity LicenseDb.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Node_id = new Property(1, String.class, "node_id", false, "NODE_ID");
        public final static Property Key = new Property(2, String.class, "key", false, "KEY");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Spdx_id = new Property(4, String.class, "spdx_id", false, "SPDX_ID");
        public final static Property Url = new Property(5, String.class, "url", false, "URL");
    }


    public LicenseDbDao(DaoConfig config) {
        super(config);
    }
    
    public LicenseDbDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LICENSE_DB\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NODE_ID\" TEXT," + // 1: node_id
                "\"KEY\" TEXT," + // 2: key
                "\"NAME\" TEXT," + // 3: name
                "\"SPDX_ID\" TEXT," + // 4: spdx_id
                "\"URL\" TEXT);"); // 5: url
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_LICENSE_DB_NODE_ID ON \"LICENSE_DB\"" +
                " (\"NODE_ID\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LICENSE_DB\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LicenseDb entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String node_id = entity.getNode_id();
        if (node_id != null) {
            stmt.bindString(2, node_id);
        }
 
        String key = entity.getKey();
        if (key != null) {
            stmt.bindString(3, key);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String spdx_id = entity.getSpdx_id();
        if (spdx_id != null) {
            stmt.bindString(5, spdx_id);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(6, url);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LicenseDb entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String node_id = entity.getNode_id();
        if (node_id != null) {
            stmt.bindString(2, node_id);
        }
 
        String key = entity.getKey();
        if (key != null) {
            stmt.bindString(3, key);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String spdx_id = entity.getSpdx_id();
        if (spdx_id != null) {
            stmt.bindString(5, spdx_id);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(6, url);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public LicenseDb readEntity(Cursor cursor, int offset) {
        LicenseDb entity = new LicenseDb( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // node_id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // key
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // spdx_id
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // url
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LicenseDb entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNode_id(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setKey(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSpdx_id(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUrl(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LicenseDb entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LicenseDb entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LicenseDb entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
