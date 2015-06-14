package com.snowalker.wwl.e_point.Database;

/**
 * Created by Administrator on 2015/6/14 0014.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


/**
 * Created by duanjigui on 2015/6/13.
 */
public class MyDatabase extends SQLiteOpenHelper {
    public static final String Account_Password = "create table Account_Passworld(" + "account text," + "password text)";
    private Context mcontext;

    public MyDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Account_Password);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
