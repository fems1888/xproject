package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.Utility.StatusBarUtils;
import com.qbao.xproject.app.adapter.BetRedBallAdapter;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityBetBinding;
import com.qbao.xproject.app.entity.BetResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * 投注界面
 * @author Created by jackieyao on 2018/9/12 下午5:10
 */

public class BetRedActivity extends BaseRxActivity<ActivityBetBinding> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.bar_one_color);
        setToolBarTitle(getString(R.string.bet));
    }
    public static void goBetActivity(Context context){
        Intent intent = new Intent(context,BetRedActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(bindingView.txtNext).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        BetBlueActivity.goBetRedActivity(activity);
                    }
                });

    }

    @Override
    protected void initData() {
        super.initData();
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
            bindingView.textBlueBallOne.setVisibility(View.INVISIBLE);
            bindingView.textBlueBallTwo.setVisibility(View.INVISIBLE);
            bindingView.textBlueBallThr.setVisibility(View.INVISIBLE);
        }else if (responseEntityList.size()==1){
            bindingView.textBlueBallOne.setVisibility(View.VISIBLE);
            bindingView.textBlueBallTwo.setVisibility(View.INVISIBLE);
            bindingView.textBlueBallThr.setVisibility(View.INVISIBLE);
            bindingView.textBlueBallOne.setText(responseEntityList.get(0).getNum());
        }else if (responseEntityList.size()==2){
            bindingView.textBlueBallOne.setVisibility(View.VISIBLE);
            bindingView.textBlueBallTwo.setVisibility(View.VISIBLE);
            bindingView.textBlueBallThr.setVisibility(View.INVISIBLE);
            bindingView.textBlueBallOne.setText(responseEntityList.get(0).getNum());
            bindingView.textBlueBallTwo.setText(responseEntityList.get(1).getNum());
        }else if (responseEntityList.size()==3){
            bindingView.textBlueBallOne.setVisibility(View.VISIBLE);
            bindingView.textBlueBallTwo.setVisibility(View.VISIBLE);
            bindingView.textBlueBallThr.setVisibility(View.VISIBLE);
            bindingView.textBlueBallOne.setText(responseEntityList.get(0).getNum());
            bindingView.textBlueBallTwo.setText(responseEntityList.get(1).getNum());
            bindingView.textBlueBallThr.setText(responseEntityList.get(2).getNum());
        }
    }


}
