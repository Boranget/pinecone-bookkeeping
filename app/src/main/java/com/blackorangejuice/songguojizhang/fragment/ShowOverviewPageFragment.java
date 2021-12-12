package com.blackorangejuice.songguojizhang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.utils.globle.BasicFragment;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

public class ShowOverviewPageFragment extends BasicFragment {
    private View thisView;
    // 当前账本名标签
    TextView currentAccountBookNameTextView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.show_overview_page,container,false);
        return thisView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 初始化标签名
        currentAccountBookNameTextView = thisView.findViewById(R.id.show_overview_page_current_account_book_name);
        currentAccountBookNameTextView.setText(GlobalInfo.currentAccountBook.getAccountBookName());
    }
}
