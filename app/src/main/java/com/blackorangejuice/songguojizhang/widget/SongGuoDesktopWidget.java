package com.blackorangejuice.songguojizhang.widget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.blackorangejuice.songguojizhang.MainActivity;
import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.transaction.home.list.account.edit.AddEditAccountPageActivity;
import com.blackorangejuice.songguojizhang.transaction.home.list.event.edit.AddEditEventPageActivity;
import com.blackorangejuice.songguojizhang.utils.other.SelfApplication;

/**
 * Implementation of App Widget functionality.
 */
public class SongGuoDesktopWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.song_guo_desktop_widget);

        // 这里刚开始想用“不同的intent”放入不同的extra来告知跳转活动的不同意图
        // 但会发生第一个声明的pendingIntent无效，第二个有效的情况
        // 经过查阅PendingIntent源码发现，Intent在判断是否为相同的intent时，不会看new，也不会看extra
        // 经过查找资料得知，不同的data域可以判断为不同的intent
        // 所以使用intent带一个data的uri，在跳转activity中再判断


        Intent accountIntent = new Intent(context, WidgetJumpActivity.class);
        accountIntent.setData(Uri.parse(WidgetJumpActivity.ACCOUNT_URI));
        PendingIntent accountPendingIntent = PendingIntent.getActivity(context, 0, accountIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        views.setOnClickPendingIntent(R.id.song_guo_desktop_widget_add_account, accountPendingIntent);



        Intent eventIntent = new Intent(context, WidgetJumpActivity.class);
        eventIntent.setData(Uri.parse(WidgetJumpActivity.EVENT_URI));
        PendingIntent eventPendingIntent = PendingIntent.getActivity(context, 1, eventIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        views.setOnClickPendingIntent(R.id.song_guo_desktop_widget_add_event, eventPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}