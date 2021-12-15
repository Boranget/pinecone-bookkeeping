package com.blackorangejuice.songguojizhang.activity.home.info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.utils.globle.BasicActivity;

public class MoreSettingActivity extends BasicActivity {
    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, MoreSettingActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_setting);
    }
}