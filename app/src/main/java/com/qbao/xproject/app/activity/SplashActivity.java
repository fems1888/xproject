package com.qbao.xproject.app.activity;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.qbao.xproject.app.R;
import com.qbao.xproject.app.manager.AccessTokenManager;

public class SplashActivity extends AppCompatActivity {
    private CountDownTimer mCountDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mCountDownTimer = new CountDownTimer(2 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                intoActivity();
            }

        };
        mCountDownTimer.start();
    }

    private void intoActivity() {
        if (TextUtils.isEmpty(AccessTokenManager.getInstance().getAccessToken())){
            LoginActivity.goLoginActivity(this);
            finish();
        }else {
            MainActivity.go(this);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer!=null){
            mCountDownTimer.cancel();
        }
    }
}
