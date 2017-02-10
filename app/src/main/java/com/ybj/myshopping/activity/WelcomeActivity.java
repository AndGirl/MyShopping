package com.ybj.myshopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.ybj.myshopping.MainActivity;
import com.ybj.myshopping.R;
import com.ybj.myshopping.utils.UIUtils;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //ActionBar的处理方式:将系统中自带的标题栏隐藏掉
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        //发送定时任务跳主界面
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        },2000);
    }
}
