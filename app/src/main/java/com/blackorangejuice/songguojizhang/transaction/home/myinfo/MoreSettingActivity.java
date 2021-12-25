package com.blackorangejuice.songguojizhang.transaction.home.myinfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.AccountItem;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.db.mapper.TagMapper;
import com.blackorangejuice.songguojizhang.utils.SongGuoUtils;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import me.rosuh.filepicker.config.FilePickerManager;

public class MoreSettingActivity extends BasicActivity {
    LinearLayout importLinerLayout;

    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, MoreSettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_setting);
        findView();
        init();
        setListener();
    }

    @Override
    public void init() {

    }

    @Override
    public void findView() {
        importLinerLayout = findViewById(R.id.activity_more_setting_import_layout);
    }

    @Override
    public void setListener() {
        importLinerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilePickerManager.INSTANCE
                        .from(MoreSettingActivity.this)
                        .forResult(FilePickerManager.REQUEST_CODE);

            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FilePickerManager.REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    List<String> strings = FilePickerManager.INSTANCE.obtainData();
                    // doing something...
                    for (String fileName : strings) {
                        try {
                            // 将文件中的内容按行存储到list中
                            List<String> fileContent = new ArrayList<>();
                            FileReader fileReader = new FileReader(fileName);
                            BufferedReader bufferedReader = new BufferedReader(fileReader);
                            String everyLine = "";
                            while (null != (everyLine = bufferedReader.readLine())) {
                                fileContent.add(everyLine);
                            }
                            // 开始解析
                            boolean isAccount = false;
                            for (String s : fileContent) {

                                if (s.startsWith("交易时间")) {
                                    isAccount = true;
                                    continue;
                                }
                                if (isAccount) {
                                    // 只解析账单部分,账单以上的部分跳过
                                    AccountItem accountItem = new AccountItem();
                                    StringBuilder remark = new StringBuilder();
                                    String[] accountContent = s.split(",");
                                    // 0 交易时间,2021-12-14 11:59:58
                                    String timeStr = accountContent[0];
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date parse = simpleDateFormat.parse(timeStr);
                                    accountItem.setAccountTime(parse.getTime());
                                    // 1 交易类型,扫二维码付款,添加到备注
                                    remark.append(accountContent[1]);
                                    remark.append("-");
                                    // 2 交易对方,
                                    remark.append(accountContent[2]);
                                    remark.append("-");
                                    // 3 商品,
                                    remark.append(accountContent[3]);
                                    remark.append("-");
                                    accountItem.setRemark(remark.toString());// 4 收/支,
                                    switch (accountContent[4]) {
                                        case "收入":
                                            accountItem.setIncomeOrExpenditure(AccountItem.INCOME);
                                            break;
                                        case "支出":
                                            accountItem.setIncomeOrExpenditure(AccountItem.EXPENDITURE);
                                            break;
                                    }
                                    // 5 金额(元),
                                    accountItem.setSum(Double.valueOf(accountContent[5].substring(1)));
                                    // 6 支付方式,
                                    remark.append(accountContent[6]);
                                    remark.append("-");
                                    // 7 当前状态,
                                    remark.append(accountContent[7]);
                                    remark.append("-");
                                    // 8 交易单号,
                                    // 9 商户单号,
                                    // 10 备注
                                    remark.append(accountContent[10]);
                                    accountItem.setRemark(remark.toString());
                                    // tag
                                    TagMapper tagMapper = new TagMapper(SongGuoDatabaseHelper.getSongGuoDatabaseHelper(MoreSettingActivity.this));
                                    accountItem.setTid(tagMapper.selectByTagName("微信").getTid());
                                    accountItem.setBid(GlobalInfo.currentAccountBook.getBid());
                                    accountItem.setIfBorrowOrLend(AccountItem.IF_FALSE);
                                    accountItem.setEid(0);
                                    // 保持账本
                                    AccountItemMapper accountItemMapper = new AccountItemMapper(SongGuoDatabaseHelper.getSongGuoDatabaseHelper(MoreSettingActivity.this));
                                    accountItemMapper.insertAccountItem(accountItem);
                                }

                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    SongGuoUtils.showOneToast(MoreSettingActivity.this, "没有选择任何文件");
                }

                break;
        }
    }
}