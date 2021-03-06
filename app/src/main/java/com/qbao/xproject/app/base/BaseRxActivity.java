package com.qbao.xproject.app.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bugtags.library.Bugtags;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.XProjectApplication;
import com.qbao.xproject.app.utility.CommonUtility;
import com.umeng.analytics.MobclickAgent;

/**
 * @author Created by jackieyao on 2018/9/11 下午6:04.
 */

public abstract class BaseRxActivity<SV extends ViewDataBinding> extends AppCompatActivity {
    protected SV bindingView;
    protected Activity activity;
    protected Toolbar mToolbar;
    protected String TAG;
    private XProjectApplication mApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TAG = new StringBuilder().append(getPackageName()).append(".").append(getClass().getSimpleName()).toString();
        CommonUtility.DebugLog.e("BaseRxActivity", TAG);
        activity = this;
        mApplication = (XProjectApplication) getApplication();
        mApplication.putActivity(activity);
        getIntentData();
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        bindingView = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);
        getWindow().setContentView(bindingView.getRoot());
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (!CommonUtility.isNull(mToolbar)) {
            if (isShowToolBar()) {
                mToolbar.setVisibility(View.VISIBLE);
                setToolBar();
            } else {
                mToolbar.setVisibility(View.GONE);
            }
        }
        initViews();
        initListener();
        initData();
    }



    protected void getIntentData() {
    }

    protected void initViews() {
    }


    protected void initData() {
    }

    protected void initListener() {

    }


    protected void setFitsSystemWindow() {
        ViewGroup content = (ViewGroup) findViewById(android.R.id.content);
        if (content.getChildCount() == 0)
            return;
        View rootView = content.getChildAt(0);
        rootView.setFitsSystemWindows(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!CommonUtility.isNull(mToolbar)) {
            mToolbar.setNavigationOnClickListener(v -> onBackPressed());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonUtility.DebugLog.e("BaseRxActivity", "onDestroy");
    }

    protected boolean isShowToolBar() {
        return true;
    }

    protected void setToolBarTitle(int layoutRes) {
        setToolBarTitle(getString(layoutRes));
    }

    protected void setToolBarTitle(String title) {
        if (mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        }
        if (!CommonUtility.isNull(mToolbar)) {
            mToolbar.setTitle(title);
        }
    }


    /**
     * 设置titlebar
     */
    protected void setToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (!CommonUtility.isNull(mToolbar)) {
            setSupportActionBar(mToolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                //去除默认Title显示
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        Bugtags.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //记录按键操作步骤
        Bugtags.onDispatchKeyEvent(this, event);
        return super.dispatchKeyEvent(event);
    }

    /**
     * ]* @param ev
     *
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Bugtags.onDispatchTouchEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }
}
