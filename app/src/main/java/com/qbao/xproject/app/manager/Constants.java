package com.qbao.xproject.app.manager;

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
}
