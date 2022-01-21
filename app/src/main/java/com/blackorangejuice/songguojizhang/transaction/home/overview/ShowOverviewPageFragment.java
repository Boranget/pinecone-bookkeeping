package com.blackorangejuice.songguojizhang.transaction.home.overview;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.AccountBook;
import com.blackorangejuice.songguojizhang.bean.AccountItem;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountBookMapper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.utils.SongGuoUtils;
import com.blackorangejuice.songguojizhang.utils.basic.BasicFragment;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowOverviewPageFragment extends BasicFragment {
    public static final String ALL = "all";
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String WEEK = "week";

    private View thisView;
    SongGuoDatabaseHelper songGuoDatabaseHelper;
    Activity activity;
    AccountItemMapper accountItemMapper;
    AccountBookMapper accountBookMapper;


    TextView currentAccountBookNameTextView;

    LinearLayout surplusLinearLayout;
    TextView surplusTypeTextView;
    TextView surplusSumTextView;

    TextView expenditureAllTextView;
    TextView expenditureWeekTextView;
    TextView expenditureMonthTextView;
    TextView expenditureYearTextView;

    TextView incomeAllTextView;
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

        // 结余
        accountBookMapper = new AccountBookMapper(songGuoDatabaseHelper);
        // 设置结余的类型
        String overviewSurplus = GlobalInfo.currentAccountBook.getOverviewBudget();
        if (SongGuoUtils.notEmptyString(overviewSurplus)) {
            switch (overviewSurplus){
                case AccountBook.SHOW_ALL:
                    surplusTypeTextView.setText("全部结余");
                    break;
                case AccountBook.SHOW_YEAR:
                    surplusTypeTextView.setText("本年结余");
                    break;
                case AccountBook.SHOW_MONTH:
                    surplusTypeTextView.setText("本月结余");
                    break;
                case AccountBook.SHOW_WEEK:
                    surplusTypeTextView.setText("本周结余");
                    break;
                case AccountBook.SHOW_NONE:
                    surplusTypeTextView.setText("未设置");
                    break;
            }
        }

        // 设置结余金额
        switch (overviewSurplus) {
            case AccountBook.SHOW_ALL:
                // 获取账本设置中的全部预算
                Double surplusAll = GlobalInfo.currentAccountBook.getBudgetAll();
                // 获取已经消费的数目
                Double allExpenditure = Double.valueOf(getIncomeOrExpenditureSum(AccountItem.EXPENDITURE, ALL));
                surplusSumTextView.setText(String.valueOf(surplusAll - allExpenditure));
                break;
            case AccountBook.SHOW_YEAR:
                // 获取账本设置中的年预算
                Double surplusYear = GlobalInfo.currentAccountBook.getBudgetYear();
                // 获取已经消费的数目
                Double yearExpenditure = Double.valueOf(getIncomeOrExpenditureSum(AccountItem.EXPENDITURE, ALL));
                surplusSumTextView.setText(String.valueOf(surplusYear - yearExpenditure));
                break;
            case AccountBook.SHOW_MONTH:
                // 获取账本设置中的月预算
                Double surplusMonth = GlobalInfo.currentAccountBook.getBudgetMonth();
                // 获取已经消费的数目
                Double monthExpenditure = Double.valueOf(getIncomeOrExpenditureSum(AccountItem.EXPENDITURE, MONTH));
                surplusSumTextView.setText(String.valueOf(surplusMonth - monthExpenditure));
                break;
            case AccountBook.SHOW_WEEK:
                // 获取账本设置中的周预算
                Double surplusWeek = GlobalInfo.currentAccountBook.getBudgetWeek();
                // 获取已经消费的数目
                Double weekExpenditure = Double.valueOf(getIncomeOrExpenditureSum(AccountItem.EXPENDITURE, ALL));
                surplusSumTextView.setText(String.valueOf(surplusWeek - weekExpenditure));
                break;
            case AccountBook.SHOW_NONE:
                surplusSumTextView.setText("点击设置");
                break;
        }


        // 填充统计栏,顺便设置账本中的收入支出信息

        AccountBook accountBook = GlobalInfo.currentAccountBook;

        accountBook.setExpenditureAll(getIncomeOrExpenditureSum(AccountItem.EXPENDITURE, ALL));
        accountBook.setExpenditureYear(getIncomeOrExpenditureSum(AccountItem.EXPENDITURE, YEAR));
        accountBook.setExpenditureMonth(getIncomeOrExpenditureSum(AccountItem.EXPENDITURE, MONTH));
        accountBook.setExpenditureWeek(getIncomeOrExpenditureSum(AccountItem.EXPENDITURE, WEEK));

        expenditureAllTextView.setText(getIncomeOrExpenditureText(AccountItem.EXPENDITURE, ALL));
        expenditureYearTextView.setText(getIncomeOrExpenditureText(AccountItem.EXPENDITURE, YEAR));
        expenditureMonthTextView.setText(getIncomeOrExpenditureText(AccountItem.EXPENDITURE, MONTH));
        expenditureWeekTextView.setText(getIncomeOrExpenditureText(AccountItem.EXPENDITURE, WEEK));

        accountBook.setIncomeAll(getIncomeOrExpenditureSum(AccountItem.INCOME,ALL));
        accountBook.setIncomeYear(getIncomeOrExpenditureSum(AccountItem.INCOME,MONTH));
        accountBook.setIncomeMonth(getIncomeOrExpenditureSum(AccountItem.INCOME,YEAR));
        accountBook.setIncomeWeek(getIncomeOrExpenditureSum(AccountItem.INCOME,WEEK));

        incomeAllTextView.setText(getIncomeOrExpenditureText(AccountItem.INCOME, ALL));
        incomeYearTextView.setText(getIncomeOrExpenditureText(AccountItem.INCOME, YEAR));
        incomeMonthTextView.setText(getIncomeOrExpenditureText(AccountItem.INCOME, MONTH));
        incomeWeekTextView.setText(getIncomeOrExpenditureText(AccountItem.INCOME, WEEK));

    }

    @Override
    public void findView() {
        currentAccountBookNameTextView = thisView.findViewById(R.id.show_overview_page_current_account_book_name);

        surplusLinearLayout = thisView.findViewById(R.id.show_overview_page_surplus_layout);
        surplusTypeTextView = thisView.findViewById(R.id.show_overview_page_surplus_tpye_textview);
        surplusSumTextView = thisView.findViewById(R.id.show_overview_page_surplus_sum_textview);

        expenditureAllTextView = thisView.findViewById(R.id.show_overview_page_expenditure_all);
        expenditureWeekTextView = thisView.findViewById(R.id.show_overview_page_expenditure_week);
        expenditureMonthTextView = thisView.findViewById(R.id.show_overview_page_expenditure_month);
        expenditureYearTextView = thisView.findViewById(R.id.show_overview_page_expenditure_year);

        incomeAllTextView = thisView.findViewById(R.id.show_overview_page_income_all);
        incomeWeekTextView = thisView.findViewById(R.id.show_overview_page_income_week);
        incomeMonthTextView = thisView.findViewById(R.id.show_overview_page_income_month);
        incomeYearTextView = thisView.findViewById(R.id.show_overview_page_income_year);

        lendTextView = thisView.findViewById(R.id.show_overview_page_lend);
        borrowTextView = thisView.findViewById(R.id.show_overview_page_borrow);
    }

    @Override
    public void setListener() {
        surplusLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingBudgetActivity.startThisActivity(getContext());
            }
        });
    }


    public String getIncomeOrExpenditureText(String ioe, String scope) {
        Double result = getIncomeOrExpenditureSum(ioe, scope);
        switch (ioe) {
            case AccountItem.INCOME:
                return "+" + String.valueOf(result);
            case AccountItem.EXPENDITURE:
                return "-" + String.valueOf(result);
            default:
                return "error";
        }

    }
    public Double getIncomeOrExpenditureSum(String ioe, String scope) {
        // 获取今天的日期
        Date todayDate = new Date();
        Double result = 0.0;
        try {
            switch (scope) {
                case ALL:
                    // 取最小时间戳与最大时间戳
                    Date dateMin = new Date(0);
                    result = accountItemMapper.calculateAccountItemSum(
                            ioe, dateMin, todayDate
                    );
                    break;
                case YEAR:
                    SimpleDateFormat simpleDateFormatYear = new SimpleDateFormat("yyyy");

                    String currentYear = simpleDateFormatYear.format(todayDate);
                    // 获取今年年份
                    Date parseYear = simpleDateFormatYear.parse(currentYear);
                    result = accountItemMapper.calculateAccountItemSum(
                            ioe, parseYear, todayDate
                    );
                    break;
                case MONTH:
                    SimpleDateFormat simpleDateFormatMonth = new SimpleDateFormat("yyyy,MM");

                    String currentMonth = simpleDateFormatMonth.format(todayDate);
                    // 获取当前月
                    Date parseMonth = simpleDateFormatMonth.parse(currentMonth);
                    result = accountItemMapper.calculateAccountItemSum(
                            ioe, parseMonth, todayDate
                    );
                    break;
                case WEEK:
                    SimpleDateFormat simpleDateFormatWeek = new SimpleDateFormat("yyyy,MM,W");

                    String currentWeek = simpleDateFormatWeek.format(todayDate);
                    // 获取当前周,国外以周日为第一天,若按星期一为第一天,需要加一天的时间
                    // 但如果今天是周日，则当前周的时间为减去六天的时间
                    Date parseWeek = simpleDateFormatWeek.parse(currentWeek);
                    Long trueWeekTime = 0l;
                    if ("1".equals(new SimpleDateFormat("F").format(todayDate))) {
                        trueWeekTime = parseWeek.getTime() - 6 * (24 * 60 * 60 * 1000);
                    } else {
                        trueWeekTime = parseWeek.getTime() + (24 * 60 * 60 * 1000);
                    }

                    Date trueWeek = new Date(trueWeekTime);
                    result = accountItemMapper.calculateAccountItemSum(
                            ioe, trueWeek, todayDate
                    );
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
}
