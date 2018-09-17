package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qbao.xproject.app.R;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityWithdrawBinding;
import com.qbao.xproject.app.interf.StatusBarContentColor;
import com.qbao.xproject.app.utility.StatusBarUtils;

/**
 * @author Created by jackieyao on 2018/9/17 上午9:55
 */

public class WithdrawActivity extends BaseRxActivity<ActivityWithdrawBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        setToolBarTitle(getString(R.string.withdraw));
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.white, StatusBarContentColor.GRAY);
    }
    public static void go(Context context){
        Intent intent = new Intent(context,WithdrawActivity.class);
        context.startActivity(intent);
    }
    
}
