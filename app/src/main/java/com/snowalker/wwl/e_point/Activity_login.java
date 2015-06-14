package com.snowalker.wwl.e_point;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.snowalker.wwl.e_point.Database.MyDatabase;

public class Activity_login extends Activity {
    private SQLiteDatabase db;
    private MyDatabase dbHelper;
    private Button login;
    private Cursor query;
    //登录
    private EditText passwordEdit;
    //密码
    private EditText accountEdit;

    //账号
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light_DarkActionBar);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);

        dbHelper = new MyDatabase(this, "Account_Passworld", null, 1);
        db = dbHelper.getWritableDatabase();
        inserts();
        /**
         * 登录的逻辑
         */
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = db.query("Account_Passworld", null, null, null, null, null, null);
                if (query.moveToNext()) {
                    do {
                        String account = accountEdit.getText().toString();
                        String passworld = passwordEdit.getText().toString();
                        if (account.equals(query.getString(query.getColumnIndex("account"))) && passworld.equals(query.getString(query.getColumnIndex("password")))) {
                            //隐式启动活动
                            Intent intent = new Intent("com.snowalker.wwl.e_point.ACTION_START");
                            intent.addCategory("com.snowalker.wwl.e_point.ACTIVITY_CALL_THE_ROLL");
                            startActivity(intent);
                        }
                    } while (query.moveToNext());
                    /**
                     * 输入匹配
                     */
                    //Toast.makeText(Activity_login.this, "登陆成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Activity_login.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                }
                query.close();
            }
        });
        /**
         * 插入数据
         */
    }

    public void inserts() {
        db.execSQL("insert into Account_Passworld(account,password) values(?,?)", new String[]{"admin", "admin"});
        db.execSQL("insert into Account_Passworld(account,password) values(?,?)", new String[]{"user1", "user1"});
        db.execSQL("insert into Account_Passworld(account,password) values(?,?)", new String[]{"user2", "user2"});
    }

    protected void onStart() {
        super.onStart();
        MyApplication mApp = (MyApplication) getApplication();
        if (mApp.isExit()) {
            finish();
        }
    }
}
