package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jakewharton.rxbinding2.view.RxView;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityAccelerateBinding;
import com.qbao.xproject.app.entity.AccelerateFactorEntity;
import com.qbao.xproject.app.interf.StatusBarContentColor;
import com.qbao.xproject.app.manager.RxBusManager;
import com.qbao.xproject.app.request_body.ReceiveSpeedRequest;
import com.qbao.xproject.app.utility.CommonUtility;
import com.qbao.xproject.app.utility.RxBus;
import com.qbao.xproject.app.utility.RxSchedulers;
import com.qbao.xproject.app.utility.StatusBarUtils;
import com.qbao.xproject.app.viewmodel.MineViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * @author Created by jackieyao on 2018/9/14 下午3:42
 */

public class AccelerateActivity extends BaseRxActivity<ActivityAccelerateBinding> {
    private String mGambleNo;
    private int mNextgambleId;
    public static final String GAMBLE_NO = "GambleNo";
    public static final String NEXT_GAMBLE_ID = "mNextgambleId";
    /**
     * 能否领取登录速度因子  能的话就调领取速度因子的接口
     */
    private boolean mCanReceiveLoginSpeed;
    /**
     * 能否领取活动速度因子  能的话就调领取速度因子的接口
     *
     */
    private boolean mCanReceiveActivitySpeed;

    public static final int GO_DONE = 1;
    public static final int RIGHT_RECEIVE = 2;
    private int mLoginStatus;
    private int mActivityStatus;
    private MineViewModel viewModel;
    /**
     * 是否领取速度因子
     */
    private boolean mReceiveSpeed;
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
    public static void goAccelerateActivity(Context context,String gambleNo,int nextGambleId){
        Intent intent = new Intent(context,AccelerateActivity.class);
        intent.putExtra(GAMBLE_NO,gambleNo);
        intent.putExtra(NEXT_GAMBLE_ID,nextGambleId);
        context.startActivity(intent);
    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        mGambleNo = getIntent().getStringExtra(GAMBLE_NO);
        mNextgambleId = getIntent().getIntExtra(NEXT_GAMBLE_ID,-1);
    }

    @Override
    protected void initData() {
        super.initData();
        bindingView.buttonActivity.setEnabled(false);
        bindingView.buttonLogin.setEnabled(false);
        MineViewModel viewModel = new MineViewModel(activity.getApplication(),TAG);
        viewModel.findAllTaskCompleteList()
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<List<AccelerateFactorEntity>>() {
                    @Override
                    public void accept(List<AccelerateFactorEntity> entities) throws Exception {
                        fetchData(entities);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }

    /**
     * taskType：1登录领取 2做任务
     * 需要看canReceive判断是否可领取。不可领取代表任务未完成，要先完成任务。控制立即领取   和  去完成(去投注界面)
     * speedAdd>0代表已领取。否则代表未领取  控制按钮是否置灰
     * @param entities
     */
    private void fetchData(List<AccelerateFactorEntity> entities) {
        if (entities.size()>=2){
            if (entities.get(0).getTaskType() == 1){
                initView(entities,0);
            }else {
                initView(entities,1);
            }
        }

    }

    private void initView(List<AccelerateFactorEntity> entities,int loginIndex) {
        int index = loginIndex>0?loginIndex-1:loginIndex+1;
        if (entities.get(loginIndex).getSpeedAdd()>0){
            bindingView.buttonLogin.setText(R.string.received);
            bindingView.buttonLogin.setEnabled(false);
            bindingView.textLoginFactor.setText(CommonUtility.formatString("+",CommonUtility.getFormatDoubleTwo(entities.get(loginIndex).getSpeedAdd())));
        }else {
            if (entities.get(loginIndex).isCanReceive()){
                mLoginStatus = RIGHT_RECEIVE;
                mCanReceiveLoginSpeed = true;
                bindingView.buttonLogin.setText(R.string.right_receive);
                bindingView.buttonLogin.setEnabled(true);
                bindingView.textLoginFactor.setText(CommonUtility.formatString("+",CommonUtility.getFormatDoubleTwo(entities.get(loginIndex).getSpeedExpect())));
            }else {
            }
        }
        if (entities.get(index).getSpeedAdd()>0){
            bindingView.buttonActivity.setText(R.string.received);
            bindingView.buttonActivity.setEnabled(false);
            bindingView.textActivityFactor.setText(CommonUtility.formatString("+",CommonUtility.getFormatDoubleTwo(entities.get(index).getSpeedAdd())));
        }else {
            if (entities.get(index).isCanReceive()){
                mActivityStatus = RIGHT_RECEIVE;
                bindingView.buttonActivity.setText(R.string.right_receive);
                bindingView.buttonActivity.setEnabled(true);
                bindingView.textActivityFactor.setText(CommonUtility.formatString("+",CommonUtility.getFormatDoubleTwo(entities.get(index).getSpeedExpect())));
            }else {
                mActivityStatus = GO_DONE;
                bindingView.buttonActivity.setText(R.string.go_done);
                bindingView.buttonActivity.setEnabled(true);
                bindingView.textActivityFactor.setText(CommonUtility.formatString("+",CommonUtility.getFormatDoubleTwo(entities.get(index).getSpeedExpect())));
            }
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(bindingView.buttonLogin).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (mLoginStatus == RIGHT_RECEIVE){

                            receiveSpeed(GO_DONE);
                        }
                    }
                });
        RxView.clicks(bindingView.buttonActivity).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (mActivityStatus == RIGHT_RECEIVE){

                            receiveSpeed(RIGHT_RECEIVE);
                        }else {
                            goBet();
                        }
                    }
                });
    }

    private void goBet() {
        BetRedActivity.goBetActivity(activity,mGambleNo,mNextgambleId);
    }

    private void receiveSpeed(int taskType) {
        if (viewModel == null){
            viewModel = new MineViewModel(activity.getApplication(),TAG);
        }
        ReceiveSpeedRequest request = new ReceiveSpeedRequest();
        request.setTaskType(taskType);
        viewModel.receiveSpeed(request)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<AccelerateFactorEntity>() {
                    @Override
                    public void accept(AccelerateFactorEntity entity) throws Exception {
                        Log.e("accept: ", "success");
                        if (taskType == GO_DONE){
                            bindingView.buttonLogin.setText(getString(R.string.received));
                            bindingView.buttonLogin.setEnabled(false);
                            bindingView.textLoginFactor.setText(CommonUtility.formatString("+",CommonUtility.getFormatDoubleTwo(entity.getSpeedAdd())));
                        }else {
                            bindingView.buttonActivity.setText(getString(R.string.received));
                            bindingView.buttonActivity.setEnabled(false);
                            bindingView.buttonActivity.setText(CommonUtility.formatString("+",CommonUtility.getFormatDoubleTwo(entity.getSpeedAdd())));
                        }
                        RxBus.getDefault().post(new RxBusManager.EventSpeedFactor(entity.getSpeedAdd()));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("accept: ", "fail");
                    }
                });
    }

}
