package com.qbao.xproject.app.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.nfc.Tag;
import android.support.annotation.NonNull;

import com.qbao.xproject.app.XProjectApplication;

/**
 * @author Created by jackieyao on 2018/9/14 下午1:35.
 */

public class BaseViewModel extends AndroidViewModel {
    protected XProjectApplication application;
    protected String TAG;
    public BaseViewModel(@NonNull Application application,String tag) {
        super(application);
        this.application = (XProjectApplication) application;
        TAG = tag;
    }
}
