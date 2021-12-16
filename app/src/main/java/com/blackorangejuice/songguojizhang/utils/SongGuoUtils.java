package com.blackorangejuice.songguojizhang.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.blackorangejuice.songguojizhang.utils.other.SelfApplication;

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
        if (s.trim().isEmpty()) {
            return false;
        }
        if (s == null) {
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





}
