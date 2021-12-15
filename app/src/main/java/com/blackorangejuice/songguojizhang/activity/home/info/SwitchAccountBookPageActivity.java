package com.blackorangejuice.songguojizhang.activity.home.info;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.adapter.SwitchAccountBookAdapter;
import com.blackorangejuice.songguojizhang.bean.AccountBook;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountBookMapper;
import com.blackorangejuice.songguojizhang.utils.globle.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.blackorangejuice.songguojizhang.utils.globle.SongGuoUtils;

import java.util.List;

public class SwitchAccountBookPageActivity extends BasicActivity {
    TextView backTextView;
    TextView addTextView;
    LinearLayout addLinearLayout;
    RecyclerView recyclerView;
    SwitchAccountBookAdapter switchAccountBookAdapter;

    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, SwitchAccountBookPageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_account_book_page);

        findView();
        init();
        setListener();
    }


    private void findView() {
        backTextView = findViewById(R.id.activity_switch_account_book_page_back_textview);
        addTextView = findViewById(R.id.activity_switch_account_book_page_add);
        addLinearLayout = findViewById(R.id.activity_switch_account_book_page_add_linear);
        recyclerView = findViewById(R.id.activity_switch_account_book_page_recycler_view);
    }

    private void init() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(SwitchAccountBookPageActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        // 获取所有账本
        AccountBookMapper accountBookMapper = new AccountBookMapper(SongGuoDatabaseHelper.getSongGuoDatabaseHelper(SwitchAccountBookPageActivity.this));
        GlobalInfo.accountBooks = accountBookMapper.selectAll();
        switchAccountBookAdapter = new SwitchAccountBookAdapter(SwitchAccountBookPageActivity.this, GlobalInfo.accountBooks);
        recyclerView.setAdapter(switchAccountBookAdapter);

    }

    private void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchAccountBookPageActivity.this.finish();
            }
        });
        addTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchAccountBookPageActivity.this.addAccountBook();
            }
        });
        addLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchAccountBookPageActivity.this.addAccountBook();
            }
        });
    }

    /**
     * 新建账本并作为当前账本
     */
    private void addAccountBook() {
        // 弹窗输入框输入账本名
        EditText editText = new EditText(SwitchAccountBookPageActivity.this);
        new AlertDialog.Builder(SwitchAccountBookPageActivity.this).setTitle("请输入账本名")
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setView(editText)
                .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (SongGuoUtils.notEmptyString(editText.getText().toString())) {
                                    AccountBookMapper accountBookMapper = new AccountBookMapper(SongGuoDatabaseHelper.getSongGuoDatabaseHelper(SwitchAccountBookPageActivity.this));
                                    AccountBook accountBook = new AccountBook();
                                    accountBook.setAccountBookName(editText.getText().toString());
                                    accountBook = accountBookMapper.insertAccountBook(accountBook);
                                    GlobalInfo.currentAccountBook = accountBook;
                                    refreshAccountBook();
                                    SongGuoUtils.showOneToast(SwitchAccountBookPageActivity.this,"添加成功,当前帐本为《"+accountBook.getAccountBookName()+"》");

                                } else {
                                    SongGuoUtils.showOneToast(SwitchAccountBookPageActivity.this, "账本名不能为空");
                                }

                            }
                        }
                ).setNegativeButton("取消", null).show();

    }

    public void refreshAccountBook() {
        // 全局账本清空
        GlobalInfo.accountBooks.clear();
        // 全局账本重新添加
        AccountBookMapper accountBookMapper = new AccountBookMapper(SongGuoDatabaseHelper.getSongGuoDatabaseHelper(SwitchAccountBookPageActivity.this));
        GlobalInfo.accountBooks.addAll(accountBookMapper.selectAll());
        switchAccountBookAdapter.notifyDataSetChanged();
    }
}