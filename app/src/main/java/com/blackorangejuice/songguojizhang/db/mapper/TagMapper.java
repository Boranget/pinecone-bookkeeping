package com.blackorangejuice.songguojizhang.db.mapper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blackorangejuice.songguojizhang.bean.Tag;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class TagMapper {
    public static final String INIT =
            "insert into t_tag (tag_name,tag_img_name) values (?,?);\n";
    public static final String INSERT_TAG = "insert into t_tag \n" +
            "(tag_name, tag_img_name)\n" +
            "values\n" +
            "(?,?)";
    public static final String SELECT_BY_TID = "select * from t_tag where tid = ?";
    public static final String SELECT_BY_TAG_NAME = "select * from t_tag where tag_name = ?";

    public static final String SELECT_ALL = "select * from t_tag";
    SongGuoDatabaseHelper songGuoDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;

    public TagMapper(SongGuoDatabaseHelper songGuoDatabaseHelper) {
        this.songGuoDatabaseHelper = songGuoDatabaseHelper;
        sqLiteDatabase = songGuoDatabaseHelper.getWritableDatabase();
    }

    /**
     * 初始化tag
     */
    public void init() {
        String strs[][] = {
                {"交通","jiaotong"},
                {"超市","chaoshi"},
                {"充值","chongzhi"},
                {"服饰","fushi"},
                {"吃喝","chihe"},
                {"买菜","maicai"},
                {"化妆","huazhuang"},
                {"日用","riyong"},
                {"红包","hongbao"},
                {"话费","huafei"},
                {"娱乐","yvle"},
                {"医疗","yiliao"},
                {"宠物","chongwu"},
                {"养车","yangche"},
                {"洗澡","xizao"},
                {"学习","xuexi"},
                {"微信","weixin"},
                {"支付宝","zhifubao"},
                {"工资","gongzi"},
                {"投资","touzi"},
                {"奖金","jiangjin"},
                {"兼职","jianzhi"}

        };
        for(String s[]:strs){
            sqLiteDatabase.execSQL(INIT, new String[]{s[0],s[1]+".jpg"});
        }
    }

    public Tag insertTag(Tag tag) {
        Tag tag0 = selectByTagName(tag.getTagName());
        // 如果不存在该名称的tag,则插入
        if (tag0 == null) {
            sqLiteDatabase.execSQL(INSERT_TAG, new String[]{tag.getTagName(), tag.getTagImgName()});
        }
        return selectByTagName(tag.getTagName());
    }

    /**
     * 按tid查找标签
     *
     * @param tid
     * @return
     */
    public Tag selectByTid(Integer tid) {
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_BY_TID, new String[]{String.valueOf(tid)});
        Tag tag = null;
        if (cursor.moveToFirst()) {
            tag = new Tag();
            tag.setTid(cursor.getInt(cursor.getColumnIndex("tid")));
            tag.setTagName(cursor.getString(cursor.getColumnIndex("tag_name")));
            tag.setTagImgName(cursor.getString(cursor.getColumnIndex("tag_img_name")));
        }
        cursor.close();
        return tag;
    }

    /**
     * 按标签名查找标签
     *
     * @param tagName
     * @return
     */
    public Tag selectByTagName(String tagName) {
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_BY_TAG_NAME, new String[]{tagName});
        Tag tag = null;
        if (cursor.moveToFirst()) {
            tag = new Tag();
            tag.setTid(cursor.getInt(cursor.getColumnIndex("tid")));
            tag.setTagName(cursor.getString(cursor.getColumnIndex("tag_name")));
            tag.setTagImgName(cursor.getString(cursor.getColumnIndex("tag_img_name")));
        }
        cursor.close();
        return tag;
    }

    /**
     * 查找所有tag
     *
     * @return
     */
    public List<Tag> selectAll() {
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL, null);
        List<Tag> tags = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Tag tag = new Tag();
                tag.setTid(cursor.getInt(cursor.getColumnIndex("tid")));
                tag.setTagName(cursor.getString(cursor.getColumnIndex("tag_name")));
                tag.setTagImgName(cursor.getString(cursor.getColumnIndex("tag_img_name")));
                tags.add(tag);
            } while (cursor.moveToNext());

        }
        cursor.close();
        return tags;
    }

}
