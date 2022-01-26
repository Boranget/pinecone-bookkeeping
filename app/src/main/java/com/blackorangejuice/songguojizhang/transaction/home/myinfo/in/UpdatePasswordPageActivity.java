package com.blackorangejuice.songguojizhang.transaction.home.myinfo.in;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.SettingInfo;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.SettingInfoMapper;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.blackorangejuice.songguojizhang.utils.SongGuoUtils;

import java.util.Objects;

public class UpdatePasswordPageActivity extends BasicActivity {
    TextView backTextView;
    TextView saveTextView;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, UpdatePasswordPageActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password_page);
        findView();
        init();
        setListener();
    }

    @Override
    public void init() {

    }


    public void findView(){
        backTextView = findViewById(R.id.activity_update_password_page_back_textview);
        saveTextView = findViewById(R.id.activity_update_password_page_save);
        passwordEditText = findViewById(R.id.activity_update_password_page_password);
        confirmPasswordEditText = findViewById(R.id.activity_update_password_page_confirm_password);
    }
    public void setListener(){
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePasswordPageActivity.this.finish();
            }
        });
        saveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取密码
                String passwordS = passwordEditText.getText().toString();
                // 获取确认密码
                String confirmPasswordS = confirmPasswordEditText.getText().toString();

                if(!SongGuoUtils.notEmptyString(passwordS)){
                    SongGuoUtils.showOneToast(UpdatePasswordPageActivity.this,"密码不能为空");
                    return;
                }else if(!Objects.equals(passwordS,confirmPasswordS)){
                    SongGuoUtils.showOneToast(UpdatePasswordPageActivity.this,"两次密码不同");
                    return;
                }else{
                    SettingInfoMapper settingInfoMapper = new SettingInfoMapper(SongGuoDatabaseHelper.getSongGuoDatabaseHelper(UpdatePasswordPageActivity.this));
                    SettingInfo settingInfo = GlobalInfo.settingInfo;
                    settingInfo.setPassword(passwordS);
                    settingInfoMapper.updateBySid(settingInfo);
                    UpdatePasswordPageActivity.this.finish();
                    SongGuoUtils.showOneToast(UpdatePasswordPageActivity.this,"修改成功");
                }


            }
        });
    }
}