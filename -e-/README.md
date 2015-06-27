# -e-
“e点”教师辅助点名项目进度及文档及codes


# 6.6日正式开始项目编制

## welcome界面已完成
1. 解决首页图片不能全屏拉伸的问题，关于margin和padding的属性问题；
2. 使用FrameLayout进行图片的放置；
3. 解决logo的设计问题，使用油画视觉效果，突出“e”字主题；
4. 关于首页展示项目的宣传语问题：
    让点名容易“e点”
5. 解决多屏幕适配的问题。
##【bug-fixed-note】
	消除首页面上方菜单栏(建议设置为自动隐藏)

## login/register界面
1. 解决输入框自适应问题；
2. 解决登陆按钮背景颜色的问题；
3. 对于welcome界面与login界面之间的衔接遇到问题：
        1.试图通过为welcome中的图片添加监听器使得intent能够识别并跳转至login界面，但在点击屏幕后程序崩溃；

##【bug-fixed-note】
        解决方式：
                将显式Intent转换为隐式Intent，并更改category目的，从而使得主活动能够自动匹配login活动。(第一屏到第二屏之间存在相应时间，应当确认是否为debug机器本身的相应问题，请进行真机测试)
		6.6/19:06
			已进行真机测试，响应时间正常，经测试表明确实为虚拟机卡顿造成影响。


## CallTheRoll界面
1. 解决按钮平均划分的问题
2. 解决Intent找不到对象的问题
## 【bug-fixed-note】
        解决方式：
                1. 通过**android:layout_weight="1"**实现各组件之间比重相等
                2. 隐式intent，通过添加category实现

## 【bug-fixed-note】
	### 试图通过按下返回键两次退出程序
1. 使用了ActivityCollector活动管理控制关闭活动，但只能退出当前的活动。之前的活动依旧会重新进栈；
-------------------------------------------------------------------------
package com.snowalker.wwl.e_point;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/12 0012.
 */
public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
----------------------想要关闭的活动-----------------------------
        ActivityCollector.addActivity(this);

        Button button = (Button) findViewById(R.id.about_us);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCollector.finishAll();
            }
        });
2.使用Application来保存一个全局变量isProgramExit。（对Application使用不熟悉的话查看相关资料）


public class MyApplication extends Application {
    // 程序退出标记
    private static boolean isProgramExit = false;
    public void setExit(boolean exit) {
        isProgramExit= exit;
    }

    public boolean isExit() {
        return isProgramExit;
    }
}
在你退出时需要结束的每个 Activity 的 onStart 方法加上以下代码：

 protected void onStart() {
        super.onStart();
        MyApplication mApp = (MyApplication)getApplication();
        if(mApp.isExit()) {
            finish();
        }
    }
比如，程序中有三个Activity，A —— B —— C；现在在C中有个按钮，点击退出整个程序。按照上面说的，在A和B的onStart方法中加入判断代码（C中可以不加），C中点击按钮时执行：

MyApplication mApp = (MyApplication)getApplication();
mApp.setExit(true);
finish();
这样C结束了自己，按照流程会回到B，然后B中会进行判断，因为退出条件满足，结束自己；再回到A，同样也结束……

可以看到，这种方法并不高明，甚至有些复杂，因为要在每个Activity的onStart中都加上判断。但不可否认的是这种方法却是绝对有效的，不管你的程序中Activity如何跳转来跳转去，只要一声令下，通通结束，屡试不爽。

所以，有遇到程序退出问题的朋友可以作为参考。 

---------------------------application说明。txt----------------------------
Application类

Application和Activity,Service一样是Android框架的一个系统组件，当Android程序启动时系统会创建一个Application对象，用来存储系统的一些信息。

Android系统自动会为每个程序运行时创建一个Application类的对象且只创建一个，所以Application可以说是单例（singleton）模式的一个类。

通常我们是不需要指定一个Application的，系统会自动帮我们创建，如果需要创建自己的Application，那也很简单！创建一个类继承Application并在AndroidManifest.xml文件中的application标签中进行注册（只需要给application标签增加name属性，并添加自己的 Application的名字即可）。

启动Application时，系统会创建一个PID，即进程ID，所有的Activity都会在此进程上运行。那么我们在Application创建的时候初始化全局变量，同一个应用的所有Activity都可以取到这些全局变量的值，换句话说，我们在某一个Activity中改变了这些全局变量的值，那么在同一个应用的其他Activity中值就会改变。

Application对象的生命周期是整个程序中最长的，它的生命周期就等于这个程序的生命周期。因为它是全局的单例的，所以在不同的Activity,Service中获得的对象都是同一个对象。所以可以通过Application来进行一些，如：数据传递、数据共享和数据缓存等操作。

应用场景：

在Android中，可以通过继承Application类来实现应用程序级的全局变量，这种全局变量方法相对静态类更有保障，直到应用的所有Activity全部被destory掉之后才会被释放掉。

实现步骤：

1、继承Application

复制代码
public class CustomApplication extends Application
{
    private static final String VALUE = "Harvey";
    
    private String value;
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        setValue(VALUE); // 初始化全局变量
    }
    
    public void setValue(String value)
    {
        this.value = value;
    }
    
    public String getValue()
    {
        return value;
    }
}
复制代码
注：继承Application类，主要重写里面的onCreate（）方法（android.app.Application包的onCreate（）才是真正的Android程序的入口点），就是创建的时候，初始化变量的值。然后在整个应用中的各个文件中就可以对该变量进行操作了。

2、在ApplicationManifest.xml文件中配置自定义的Application

<application
        android:name="CustomApplication">
</application>
实例代码：

CustomApplication.java

复制代码
/**
 * 继承Application
 * 
 * @author admin
 * 
 */
public class CustomApplication extends Application
{
    private static final String VALUE = "Harvey";
    
    private String value;
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        setValue(VALUE); // 初始化全局变量
    }
    
    public void setValue(String value)
    {
        this.value = value;
    }
    
    public String getValue()
    {
        return value;
    }
}
复制代码
FirstActivity.java

复制代码
public class FirstActivity extends Activity
{
    private CustomApplication app;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        app = (CustomApplication) getApplication(); // 获得CustomApplication对象
        
        Log.i("FirstActivity", "初始值=====" + app.getValue()); // 获取进程中的全局变量值，看是否是初始化值
        
        app.setValue("Harvey Ren"); // 重新设置值
        
        Log.i("FirstActivity", "修改后=====" + app.getValue()); // 再次获取进程中的全局变量值，看是否被修改
        
        Intent intent = new Intent();
        intent.setClass(this, SecondActivity.class);
        startActivity(intent);
    }
}
复制代码
注：只需要调用Context的 getApplicationContext或者Activity的getApplication方法来获得一个Application对象，然后再得到相应的成员变量即可。它是代表我们的应用程序的类，使用它可以获得当前应用的主题和资源文件中的内容等，这个类更灵活的一个特性就是可以被我们继承，来添加我们自己的全局属性。

SecondActivity.java

复制代码
public class SecondActivity extends Activity
{
    private CustomApplication app;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        app = (CustomApplication) getApplication(); // 获取应用程序

       Log.i("SecondActivity", "当前值=====" + app.getValue()); // 获取全局值
    }
}
复制代码
AndroidManifest.xml

复制代码
<?xml version="1.0" encoding="utf-8"?>
<manifest
    xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.android.test"
    android:versionCode="1"
    android:versionName="1.0">
    <uses-sdk
        android:minSdkVersion="8" />

    <application
        android:icon="@drawable/icon"
        android:label="@string/app_name"
        android:name="CustomApplication">
        <!-- 将我们以前一直用的默认Application设置成自定义的CustomApplication -->
        <activity
            android:name=".FirstActivity"
            android:label="@string/app_name">
            <intent-filter>
                <action
                    android:name="android.intent.action.MAIN" />
                <category
                    android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <activity
            android:name=".SecondActivity"
            android:label="@string/app_name">
        </activity>
    </application>
</manifest>
-------------------------------------------------------------------------------
---------------------------关于隐式intent------------------------------------
## 【bug-fixed-note】
 # 误删除<category android:name="android.intent.category.DEFAULT" />
隐式Intent通过需要Android解析，将Intent映射给可以处理此Intent的Activity、IntentReceiver或Service。

Intent解析机制主要是通过查找已注册在AndroidManifest.xml中的所有IntentFilter及其中定义的Intent，最终找到匹配的Intent。

在这个解析过程中，Android是通过Intent的action、type、category这三个属性来进行判断的，判断方法如下：

1 如果Intent指明定了action，则目标组件的IntentFilter的action列表中就必须包含有这个action，否则不能匹配；

2 如果Intent没有提供type，系统将从data中得到数据类型。和action一样，

目标组件的数据类型列表中必须包含Intent的数据类型，否则不能匹配。

3 如果Intent中的数据不是content: 类型的URI，而且Intent也没有明确指定它的type，

将根据Intent中数据的scheme （比如 http: 或者mailto:） 进行匹配。

同上，Intent 的scheme必须出现在目标组件的scheme列表中。

4 如果Intent指定了一个或多个category，这些类别必须全部出现在组建的类别列表中。

比如Intent中包含了两个类别：LAUNCHER_CATEGORY 和 ALTERNATIVE_CATEGORY，

解析得到的目标组件必须至少包含这两个类别。

每一个通过startActivity()方法发出的隐式Intent都至少有一个category，就是 "android.intent.category.DEFAULT"，

所以只要是想接收一个隐式Intent的Activity都应该包括"android.intent.category.DEFAULT" category，不然将导致 Intent 匹配失败。

Intent-Filter的定义属性设置的例子：

<intent-filter >
    <action android:name="com.netease.push.action.client.MESSAGE"/>
    <action android:name="com.netease.push.action.client.NEWID"/>
    <action android:name="com.netease.push.action.client.METHOD"/>
    <category android:name="android.intent.category.DEFAULT" />
</intent-filter>