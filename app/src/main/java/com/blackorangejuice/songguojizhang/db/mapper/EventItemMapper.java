package com.blackorangejuice.songguojizhang.db.mapper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blackorangejuice.songguojizhang.bean.AccountItem;
import com.blackorangejuice.songguojizhang.bean.EventItem;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

import java.util.ArrayList;
import java.util.List;

public class EventItemMapper {
    public static final String INSERT_EVENT_ITEM = "insert into t_event_item\n" +
            " (event_title,event_content,event_time,bid)\n" +
            " values\n" +
            " (?,?,?,?)";
    public static final String SELECT_THE_NEW = "select * from t_event_item order by eid desc limit 1";
    public static final String DELECT_EVENT_ITEM = "delete from t_event_item where eid = ?";
    public static final String UPDATE_EVENT_ITEM = "update t_event_item \n" +
            "set \n" +
            "event_title = ?,\n" +
            "event_content = ?,\n" +
            "event_time = ?,\n" +
            "bid = ?\n" +
            "where eid = ?";
    public static final String SELECT_DESC_PAGE = "select * from t_event_item where bid = ? order by event_time desc limit ?,?";
    public static final String SELECT_BY_EID = "select * from t_event_item where eid = ?";
    SongGuoDatabaseHelper songGuoDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;

    public EventItemMapper(SongGuoDatabaseHelper songGuoDatabaseHelper) {
        this.songGuoDatabaseHelper = songGuoDatabaseHelper;
        sqLiteDatabase = songGuoDatabaseHelper.getWritableDatabase();

    }

    /**
     * 插入一条事件
     * @param eventItem
     * @return
     */
    public EventItem insertEventItem(EventItem eventItem) {
        sqLiteDatabase.execSQL(INSERT_EVENT_ITEM, new String[]{
                eventItem.getEventTitle(), eventItem.getEventContent(),
                String.valueOf(eventItem.getEventTime()), String.valueOf(eventItem.getBid())});
        return selectTheNew();
    }

    /**
     * 寻找刚插入的事件
     * @return
     */
    private EventItem selectTheNew() {
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_THE_NEW, null);
        EventItem eventItem = new EventItem();
        if(cursor.moveToFirst()){
            eventItem.setEid(cursor.getInt(cursor.getColumnIndex("eid")));
            eventItem.setEventTitle(cursor.getString(cursor.getColumnIndex("event_title")));
            eventItem.setEventContent(cursor.getString(cursor.getColumnIndex("event_content")));
            eventItem.setEventTime(cursor.getLong(cursor.getColumnIndex("event_time")));
            eventItem.setBid(cursor.getInt(cursor.getColumnIndex("bid")));
        }
        cursor.close();
        return eventItem;
    }

    /**
     * 删除事件
     * @param eventItem
     */
    public void deleteEventItem(EventItem eventItem){
        sqLiteDatabase.execSQL(DELECT_EVENT_ITEM,new String[]{String.valueOf(eventItem.getEid())});
    }

    /**
     * 更新事件
     * @param eventItem
     * @return
     */
    public EventItem updateEventItem(EventItem eventItem){
        sqLiteDatabase.execSQL(UPDATE_EVENT_ITEM,new String[]{
                eventItem.getEventTitle(),eventItem.getEventContent(),
                String.valueOf(eventItem.getEventTime()), String.valueOf(eventItem.getBid()),
                String.valueOf(eventItem.getEid())});
        return selectByEid(eventItem.getEid());
    }
    /**
     * 分页查找事件
     * @param page
     * @param size
     * @return
     */
    public List<EventItem> selectDescPage(Integer page, Integer size){
        // 页面转指针
        int index = (page - 1) * size;
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_DESC_PAGE, new String[]{String.valueOf(GlobalInfo.currentAccountBook.getBid()), String.valueOf(index), String.valueOf(size)});
        List<EventItem> eventItems = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                EventItem eventItem = new EventItem();
                eventItem.setEid(cursor.getInt(cursor.getColumnIndex("eid")));
                eventItem.setEventTitle(cursor.getString(cursor.getColumnIndex("event_title")));
                eventItem.setEventContent(cursor.getString(cursor.getColumnIndex("event_content")));
                eventItem.setEventTime(cursor.getLong(cursor.getColumnIndex("event_time")));
                eventItem.setBid(cursor.getInt(cursor.getColumnIndex("bid")));
                eventItems.add(eventItem);
            }while (cursor.moveToNext());
        }


        cursor.close();
        return eventItems;
    }

    /**
     * 通过eid查找事件
     * @param eid
     * @return
     */
    public EventItem selectByEid(Integer eid){
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_BY_EID, new String[]{String.valueOf(eid)});
        EventItem eventItem = new EventItem();
        if(cursor.moveToFirst()){
            eventItem.setEid(cursor.getInt(cursor.getColumnIndex("eid")));
            eventItem.setEventTitle(cursor.getString(cursor.getColumnIndex("event_title")));
            eventItem.setEventContent(cursor.getString(cursor.getColumnIndex("event_content")));
            eventItem.setEventTime(cursor.getLong(cursor.getColumnIndex("event_time")));
            eventItem.setBid(cursor.getInt(cursor.getColumnIndex("bid")));
        }
        cursor.close();
        return eventItem;
    }
}
