package com.blackorangejuice.songguojizhang.transaction.home.list.account.edit;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.AccountItem;
import com.blackorangejuice.songguojizhang.bean.Tag;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.db.mapper.TagMapper;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.blackorangejuice.songguojizhang.utils.SongGuoUtils;
import com.blackorangejuice.songguojizhang.utils.inputfilter.CashierInputFilter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UpdateEditAccountPageActivity extends EditAccountActivity {
    EditText sumEditText;
    RecyclerView recyclerView;
    TextView backTextView;
    TextView incomeTextView;
    TextView expenditureTextView;
    ImageView tagImageView;
    TextView tagNameTextView;
    EditText remarkEditText;
    TextView timeTextView;
    TextView deleteTextView;
    TextView saveTextView;


    AccountItem accountItem;
    SimpleDateFormat simpleDateFormat;
    SongGuoDatabaseHelper songGuoDatabaseHelper;


    /**
     *
     * 启动此活动
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, UpdateEditAccountPageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_edit_account_page);

        findView();
        init();
        setListener();


    }

    public void getAccountInfoToAccountItem() {
        // 获取金额

        String sum = "";
        if (!SongGuoUtils.notEmptyString(sum = sumEditText.getText().toString())) {
            sum = "0.0";
        }

        accountItem.setSum(Double.valueOf(sum));

        // 设置tagId
        accountItem.setTid(accountItem.getTag().getTid());
        // 获取备注

        String remark = "";
        if (!SongGuoUtils.notEmptyString(remark = remarkEditText.getText().toString())) {
            remark = "无备注";
        }
        accountItem.setRemark(remark);


        // 设置账本为当前全局账本
        accountItem.setAccountBook(GlobalInfo.currentAccountBook);
        // Bid
        accountItem.setBid(GlobalInfo.currentAccountBook.getBid());


    }


    @Override
    public void init() {
        // 初始化记账项对象
        if (GlobalInfo.lastAddAccount == null) {
            SongGuoUtils.showOneToast(UpdateEditAccountPageActivity.this, "发生错误：空的记账项");
            UpdateEditAccountPageActivity.this.finish();
        } else {
            accountItem = GlobalInfo.lastAddAccount;
        }

        // 取出金额显示
        sumEditText.setText(String.valueOf(accountItem.getSum()));
        // 取出备注显示
        remarkEditText.setText(accountItem.getRemark());

        // 时间设为accountitem中的时间
        Date date = new Date(accountItem.getAccountTime());
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        timeTextView.setText(simpleDateFormat.format(date));

        // 金额输入框过滤器
        CashierInputFilter.setCashierInputFilter(sumEditText);

        // tag网格
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);//第二个参数为网格的列数
        recyclerView.setLayoutManager(layoutManager);
        songGuoDatabaseHelper = SongGuoDatabaseHelper.getSongGuoDatabaseHelper(UpdateEditAccountPageActivity.this);
        TagMapper tagMapper = new TagMapper(songGuoDatabaseHelper);
        List<Tag> tags = tagMapper.selectAll();
        recyclerView.setAdapter(new
                TagGridAdapter(tags, this));
        // 设置tag为accountitem中的tag
        setTagNameAndImg(accountItem.getTag());

        // 支出还是收入text View处理
        // 查看accountitem中的记账是啥
        String incomeOrExpenditure = accountItem.getIncomeOrExpenditure();
        switch (incomeOrExpenditure) {
            case AccountItem.INCOME:
                incomeTextView.setTextColor(0xff323232);
                expenditureTextView.setTextColor(0xff808080);
                break;
            case AccountItem.EXPENDITURE:
                expenditureTextView.setTextColor(0xff323232);
                incomeTextView.setTextColor(0xff808080);
                break;
        }
    }

    @Override
    public void findView() {
        // 返回标签
        backTextView = findViewById(R.id.activity_update_edit_account_page_back_textview);
        // 标签名
        tagNameTextView = findViewById(R.id.activity_update_edit_account_page_tag_name);
        // 标签图片
        tagImageView = findViewById(R.id.activity_update_edit_account_page_tag_img);
        // 金额输入框
        sumEditText = findViewById(R.id.activity_update_edit_account_page_sum_edit);
        // 标签选择网格
        recyclerView = findViewById(R.id.activity_update_edit_account_page_tag_grid);
        // 收入与支出标签
        incomeTextView = findViewById(R.id.activity_update_edit_account_page_income_textview);
        expenditureTextView = findViewById(R.id.activity_update_edit_account_page_expenditure_textview);
        // 备注输入框
        remarkEditText = findViewById(R.id.activity_update_edit_account_page_remark_edit);
        // 时间
        timeTextView = findViewById(R.id.activity_update_edit_account_page_time);
        // 保存
        saveTextView = findViewById(R.id.activity_update_edit_account_page_save);
        // 删除
        deleteTextView = findViewById(R.id.activity_update_edit_account_page_delete);

    }

    @Override
    public void setListener() {
        // 返回处理
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateEditAccountPageActivity.this.finish();
            }
        });

        // 删除处理
        deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateEditAccountPageActivity.this);
                builder.setTitle("确认删除该条吗?");
                builder.setMessage("删除后不可恢复");
                builder.setCancelable(true);
                builder.setPositiveButton("确认删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SongGuoUtils.showOneToast(UpdateEditAccountPageActivity.this, "删除成功");
                        AccountItemMapper accountItemMapper = new AccountItemMapper(songGuoDatabaseHelper);
                        accountItemMapper.deleteAccountItem(accountItem);
                        UpdateEditAccountPageActivity.this.finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        SongGuoUtils.showOneToast(UpdateEditAccountPageActivity.this,"取消成功");
                    }
                });
                builder.show();
            }
        });
        // 时间选择
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cale1 = Calendar.getInstance();

                new DatePickerDialog(UpdateEditAccountPageActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        String dataS = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        Date theDateAfterParse = new Date();
                        try {
                            theDateAfterParse = simpleDateFormat.parse(dataS);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        timeTextView.setText(simpleDateFormat.format(theDateAfterParse));
                        // 账目设置时间
                        accountItem.setAccountTime(theDateAfterParse.getTime());
                    }
                }
                        , cale1.get(Calendar.YEAR)
                        , cale1.get(Calendar.MONTH)
                        , cale1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        incomeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountItem.setIncomeOrExpenditure(AccountItem.INCOME);
                incomeTextView.setTextColor(0xff323232);
                expenditureTextView.setTextColor(0xff808080);
            }
        });
        expenditureTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountItem.setIncomeOrExpenditure(AccountItem.EXPENDITURE);
                expenditureTextView.setTextColor(0xff323232);
                incomeTextView.setTextColor(0xff808080);
            }
        });

        // 保存按钮
        saveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAccountInfoToAccountItem();
                AccountItemMapper accountItemMapper = new AccountItemMapper(songGuoDatabaseHelper);
                // 返回带id的对象
                accountItem = accountItemMapper.updateAccountItem(UpdateEditAccountPageActivity.this.accountItem);
                SongGuoUtils.showOneToast(UpdateEditAccountPageActivity.this, "修改成功");
                UpdateEditAccountPageActivity.this.finish();
            }
        });
    }

    @Override
    public void setTagNameAndImg(Tag tag) {
        accountItem.setTag(tag);
        tagNameTextView.setText(tag.getTagName());
        String tagImgName = tag.getTagImgName();
        Bitmap bitmap = SongGuoUtils.getBitmapByFileName(this, "tag/" + tagImgName);
        tagImageView.setImageBitmap(bitmap);
    }


}