package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.utility.StatusBarUtils;
import com.qbao.xproject.app.adapter.BetRedBallAdapter;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityBetBinding;
import com.qbao.xproject.app.entity.BetResponseEntity;
import com.qbao.xproject.app.utility.XProjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * 投注界面
 * @author Created by jackieyao on 2018/9/12 下午5:10
 */

public class BetRedActivity extends BaseRxActivity<ActivityBetBinding> {
    private String mGambleNo;
    private int mNextgambleId;
    public static final String GAMBLE_NO = "GambleNo";
    public static final String NEXT_GAMBLE_ID = "mNextgambleId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.bar_one_color);
        setToolBarTitle(getString(R.string.bet));
    }
    public static void goBetActivity(Context context,String gambleNo,int nextGambleId){
        Intent intent = new Intent(context,BetRedActivity.class);
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
    protected void initListener() {
        super.initListener();
        RxView.clicks(bindingView.buttonNext).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        XProjectUtil.eventReport(activity,getString(R.string.event_id_1053));
                        BetBlueActivity.goBetRedActivity(activity,bindingView.textBlueBallOne.getText().toString(),bindingView.textBlueBallTwo.getText().toString(),bindingView.textBlueBallThr.getText().toString(),mGambleNo,mNextgambleId);
                    }
                });

    }

    @Override
    protected void initData() {
        super.initData();
        bindingView.buttonNext.setEnabled(false);
        String[] array = getResources().getStringArray(R.array.blue_ball);
        int length = array.length;
        List<BetResponseEntity> list = new ArrayList<>();
        for (int i = 0 ; i < length ; i++){
            BetResponseEntity entity = new BetResponseEntity();
            entity.setNum(array[i]);
            list.add(entity);
        }
        bindingView.recyclerBlue.setHasFixedSize(true);
        BetRedBallAdapter mAdapter = new BetRedBallAdapter(R.layout.layout_item_bet,list);
        bindingView.recyclerBlue.setLayoutManager(new GridLayoutManager(activity,4));
        mAdapter.bindToRecyclerView(bindingView.recyclerBlue);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            int choseNum = 0;
            for (BetResponseEntity entity : list){
                if (entity.isChosed()){
                    choseNum++;
                }
            }
            if (choseNum<3){
                if (list.get(position).isChosed()){
                    list.get(position).setChosed(false);
                }else {
                    list.get(position).setChosed(true);
                }

            }else if (list.get(position).isChosed()){
                list.get(position).setChosed(false);
            }
            mAdapter.setNewData(list);
            setChosedShow(list);

            if (position == 0){
                XProjectUtil.eventReport(activity,getString(R.string.event_id_1037));
            }else if (position == 1){
                XProjectUtil.eventReport(activity,getString(R.string.event_id_1038));
            }else if (position == 2){
                XProjectUtil.eventReport(activity,getString(R.string.event_id_1039));
            }else if (position == 3){
                XProjectUtil.eventReport(activity,getString(R.string.event_id_1040));
            }else if (position == 4){
                XProjectUtil.eventReport(activity,getString(R.string.event_id_1041));
            }else if (position == 5){
                XProjectUtil.eventReport(activity,getString(R.string.event_id_1042));
            }else if (position == 6){
                XProjectUtil.eventReport(activity,getString(R.string.event_id_1043));
            }else if (position == 7){
                XProjectUtil.eventReport(activity,getString(R.string.event_id_1044));
            }else if (position == 8){
                XProjectUtil.eventReport(activity,getString(R.string.event_id_1045));
            }else if (position == 9){
                XProjectUtil.eventReport(activity,getString(R.string.event_id_1046));
            }else if (position == 10){
                XProjectUtil.eventReport(activity,getString(R.string.event_id_1047));
            }else if (position == 11){
                XProjectUtil.eventReport(activity,getString(R.string.event_id_1048));
            }else if (position == 12){
                XProjectUtil.eventReport(activity,getString(R.string.event_id_1049));
            }else if (position == 13){
                XProjectUtil.eventReport(activity,getString(R.string.event_id_1050));
            }else if (position == 14){
                XProjectUtil.eventReport(activity,getString(R.string.event_id_1051));
            }else if (position == 15){
                XProjectUtil.eventReport(activity,getString(R.string.event_id_1052));
            }

        });

    }

    private void setChosedShow(List<BetResponseEntity> list) {
        List<BetResponseEntity> responseEntityList = new ArrayList<>();
        for (BetResponseEntity entity : list){
            if (entity.isChosed()){
                responseEntityList.add(entity);
            }
        }
        if (responseEntityList.size()==0){
            bindingView.buttonNext.setEnabled(false);
            bindingView.textBlueBallOne.setVisibility(View.INVISIBLE);
            bindingView.textBlueBallTwo.setVisibility(View.INVISIBLE);
            bindingView.textBlueBallThr.setVisibility(View.INVISIBLE);
        }else if (responseEntityList.size()==1){
            bindingView.buttonNext.setEnabled(false);
            bindingView.textBlueBallOne.setVisibility(View.VISIBLE);
            bindingView.textBlueBallTwo.setVisibility(View.INVISIBLE);
            bindingView.textBlueBallThr.setVisibility(View.INVISIBLE);
            bindingView.textBlueBallOne.setText(responseEntityList.get(0).getNum());
        }else if (responseEntityList.size()==2){
            bindingView.buttonNext.setEnabled(false);
            bindingView.textBlueBallOne.setVisibility(View.VISIBLE);
            bindingView.textBlueBallTwo.setVisibility(View.VISIBLE);
            bindingView.textBlueBallThr.setVisibility(View.INVISIBLE);
            bindingView.textBlueBallOne.setText(responseEntityList.get(0).getNum());
            bindingView.textBlueBallTwo.setText(responseEntityList.get(1).getNum());
        }else if (responseEntityList.size()==3){
            bindingView.buttonNext.setEnabled(true);
            bindingView.textBlueBallOne.setVisibility(View.VISIBLE);
            bindingView.textBlueBallTwo.setVisibility(View.VISIBLE);
            bindingView.textBlueBallThr.setVisibility(View.VISIBLE);
            bindingView.textBlueBallOne.setText(responseEntityList.get(0).getNum());
            bindingView.textBlueBallTwo.setText(responseEntityList.get(1).getNum());
            bindingView.textBlueBallThr.setText(responseEntityList.get(2).getNum());
        }
    }


}
