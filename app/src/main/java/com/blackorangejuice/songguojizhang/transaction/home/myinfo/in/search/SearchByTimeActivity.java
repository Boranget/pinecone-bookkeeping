package com.blackorangejuice.songguojizhang.transaction.home.myinfo.in.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.SearchItem;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.db.mapper.EventItemMapper;
import com.blackorangejuice.songguojizhang.transaction.home.list.in.account.edit.UpdateEditAccountPageActivity;
import com.blackorangejuice.songguojizhang.utils.SongGuoUtils;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class SearchByTimeActivity extends BasicActivity {

    TextView backTextView;
    LinearLayout chooseTimeLinearLayout;
    TextView chooseTimeTextView;
    RecyclerView searchRecyclerView;
    SongGuoDatabaseHelper songGuoDatabaseHelper;
    AccountItemMapper accountItemMapper;
    EventItemMapper eventItemMapper;

    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, SearchByTimeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_time_page);
        findView();
        init();
        setListener();
    }


    @Override
    public void init() {
        songGuoDatabaseHelper = SongGuoDatabaseHelper.getSongGuoDatabaseHelper(this);
        accountItemMapper = new AccountItemMapper(songGuoDatabaseHelper);
        eventItemMapper = new EventItemMapper(songGuoDatabaseHelper);

    }

    @Override
    public void findView() {
        backTextView = findViewById(R.id.activity_search_by_time_page_back_textview);
        chooseTimeLinearLayout = findViewById(R.id.activity_search_by_time_page_time_linear_layout);
        chooseTimeTextView = findViewById(R.id.activity_search_by_time_page_time);
        searchRecyclerView = findViewById(R.id.activity_search_by_time_page_show_result_recycle_view);
    }

    @Override
    public void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchByTimeActivity.this.finish();
            }
        });
        chooseTimeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Calendar cale1 = Calendar.getInstance();

                new DatePickerDialog(SearchByTimeActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                        chooseTimeTextView.setText(simpleDateFormat.format(theDateAfterParse));
                        // 获得搜索时间
                        long time = theDateAfterParse.getTime();
                        // 计算时间范围
                        Date date1 = new Date(time);
                        Date date2 = new Date(time + (24 * 60 * 60 * 1000));
                        // 查找账单
                        List<SearchItem> searchItemsAccount = accountItemMapper.selectByTime(date1,date2);
                        // 查找事件
                        List<SearchItem> searchItemsEvent = eventItemMapper.selectByTime(date1,date2);
                        // 组合list
                        List<SearchItem> searchItemsAll = new ArrayList<>();
                        searchItemsAll.addAll(searchItemsAccount);
                        searchItemsAll.addAll(searchItemsEvent);
                        // 按时间排序
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            searchItemsAll.sort(new Comparator<SearchItem>() {
                                @Override
                                public int compare(SearchItem o1, SearchItem o2) {
                                    return (int) (o1.getTime() - o2.getTime());
                                }
                            });
                        }
                        // 提交给recyclerView
                        searchRecyclerView.setLayoutManager(new LinearLayoutManager(SearchByTimeActivity.this));
                        searchRecyclerView.setAdapter(new SearchAdapter(SearchByTimeActivity.this, searchItemsAll));


                    }
                }
                        , cale1.get(Calendar.YEAR)
                        , cale1.get(Calendar.MONTH)
                        , cale1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
}