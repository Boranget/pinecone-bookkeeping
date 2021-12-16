package com.blackorangejuice.songguojizhang.transaction.home.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.transaction.home.list.account.edit.AddEditAccountPageActivity;
import com.blackorangejuice.songguojizhang.transaction.home.list.account.show.ShowAccountListPageFragment;
import com.blackorangejuice.songguojizhang.transaction.home.list.event.edit.AddEditEventPageActivity;
import com.blackorangejuice.songguojizhang.utils.basic.BasicFragment;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShowAddListPageFragment extends BasicFragment {
    private View thisView;
    private TextView accountTextView;
    private TextView eventTextView;
    FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.show_add_list_page, container, false);
        return thisView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findView();
        init();
        setListener();


    }

    /**
     * 刷新当前标题颜色
     */
    private void refreshCurrentTextView() {
        switch (GlobalInfo.currentAddPage) {
            case GlobalConstant.ADD_PAGE_ACCOUNT:
                accountTextView.setTextColor(0xff323232);
                eventTextView.setTextColor(0xff808080);
                break;
            case GlobalConstant.ADD_PAGE_EVENT:
                eventTextView.setTextColor(0xff323232);
                accountTextView.setTextColor(0xff808080);
                break;
        }
    }

    /**
     * 替换碎片
     * @param fragment
     */
    protected void replaceFragment(Fragment fragment) {
        // 这里更换为子碎片管理器
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.show_add_list_page_fragment,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void init() {
        refreshCurrentTextView();
        // 默认记账页面
        replaceFragment(new ShowAccountListPageFragment());

    }

    @Override
    public void findView() {
        // 组件绑定
        // 两个标签
        accountTextView = thisView.findViewById(R.id.show_add_list_page_account_textview);
        eventTextView = thisView.findViewById(R.id.show_add_list_page_event_textview);
        // 新增按钮
        floatingActionButton = thisView.findViewById(R.id.show_add_list_page_add_button);
    }

    @Override
    public void setListener() {
        // 绑定监听器
        accountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalInfo.currentAddPage = GlobalConstant.ADD_PAGE_ACCOUNT;
                refreshCurrentTextView();
                // 更改fragment中的内容
                replaceFragment(new ShowAccountListPageFragment());
            }
        });
        eventTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalInfo.currentAddPage = GlobalConstant.ADD_PAGE_EVENT;
                refreshCurrentTextView();
                // 更改fragment中的内容

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch(GlobalInfo.currentAddPage){
                    case GlobalConstant.ADD_PAGE_ACCOUNT:
                        // 跳到新增账本界面
                        AddEditAccountPageActivity.startThisActivity(getActivity());
                        break;
                    case GlobalConstant.ADD_PAGE_EVENT:
                        // 跳到新增事件界面
                        AddEditEventPageActivity.startThisActivity(getActivity());
                        break;
                }
            }
        });
    }

//    /**
//     * 获取当前设置的默认界面
//     * @return
//     */
//    private Fragment getDefultFragment(){
//        switch (defultSelectId){
//            case OVER_PAGE_NUM:
//                return new ShowOverviewPageFragment();
//            case ADD_LIST_PAGE_NUM:
//                return new ShowAddListPageFragment();
//            case MY_INFO_PAGE_NUM:
//                return new ShowMyInfoPageFragment();
//            default:
//                return new ShowOverviewPageFragment();
//        }
//    }

}
