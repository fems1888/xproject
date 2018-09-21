package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.adapter.BillAdapter;
import com.qbao.xproject.app.base.BaseRefreshActivity;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityBillBinding;
import com.qbao.xproject.app.entity.BillResponseEntity;
import com.qbao.xproject.app.interf.StatusBarContentColor;
import com.qbao.xproject.app.utility.RxSchedulers;
import com.qbao.xproject.app.utility.StatusBarUtils;
import com.qbao.xproject.app.viewmodel.BillViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @author Created by jackieyao on 2018/9/17 上午11:51
 */

public class BillActivity extends BaseRefreshActivity<ActivityBillBinding,BillAdapter.BillViewHolder,BillResponseEntity> {
    private BillAdapter mAdapter;
    private BillViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        setToolBarTitle(getString(R.string.bill));
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.white, StatusBarContentColor.GRAY);
    }

    @Override
    protected BaseQuickAdapter<BillResponseEntity, BillAdapter.BillViewHolder> initAdapter() {
        mAdapter = new BillAdapter(R.layout.layout_item_bet,new ArrayList<>());
        return mAdapter;
    }

    @Override
    protected void onRefreshCallback(int page, int size) {
           getBillList();
    }



    @Override
    protected void onLoadMoreCallback(int page, int size) {

    }

    public static void go(Context context){
        Intent intent = new Intent(context,BillActivity.class);
        context.startActivity(intent);
    }
    private void getBillList() {
        if (viewModel == null){
            viewModel = new BillViewModel(activity.getApplication(), TAG);
        }
        viewModel.findBillList(mPage,size)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<List<BillResponseEntity>>() {
                    @Override
                    public void accept(List<BillResponseEntity> billResponseEntities) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

}
