package com.snowalker.wwl.e_point;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

//import android.view.Menu;
//import android.view.MenuItem;

public class WelcomeActivity extends Activity {


    private ImageView imageView;//定义一个图形并添加监听器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light_DarkActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        imageView = (ImageView) findViewById(R.id.welcome_stuff);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.snowalker.wwl.e_point.ACTION_START");
                intent.addCategory("com.snowalker.wwl.e_point.ACTIVITY_LOGIN");
                startActivity(intent);
            }
        });
    }

    protected void onStart() {
        super.onStart();
        MyApplication mApp = (MyApplication)getApplication();
        if(mApp.isExit()) {
            finish();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
