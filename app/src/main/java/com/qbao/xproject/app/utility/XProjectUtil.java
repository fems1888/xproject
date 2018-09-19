package com.qbao.xproject.app.utility;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

/**
 * @author Created by jackieyao on 2018/9/19 上午10:46.
 */

public class XProjectUtil {

    /**
     * @param context
     * @param eventName
     * @desc 仅统计事件的次数
     */
    public static void eventReport(Context context, String eventName) {
        MobclickAgent.onEvent(context, eventName);//添加umeng统计做备份处理
    }
}
