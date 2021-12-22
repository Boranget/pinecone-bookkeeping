package com.blackorangejuice.songguojizhang.transaction.home.list.event.choose;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.blackorangejuice.songguojizhang.R;

public class ShowChosenAccountPageActivity extends AppCompatActivity {

    public static void startThisActivity(Context context){
        Intent intent = new Intent(context,ShowChosenAccountPageActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_chosen_account_page);
    }
}