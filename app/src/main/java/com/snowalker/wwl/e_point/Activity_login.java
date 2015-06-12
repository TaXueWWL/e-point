package com.snowalker.wwl.e_point;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by Administrator on 2015/6/6 0006.
 */
public class Activity_login extends Activity {

    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light_DarkActionBar);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.snowalker.wwl.e_point.ACTION_START");
                intent.addCategory("com.snowalker.wwl.e_point.ACTIVITY_CALL_THE_ROLL");
                startActivity(intent);
            }
        });
    }
}
