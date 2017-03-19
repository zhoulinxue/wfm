package com.zx.wfm.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.zx.wfm.bean.TeleMsgbean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TELE_MSGBEAN".
*/
public class TeleMsgbeanDao extends AbstractDao<TeleMsgbean, String> {

    public static final String TABLENAME = "TELE_MSGBEAN";

    /**
     * Properties of entity TeleMsgbean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property ObjectId = new Property(0, String.class, "objectId", false, "OBJECT_ID");
        public final static Property TelevisionId = new Property(1, String.class, "TelevisionId", true, "TELEVISION_ID");
        public final static Property Userid = new Property(2, String.class, "userid", false, "USERID");
        public final static Property PlayNum = new Property(3, String.class, "playNum", false, "PLAY_NUM");
        public final static Property Total = new Property(4, String.class, "total", false, "TOTAL");
        public final static Property IsZan = new Property(5, String.class, "isZan", false, "IS_ZAN");
    };


    public TeleMsgbeanDao(DaoConfig config) {
        super(config);
    }
    
    public TeleMsgbeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TELE_MSGBEAN\" (" + //
                "\"OBJECT_ID\" TEXT," + // 0: objectId
                "\"TELEVISION_ID\" TEXT PRIMARY KEY NOT NULL ," + // 1: TelevisionId
                "\"USERID\" TEXT," + // 2: userid
                "\"PLAY_NUM\" TEXT," + // 3: playNum
                "\"TOTAL\" TEXT," + // 4: total
                "\"IS_ZAN\" TEXT);"); // 5: isZan
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TELE_MSGBEAN\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TeleMsgbean entity) {
        stmt.clearBindings();
 
        String objectId = entity.getObjectId();
        if (objectId != null) {
            stmt.bindString(1, objectId);
        }
 
        String TelevisionId = entity.getTelevisionId();
        if (TelevisionId != null) {
            stmt.bindString(2, TelevisionId);
        }
 
        String userid = entity.getUserid();
        if (userid != null) {
            stmt.bindString(3, userid);
        }
 
        String playNum = entity.getPlayNum();
        if (playNum != null) {
            stmt.bindString(4, playNum);
        }
 
        String total = entity.getTotal();
        if (total != null) {
            stmt.bindString(5, total);
        }
 
        String isZan = entity.getIsZan();
        if (isZan != null) {
            stmt.bindString(6, isZan);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1);
    }    

    /** @inheritdoc */
    @Override
    public TeleMsgbean readEntity(Cursor cursor, int offset) {
        TeleMsgbean entity = new TeleMsgbean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // objectId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // TelevisionId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userid
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // playNum
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // total
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // isZan
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TeleMsgbean entity, int offset) {
        entity.setObjectId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setTelevisionId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserid(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPlayNum(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTotal(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setIsZan(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(TeleMsgbean entity, long rowId) {
        return entity.getTelevisionId();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(TeleMsgbean entity) {
        if(entity != null) {
            return entity.getTelevisionId();
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
