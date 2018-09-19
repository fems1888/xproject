package com.qbao.xproject.app.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.entity.Account;
import com.qbao.xproject.app.interf.StatusBarContentColor;
import com.qbao.xproject.app.manager.AccountManager;
import com.qbao.xproject.app.manager.Constants;
import com.qbao.xproject.app.utility.CommonUtility;
import com.qbao.xproject.app.utility.RxSchedulers;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityLoginBinding;
import com.qbao.xproject.app.request_body.UserLoginRequest;
import com.qbao.xproject.app.utility.StatusBarUtils;
import com.qbao.xproject.app.viewmodel.LoginViewModel;
import com.qbao.xproject.app.widget.UITipDialog;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import retrofit2.Response;

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
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.white, StatusBarContentColor.GRAY);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    protected void initData() {
        super.initData();
        bindingView.textGetCode.setEnabled(false);
        bindingView.textLogin.setEnabled(false);
        viewModel = new LoginViewModel(activity.getApplication(),TAG);
        bindingView.editPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (bindingView.editPhone.getText().toString().length() == 11){
                    bindingView.textGetCode.setEnabled(true);
                    if (bindingView.editCode.getText().toString().length() == 6){
                        bindingView.textLogin.setEnabled(true);
                    }else {
                        bindingView.textLogin.setEnabled(false);
                    }
                }else {
                    bindingView.textGetCode.setEnabled(false);
                    bindingView.textLogin.setEnabled(false);
                }

            }
        });

        bindingView.editCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (bindingView.editCode.getText().toString().length() == 6&&bindingView.editPhone.getText().toString().length() == 11){
                    bindingView.textLogin.setEnabled(true);
                }else {
                    bindingView.textLogin.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();

        RxView.clicks(bindingView.image).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        MainActivity.go(activity);

                    }
                });
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


//        NewUserRegisterActivity.goNewUserRegisterActivity(activity,bindingView.editCode.getText().toString(),bindingView.editPhone.getText().toString());
        UITipDialog dialog = new UITipDialog.CustomBuilder(activity,false).setContent(R.layout.view_loading).create();
        dialog.show();
        UserLoginRequest request = new UserLoginRequest();
        request.setPhone(bindingView.editPhone.getText().toString());
        request.setCaptchaCode(bindingView.editCode.getText().toString());
        viewModel.userLogin(request).compose(RxSchedulers.io_main())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String str) throws Exception {
                        //返回给你们的errorCode是-10002，说明是新的用户，把页面迁移到注册页面。
                        dialog.dismiss();
                        if (str.equals(Constants.SUCCESS)){
                            MainActivity.go(activity);
                        }else {
                            if (str.equals(Constants.REGISTER_CODE_IS_NULL)){
                                NewUserRegisterActivity.goNewUserRegisterActivity(activity,bindingView.editPhone.getText().toString());
                            }else {
                                Toast.makeText(activity,str,Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ddd", throwable.getMessage());
                        dialog.dismiss();
//                        Toast.makeText(activity,throwable.getMessage(),Toast.LENGTH_LONG).show();
//                        NewUserRegisterActivity.goNewUserRegisterActivity(activity,bindingView.editCode.getText().toString(),bindingView.editPhone.getText().toString());
                    }
                });
    }

    /**
     * 获取验证码
     */
    private void getVerifyCode() {
        mCountDownTimer.start();
        UITipDialog dialog = new UITipDialog.CustomBuilder(activity,false).setContent(R.layout.view_loading).create();
        dialog.show();
        //调用获取验证码接口
        viewModel.getVerifyCode(bindingView.editPhone.getText().toString(),"+86")
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        dialog.dismiss();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ddd", throwable.getMessage());
                        dialog.dismiss();
                        Toast.makeText(activity,throwable.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }
}
