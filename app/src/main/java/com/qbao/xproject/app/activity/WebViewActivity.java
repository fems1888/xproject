package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qbao.xproject.app.R;
import com.qbao.xproject.app.base.BaseWebActivity;
import com.qbao.xproject.app.interf.StatusBarContentColor;
import com.qbao.xproject.app.utility.StatusBarUtils;

public class WebViewActivity extends BaseWebActivity {
    public static final String URL = "url";
    private String mUrl;

    public static void goOpenIn(Context context, String mUrl) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(URL, mUrl);
        context.startActivity(intent);
    }

    @Override
    protected String[] getMedias() {

        return new String[]{getString(R.string.camera), getString(R.string.file_choose)};
    }

    @Override
    protected String getMaxFileLengthLimit() {
        return getString(R.string.choose_file_not_more);
    }

    @Override
    protected String getLeaveApp() {
        return getString(R.string.need_leave_to_other);
    }

    protected String getConfirm() {
        return getString(R.string.leave);
    }

    @Override
    protected String getCancel() {
        return getString(R.string.dialog_cancel);
    }

    @Override
    protected String getAgentTitle() {
        return getString(R.string.tips);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mUrl = getIntent().getStringExtra(URL);
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.white, StatusBarContentColor.GRAY);
    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        mUrl = getIntent().getStringExtra(URL);
    }

    @Override
    protected void initWebData() {
        super.initWebData();


    }

    @Override
    protected void onStart() {
        super.onStart();
        findViewById(R.id.tool_bar).setOnClickListener(view -> {
            WebViewActivity.this.finish();
        });
    }


    @Nullable
    @Override
    public String getUrl() {
        return mUrl;
    }

}
