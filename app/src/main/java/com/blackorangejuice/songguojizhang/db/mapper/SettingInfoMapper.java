package com.blackorangejuice.songguojizhang.db.mapper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blackorangejuice.songguojizhang.bean.SettingInfo;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;

public class SettingInfoMapper {
    public static final String INSERT_SETTING_INFO = "insert into t_setting_info \n" +
            "(uid, if_enable_password_check, defult_launch_page, defult_add_page,current_account_book_bid)\n" +
            "values\n" +
            "(?,?,?,?,?)";
    public static final String UPDATE_BY_SID = "update t_setting_info\n" +
            "set uid = ?,if_enable_password_check = ?,defult_launch_page = ?,defult_add_page = ?,current_account_book_bid = ?\n" +
            "where sid = ?";
    public static final String UPDATE_BY_UID = "update t_setting_info \n" +
            "set if_enable_password_check = ?,defult_launch_page = ?,defult_add_page = ?,current_account_book_bid = ?\n" +
            "where uid = ?";
    public static final String UPDATE_IF_ENABLE_PASSWORD_CHECK = "update t_setting_info \n" +
            "set if_enable_password_check = ?\n" +
            "where uid = ?";
    public static final String UPDATE_DEFULT_LAUNCH_PAGE = "update t_setting_info \n" +
            "set defult_launch_page = ?\n" +
            "where uid = ?";
    public static final String UPDATE_DEFULT_ADD_PAGE = "update t_setting_info \n" +
            "set defult_add_page = ?\n" +
            "where uid = ?";
    public static final String UPDATE_CURRENT_ACCOUNT_BOOK_BID = "update t_setting_info \n" +
            "set current_account_book_bid = ?\n" +
            "where uid = ?";
    public static final String SELECT_BY_SID = "select * from t_setting_info where sid = ?";
    public static final String SELECT_BY_UID = "select * from t_setting_info where uid = ?";
    SongGuoDatabaseHelper songGuoDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;

    /**
     * 构造方法传入helper
     *
     * @param songGuoDatabaseHelper
     */
    public SettingInfoMapper(SongGuoDatabaseHelper songGuoDatabaseHelper) {
        this.songGuoDatabaseHelper = songGuoDatabaseHelper;
        sqLiteDatabase = songGuoDatabaseHelper.getWritableDatabase();

    }

    /**
     * 插入设置信息
     * 并返回插入后的设置对象
     *
     * @param settingInfo
     * @return
     */
    public SettingInfo insertSettingInfo(SettingInfo settingInfo) {
        sqLiteDatabase.execSQL(INSERT_SETTING_INFO, new String[]{String.valueOf(settingInfo.getUid()), settingInfo.getIfEnablePasswordCheck(), settingInfo.getDefultLaunchPage(), settingInfo.getDefultAddPage(), String.valueOf(settingInfo.getCurrentAccountBookBid())});
        return null;
    }

    /**
     * 通过sid更新
     *
     * @param settingInfo
     * @return
     */
    public SettingInfo updateBySid(SettingInfo settingInfo) {
        sqLiteDatabase.execSQL(UPDATE_BY_SID, new String[]{String.valueOf(settingInfo.getUid()), settingInfo.getIfEnablePasswordCheck(), settingInfo.getDefultLaunchPage(), settingInfo.getDefultAddPage(), String.valueOf(settingInfo.getCurrentAccountBookBid()), String.valueOf(settingInfo.getSid())});
        return null;
    }

    /**
     * 通过uid更新
     *
     * @param settingInfo
     * @return
     */
    public SettingInfo updateByUid(SettingInfo settingInfo) {
        sqLiteDatabase.execSQL(UPDATE_BY_UID, new String[]{settingInfo.getIfEnablePasswordCheck(), settingInfo.getDefultLaunchPage(), settingInfo.getDefultAddPage(), String.valueOf(settingInfo.getCurrentAccountBookBid()),
                String.valueOf(settingInfo.getUid())});
        return null;
    }

    /**
     * 通过uid更新IfEnablePasswordCheck
     *
     * @param ifEnablePasswordCheck
     * @param uid
     * @return
     */
    public SettingInfo updateIfEnablePasswordCheck(String ifEnablePasswordCheck, Integer uid) {
        sqLiteDatabase.execSQL(UPDATE_IF_ENABLE_PASSWORD_CHECK, new String[]{ifEnablePasswordCheck, String.valueOf(uid)});
        return selectByUid(uid);
    }

    /**
     * 通过uid更新defultLaunchPage
     *
     * @param defultLaunchPage
     * @param uid
     * @return
     */
    public SettingInfo updateDefultLaunchPage(String defultLaunchPage, Integer uid) {
        sqLiteDatabase.execSQL(UPDATE_DEFULT_LAUNCH_PAGE, new String[]{defultLaunchPage, String.valueOf(uid)});
        return selectByUid(uid);
    }

    /**
     * 通过uid更新defultAddPage
     *
     * @param defultAddPage
     * @param uid
     * @return
     */
    public SettingInfo updateDefuleAddPage(String defultAddPage, Integer uid) {
        sqLiteDatabase.execSQL(UPDATE_DEFULT_ADD_PAGE, new String[]{defultAddPage, String.valueOf(uid)});
        return selectByUid(uid);
    }

    /**
     * 通过uid更新当前使用账本bid
     *
     * @param bid
     * @param uid
     * @return
     */
    public SettingInfo updatecurrentAccountBookBid(Integer bid, Integer uid) {
        sqLiteDatabase.execSQL(UPDATE_CURRENT_ACCOUNT_BOOK_BID, new String[]{String.valueOf(bid), String.valueOf(uid)});
        return selectByUid(uid);
    }

    /**
     * 通过sid搜索设置
     *
     * @param sid
     * @return
     */
    public SettingInfo selectBySid(Integer sid) {
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_BY_SID, new String[]{String.valueOf(sid)});
        SettingInfo settingInfo = new SettingInfo();
        settingInfo.setSid(sid);
        if (cursor.moveToFirst()) {
            Integer uid = cursor.getInt(cursor.getColumnIndex("uid"));
            String ifEnablePasswordCheck = cursor.getString(cursor.getColumnIndex("if_enable_password_check"));
            String defultLaunchPage = cursor.getString(cursor.getColumnIndex("defult_launch_page"));
            String defultAddPage = cursor.getString(cursor.getColumnIndex("defult_add_page"));
            Integer currentAccountBookBid = cursor.getInt(cursor.getColumnIndex("current_account_book_bid"));
            settingInfo.setUid(uid);
            settingInfo.setIfEnablePasswordCheck(ifEnablePasswordCheck);
            settingInfo.setDefultLaunchPage(defultLaunchPage);
            settingInfo.setDefultAddPage(defultAddPage);
            settingInfo.setCurrentAccountBookBid(currentAccountBookBid);
        }
        cursor.close();
        return settingInfo;
    }

    /**
     * 通过uid搜索设置
     *
     * @param uid
     * @return
     */
    public SettingInfo selectByUid(Integer uid) {
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_BY_UID, new String[]{String.valueOf(uid)});
        SettingInfo settingInfo = new SettingInfo();
        settingInfo.setUid(uid);
        if (cursor.moveToFirst()) {
            Integer sid = cursor.getInt(cursor.getColumnIndex("sid"));
            String ifEnablePasswordCheck = cursor.getString(cursor.getColumnIndex("if_enable_password_check"));
            String defultLaunchPage = cursor.getString(cursor.getColumnIndex("defult_launch_page"));
            String defultAddPage = cursor.getString(cursor.getColumnIndex("defult_add_page"));
            Integer currentAccountBookBid = cursor.getInt(cursor.getColumnIndex("current_account_book_bid"));
            settingInfo.setSid(sid);
            settingInfo.setIfEnablePasswordCheck(ifEnablePasswordCheck);
            settingInfo.setDefultLaunchPage(defultLaunchPage);
            settingInfo.setDefultAddPage(defultAddPage);
            settingInfo.setCurrentAccountBookBid(currentAccountBookBid);
        }
        cursor.close();
        return settingInfo;
    }
}
