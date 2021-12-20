package com.blackorangejuice.songguojizhang.transaction.home.overview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.AccountItem;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.utils.basic.BasicFragment;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

import org.w3c.dom.ls.LSOutput;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowOverviewPageFragment extends BasicFragment {
    public static final String Year = "year";
    public static final String Month = "month";
    public static final String Week = "week";
    private View thisView;
    SongGuoDatabaseHelper songGuoDatabaseHelper;
    Activity activity;
    AccountItemMapper accountItemMapper;


    TextView currentAccountBookNameTextView;

    TextView expenditureWeekTextView;
    TextView expenditureMonthTextView;
    TextView expenditureYearTextView;

    TextView incomeWeekTextView;
    TextView incomeMonthTextView;
    TextView incomeYearTextView;

    TextView lendTextView;
    TextView borrowTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.show_overview_page, container, false);
        return thisView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findView();
        init();
        setListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void init() {
        // 设置顶部账本名
        currentAccountBookNameTextView.setText(GlobalInfo.currentAccountBook.getAccountBookName());
        activity = getActivity();
        songGuoDatabaseHelper = SongGuoDatabaseHelper.getSongGuoDatabaseHelper(activity);
        accountItemMapper = new AccountItemMapper(songGuoDatabaseHelper);


        // 填充统计栏
        expenditureYearTextView.setText(getExpenditure(Year));
        expenditureMonthTextView.setText(getExpenditure(Month));
        expenditureWeekTextView.setText(getExpenditure(Week));

        incomeYearTextView.setText(getIncome(Year));
        incomeMonthTextView.setText(getIncome(Month));
        incomeWeekTextView.setText(getIncome(Week));

    }

    @Override
    public void findView() {
        currentAccountBookNameTextView = thisView.findViewById(R.id.show_overview_page_current_account_book_name);

        expenditureWeekTextView = thisView.findViewById(R.id.show_overview_page_expenditure_week);
        expenditureMonthTextView = thisView.findViewById(R.id.show_overview_page_expenditure_month);
        expenditureYearTextView = thisView.findViewById(R.id.show_overview_page_expenditure_year);

        incomeWeekTextView = thisView.findViewById(R.id.show_overview_page_income_week);
        incomeMonthTextView = thisView.findViewById(R.id.show_overview_page_income_month);
        incomeYearTextView = thisView.findViewById(R.id.show_overview_page_income_year);

        lendTextView = thisView.findViewById(R.id.show_overview_page_lend);
        borrowTextView = thisView.findViewById(R.id.show_overview_page_borrow);
    }

    @Override
    public void setListener() {

    }

    private String getExpenditure(String type) {
        // 获取今天的日期
        Date date = new Date();
        Double result = 0.0;
        try {
            switch (type) {
                case Year:
                    SimpleDateFormat simpleDateFormatYear = new SimpleDateFormat("yyyy");

                    String currentYear = simpleDateFormatYear.format(date);
                    // 获取今年年份
                    Date parseYear = simpleDateFormatYear.parse(currentYear);
                    result = accountItemMapper.calculateAccountItemSum(
                            AccountItem.EXPENDITURE, parseYear, date
                    );
                    break;
                case Month:
                    SimpleDateFormat simpleDateFormatMonth = new SimpleDateFormat("yyyy,MM");

                    String currentMonth = simpleDateFormatMonth.format(date);
                    // 获取当前月
                    Date parseMonth = simpleDateFormatMonth.parse(currentMonth);
                    result =  accountItemMapper.calculateAccountItemSum(
                            AccountItem.EXPENDITURE, parseMonth, date
                    );
                    break;
                case Week:
                    SimpleDateFormat simpleDateFormatWeek = new SimpleDateFormat("yyyy,MM,W");

                    String currentWeek = simpleDateFormatWeek.format(date);
                    // 获取当前周,国外以周日为第一天,若按星期一为第一天,需要加一天的时间
                    Date parseWeek = simpleDateFormatWeek.parse(currentWeek);
                    Long trueWeekTime = parseWeek.getTime() + (24 * 60 * 60 * 1000);
                    Date trueWeek = new Date(trueWeekTime);
                    result =  accountItemMapper.calculateAccountItemSum(
                            AccountItem.EXPENDITURE, trueWeek, date
                    );
                    break;
            }
        } catch (Exception e) {
        }

        return "-"+String.valueOf(result);
    }

    private String getIncome(String type) {
        // 获取今天的日期
        Date date = new Date();
        Double result = 0.0;
        try {
            switch (type) {
                case Year:
                    SimpleDateFormat simpleDateFormatYear = new SimpleDateFormat("yyyy");

                    String currentYear = simpleDateFormatYear.format(date);
                    // 获取今年年份
                    Date parseYear = simpleDateFormatYear.parse(currentYear);
                    result =  accountItemMapper.calculateAccountItemSum(
                            AccountItem.INCOME, parseYear, date
                    );
                    break;
                case Month:
                    SimpleDateFormat simpleDateFormatMonth = new SimpleDateFormat("yyyy,MM");

                    String currentMonth = simpleDateFormatMonth.format(date);
                    // 获取当前月
                    Date parseMonth = simpleDateFormatMonth.parse(currentMonth);
                    result =  accountItemMapper.calculateAccountItemSum(
                            AccountItem.INCOME, parseMonth, date
                    );
                    break;
                case Week:
                    SimpleDateFormat simpleDateFormatWeek = new SimpleDateFormat("yyyy,MM,W");

                    String currentWeek = simpleDateFormatWeek.format(date);
                    // 获取当前周,国外以周日为第一天,若按星期一为第一天,需要加一天的时间
                    Date parseWeek = simpleDateFormatWeek.parse(currentWeek);
                    Long trueWeekTime = parseWeek.getTime() + (24 * 60 * 60 * 1000);
                    Date trueWeek = new Date(trueWeekTime);
                    result =  accountItemMapper.calculateAccountItemSum(
                            AccountItem.INCOME, trueWeek, date
                    );
                    break;
            }
        } catch (Exception e) {

        }
        return "+"+String.valueOf(result);
    }
}
