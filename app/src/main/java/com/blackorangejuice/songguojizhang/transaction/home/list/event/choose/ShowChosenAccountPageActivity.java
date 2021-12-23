package com.blackorangejuice.songguojizhang.transaction.home.list.event.choose;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.AccountItem;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.db.mapper.TagMapper;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

import java.util.List;

public class ShowChosenAccountPageActivity extends BasicActivity {
    RecyclerView chosenAccountRecyclerView;
    TextView backTextView;
    TextView addTextView;

    SongGuoDatabaseHelper songGuoDatabaseHelper;

    public static void startThisActivity(Context context){
        Intent intent = new Intent(context,ShowChosenAccountPageActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_chosen_account_page);

        findView();
        init();
        setListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        init();
    }

    @Override
    public void init() {
        songGuoDatabaseHelper = SongGuoDatabaseHelper.getSongGuoDatabaseHelper(ShowChosenAccountPageActivity.this);
        AccountItemMapper accountItemMapper = new AccountItemMapper(songGuoDatabaseHelper);
        List<AccountItem> accountItems = accountItemMapper.selectByEvent(GlobalInfo.lastAddEvent);
        // 组合tag
        TagMapper tagMapper = new TagMapper(songGuoDatabaseHelper);
        for(AccountItem accountItem:accountItems){
            accountItem.setTag(tagMapper.selectByTid(accountItem.getTid()));
        }
        ShowChosenAccountItemRecycleViewAdapter showChosenAccountItemRecycleViewAdapter = new ShowChosenAccountItemRecycleViewAdapter(ShowChosenAccountPageActivity.this,accountItems);
        chosenAccountRecyclerView.setAdapter(showChosenAccountItemRecycleViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ShowChosenAccountPageActivity.this);
        chosenAccountRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void findView() {
        chosenAccountRecyclerView = findViewById(R.id.activity_show_chosen_account_page_show_event_list_recycle_view);
        backTextView = findViewById(R.id.activity_show_chosen_account_page_back_textview);
        addTextView = findViewById(R.id.activity_show_chosen_account_page_add);
    }

    @Override
    public void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowChosenAccountPageActivity.this.finish();
            }
        });
        addTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseShowAccountListPageActivity.startThisActivity(ShowChosenAccountPageActivity.this);
            }
        });
    }
}