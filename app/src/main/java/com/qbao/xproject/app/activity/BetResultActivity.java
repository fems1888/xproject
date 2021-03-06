package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jakewharton.rxbinding2.view.RxView;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.manager.Constants;
import com.qbao.xproject.app.utility.StatusBarUtils;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityBetResultBinding;
import com.qbao.xproject.app.utility.XProjectUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * @author Created by jackieyao on 2018/9/13 下午2:44
 */

public class BetResultActivity extends BaseRxActivity<ActivityBetResultBinding> {
    public static final String BALL_ONE = "Ball_one";
    public static final String BALL_TWO = "Ball_two";
    public static final String BALL_THREE = "Ball_three";
    public static final String BALL_FOUR = "Ball_Four";
    public static final String GAMBLE_NO = "GambleNo";
    private String mBlueOne;
    private String mRedBallOne;
    private String mRedBallTwo;
    private String mGambleNo;
    private String mRedBallThree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_result);
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.bar_one_color);
    }
    public static void goBetResultActivity(Context context, String mRedBallOne, String mRedBallTwo, String mRedBallThree, String mBlueBall,String mGambleNo){
        Intent intent = new Intent(context,BetResultActivity.class);
        intent.putExtra(BALL_ONE,mRedBallOne);
        intent.putExtra(BALL_TWO,mRedBallTwo);
        intent.putExtra(BALL_THREE,mRedBallThree);
        intent.putExtra(BALL_FOUR,mBlueBall);
        intent.putExtra(GAMBLE_NO,mGambleNo);
        context.startActivity(intent);
    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        mBlueOne = getIntent().getStringExtra(BALL_FOUR);
        mRedBallOne = getIntent().getStringExtra(BALL_ONE);
        mRedBallTwo = getIntent().getStringExtra(BALL_TWO);
        mRedBallThree = getIntent().getStringExtra(BALL_THREE);
        mGambleNo = getIntent().getStringExtra(GAMBLE_NO);
    }

    @Override
    protected void initViews() {
        super.initViews();
        setToolBarTitle(getString(R.string.bet_result));
    }

    @Override
    protected void initData() {
        super.initData();
        bindingView.textRedOne.setText(mRedBallOne);
        bindingView.textRedTwo.setText(mRedBallTwo);
        bindingView.textRedThree.setText(mRedBallThree);
        bindingView.textRedFour.setText(mBlueOne);
        bindingView.textName.setText(String.format(getString(R.string.bet_result_tip),mGambleNo));
    }

    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(bindingView.buttonDone).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        XProjectUtil.eventReport(activity,getString(R.string.event_id_1068));
                        finish();
                    }
                });
        RxView.clicks(bindingView.textRule).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        XProjectUtil.eventReport(activity,getString(R.string.event_id_1069));
                        WebViewActivity.goOpenIn(activity, Constants.getArenaRuleUrl());
                    }
                });
    }
}
