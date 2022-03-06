package com.blackorangejuice.songguojizhang.transaction.home.myinfo.in.moresetting.exports;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.ExportItem;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.utils.SongGuoUtils;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.blackorangejuice.songguojizhang.utils.other.ExcelUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExportsActivity extends BasicActivity {
    TextView backTextView;
    LinearLayout exportExcelLinearLayout;
    SongGuoDatabaseHelper songGuoDatabaseHelper;
    AccountItemMapper accountItemMapper;

    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, ExportsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exports);
        findView();
        init();
        setListener();
    }

    @Override
    public void init() {
        songGuoDatabaseHelper = SongGuoDatabaseHelper.getSongGuoDatabaseHelper(ExportsActivity.this);
        accountItemMapper = new AccountItemMapper(songGuoDatabaseHelper);
    }

    @Override
    public void findView() {
        backTextView = findViewById(R.id.activity_exports_back_textview);
        exportExcelLinearLayout = findViewById(R.id.activity_exports_excel);
    }

    @Override
    public void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExportsActivity.this.finish();
            }
        });
        exportExcelLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                export();
            }
        });
    }

    public void export() {
        // 这里找不到文件异常
        SongGuoUtils.showOneToast(ExportsActivity.this,"正在导出.........");
        SongGuoUtils.requestPower(this,Manifest.permission.READ_EXTERNAL_STORAGE);
        SongGuoUtils.requestPower(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        String filePath = Environment.getExternalStorageDirectory() + "/SongGuoExportExcel";
        File file = new File(Environment.getExternalStorageDirectory(),"SongGuoExportExcel");
        if (!file.exists()) {
            file.mkdirs();
        }
        if (file.exists()) {
            System.out.println("yes");
        }
        String excelName = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒导出账单").format(new Date());
        String accountBookName = GlobalInfo.currentAccountBook.getAccountBookName();

        String excelFileName = excelName + ".xls";

        String sheetName = accountBookName;

        List<ExportItem> exportItems = accountItemMapper.exportToExcel();

        ExcelUtil.initExcel(filePath, excelFileName);
        ExcelUtil.writeToExcel(exportItems, filePath, excelFileName, ExportsActivity.this);

    }
}