package com.zx.wfm.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

import com.zx.wfm.dao.BaseUserDao;
import com.zx.wfm.dao.IDCardDao;
import com.zx.wfm.dao.TelevisionbeanDao;
import com.zx.wfm.dao.MoviebeanDao;
import com.zx.wfm.dao.MovieCourseDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 1): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        BaseUserDao.createTable(db, ifNotExists);
        IDCardDao.createTable(db, ifNotExists);
        TelevisionbeanDao.createTable(db, ifNotExists);
        MoviebeanDao.createTable(db, ifNotExists);
        MovieCourseDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        BaseUserDao.dropTable(db, ifExists);
        IDCardDao.dropTable(db, ifExists);
        TelevisionbeanDao.dropTable(db, ifExists);
        MoviebeanDao.dropTable(db, ifExists);
        MovieCourseDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(BaseUserDao.class);
        registerDaoClass(IDCardDao.class);
        registerDaoClass(TelevisionbeanDao.class);
        registerDaoClass(MoviebeanDao.class);
        registerDaoClass(MovieCourseDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}
