package com.zx.wfm.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zx.wfm.bean.Televisionbean;

/**
 * Created by zx on 2016/12/13.
 */
public class HMROpenHelper extends DaoMaster.OpenHelper {

    public HMROpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db,TelevisionbeanDao.class,BaseUserDao.class,MoviebeanDao.class,IDCardDao.class,MovieCourseDao.class);
    }
}
