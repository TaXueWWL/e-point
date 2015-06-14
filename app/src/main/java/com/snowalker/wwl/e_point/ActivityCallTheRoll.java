package com.snowalker.wwl.e_point;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/6/12 0012.
 */

public class ActivityCallTheRoll extends Activity {

    private Button aboutUs;
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light_DarkActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calltheroll);

        aboutUs = (Button) findViewById(R.id.about_us);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐式启动活动
                Intent intent = new Intent("com.snowalker.wwl.e_point.ACTION_START");
                intent.addCategory("com.snowalker.wwl.e_point.ACTIVITY_ABOUT_US");
                startActivity(intent);
            }
        });
    }


    /**
     * 再按一次退出程序
     */
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                MyApplication mApp = (MyApplication) getApplication();
                mApp.setExit(true);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /*private long mPressedTime = 0;

    @Override
    public void onBackPressed() {
       long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if((mNowTime - mPressedTime) > 2000){//比较两次按键时间差
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
            }
        else{//退出程序
           this.finish();
            System.exit(0);
            }
    }*/
}
