package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivitySetBinding;
import com.qbao.xproject.app.interf.StatusBarContentColor;
import com.qbao.xproject.app.manager.AccessTokenManager;
import com.qbao.xproject.app.manager.AccountManager;
import com.qbao.xproject.app.request_body.UserLoginOutRequest;
import com.qbao.xproject.app.utility.MaterialDialogUtility;
import com.qbao.xproject.app.utility.RxSchedulers;
import com.qbao.xproject.app.utility.StatusBarUtils;
import com.qbao.xproject.app.viewmodel.LoginViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * @author Created by jackieyao on 2018/9/19 下午1:22
 */

public class SetActivity extends BaseRxActivity<ActivitySetBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.white, StatusBarContentColor.GRAY);
        setToolBarTitle(getString(R.string.text_set));
    }
    public static void go(Context context){
        Intent intent = new Intent(context,SetActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(bindingView.buttonOut).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        loginOut();
                    }
                });
    }

    private void loginOut() {

        MaterialDialogUtility.showLoginOutDialog(activity, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLoginOutRequest request = new UserLoginOutRequest();
                request.setAccountNo(AccountManager.getInstance().getAccountEntity().getAccountNo());
                LoginViewModel viewModel = new LoginViewModel(activity.getApplication(),TAG);
                viewModel.loginOut(request)
                        .compose(RxSchedulers.io_main())
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                AccessTokenManager.getInstance().clearAccessToken();
                                AccountManager.getInstance().cleanAccount();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e(TAG, "accept: "+throwable.getMessage() );
                            }
                        });
            }
        });

    }
}
