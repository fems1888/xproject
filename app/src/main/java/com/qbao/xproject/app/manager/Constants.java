package com.qbao.xproject.app.manager;

import android.content.Context;

import com.qbao.xproject.app.BuildConfig;
import com.qbao.xproject.app.utility.CommonUtility;

/**
 * @author Created by jackieyao on 2018/9/14 上午10:31.
 */

public class Constants {
    /**
     * REGISTER_CODE_IS_NULL = 注册码为空!
     * 返回给你们的errorCode是-10002，说明是新的用户，把页面迁移到注册页面。
     */
    public static final String REGISTER_CODE_IS_NULL = "-10002";
    /**
     * REGISTER_CODE_NOT_EXIST = 注册码不存在!
     */
    public static final String REGISTER_CODE_NOT_EXIST = "-10003";

    //Token
    public static final String ACCESS_TOKEN = "accessToken";

    public static final String ACCOUNT = "ACCOUNT";//账号

    public static final String SUCCESS = "success";

    public static final String GAMBLE_ID = "gamble_id";

    public static final int A_DAY_MILLS = 24*60*60*1000;
    public static final int TEN_MINUTE_MILLS = 10*60*1000;


    public static String formatHttpUrl(String url) {
        if (CommonUtility.isNull(url)) {
            return "";
        }
        return CommonUtility.formatString(BuildConfig.URL_API_BASE, "/core/file/download/icon/", url);
    }


    /**
     * 攻略
     * @return
     */
    public static String getStrategyUrl() {
        return CommonUtility.formatString(BuildConfig.XPROJECT_HTML, "/core/pageSkip/pageSkip?name=0");
    }

    /**竞技场规则
     * @return
     */
    public static String getArenaRuleUrl() {
        return CommonUtility.formatString(BuildConfig.XPROJECT_HTML, "/core/pageSkip/pageSkip?name=1");
    }
}
