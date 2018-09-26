package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.adapter.SpinnerAdapter;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityWithdrawBinding;
import com.qbao.xproject.app.entity.MyWalletResponse;
import com.qbao.xproject.app.entity.WithdrawResponseEntity;
import com.qbao.xproject.app.interf.StatusBarContentColor;
import com.qbao.xproject.app.manager.RxBusManager;
import com.qbao.xproject.app.request_body.WithdrawRequest;
import com.qbao.xproject.app.utility.CommonUtility;
import com.qbao.xproject.app.utility.RxBus;
import com.qbao.xproject.app.utility.RxSchedulers;
import com.qbao.xproject.app.utility.StatusBarUtils;
import com.qbao.xproject.app.utility.XProjectUtil;
import com.qbao.xproject.app.viewmodel.MyWalletViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * @author Created by jackieyao on 2018/9/17 上午9:55
 */

public class WithdrawActivity extends BaseRxActivity<ActivityWithdrawBinding> {
    private SpinnerAdapter mAdapter;
    private List<MyWalletResponse.MyWalletList> mCoinList;
    public static final String COIN_LIST = "coin_list";
    private int mUnit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        setToolBarTitle(getString(R.string.withdraw));
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.white, StatusBarContentColor.GRAY);
    }
    public static void go(Context context, ArrayList<MyWalletResponse.MyWalletList> mCoinList){
        Intent intent = new Intent(context,WithdrawActivity.class);
        intent.putParcelableArrayListExtra(COIN_LIST,mCoinList);
        context.startActivity(intent);
    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        mCoinList = getIntent().getParcelableArrayListExtra(COIN_LIST);
    }

    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(bindingView.buttonWithdraw).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        XProjectUtil.eventReport(activity,getString(R.string.event_id_1010));
                        withDraw();
                    }
                });
    }

    private void withDraw() {
        if (TextUtils.isEmpty(bindingView.editAddress.getText().toString())){
            Toast.makeText(activity, R.string.input_withdraw_address,Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(bindingView.editAmount.getText().toString())){
            Toast.makeText(activity, R.string.input_withdraw_amount,Toast.LENGTH_LONG).show();
            return;
        }
        WithdrawRequest request = new WithdrawRequest();
        request.setToAddress(bindingView.editAddress.getText().toString());
        request.setUnit(mUnit);
        request.setAmount(bindingView.editAmount.getText().toString());
        request.setFeePerKb(bindingView.textFee.getText().toString());
        MyWalletViewModel viewModel = new MyWalletViewModel(activity.getApplication(),TAG);
        viewModel.withdrawApply(request)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<WithdrawResponseEntity>() {
                    @Override
                    public void accept(WithdrawResponseEntity o) throws Exception {

                        Toast.makeText(activity, R.string.withdraw_success,Toast.LENGTH_LONG).show();
                        RxBus.getDefault().post(new RxBusManager.EventWalletRefresh());
//                        finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();
        CommonUtility.setEditTextZeroStart(bindingView.editAmount);
        mAdapter = new SpinnerAdapter(mCoinList,activity);
        bindingView.spinner.setAdapter(mAdapter);
        bindingView.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mUnit = mCoinList.get(position).getUnit();
                bindingView.textFee.setText(CommonUtility.getFormatDouble(mCoinList.get(position).getWithdrowFee()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
