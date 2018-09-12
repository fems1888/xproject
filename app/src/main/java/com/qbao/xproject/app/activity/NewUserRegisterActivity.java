package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jakewharton.rxbinding2.view.RxView;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.Utility.MaterialDialogUtility;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityNewUserRegisterBinding;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

public class NewUserRegisterActivity extends BaseRxActivity<ActivityNewUserRegisterBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_register);
    }

    public static void goNewUserRegisterActivity(Context context){
        Intent intent = new Intent(context,NewUserRegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(bindingView.textRegister).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        MaterialDialogUtility.showWinPriceDialog(activity);
                    }
                });
    }
}
