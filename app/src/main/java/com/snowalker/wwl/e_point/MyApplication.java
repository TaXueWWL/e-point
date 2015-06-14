package com.snowalker.wwl.e_point;

import android.app.Application;

/**
 * Created by Administrator on 2015/6/12 0012.
 * 管理应用中的活动，使得活动能够直接退出到系统环境
 */
public class MyApplication extends Application {
    // 程序退出标记
    private static boolean isProgramExit = false;

    public void setExit(boolean exit) {
        isProgramExit = exit;
    }

    public boolean isExit() {
        return isProgramExit;
    }
}