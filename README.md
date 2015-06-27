# 6.6日正式开始项目编制
-----------------------------------------------------------

## 特别功能界面架设完成
### 【fix-note】
	采用类似主功能界面的布局
	增设六个同等属性模块，只进行模块的绘制，暂时不对功能进行实现
------------------------------------------------------------
## 对welcome界面背景进行重新设置
###【问题描述】
	由于原先图片分辨率不高，在实体机上显示效果并不理想
　　【bug-fix-note】
	更换为分辨率较高的图片
### 6.27
## android studio常用快捷键

 

快捷键	用途描述
Alt+回车	导入包,自动修正
Ctrl+N	查找类
Ctrl+Shift+N	查找文件
Ctrl+Alt+L	格式化代码
Ctrl+Alt+O	优化导入的类和包
Alt+Insert	生成代码(如get,set方法,构造函数等)
Ctrl+E
Alt+Shift+C	最近更改的代码
Ctrl+R	替换文本
Ctrl+F	查找文本
Ctrl+Shift+Space	自动补全代码
Ctrl+空格	代码提示
Ctrl+Alt+Space	类名或接口名提示
Ctrl+P	方法参数提示
Ctrl+Shift+Alt+N	查找类中的方法或变量
Alt+Shift+C	对比最近修改的代码
Shift+F6	重构-重命名
Ctrl+X	删除行
Ctrl+U	导航到父类
Ctrl+O	重写或实现父类
Ctrl+D	复制行
Ctrl+/	行注释
Ctrl+Shift+/	代码段注释
Ctrl+J	自动代码
Ctrl+E	最近打开的文件
Ctrl+H	显示类结构图
Ctrl+Q	显示注释文档 
Alt+F1	查找代码所在位置
Alt+1	快速打开或隐藏工程面板
Ctrl+Alt+left/right	返回至上次浏览的位置 
Alt+left/right	切换代码视图
Alt+Up/Down	在方法间快速移动定位 
Ctrl+Shift+Up/Down	代码向上/下移动
F2或Shift+F2	高亮错误或警告快速定位 
Ctrl+Shift+F7	高亮显示所有该文体，按Esc高亮消失
Alt+F3	逐个往下查找相同文本
Ctrl＋E	显示最近编辑的文件列表
Ctrl＋[或]	可以跳到大括号的开头结尾
Ctrl＋Shift＋Backspace	可以跳转到上次编辑的地方 
Ctrl＋F7	当前元素在当前文件中的引用
Ctrl＋P	可以显示参数信息
Ctrl＋Alt＋T	可以把代码包在一块内，例如try/catch
 

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

## 关于登陆验证
【bug-fixed-note】by 段吉贵
使用SqlLite进行登陆验证，设置三条记录
admin admin
user1 user1
user2 user2

## 关于欢迎界面自动转换
【bug-fixed-note】by 段吉贵/SnoWalker
删除ImageView监听器，替换为延时自动切换
### 使用了Splash窗口类，在styles.xml中定义主题
> private final int SPLASH_LENGHT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light_DarkActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        //imageView = (ImageView) findViewById(R.id.welcome_stuff);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(WelcomeActivity.this,Activity_login.class);
                WelcomeActivity.this.startActivity(mainIntent);
                WelcomeActivity.this.finish();
            }
        },SPLASH_LENGHT);

## aboutUs界面完成
【bug-fix-note】
	1. 解决了添加Layout背景颜色的问题
----------------------------------------------------------------
		 安卓修改背景色和文字的颜色
分类： 安卓开发 2013-08-27 09:22 1461人阅读 评论(0) 收藏 举报
Android颜色 背景
1,可以在color.xml中先定义颜色例如下面

<?xml version="1.0" encoding="utf-8"?>
<resources>
<drawable name="c1">#0ffff0</drawable>
<drawable name="c2">#00FFFF</drawable>
<drawable name="translucent_background">#7F000000</drawable>

</resources>

再在
main.xml里面添加背景的颜色android:background="@drawable/c1"


[html] view plaincopy在CODE上查看代码片派生到我的代码片
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"  
    android:layout_width="fill_parent"  
    android:layout_height="fill_parent"  
    android:background="@drawable/c1"  
     >  
  
  
    <TextView  
        android:id="@+id/textView1"  
        android:layout_width="wrap_content"  
        android:layout_height="wrap_content"  
        android:layout_alignParentTop="true"  
        android:layout_marginTop="121dp"  
        android:layout_toLeftOf="@+id/editText1"  
        android:text="账号"  
        android:textSize="20sp" />  
  
  
    <EditText  
        android:id="@+id/editText1"  
        android:layout_width="wrap_content"  
        android:layout_height="wrap_content"  
        android:layout_alignBaseline="@+id/textView1"  
        android:layout_alignBottom="@+id/textView1"  
        android:layout_alignParentRight="true"  
        android:layout_marginRight="40dp"  
        android:ems="10" />  
  
  
    <TextView  
        android:id="@+id/textView2"  
        android:layout_width="wrap_content"  
        android:layout_height="wrap_content"  
        android:layout_alignLeft="@+id/textView1"  
        android:layout_below="@+id/editText1"  
        android:layout_marginTop="28dp"  
        android:text="密码"  
        android:textSize="20sp" />  
  
  
    <EditText  
        android:id="@+id/editText2"  
        android:layout_width="wrap_content"  
        android:layout_height="wrap_content"  
        android:layout_alignBaseline="@+id/textView2"  
        android:layout_alignBottom="@+id/textView2"  
        android:layout_alignLeft="@+id/editText1"  
        android:ems="10"  
        android:inputType="textPassword" >  
  
  
        <requestFocus />  
    </EditText>  
  
  
</RelativeLayout>  





也可以在oncreat（）里面修改颜色textview = (TextView)findViewById(R.id.textView1);textview.setTextColor(Color.RED);
 
 Resources resources = getBaseContext().getResources();
  Drawable HippoDrawable = resources.getDrawable(R.drawable.c2);
  textview1.setBackgroundDrawable(HippoDrawable);
------------------------------------------------------------

