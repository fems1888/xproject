package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.jakewharton.rxbinding2.view.RxView;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.Utility.StatusBarUtils;
import com.qbao.xproject.app.adapter.BlueBallBetAdapter;
import com.qbao.xproject.app.adapter.RedBallBetAdapter;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityBetBinding;
import com.qbao.xproject.app.databinding.ActivityBetRedBinding;
import com.qbao.xproject.app.entity.BetResponseEntity;
import com.qbao.xproject.app.fragment.dialog_fragment.BetSureDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

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

    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(bindingView.textSure).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        BetSureDialogFragment dialogFragment = new BetSureDialogFragment();
                        dialogFragment.show(getSupportFragmentManager(),dialogFragment.getClass().getCanonicalName());
                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();
        String[] array = getResources().getStringArray(R.array.red_ball);
        int length = array.length;
        List<BetResponseEntity> list = new ArrayList<>();
        for (int i = 0 ; i < length ; i++){
            BetResponseEntity entity = new BetResponseEntity();
            entity.setNum(array[i]);
            list.add(entity);
        }
        bindingView.recyclerRed.setHasFixedSize(true);
        RedBallBetAdapter mAdapter = new RedBallBetAdapter(R.layout.layout_item_bet,list);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(activity);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setAlignItems(AlignItems.BASELINE);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setAutoMeasureEnabled(true);
        bindingView.recyclerRed.setLayoutManager(layoutManager);
        mAdapter.bindToRecyclerView(bindingView.recyclerRed);
    }
}
