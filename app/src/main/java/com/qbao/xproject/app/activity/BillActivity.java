package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qbao.xproject.app.R;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityBillBinding;
import com.qbao.xproject.app.interf.StatusBarContentColor;
import com.qbao.xproject.app.utility.StatusBarUtils;

/**
 * @author Created by jackieyao on 2018/9/17 上午11:51
 */

public class BillActivity extends BaseRxActivity<ActivityBillBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        setToolBarTitle(getString(R.string.bill));
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.white, StatusBarContentColor.GRAY);
    }
    public static void go(Context context){
        Intent intent = new Intent(context,BillActivity.class);
        context.startActivity(intent);
    }

}
