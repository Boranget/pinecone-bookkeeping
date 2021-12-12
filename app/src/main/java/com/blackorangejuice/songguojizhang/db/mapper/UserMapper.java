package com.blackorangejuice.songguojizhang.db.mapper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blackorangejuice.songguojizhang.bean.User;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;

public class UserMapper {
    public static final String INSERT_USER = "insert into t_user (username, password) values (?,?)\n";
    public static final String UPDATE_USER_USERNAME = "update t_user set username = ? where uid = ?\n";
    public static final String UPDATE_USER_PASSWORD = "update t_user set password = ? where uid = ?\n";
    public static final String SELECT_USER_BY_UID = "select * from t_user where uid = ?\n";
    public static final String SELECT_USER_BY_USERNAME = "select * from t_user where username = ?\n";

    SongGuoDatabaseHelper songGuoDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;

    /**
     * 构造方法传入helper
     * @param songGuoDatabaseHelper
     */
    public UserMapper(SongGuoDatabaseHelper songGuoDatabaseHelper) {
        this.songGuoDatabaseHelper = songGuoDatabaseHelper;
        sqLiteDatabase = songGuoDatabaseHelper.getWritableDatabase();
    }

    /**
     * 插入用户
     * 并返回插入的用户
     * @param user
     * @return
     */
    public User insertUser(User user) {
        sqLiteDatabase.execSQL(INSERT_USER, new String[]{user.getUsername(), user.getPassword()});
        return selectUserByUsername(user.getUsername());
    }

    /**
     * 修改用户名
     * 并返回修改后的对象
     * @param user
     */
    public User updateUserUsername(User user) {
        sqLiteDatabase.execSQL(UPDATE_USER_USERNAME, new String[]{user.getUsername(), String.valueOf(user.getUid())});
        return selectUserByUsername(user.getUsername());
    }

    /**
     * 修改密码
     * 并返回修改后的对象
     * @param user
     */
    public User updateUserPassword(User user) {
        sqLiteDatabase.execSQL(UPDATE_USER_PASSWORD, new String[]{user.getPassword(), String.valueOf(user.getUid())});
        return selectUserByUid(user.getUid());
    }

    /**
     * 通过uid查找user
     * @param uid
     * @return
     */
    public User selectUserByUid(Integer uid){
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_USER_BY_UID, new String[]{String.valueOf(uid)});
        User user = new User();
        user.setUid(uid);
        if(cursor.moveToFirst()){
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            user.setUsername(username);
            user.setPassword(password);
        }
        cursor.close();
        return user;
    }

    /**
     * 通过用户名查找user
     * @param username
     * @return
     */
    public User selectUserByUsername(String username){
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_USER_BY_USERNAME, new String[]{username});
        User user = new User();
        user.setUsername(username);
        if(cursor.moveToFirst()){
            Integer uid = cursor.getInt(cursor.getColumnIndex("uid"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            user.setUid(uid);
            user.setPassword(password);
        }
        cursor.close();
        return user;
    }
}
