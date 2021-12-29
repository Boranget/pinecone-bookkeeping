package com.blackorangejuice.songguojizhang.transaction.home.myinfo.moresetting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.AccountItem;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.db.mapper.TagMapper;
import com.blackorangejuice.songguojizhang.transaction.home.myinfo.MoreSettingActivity;
import com.blackorangejuice.songguojizhang.utils.SongGuoUtils;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.rosuh.filepicker.config.FilePickerManager;

public class ImportsActivity extends BasicActivity {
    public static int currentImport = 0;
    TextView backTextView;
    LinearLayout wechatLinearLayout;
    LinearLayout alipayLinearLayout;
    public static final int WECHAT = 2112291034;
    public static final int ALIPAY = 2112291035;
    SongGuoDatabaseHelper songGuoDatabaseHelper;
    TagMapper tagMapper;
    AccountItemMapper accountItemMapper;

    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, ImportsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imports);
        findView();
        init();
        setListener();
    }

    @Override
    public void init() {
        songGuoDatabaseHelper = SongGuoDatabaseHelper.getSongGuoDatabaseHelper(ImportsActivity.this);
        tagMapper = new TagMapper(songGuoDatabaseHelper);
        accountItemMapper = new AccountItemMapper(songGuoDatabaseHelper);
    }

    @Override
    public void findView() {
        backTextView = findViewById(R.id.activity_imports_back_textview);
        wechatLinearLayout = findViewById(R.id.activity_imports_wechat);
        alipayLinearLayout = findViewById(R.id.activity_imports_alipay);
    }

    @Override
    public void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImportsActivity.this.finish();
            }
        });
        wechatLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importsWechat();
            }

            private void importsWechat() {
                FilePickerManager.INSTANCE
                        .from(ImportsActivity.this)
                        .forResult(FilePickerManager.REQUEST_CODE);
                currentImport = WECHAT;

            }
        });
        alipayLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importsAlipay();
            }

            private void importsAlipay() {
                FilePickerManager.INSTANCE
                        .from(ImportsActivity.this)
                        .forResult(FilePickerManager.REQUEST_CODE);
                currentImport = ALIPAY;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // 获取选择的文件列表
            List<String> strings = FilePickerManager.INSTANCE.obtainData();
            // 处理选择的文件
            for (String fileName : strings) {
                try {
                    // 将文件中的内容按行存储到list中，供之后使用
                    List<String> fileContent = new ArrayList<>();
                    String currentCharset = "UTF-8";
                    if(currentImport == ALIPAY){
                        currentCharset = "GB2312";
                    }
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(new FileInputStream(fileName),currentCharset)
                    );
                    String everyLine = "";
                    while (null != (everyLine = bufferedReader.readLine())) {
                        fileContent.add(everyLine);
                    }
                    // 开始解析
                    // 是否处于账本区域
                    boolean isAccount = false;
                    int otherNum = 0;
                    int incomeNum = 0;
                    int expenditureNum = 0;
                    StringBuilder stringBuilder = new StringBuilder();
                    switch (ImportsActivity.currentImport) {
                        case WECHAT:
                            wechatfor:
                            for (String s : fileContent) {
                                if (s.startsWith("交易时间")) {
                                    isAccount = true;
                                    continue;
                                }
                                if (isAccount) {
                                    // 只解析账单部分,账单以上的部分跳过
                                    AccountItem accountItem = new AccountItem();
                                    // 备注
                                    StringBuilder remark = new StringBuilder();
                                    String[] accountContent = s.split(",");
                                    // 0 交易时间,2021-12-14 11:59:58
                                    String timeStr = accountContent[0];
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date parse = simpleDateFormat.parse(timeStr);
                                    accountItem.setAccountTime(parse.getTime());
                                    // 1 交易类型,扫二维码付款,添加到备注
                                    remark.append(accountContent[1].trim());
                                    remark.append("-");
                                    // 2 交易对方,
                                    remark.append(accountContent[2].trim());
                                    remark.append("-");
                                    // 3 商品,
                                    remark.append(accountContent[3].trim());
                                    remark.append("-");
                                    accountItem.setRemark(remark.toString());
                                    // 4 收/支,
                                    switch (accountContent[4].trim()) {
                                        case "收入":
                                            accountItem.setIncomeOrExpenditure(AccountItem.INCOME);
                                            incomeNum++;
                                            break;
                                        case "支出":
                                            accountItem.setIncomeOrExpenditure(AccountItem.EXPENDITURE);
                                            expenditureNum++;
                                            break;
                                        default:
                                            otherNum++;
                                            continue wechatfor;
                                    }
                                    // 5 金额(元),
                                    accountItem.setSum(Double.valueOf(accountContent[5].trim().substring(1)));
                                    // 6 支付方式,
                                    remark.append(accountContent[6].trim());
                                    remark.append("-");
                                    // 7 当前状态,
                                    remark.append(accountContent[7].trim());
                                    remark.append("-");
                                    // 8 交易单号,
                                    // 9 商户单号,
                                    // 10 备注
                                    remark.append(accountContent[10].trim());
                                    accountItem.setRemark(remark.toString());
                                    // tag
                                    accountItem.setTid(tagMapper.selectByTagName("微信").getTid());
                                    // Bid
                                    accountItem.setBid(GlobalInfo.currentAccountBook.getBid());
                                    // 借入借出
                                    accountItem.setIfBorrowOrLend(AccountItem.IF_FALSE);
                                    // 关联事件
                                    accountItem.setEid(0);
                                    // 保存账本
                                    accountItemMapper.insertAccountItem(accountItem);
                                }

                            }
                            stringBuilder.append("成功从微信账单文件导入[");
                            stringBuilder.append(incomeNum + expenditureNum);
                            stringBuilder.append("]条账单,其中有[");
                            stringBuilder.append(incomeNum);
                            stringBuilder.append("]条是收入账单,[");
                            stringBuilder.append(expenditureNum);
                            stringBuilder.append("]条是支出账单,除此之外还有[");
                            stringBuilder.append(otherNum);
                            stringBuilder.append("]条不支持的账单类型");
                            SongGuoUtils.showOneToast(ImportsActivity.this, stringBuilder.toString());


                            break;
                        case ALIPAY:
                            // 循环标签,便于跳出
                            alipayfor:
                            for (String s : fileContent) {
                                if (s.startsWith("收/支")) {
                                    isAccount = true;
                                    continue;
                                }
                                if(s.startsWith("----------------------------")){
                                    isAccount = false;
                                    break;
                                }
                                if (isAccount) {
                                    // 只解析账单部分,账单以上的部分跳过
                                    AccountItem accountItem = new AccountItem();
                                    StringBuilder remark = new StringBuilder();
                                    String[] accountContent = s.split(",");
                                    // 0 收支
                                    switch (accountContent[0].trim()) {
                                        case "收入":
                                            accountItem.setIncomeOrExpenditure(AccountItem.INCOME);
                                            incomeNum++;
                                            break;
                                        case "支出":
                                            accountItem.setIncomeOrExpenditure(AccountItem.EXPENDITURE);
                                            expenditureNum++;
                                            break;
                                        default:
                                            otherNum++;
                                            continue alipayfor;
                                    }
                                    // 1 交易对方
                                    remark.append(accountContent[1].trim());
                                    remark.append("-");
                                    // 2 对方账号
//                                    remark.append(accountContent[2].trim());
//                                    remark.append("-");
                                    // 3 商品说明
                                    remark.append(accountContent[3].trim());
                                    remark.append("-");
                                    // 4 收付款方式 余额宝 银行卡
                                    remark.append(accountContent[4].trim());
                                    remark.append("-");
                                    // 5 金额
                                    accountItem.setSum(Double.valueOf(accountContent[5]));
                                    // 6 交易状态
                                    remark.append(accountContent[6].trim());
                                    remark.append("-");
                                    // 7 交易分类 转账红包
                                    remark.append(accountContent[7].trim());
                                    // 8 订单号
                                    // 9 商家订单号
                                    // 10 交易时间
                                    String timeStr = accountContent[10];
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                                    Date parse = simpleDateFormat.parse(timeStr);
                                    accountItem.setAccountTime(parse.getTime());
                                    // remark
                                    accountItem.setRemark(remark.toString());
                                    // tag
                                    accountItem.setTid(tagMapper.selectByTagName("支付宝").getTid());
                                    // Bid
                                    accountItem.setBid(GlobalInfo.currentAccountBook.getBid());
                                    // 借入借出
                                    accountItem.setIfBorrowOrLend(AccountItem.IF_FALSE);
                                    // 关联事件
                                    accountItem.setEid(0);
                                    // 保存账本
                                    accountItemMapper.insertAccountItem(accountItem);

                                }

                            }

                            stringBuilder.append("成功从支付宝账单文件导入[");
                            stringBuilder.append(incomeNum + expenditureNum);
                            stringBuilder.append("]条账单,其中有[");
                            stringBuilder.append(incomeNum);
                            stringBuilder.append("]条是收入账单,[");
                            stringBuilder.append(expenditureNum);
                            stringBuilder.append("]条是支出账单,除此之外还有[");
                            stringBuilder.append(otherNum);
                            stringBuilder.append("]条不支持的账单类型");
                            SongGuoUtils.showOneToast(ImportsActivity.this, stringBuilder.toString());
                            break;
                        default:
                            SongGuoUtils.showOneToast(ImportsActivity.this, "异常");
                            break;
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
            SongGuoUtils.showOneToast(ImportsActivity.this, "没有选择任何文件");
        }

    }
}
