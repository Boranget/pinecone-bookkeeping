package com.blackorangejuice.songguojizhang.utils;

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

import com.blackorangejuice.songguojizhang.utils.other.SongGuoApplication;

import java.io.FileInputStream;
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
                PreferenceManager.getDefaultSharedPreferences(SongGuoApplication.getContext());
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




}
