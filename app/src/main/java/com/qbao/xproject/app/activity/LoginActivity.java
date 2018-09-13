package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jakewharton.rxbinding2.view.RxView;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.Utility.RxSchedulers;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityLoginBinding;
import com.qbao.xproject.app.request_body.UserLoginRequest;
import com.qbao.xproject.app.viewmodel.LoginViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * @author Created by jackieyao on 2018/9/12 下午5:10
 */

public class LoginActivity extends BaseRxActivity<ActivityLoginBinding> {
    private CountDownTimer mCountDownTimer;
    private LoginViewModel viewModel;

    public static void goLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    protected void initData() {
        super.initData();
        viewModel = new LoginViewModel(activity.getApplication());
    }

    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(bindingView.textGetCode).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        getVerifyCode();

                    }
                });
        RxView.clicks(bindingView.textLogin).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Login();

                    }
                });
        mCountDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                bindingView.textGetCode.setEnabled(false);
                bindingView.textGetCode.setText(String.format(getString(R.string.text_resend_verify_code), millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                bindingView.textGetCode.setEnabled(true);
                bindingView.textGetCode.setText(getString(R.string.get_code));
            }

        };
    }

    private void Login() {
        UserLoginRequest request = new UserLoginRequest();
        request.setPhone(bindingView.editPhone.getText().toString());
        request.setCaptchaCode(bindingView.editCode.getText().toString());
        viewModel.userLogin(request).compose(RxSchedulers.io_main())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                });
    }

    /**
     * 获取验证码
     */
    private void getVerifyCode() {
        mCountDownTimer.start();
        //调用获取验证码接口
        viewModel.getVerifyCode(bindingView.editPhone.getText().toString())
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                });
    }
}
