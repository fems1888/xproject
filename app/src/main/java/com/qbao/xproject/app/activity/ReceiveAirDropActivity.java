package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qbao.xproject.app.R;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityReceiveAirDropBinding;
import com.qbao.xproject.app.interf.StatusBarContentColor;
import com.qbao.xproject.app.utility.StatusBarUtils;

/**
 * @author Created by jackieyao on 2018/9/17 下午1:21
 */

public class ReceiveAirDropActivity extends BaseRxActivity<ActivityReceiveAirDropBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_air_drop);
        setToolBarTitle(getString(R.string.text_space));
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.white, StatusBarContentColor.GRAY);
    }
    public static void go(Context context){
        Intent intent = new Intent(context,ReceiveAirDropActivity.class);
        context.startActivity(intent);
    }
}
