package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qbao.xproject.app.R;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityAccelerateBinding;
import com.qbao.xproject.app.interf.StatusBarContentColor;
import com.qbao.xproject.app.utility.StatusBarUtils;

/**
 * @author Created by jackieyao on 2018/9/14 下午3:42
 */

public class AccelerateActivity extends BaseRxActivity<ActivityAccelerateBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerate);
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.white, StatusBarContentColor.GRAY);
        setToolBarTitle(getString(R.string.accelerate));
    }

    public static void goAccelerateActivity(Context context){
        Intent intent = new Intent(context,AccelerateActivity.class);
        context.startActivity(intent);
    }


}
