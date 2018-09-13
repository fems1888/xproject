package com.qbao.xproject.app.fragment;

import com.jakewharton.rxbinding2.view.RxView;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.activity.LoginActivity;
import com.qbao.xproject.app.base.BaseRxFragment;
import com.qbao.xproject.app.databinding.LayoutFragmentArenaBinding;
import com.qbao.xproject.app.databinding.LayoutFragmentCoinMineBinding;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * @author Created by jackieyao on 2018/9/11 下午6:28.
 */

public class CoinMineFragment extends BaseRxFragment<LayoutFragmentCoinMineBinding> {
    @Override
    public int setContent() {
        return R.layout.layout_fragment_coin_mine;
    }

    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(bindingView.text).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        LoginActivity.goLoginActivity(activity);
                    }
                });
    }
}
