package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qbao.xproject.app.R;
import com.qbao.xproject.app.Utility.StatusBarUtils;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityBetRedBinding;
import com.qbao.xproject.app.databinding.ActivityBetResultBinding;

/**
 * @author Created by jackieyao on 2018/9/13 下午2:44
 */

public class BetResultActivity extends BaseRxActivity<ActivityBetResultBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_result);
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.bar_one_color);
    }
    public static void goBetResultActivity(Context context){
        Intent intent = new Intent(context,BetResultActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void initViews() {
        super.initViews();
        setToolBarTitle(getString(R.string.bet_result));
    }
}
