package com.blackorangejuice.songguojizhang.db.mapper;

import android.database.sqlite.SQLiteDatabase;

import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;

public class EventItemMapper {
    SongGuoDatabaseHelper songGuoDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;
    public EventItemMapper(SongGuoDatabaseHelper songGuoDatabaseHelper){
        this.songGuoDatabaseHelper = songGuoDatabaseHelper;
        sqLiteDatabase = songGuoDatabaseHelper.getWritableDatabase();

    }
}
