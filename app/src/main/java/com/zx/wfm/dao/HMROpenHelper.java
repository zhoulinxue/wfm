package com.zx.wfm.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by chenshaolin on 2016/12/13.
 */
public class HMROpenHelper extends DaoMaster.OpenHelper {

    public HMROpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, AddressTableDao.class, ChannelDetailDao.class, ChannelInfoDao.class, ChannelMemberDao.class, ChannelMsgDao.class,
                CityInfoDao.class, ConversationInfoDao.class, ConversationRecordDao.class, CropImageDao.class, DrivingTableDao.class,
                FriendInfoDao.class, FriendmsgDao.class, UserLogoTableDao.class, UserTableDao.class,ImTableDao.class,RadioInfoDao.class);
    }
}
