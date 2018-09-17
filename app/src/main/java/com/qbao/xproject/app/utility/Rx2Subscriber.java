package com.qbao.xproject.app.utility;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;


import com.qbao.xproject.app.http.ExceptionHandle;
import com.qbao.xproject.app.http.RxManager;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Subscriber基类,可以在这里处理client网络连接状况
 * （比如没有wifi，没有4g，没有联网等）
 * <p>
 * Created by hubert on 2017/9/20.
 */

public abstract class Rx2Subscriber<T> implements Observer<T> {

    private static final String TAG = "Rx2Subscriber";
    private Context context;

    //默认 显示错误提示
    private boolean showTips = true;

    //默认检查网络是否可用
    private boolean checkNetwork = true;

    private boolean showLoading = true;
    private boolean showToast = true;

    public static final int TIPS = 1, CHECK_NET = 2, SHOW_LOADING = 3, TIPS_CEHCK = 4, TIPS_SHOW = 5, CHECK_SHOW = 6, TOAST_STATE = 7;

    private int type;
    private String mTag;

    public Rx2Subscriber(Context context, String tag) {
        this.context = context;
        this.mTag = tag;
    }

    public Rx2Subscriber(Context context, String tag, int type) {
        this.context = context;
        this.type = type;
        this.mTag = tag;
    }

    @Override
    public void onSubscribe(Disposable d) {
        RxManager.getInstance().add(mTag, d);
        switch (type) {
            case TIPS:
                checkNetwork = false;
                showLoading = false;
                break;
            case CHECK_NET:
                showTips = false;
                checkNetwork = true;
                showLoading = false;
                break;
            case SHOW_LOADING:
                showTips = false;
                checkNetwork = false;
                showLoading = true;
                break;
            case TIPS_CEHCK:
                showLoading = false;
                break;
            case TIPS_SHOW:
                checkNetwork = false;
                break;
            case CHECK_SHOW:
                showTips = false;
                break;
            case TOAST_STATE:
                showLoading = false;
                showToast = false;
                break;
            default:
                break;
        }
        showLoading(showLoading);
        CommonUtility.DebugLog.e(TAG, "Rx2Subscriber.onStart()");
        if (checkNetwork) {
            //接下来可以检查网络连接等操作
            if (!CheckNetwork.isNetworkConnected(context)) {
                showTips("网络错误");
                hideLoading();
                onError(new ExceptionHandle.ResponseThrowable("网络错误", ExceptionHandle.ERROR.NETWORD_ERROR));
                // 一定要主动调用下面这一句,取消本次Subscriber订阅
                d.dispose();
                return;
            }
        }
    }


    @Override
    public void onError(Throwable e) {
        hideLoading();
        if (e instanceof Exception) {
            //访问获得对应的Exception
            ExceptionHandle.ResponseThrowable responseThrowable = ExceptionHandle.handleException(e, context.getResources());
            onError(responseThrowable);
            CommonUtility.DebugLog.e(TAG, "Toast Message = " + responseThrowable.message);
            showTips(responseThrowable.message);

        } else {
            //将Throwable 和 未知错误的status code返回
            onError(new ExceptionHandle.ResponseThrowable(e.getMessage(), ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    private void showTips(String message1) {
        showTipsMessage(message1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(message -> {
                    if (!CommonUtility.isNull(message) && !message.contains("HTTP 401")) {
                    }
                }, throwable ->
                        throwable.printStackTrace());
    }

    private void showLoading(boolean showLoading) {
        showLoadingView(showLoading)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                    } else {
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                });
    }

    protected Observable<Boolean> showLoadingView(boolean showLoading) {
        if (showLoading && context instanceof Activity && showLoading) {
            return Observable.just(showLoading);
        }
        return Observable.just(false);
    }

    protected Observable<String> showTipsMessage(String message) {
        if (showTips && context instanceof Activity && !CommonUtility.isNull(message)) {
            return Observable.just(message);
        }
        return Observable.just("");
    }


    public abstract void onError(ExceptionHandle.ResponseThrowable responseThrowable);

    @Override
    public void onComplete() {
        hideLoading();
        CommonUtility.DebugLog.e(TAG, "Rx2Subscriber.onComplete()");
    }

    private void hideLoading() {
        showLoading(false);
    }

    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
