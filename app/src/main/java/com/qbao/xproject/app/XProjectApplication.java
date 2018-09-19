package com.qbao.xproject.app;

import android.app.Application;
import android.content.Intent;
import android.os.StrictMode;

import com.qbao.xproject.app.db.DaoMaster;
import com.qbao.xproject.app.db.DaoSession;
import com.qbao.xproject.app.db.XProjectSQLiteHelper;
import com.umeng.commonsdk.UMConfigure;

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
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().permitDiskReads()
                .permitDiskWrites().penaltyLog().build());
        JPushInterface.setDebugMode(true);
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

    /**
     * 退出应用程序
     */
    public void exit(int code) {
        System.exit(code);
    }

    public void logout(){
        //删除密码文件
//        KeyStorage.getInstance().clearKeyStorage();
        // Clean Account Information
//        CommonUtility.SharedPreferencesUtility.clear(getApplicationContext());
//        AccountManager.getInstance(getApplicationContext()).cleanAccount();
//        getDaoSession().getTokenEntityDao().deleteAll();
//        getDaoSession().getLocalAddressDao().deleteAll();
//        Intent intent = new Intent(getApplicationContext(), ActivityStartPage.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);

    }
}
