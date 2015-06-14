package com.snowalker.wwl.e_point;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
//import android.view.View;
//import android.widget.ImageView;


public class WelcomeActivity extends Activity {

    private final int SPLASH_LENGHT = 2500;

    //private ImageView imageView;//定义一个图形并添加监听器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light_DarkActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //imageView = (ImageView) findViewById(R.id.welcome_stuff);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(WelcomeActivity.this, Activity_login.class);
                WelcomeActivity.this.startActivity(mainIntent);
                WelcomeActivity.this.finish();
            }
        }, SPLASH_LENGHT);

            /*imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick (View v){
                    Intent intent = new Intent("com.snowalker.wwl.e_point.ACTION_START");
                    intent.addCategory("com.snowalker.wwl.e_point.ACTIVITY_LOGIN");
                    startActivity(intent);
                }
            });*/
    }

    protected void onStart() {
        super.onStart();
        MyApplication mApp = (MyApplication) getApplication();
        if (mApp.isExit()) {
            finish();
        }
    }
}
