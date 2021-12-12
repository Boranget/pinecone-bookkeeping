package com.blackorangejuice.songguojizhang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.utils.globle.BasicFragment;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

public class ShowMyInfoPageFragment extends BasicFragment {
    private View thisView;
    private LinearLayout usernameLayout;
    private TextView usernameTextView;
//    private LinearLayout Layout;
//    private LinearLayout Layout;
//    private LinearLayout Layout;
//    private LinearLayout Layout;
//    private LinearLayout Layout;
//    private LinearLayout Layout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thisView =  inflater.inflate(R.layout.my_info_page,container,false);
        return thisView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 加载用户名
        usernameTextView = thisView.findViewById(R.id.my_info_page_username);
        usernameTextView.setText(GlobalInfo.user.getUsername());
        // 用户名点击事件
        usernameLayout = thisView.findViewById(R.id.my_info_page_username_layout);
        usernameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
