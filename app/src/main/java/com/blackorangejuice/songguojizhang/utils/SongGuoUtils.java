package com.blackorangejuice.songguojizhang.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blackorangejuice.songguojizhang.utils.other.SelfApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class SongGuoUtils {
    /**
     * 字符串非空判断
     *
     * @param s
     * @return
     */
    public static boolean notEmptyString(String s) {
        // 先判断是否为空，否则会出现空指针异常
        if (s == null) {
            return false;
        }
        if (s.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 显示提示框
     *
     * @param context
     * @param s
     */
    public static void showOneToast(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取全局的SharedPreferences对象
     *
     * @return
     */
    public static SharedPreferences getSongGuoSharedPreferences() {
        SharedPreferences defaultSharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(SelfApplication.getContext());
        return defaultSharedPreferences;

    }

    /**
     * 通过文件名获取bitmap
     *
     * @param context
     * @param fileName
     * @return
     */
    public static Bitmap getBitmapByFileName(Context context, String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 备份数据库
     */
    public static void getPrivateFile() {
        String path = "/data/data/com.blackorangejuice.songguojizhang/databases/songguojizhang.db";
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            //
        } catch (Exception e) {

        }
    }

    /**
     * 解析表格时去掉横线的方法
     *
     * @param originalString
     * @return
     */
    public static String subtractLineFromString(String originalString) {
        // 若起始与结束都没有-,不做处理
        if (!(originalString.startsWith("-") || originalString.endsWith("-"))) {
            return originalString;
        }
        String regex = "^-*|-*$";
        return originalString.replaceAll(regex, "");
    }

    /**
     * 动态申请权限
     */
    public static void requestPower(Activity activity, String permission) {
        //判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
            ActivityCompat.requestPermissions(activity,
                    new String[]{permission,}, 1);

        }
    }


}
