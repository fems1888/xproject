package com.qbao.xproject.app.manager;

import android.content.Context;

import com.qbao.xproject.app.utility.CommonUtility;
import com.qbao.xproject.app.XProjectApplication;

/**
 * Created by hubert on 2018/1/16.
 */

public class AccessTokenManager {
    public static Context mContext;

    public static AccessTokenManager getInstance() {
        mContext = XProjectApplication.getInstance().getApplicationContext();
        return TokenHolder.INSTANCE;
    }

    public static class TokenHolder {
        private static final AccessTokenManager INSTANCE = new AccessTokenManager();
    }

    public void saveAccessToken(String token) {
        CommonUtility.SharedPreferencesUtility.put(mContext, Constants.ACCESS_TOKEN, token);
    }

    public String getAccessToken() {
        String accessToken = CommonUtility.SharedPreferencesUtility.getString(mContext, Constants.ACCESS_TOKEN, "");
        return accessToken;
    }

    public void clearAccessToken() {
        CommonUtility.SharedPreferencesUtility.put(mContext, Constants.ACCESS_TOKEN, "");
    }

}
