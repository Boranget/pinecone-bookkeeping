package com.blackorangejuice.songguojizhang.activity.guide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.activity.home.HomePageActivity;
import com.blackorangejuice.songguojizhang.bean.AccountBook;
import com.blackorangejuice.songguojizhang.bean.GuideInfo;
import com.blackorangejuice.songguojizhang.bean.SettingInfo;
import com.blackorangejuice.songguojizhang.bean.User;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountBookMapper;
import com.blackorangejuice.songguojizhang.db.mapper.SettingInfoMapper;
import com.blackorangejuice.songguojizhang.db.mapper.TagMapper;
import com.blackorangejuice.songguojizhang.db.mapper.UserMapper;
import com.blackorangejuice.songguojizhang.utils.globle.ActivityController;
import com.blackorangejuice.songguojizhang.utils.globle.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.blackorangejuice.songguojizhang.utils.globle.SongGuoUtils;

public class GuideAccountBookNamePageActivity extends BasicActivity {
    // 账本输入框
    private EditText accountBookNameEditText;
    // 退出按钮
    private TextView backTextView;
    // 继续按钮
    private TextView continueTextView;
    /**
     * 启动此活动
     * @param context
     */
    public static void startThisActivity(Context context){
        Intent intent = new Intent(context,GuideAccountBookNamePageActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_account_book_name_page);

        accountBookNameEditText = findViewById(R.id.activity_guide_account_book_name_page_account_book_name);
        backTextView = findViewById(R.id.activity_guide_account_book_name_page_back);
        continueTextView = findViewById(R.id.activity_guide_account_book_name_page_continue);

        // 退出按钮监听器
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuideAccountBookNamePageActivity.this.finish();
            }
        });
        continueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SongGuoUtils.notEmptyString(accountBookNameEditText.getText().toString())) {
                    SongGuoUtils.showOneToast(GuideAccountBookNamePageActivity.this, "账本名不能为空");
                    return;
                }
                // 防止用户多次点击
                continueTextView.setClickable(false);
                // 保存用户名到全局类
                GlobalInfo.guideInfo.setAccountBookName(accountBookNameEditText.getText().toString());
                // 开始解析guide信息
                GuideInfo guideInfo = GlobalInfo.guideInfo;
                // 用户名
                String username = guideInfo.getUsername();
                // 密码
                String password = guideInfo.getPassword();
                // 新建用户
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                Log.d("GuideAccountBookNamePageActivity.java", user.toString());
                // 初始化Helper
                SongGuoDatabaseHelper songGuoDatabaseHelper = SongGuoDatabaseHelper.getSongGuoDatabaseHelper(GuideAccountBookNamePageActivity.this);
                // 并保存到数据库
                UserMapper userMapper = new UserMapper(songGuoDatabaseHelper);
                user = userMapper.insertUser(user);
                Log.d("GuideAccountBookNamePageActivity.java", user.toString());
                // 保存当前用户的uid
                SharedPreferences songGuoSharedPreferences = SongGuoUtils.getSongGuoSharedPreferences();
                SharedPreferences.Editor editor = songGuoSharedPreferences.edit();
                editor.putInt(GlobalConstant.UID,user.getUid());
                // 是否启用密码验证
                String ifEnablePasswordCheck = guideInfo.getIfEnablePasswordCheck();
                // 新建账本并保存到数据库
                AccountBook accountBook = new AccountBook();
                accountBook.setUid(user.getUid());
                accountBook.setAccountBookName(guideInfo.getAccountBookName());
                AccountBookMapper accountBookMapper = new AccountBookMapper(songGuoDatabaseHelper);
                // accountBook 替换为带id的对象
                accountBook = accountBookMapper.insertAccountBook(accountBook);
                // 新建设置并保存到数据库
                SettingInfo settingInfo = SettingInfo.getDefultSettingInfo(user.getUid());
                settingInfo.setIfEnablePasswordCheck(ifEnablePasswordCheck);
                settingInfo.setCurrentAccountBookBid(accountBook.getBid());
                SettingInfoMapper settingInfoMapper = new SettingInfoMapper(songGuoDatabaseHelper);
                settingInfoMapper.insertSettingInfo(settingInfo);
                // 更改第一次使用标识
                editor.putBoolean(GlobalConstant.isFirstUse,false);
                editor.apply();

                // 初始化全局类
                // 用户
                GlobalInfo.user = user;
                // 设置
                GlobalInfo.settingInfo = settingInfo;
                // 账本
                GlobalInfo.currentAccountBook = accountBook;
                // 初始化tag
                initTag(songGuoDatabaseHelper);

                // bug:进入主界面还能返回引导界面
                // 解决:结束之前所有活动,防止用户返回
                ActivityController.finishAll();
                // 跳转到home界面
                HomePageActivity.startThisActivity(GuideAccountBookNamePageActivity.this);
            }
        });
    }
    // 初始化tag
    void initTag(SongGuoDatabaseHelper s){
        TagMapper tagMapper = new TagMapper(s);
        tagMapper.init();
    }
}