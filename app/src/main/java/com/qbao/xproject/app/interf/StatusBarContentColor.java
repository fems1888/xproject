package com.qbao.xproject.app.interf;

import android.support.annotation.IntDef;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Created by jackieyao on 2018/9/17 下午3:29.
 */
@IntDef({StatusBarContentColor.WHITE,StatusBarContentColor.GRAY})
@Retention(RetentionPolicy.SOURCE)//保留策略 RetentionPolicy.SOURCE 只在于源码即程序运行后，不会再 .class文件里，即编译时不考虑
public @interface StatusBarContentColor {
    int WHITE = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
    int GRAY = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
}
