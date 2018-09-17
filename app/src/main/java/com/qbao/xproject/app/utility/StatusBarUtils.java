package com.qbao.xproject.app.utility;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.qbao.xproject.app.interf.StatusBarContentColor;

/**
 * @author Created by jackieyao on 2018/9/12 下午6:45.
 */

public class StatusBarUtils {
    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {//android6.0以后可以对状态栏文字颜色和图标进行修改
                activity.getWindow().getDecorView().setSystemUiVisibility(StatusBarContentColor.WHITE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param activity
     * @param barBgColor 系统状态栏背景颜色
     * @param statusBarContentColor 系统状态栏元素显示的颜色 时间 电量等显示颜色
     */
    public static void setWindowStatusBarColor(Activity activity, int barBgColor,@StatusBarContentColor int statusBarContentColor) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(barBgColor));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {//android6.0以后可以对状态栏文字颜色和图标进行修改
                activity.getWindow().getDecorView().setSystemUiVisibility(statusBarContentColor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
