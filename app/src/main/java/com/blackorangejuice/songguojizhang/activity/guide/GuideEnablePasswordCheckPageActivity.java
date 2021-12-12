package com.blackorangejuice.songguojizhang.activity.guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.utils.globle.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

public class GuideEnablePasswordCheckPageActivity extends BasicActivity {
    private Switch enableSwitch;
    private TextView backTextView;
    private TextView continueTextView;
    /**
     * 启动此活动
     * @param context
     */
    public static void startThisActivity(Context context){
        Intent intent = new Intent(context,GuideEnablePasswordCheckPageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_enable_password_check_page);

        enableSwitch = findViewById(R.id.activity_guide_enable_password_check_page_switch);
        backTextView = findViewById(R.id.activity_guide_enable_password_check_page_back);
        continueTextView = findViewById(R.id.activity_guide_enable_password_check_page_continue);

        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuideEnablePasswordCheckPageActivity.this.finish();
            }
        });
        continueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 保存设置状态
                GlobalInfo.guideInfo.setIfEnablePasswordCheck(String.valueOf(enableSwitch.isChecked()));
                GuideAccountBookNamePageActivity.startThisActivity(GuideEnablePasswordCheckPageActivity.this);
            }
        });
    }
}