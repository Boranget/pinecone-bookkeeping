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

import androidx.annotation.NonNull;
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

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ExportsActivity extends BasicActivity {
    TextView backTextView;
    LinearLayout exportExcelLinearLayout;
    SongGuoDatabaseHelper songGuoDatabaseHelper;
    AccountItemMapper accountItemMapper;
    public static final int REQUEST_PERMISSIONS_CODE = 1;

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
//                if (!requestReadAndWritePower()) {
//                    export();
//                }
                useEasyPermission();


            }
        });
    }

    public boolean requestReadAndWritePower() {
        return SongGuoUtils.requestPower(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                });
    }

    @AfterPermissionGranted(REQUEST_PERMISSIONS_CODE)
    public void export() {
        // 这里找不到文件异常
        SongGuoUtils.showOneToast(ExportsActivity.this, "正在导出.........");
        String filePath = Environment.getExternalStorageDirectory() + "/SongGuoExportExcel";
        File file = new File(Environment.getExternalStorageDirectory(), "SongGuoExportExcel");
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

    public void useEasyPermission() {
        String[] perms = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            export();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "请允许本应用的读写文件权限", REQUEST_PERMISSIONS_CODE, perms);
        }
    }

    /**
     * 申请权限后的回调方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    export();
//                }else {
//                    SongGuoUtils.showOneToast(this,"由于您拒绝了读写文件的权限，导出失败！");
//                }
//                break;
//            default:
//
//        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }
}