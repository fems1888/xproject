package com.qbao.xproject.app.manager;

import android.content.Context;


import com.qbao.xproject.app.XProjectApplication;
import com.qbao.xproject.app.entity.Account;
import com.qbao.xproject.app.utility.CommonUtility;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.qbao.xproject.app.manager.Constants.ACCOUNT;


/**
 * Created by hubert on 2017/9/5.
 */

public class AccountManager {
    public static Context mContext;
    private String mAccountString = "";
    private Account mAccount;

    public static AccountManager getInstance(Context context) {
        mContext = context.getApplicationContext();
        return AccountHolder.INSTANCE;
    }

    public static AccountManager getInstance() {
        mContext = XProjectApplication.getInstance().getApplicationContext();
        return AccountHolder.INSTANCE;
    }

    public static class AccountHolder {
        private static final AccountManager INSTANCE = new AccountManager();
    }


    public void saveAccount(Object object) {
        if (object instanceof JSONObject || object instanceof String) {
            CommonUtility.SharedPreferencesUtility.put(mContext,
                    ACCOUNT, object.toString());
        } else {
            CommonUtility.DebugLog.e("Hubert", "saveAccountJson" + CommonUtility.JSONObjectUtility.GSON.toJson(object));
            CommonUtility.SharedPreferencesUtility.put(mContext,
                    ACCOUNT, CommonUtility.JSONObjectUtility.GSON.toJson(object));
        }
    }

    public Account getAccountEntity() {

        String localAccount = CommonUtility.SharedPreferencesUtility.getSharedPreferences(mContext).getString(ACCOUNT, "");
        if (CommonUtility.isNull(mAccountString) || !mAccountString.equals(localAccount)) {
            JSONObject user = null;
            try {
                user = new JSONObject(localAccount);
                mAccount = CommonUtility.JSONObjectUtility.convertJSONObject2Obj(user, Account.class);
            } catch (Exception e) {
                if (CommonUtility.isNull(mAccount)) {
                    mAccount = new Account();
                }
                return mAccount;
            }
            mAccountString = localAccount;
        }
        if (CommonUtility.isNull(mAccount)) {
            mAccount = new Account();
        }
        return mAccount;
    }

    public void cleanAccount() {
        CommonUtility.SharedPreferencesUtility.put(mContext,
                ACCOUNT, "");
    }

}
