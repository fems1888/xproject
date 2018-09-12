package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qbao.xproject.app.R;
import com.qbao.xproject.app.Utility.StatusBarUtils;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityBetBinding;
import com.qbao.xproject.app.databinding.ActivityBetRedBinding;

/**
 * 投注红球界面
 * @author Created by jackieyao on 2018/9/12 下午6:21
 */

public class BetRedActivity extends BaseRxActivity<ActivityBetRedBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_red);
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.bar_one_color);
    }
    public  static void goBetRedActivity(Context context){
        Intent intent = new Intent(context,BetRedActivity.class);
        context.startActivity(intent);
    }
}
