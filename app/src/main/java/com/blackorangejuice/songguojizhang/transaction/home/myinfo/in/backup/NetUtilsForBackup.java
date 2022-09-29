package com.blackorangejuice.songguojizhang.transaction.home.myinfo.in.backup;

import android.os.Environment;
import android.view.View;

import com.blackorangejuice.songguojizhang.utils.SongGuoUtils;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NetUtilsForBackup {
    private static OkHttpClient okHttpClient = new OkHttpClient();

    public static void backup(View view){
        String url = "http://10.211.99.83:9421/backup";
        File dbFile = new File(Environment.getDataDirectory().getAbsolutePath()+"/data/"+ GlobalConstant.APPLICATION_PACKAGE_NAME+"/databases/" +GlobalConstant.DATABASE_NAME);
        new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        upload(url,dbFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


    }

    public static ResponseBody upload(String url, File file) throws IOException {
        // 获取客户端
        OkHttpClient client = okHttpClient;
        // 填充请求体
        RequestBody requestBody = new MultipartBody.Builder()
                // 设置类型为表单
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();
        // 建立请求
        Request request = new Request.Builder()
                .header("Authorization", "ClientID" + UUID.randomUUID())
                .url(url)
                .post(requestBody)
                .build();
        // 执行请求
        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        return response.body();
    }

}
