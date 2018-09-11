package com.qbao.xproject.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qbao.xproject.app.R;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseRxActivity<ActivityLoginBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
