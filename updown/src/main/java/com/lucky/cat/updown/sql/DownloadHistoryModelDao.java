package com.lucky.cat.updown.sql;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.lucky.cat.updown.sql.DownloadHistoryModel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DOWNLOAD_HISTORY_MODEL".
*/
public class DownloadHistoryModelDao extends AbstractDao<DownloadHistoryModel, Long> {

    public static final String TABLENAME = "DOWNLOAD_HISTORY_MODEL";

    /**
     * Properties of entity DownloadHistoryModel.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property DownLoadUrl = new Property(1, String.class, "downLoadUrl", false, "DOWN_LOAD_URL");
        public final static Property SavePath = new Property(2, String.class, "savePath", false, "SAVE_PATH");
        public final static Property FileName = new Property(3, String.class, "fileName", false, "FILE_NAME");
        public final static Property FileSize = new Property(4, Long.class, "fileSize", false, "FILE_SIZE");
        public final static Property CreateTime = new Property(5, java.util.Date.class, "createTime", false, "CREATE_TIME");
    };


    public DownloadHistoryModelDao(DaoConfig config) {
        super(config);
    }
    
    public DownloadHistoryModelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DOWNLOAD_HISTORY_MODEL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"DOWN_LOAD_URL\" TEXT," + // 1: downLoadUrl
                "\"SAVE_PATH\" TEXT," + // 2: savePath
                "\"FILE_NAME\" TEXT," + // 3: fileName
                "\"FILE_SIZE\" INTEGER," + // 4: fileSize
                "\"CREATE_TIME\" INTEGER);"); // 5: createTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DOWNLOAD_HISTORY_MODEL\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, DownloadHistoryModel entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String downLoadUrl = entity.getDownLoadUrl();
        if (downLoadUrl != null) {
            stmt.bindString(2, downLoadUrl);
        }
 
        String savePath = entity.getSavePath();
        if (savePath != null) {
            stmt.bindString(3, savePath);
        }
 
        String fileName = entity.getFileName();
        if (fileName != null) {
            stmt.bindString(4, fileName);
        }
 
        Long fileSize = entity.getFileSize();
        if (fileSize != null) {
            stmt.bindLong(5, fileSize);
        }
 
        java.util.Date createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(6, createTime.getTime());
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public DownloadHistoryModel readEntity(Cursor cursor, int offset) {
        DownloadHistoryModel entity = new DownloadHistoryModel( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // downLoadUrl
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // savePath
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // fileName
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // fileSize
            cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)) // createTime
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, DownloadHistoryModel entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDownLoadUrl(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSavePath(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setFileName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setFileSize(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setCreateTime(cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(DownloadHistoryModel entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(DownloadHistoryModel entity) {
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