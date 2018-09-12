package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.jakewharton.rxbinding2.view.RxView;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.Utility.StatusBarUtils;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityBetBinding;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * 投注界面
 * @author Created by jackieyao on 2018/9/12 下午5:10
 */

public class BetActivity extends BaseRxActivity<ActivityBetBinding> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.bar_one_color);
    }
    public static void goBetActivity(Context context){
        Intent intent = new Intent(context,BetActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(bindingView.txtNext).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        BetRedActivity.goBetRedActivity(activity);
                    }
                });
    }
}
