package com.blackorangejuice.songguojizhang.activity.home.info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.utils.globle.BasicActivity;

public class AboutPageActivity extends BasicActivity {
    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, AboutPageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);
    }
}