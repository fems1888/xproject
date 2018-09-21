package com.qbao.xproject.app.fragment;

import com.jakewharton.rxbinding2.view.RxView;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.activity.FeedbackActivity;
import com.qbao.xproject.app.activity.MyWalletActivity;
import com.qbao.xproject.app.activity.ReceiveAirDropActivity;
import com.qbao.xproject.app.activity.SetActivity;
import com.qbao.xproject.app.base.BaseRxFragment;
import com.qbao.xproject.app.databinding.LayoutFragmentArenaBinding;
import com.qbao.xproject.app.databinding.LayoutFragmentMineBinding;
import com.qbao.xproject.app.manager.AccountManager;
import com.qbao.xproject.app.request_body.UserLoginRequest;
import com.qbao.xproject.app.utility.RxSchedulers;
import com.qbao.xproject.app.viewmodel.RefreshTokenViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * @author Created by jackieyao on 2018/9/11 下午6:28.
 */

public class MineFragment extends BaseRxFragment<LayoutFragmentMineBinding> {
    @Override
    public int setContent() {
        return R.layout.layout_fragment_mine;
    }

    @Override
    protected void initData() {
        super.initData();
        bindingView.textName.setText(AccountManager.getInstance().getAccountEntity().getAccountName());
    }

    @Override
    protected void initListener() {
        super.initListener();

        RxView.clicks(bindingView.relativeWallet).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
//                        MyWalletActivity.goOpenIn(activity);

                        UserLoginRequest request = new UserLoginRequest();
                        request.setPhone("");
                        RefreshTokenViewModel viewModel = new RefreshTokenViewModel(activity.getApplication(),TAG);
                        viewModel.refreshToken(request)
                                .compose(RxSchedulers.io_main())
                                .subscribe(new Consumer<String>() {
                                    @Override
                                    public void accept(String str) throws Exception {
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                    }
                                });
                    }
                });
        RxView.clicks(bindingView.relativeSpace).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        ReceiveAirDropActivity.go(activity);
                    }
                });
        RxView.clicks(bindingView.relativeFeedback).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        FeedbackActivity.go(activity);
                    }
                });
        RxView.clicks(bindingView.relativeSet).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        SetActivity.go(activity);
                    }
                });
    }
}
