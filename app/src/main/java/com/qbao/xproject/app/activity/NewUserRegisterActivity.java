package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;

import com.jakewharton.rxbinding2.view.RxView;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.interf.StatusBarContentColor;
import com.qbao.xproject.app.request_body.UserLoginRequest;
import com.qbao.xproject.app.utility.CommonUtility;
import com.qbao.xproject.app.utility.MaterialDialogUtility;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityNewUserRegisterBinding;
import com.qbao.xproject.app.utility.RxSchedulers;
import com.qbao.xproject.app.utility.StatusBarUtils;
import com.qbao.xproject.app.viewmodel.LoginViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * @author Created by jackieyao on 2018/9/12 下午5:10
 */

public class NewUserRegisterActivity extends BaseRxActivity<ActivityNewUserRegisterBinding> {
    public static final String VERIFY_CODE = "verifyCode";
    public static final String PHONE = "phone";
    private String mVerifyCode;
    private String mPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_register);
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.white, StatusBarContentColor.GRAY);
        setToolBarTitle(getString(R.string.invitation_register));
    }

    public static void goNewUserRegisterActivity(Context context,String verifyCode,String phone){
        Intent intent = new Intent(context,NewUserRegisterActivity.class);
        intent.putExtra(VERIFY_CODE,verifyCode);
        intent.putExtra(PHONE,phone);
        context.startActivity(intent);
    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        mVerifyCode = getIntent().getStringExtra(VERIFY_CODE);
        mPhone = getIntent().getStringExtra(PHONE);

    }

    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(bindingView.textRegister).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
//                        MaterialDialogUtility.showNoWinPriceDialog(activity);
                        register();
                    }
                });
    }

    private void register() {
        LoginViewModel viewModel = new LoginViewModel(activity.getApplication(),TAG);
        UserLoginRequest request = new UserLoginRequest();
        request.setPhone(/*mPhone*/"13023151465");
//        request.setCaptchaCode(mVerifyCode);
        request.setRegisterCode(bindingView.editCode.getText().toString());
        viewModel.userLogin(request).compose(RxSchedulers.io_main())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ddd", throwable.getMessage());
                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();
        bindingView.textRegister.setEnabled(false);
        bindingView.editCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!CommonUtility.isNull(bindingView.editCode.getText().toString())&&bindingView.editCode.getText().toString().length()>=6){
                    bindingView.textRegister.setEnabled(true);
                }else {
                    bindingView.textRegister.setEnabled(false);
                }
            }
        });
    }
}
