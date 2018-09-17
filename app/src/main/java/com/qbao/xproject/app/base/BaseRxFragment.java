package com.qbao.xproject.app.base;

import android.app.Activity;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.qbao.xproject.app.R;
import com.qbao.xproject.app.utility.CommonUtility;
import com.qbao.xproject.app.XProjectApplication;

import java.util.Objects;

/**
 * @author Created by jackieyao on 2018/9/11 下午6:25.
 */

public abstract class BaseRxFragment<SV extends ViewDataBinding> extends Fragment {

    // 布局view
    protected SV bindingView;

    protected Activity activity;


    protected XProjectApplication mApplication;

    protected String TAG;

    protected boolean isViewInitiated;

    protected boolean isVisibleToUser;

    protected boolean isDataInitiated;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (XProjectApplication) getActivity().getApplication();
        CommonUtility.DebugLog.e(TAG, "BaseRxFragment onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CommonUtility.DebugLog.e(TAG, "BaseRxFragment onCreateView");
        bindingView = DataBindingUtil.inflate(getActivity().getLayoutInflater(), setContent(), null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.getRoot().setLayoutParams(params);
        activity = getActivity();
        TAG = new StringBuilder().append(activity.getPackageName()).append(".").append(getClass().getSimpleName()).toString();
        CommonUtility.DebugLog.e("BaseRxFragment", TAG);
        return bindingView.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonUtility.DebugLog.e(TAG, "BaseRxFragment onActivityCreated");
        isViewInitiated = true;
        initViews();
        initListener();
        initData();
        prepareFetchData();
    }

    @ColorInt
    protected int getColorByAttributeId(@AttrRes int attrIdForColor) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        theme.resolveAttribute(attrIdForColor, typedValue, true);
        return typedValue.data;
    }

    protected void setToolBarTitle(String title) {
        if (mToolbar == null) {
            mToolbar = (Toolbar) Objects.requireNonNull(getActivity()).findViewById(R.id.tool_bar);
        }
        if (!CommonUtility.isNull(mToolbar)) {
            mToolbar.setTitle(title);
        }
    }

    protected Toolbar mToolbar;

    protected void hideToolbar(View view) {
        if (mToolbar == null) {
            mToolbar = (Toolbar) Objects.requireNonNull(view).findViewById(R.id.tool_bar);
        }
        if (!CommonUtility.isNull(mToolbar)) {
            mToolbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    protected void fetchData() {

    }

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

    public void onDestroy() {
        super.onDestroy();
        CommonUtility.DebugLog.e(TAG, "BaseRxFragment onDestroy");
    }

    protected void initViews() {

    }

    protected void initListener() {

    }

    /**
     * 显示时加载数据,需要这样的使用
     * 注意声明 isPrepared，先初始化
     * 生命周期会先执行 setUserVisibleHint 再执行onActivityCreated
     * 在 onActivityCreated 之后第一次显示加载数据，只加载一次
     */
    protected void initData() {

    }

    protected <T extends View> T getView(int id) {
        return (T) getView().findViewById(id);
    }

    /**
     * 布局
     */
    public abstract int setContent();
}
