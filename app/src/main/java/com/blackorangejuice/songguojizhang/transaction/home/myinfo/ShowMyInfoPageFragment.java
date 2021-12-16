package com.blackorangejuice.songguojizhang.transaction.home.myinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.transaction.home.myinfo.AboutPageActivity;
import com.blackorangejuice.songguojizhang.transaction.home.myinfo.MoreSettingActivity;
import com.blackorangejuice.songguojizhang.transaction.home.myinfo.SwitchAccountBookPageActivity;
import com.blackorangejuice.songguojizhang.transaction.home.myinfo.UpdatePasswordPageActivity;
import com.blackorangejuice.songguojizhang.transaction.home.myinfo.UpdateUsernamePageActivity;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.SettingInfoMapper;
import com.blackorangejuice.songguojizhang.utils.basic.BasicFragment;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

public class ShowMyInfoPageFragment extends BasicFragment {
    private View thisView;
    private LinearLayout usernameLayout;
    private TextView usernameTextView;
    private LinearLayout enablePasswordLayout;
    private Switch enablePasswordSwitch;
    private LinearLayout changePasswordLayout;
    private LinearLayout switchAccountBookLayout;
    private LinearLayout aboutLayout;
    private LinearLayout moreLayout;

    public SongGuoDatabaseHelper songGuoDatabaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.my_info_page, container, false);
        return thisView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 绑定相应的组件
        findView();
        init();
        // 添加点击监听器
        setListener();
    }

    @Override
    public void init() {
        songGuoDatabaseHelper = SongGuoDatabaseHelper.getSongGuoDatabaseHelper(getContext());
    }

    /**
     * findViewById
     */
    public void findView() {
        usernameLayout = thisView.findViewById(R.id.my_info_page_username_layout);
        usernameTextView = thisView.findViewById(R.id.my_info_page_username);
        enablePasswordLayout = thisView.findViewById(R.id.my_info_page_enable_password_layout);
        enablePasswordSwitch = thisView.findViewById(R.id.my_info_page_enable_password_switch);
        changePasswordLayout = thisView.findViewById(R.id.my_info_page_change_password_layout);
        switchAccountBookLayout = thisView.findViewById(R.id.my_info_page_switch_account_book_layout);
        aboutLayout = thisView.findViewById(R.id.my_info_page_about_layout);
        moreLayout = thisView.findViewById(R.id.my_info_page_more_setting_layout);
    }



    @Override
    public void onResume() {
        super.onResume();
        // 设置switch
        enablePasswordSwitch.setChecked(Boolean.valueOf(GlobalInfo.settingInfo.getIfEnablePasswordCheck()));
        // 加载用户名
        usernameTextView.setText(GlobalInfo.settingInfo.getUsername());


    }

    /**
     * 绑定监听器
     */
    @Override
    public void setListener() {
        usernameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 修改用户名界面
                UpdateUsernamePageActivity.startThisActivity(getActivity());
            }
        });
        enablePasswordLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 改变switch的状态
                enablePasswordSwitch.setChecked(!enablePasswordSwitch.isChecked());
            }

        });
        enablePasswordSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 更新密码启用设置
                SettingInfoMapper settingInfoMapper = new SettingInfoMapper(songGuoDatabaseHelper);
                // 更新全局setting
                GlobalInfo.settingInfo.setIfEnablePasswordCheck(String.valueOf(enablePasswordSwitch.isChecked()));
                // 更新数据库
                settingInfoMapper.updateBySid(GlobalInfo.settingInfo);

            }
        });

        changePasswordLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 修改密码界面
                UpdatePasswordPageActivity.startThisActivity(getActivity());
            }
        });
        switchAccountBookLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchAccountBookPageActivity.startThisActivity(getActivity());
                // 切换账本页面
            }
        });
        aboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 关于界面
                AboutPageActivity.startThisActivity(getActivity());
            }
        });
        moreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 更多设置界面
                MoreSettingActivity.startThisActivity(getActivity());
            }
        });

    }
}
