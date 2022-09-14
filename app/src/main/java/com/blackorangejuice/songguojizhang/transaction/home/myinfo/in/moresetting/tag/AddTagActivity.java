package com.blackorangejuice.songguojizhang.transaction.home.myinfo.in.moresetting.tag;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.AccountItem;
import com.blackorangejuice.songguojizhang.bean.EventItem;
import com.blackorangejuice.songguojizhang.bean.Tag;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.db.mapper.EventItemMapper;
import com.blackorangejuice.songguojizhang.db.mapper.TagMapper;
import com.blackorangejuice.songguojizhang.utils.SongGuoUtils;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.blackorangejuice.songguojizhang.utils.view.TextViewDrawable;
import com.larswerkman.holocolorpicker.ColorPicker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.rosuh.filepicker.config.FilePickerManager;

public class AddTagActivity extends BasicActivity {
    TextView backTextView;
    TextView saveTextView;
    EditText tagNameEditText;
    ColorPicker colorPicker;
    TextView previewTagTextView;
    SongGuoDatabaseHelper songGuoDatabaseHelper;

    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, AddTagActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);
        findView();
        init();
        setListener();
    }

    @Override
    public void init() {
        songGuoDatabaseHelper = SongGuoDatabaseHelper.getSongGuoDatabaseHelper(AddTagActivity.this);
    }

    @Override
    public void findView() {
        backTextView = findViewById(R.id.activity_add_tag_page_back_textview);
        saveTextView = findViewById(R.id.activity_add_tag_page_save_textview);
        tagNameEditText = findViewById(R.id.activity_add_tag_page_tag_name_edit_text);
        colorPicker = findViewById(R.id.activity_add_tag_page_color_picker);
        previewTagTextView = findViewById(R.id.activity_add_tag_page_preview_image_text_view);
    }

    @Override
    public void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTagActivity.this.finish();
            }
        });
        saveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tagName = tagNameEditText.getText().toString();
                Integer color = colorPicker.getColor();
                TagMapper tagMapper = new TagMapper(songGuoDatabaseHelper);
                Tag tag = new Tag();
                tag.setTagName(tagName);
                tag.setTagImgName(null);
                tag.setTagImgColor(color);
                Tag result = tagMapper.insertTag(tag);
                if(result == null){
                    SongGuoUtils.showOneToast("该标签已存在");
                }else{
                    // 返回
                    AddTagActivity.this.finish();
                }

            }

        });
        colorPicker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                colorPicker.setOldCenterColor(color);
                previewTagTextView.setBackground(TextViewDrawable.getDrawable(color));
            }
        });
        tagNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tagName = s.toString();
                previewTagTextView.setText(tagName);
            }
        });
    }

}
