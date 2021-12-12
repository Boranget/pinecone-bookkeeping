package com.blackorangejuice.songguojizhang;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.blackorangejuice.songguojizhang.activity.guide.GuideStartPageActivity;
import com.blackorangejuice.songguojizhang.activity.home.HomePageActivity;
import com.blackorangejuice.songguojizhang.bean.AccountBook;
import com.blackorangejuice.songguojizhang.bean.SettingInfo;
import com.blackorangejuice.songguojizhang.bean.User;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountBookMapper;
import com.blackorangejuice.songguojizhang.db.mapper.SettingInfoMapper;
import com.blackorangejuice.songguojizhang.db.mapper.UserMapper;
import com.blackorangejuice.songguojizhang.utils.globle.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.blackorangejuice.songguojizhang.utils.globle.SongGuoUtils;

public class MainActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行初始化操作
                // 判断是否第一次使用该软件
                // preferences 存储 第一次使用的标志,与当前使用的用户id
                SharedPreferences songguoPreferences = SongGuoUtils.getSongGuoSharedPreferences();
                // 如果没有该值,则为第一次启动
                boolean isFirstUse = songguoPreferences.getBoolean(GlobalConstant.isFirstUse,true);
                if(isFirstUse){
                    // 如果为第一次启动
                    // 进入引导界面,引导流程结束后再更改标识
                    GuideStartPageActivity.startThisActivity(MainActivity.this);
                }else {
                    // 如果不是第一次启动,
                    // 初始化全局类
                    SongGuoDatabaseHelper songGuoDatabaseHelper = SongGuoDatabaseHelper.getSongGuoDatabaseHelper(MainActivity.this);
                    // 用户
                    UserMapper userMapper = new UserMapper(songGuoDatabaseHelper);
                    Integer uid = songguoPreferences.getInt(GlobalConstant.UID,1);
                    User user = userMapper.selectUserByUid(uid);
                    GlobalInfo.user = user;
                    // 设置
                    SettingInfoMapper settingInfoMapper = new SettingInfoMapper(songGuoDatabaseHelper);
                    SettingInfo settingInfo = settingInfoMapper.selectByUid(uid);
                    GlobalInfo.settingInfo = settingInfo;
                    // 账本
                    AccountBookMapper accountBookMapper = new AccountBookMapper(songGuoDatabaseHelper);
                    Integer currentAccountBookBid = settingInfo.getCurrentAccountBookBid();
                    Log.i("currentAccountBookBid", String.valueOf(currentAccountBookBid));
                    AccountBook accountBook = accountBookMapper.selectByBid(currentAccountBookBid);
                    GlobalInfo.currentAccountBook = accountBook;
                    // 进入主页
                    HomePageActivity.startThisActivity(MainActivity.this);

                }

                MainActivity.this.finish();

            }
        }).start();
    }
}