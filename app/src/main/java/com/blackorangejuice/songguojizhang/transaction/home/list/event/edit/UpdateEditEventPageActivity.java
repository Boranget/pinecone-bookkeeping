package com.blackorangejuice.songguojizhang.transaction.home.list.event.edit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.EventItem;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.EventItemMapper;
import com.blackorangejuice.songguojizhang.utils.SongGuoUtils;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateEditEventPageActivity extends EditEventActivity {

    TextView backTextView;
    TextView timeTextView;
    TextView saveTextView;
    EditText titleEditText;
    EditText contentEditText;
    LinearLayout moreLinearLayout;

    SongGuoDatabaseHelper songGuoDatabaseHelper;
    SimpleDateFormat simpleDateFormat;
    EventItem eventItem;

    public static void startThisActivity(Context context){
        Intent intent = new Intent(context,UpdateEditEventPageActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_edit_event_page);
        findView();
        init();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void init() {
        songGuoDatabaseHelper = SongGuoDatabaseHelper.getSongGuoDatabaseHelper(this);
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        // 初始化事件项对象,若是从其他界面返回需要保存信息
        if (GlobalInfo.lastAddEvent == null) {
            eventItem = new EventItem();
        } else {
            eventItem = GlobalInfo.lastAddEvent;
        }
        // 从eventItem中获取信息
        // 时间
        timeTextView.setText(simpleDateFormat.format(new Date(eventItem.getEventTime())));
        // 标题
        titleEditText.setText(eventItem.getEventTitle());
        // 内容
        contentEditText.setText(eventItem.getEventContent());

    }

    @Override
    public void findView() {
        backTextView = findViewById(R.id.activity_update_edit_event_page_back_textview);
        timeTextView= findViewById(R.id.activity_update_edit_event_page_time);
        saveTextView= findViewById(R.id.activity_update_edit_event_page_save);
        titleEditText= findViewById(R.id.activity_update_edit_event_page_event_title);
        contentEditText= findViewById(R.id.activity_update_edit_event_page_event_content);
        moreLinearLayout= findViewById(R.id.activity_update_edit_event_page_more);
    }

    @Override
    public void setListener() {
        // 返回处理
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateEditEventPageActivity.this.finish();
            }
        });

        // 时间选择
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cale1 = Calendar.getInstance();

                new DatePickerDialog(UpdateEditEventPageActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

//                        Toast.makeText(getApplicationContext(), "你选择的是 " + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日", Toast.LENGTH_SHORT).show();
                        String dataS = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        Date theDateAfterParse = new Date();
                        try {
                            theDateAfterParse = simpleDateFormat.parse(dataS);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        timeTextView.setText(simpleDateFormat.format(theDateAfterParse));
                        // 账目设置时间
                        eventItem.setEventTime(theDateAfterParse.getTime());
                    }
                }
                        , cale1.get(Calendar.YEAR)
                        , cale1.get(Calendar.MONTH)
                        , cale1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        // 保存按钮
        saveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEventInfoToAccountItem();
                EventItemMapper eventItemMapper = new EventItemMapper(songGuoDatabaseHelper);
                // 返回带id的对象
                eventItem = eventItemMapper.updateEventItem(UpdateEditEventPageActivity.this.eventItem);
                SongGuoUtils.showOneToast(UpdateEditEventPageActivity.this, "修改成功");
                UpdateEditEventPageActivity.this.finish();
            }
        });
    }

    private void getEventInfoToAccountItem() {
        // 标题
        eventItem.setEventTitle(titleEditText.getText().toString());
        // 正文
        eventItem.setEventContent(contentEditText.getText().toString());
        // Bid
        eventItem.setBid(GlobalInfo.currentAccountBook.getBid());
        // 设置账本为当前全局账本
        eventItem.setAccountBook(GlobalInfo.currentAccountBook);
    }
}