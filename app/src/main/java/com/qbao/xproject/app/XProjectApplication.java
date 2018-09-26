package com.qbao.xproject.app;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.StrictMode;

import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;
import com.qbao.xproject.app.activity.LoginActivity;
import com.qbao.xproject.app.db.DaoMaster;
import com.qbao.xproject.app.db.DaoSession;
import com.qbao.xproject.app.db.XProjectSQLiteHelper;
import com.qbao.xproject.app.manager.AccessTokenManager;
import com.qbao.xproject.app.manager.AccountManager;
import com.qbao.xproject.app.utility.CommonUtility;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.util.concurrent.ConcurrentHashMap;

import cn.jpush.android.api.JPushInterface;

/**
 * @author Created by jackieyao on 2018/9/11 下午2:22.
 */

public class XProjectApplication extends Application{
    private static XProjectApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //Umeng 错误统计的设置
        if (releaseBuild()) {
            CommonUtility.DebugLog.e("release");
            MobclickAgent.setCatchUncaughtExceptions(false);
            JPushInterface.setDebugMode(false);
            Bugtags.start(BuildConfig.BUG_TAGS_KEY, this, Bugtags.BTGInvocationEventNone, bugTagsoptions());
        } else {
            CommonUtility.DebugLog.e("debug");
            JPushInterface.setDebugMode(true);
            MobclickAgent.setCatchUncaughtExceptions(true);
            Bugtags.start(BuildConfig.BUG_TAGS_KEY, this, Bugtags.BTGInvocationEventBubble, bugTagsoptions());
        }

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().permitDiskReads()
                .permitDiskWrites().penaltyLog().build());

        JPushInterface.init(this);
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数3:Push推送业务的secret
         */
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "5ba0b4345b5a55bdf3000217");
        if (BuildConfig.BUILD_TYPE.equals("release")) {
            UMConfigure.setLogEnabled(false);
        } else {
            UMConfigure.setLogEnabled(true);
        }
    }
    public boolean releaseBuild() {
        return BuildConfig.BUILD_TYPE.equals("release");
    }
    public static XProjectApplication getInstance() {
        return instance;
    }

    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    public DaoSession getDaoSession() {
        if (mDaoSession != null) {
            return mDaoSession;
        } else {
            XProjectSQLiteHelper helper = new XProjectSQLiteHelper(this, "XProject.db",
                    null);
            mDaoMaster = new DaoMaster(helper.getWritableDatabase());

            mDaoSession = mDaoMaster.newSession();
            return mDaoSession;
        }
    }
    private ConcurrentHashMap<String, Activity> mActivityLists = new ConcurrentHashMap<>();


    /**
     * method desc：模拟activity栈
     *
     * @param activity
     */
    public void putActivity(Activity activity) {
        mActivityLists.put(activity.getClass().getSimpleName(), activity);
    }


    /**
     * method desc：结束指定类型的activity
     *
     * @param clazz
     */
    public void finishActivity(Class<?> clazz) {
        finishActivity(clazz.getSimpleName());
    }

    public void finishActivity(String clazzName) {
        Activity activity = mActivityLists.get(clazzName);
        if (!CommonUtility.isNull(activity)) {
            activity.finish();
        }
        removeActivityFromStack(clazzName);
    }

    public void removeActivityFromStack(String className) {
        mActivityLists.remove(className);
    }

    public void finishAll() {
        for (Activity activity : mActivityLists.values()) {
            finishActivity(activity.getClass());
        }
    }
    /**
     * 退出应用程序
     */
    public void exit(int code) {
        MobclickAgent.onKillProcess(this);
        System.exit(code);
    }

    public void logout(){
        MobclickAgent.onProfileSignOff();
        AccessTokenManager.getInstance().clearAccessToken();
        AccountManager.getInstance().cleanAccount();
        finishAll();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    private BugtagsOptions bugTagsoptions() {
        BugtagsOptions options = new BugtagsOptions.Builder().
                trackingLocation(true).       //是否获取位置，默认 true
                trackingCrashLog(true).       //是否收集闪退，默认 true
                trackingConsoleLog(true).     //是否收集控制台日志，默认 true
                trackingUserSteps(true).      //是否跟踪用户操作步骤，默认 true
                crashWithScreenshot(true).    //收集闪退是否附带截图，默认 true
                trackingAnr(true).              //收集 ANR，默认 false
                trackingBackgroundCrash(true).  //收集 独立进程 crash，默认 false
                versionName(BuildConfig.VERSION_NAME).         //自定义版本名称，默认 app versionName
                versionCode(BuildConfig.VERSION_CODE).              //自定义版本号，默认 app versionCode
                trackingNetworkURLFilter("(.*)").//自定义网络请求跟踪的 url 规则，默认 null
                enableUserSignIn(true).            //是否允许显示用户登录按钮，默认 true
                startAsync(true).    //设置 为 true 则 SDK 会在异步线程初始化，节省主线程时间，默认 false
                startCallback(null).            //初始化成功回调，默认 null
                remoteConfigDataMode(Bugtags.BTGDataModeProduction).//设置远程配置数据模式，默认Bugtags.BTGDataModeProduction 参见[文档](https://docs.bugtags.com/zh/remoteconfig/android/index.html)
                remoteConfigCallback(null).//设置远程配置的回调函数，详见[文档](https://docs.bugtags.com/zh/remoteconfig/android/index.html)
                enableCapturePlus(false).        //是否开启手动截屏监控，默认 false，参见[文档](https://docs.bugtags.com/zh/faq/android/capture-plus.html)
//                extraOptions(key, value).                //设置 log 记录的行数，详见下文
        extraOptions(Bugtags.BTGConsoleLogCapacityKey, 500).//设置控制台日志行数的控制 value > 0，默认500
                extraOptions(Bugtags.BTGBugtagsLogCapacityKey, 1000).//控制 Bugtags log 行数的控制 value > 0，默认1000
                extraOptions(Bugtags.BTGUserStepLogCapacityKey, 1000).//控制用户操作步骤行数的控制 value > 0，默认1000
                extraOptions(Bugtags.BTGNetworkLogCapacityKey, 20).//控制网络请求数据行数的控制 value > 0，默认20

                build();
        return options;
    }
}
