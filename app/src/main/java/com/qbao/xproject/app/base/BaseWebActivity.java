package com.qbao.xproject.app.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.ChromeClientCallbackManager;
import com.just.agentweb.DefaultWebClient;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.databinding.ActivityCommonWebViewBinding;

/**
 * @author Created by jackieyao on 2018/9/14 下午4:42.
 */

public abstract class BaseWebActivity extends BaseRxActivity<ActivityCommonWebViewBinding> {


    protected AgentWeb mAgentWeb;
    protected LinearLayout mLinearLayout;
    protected AlertDialog mAlertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_web_view);
        mLinearLayout = (LinearLayout) this.findViewById(R.id.linear_web);

//        findViewById(R.id.app_bar_layout).setVisibility(View.GONE);
        long p = System.currentTimeMillis();
        AgentWeb.PreAgentWeb preAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))//
                .useDefaultIndicator()//
                .defaultProgressBarColor()
                .setReceivedTitleCallback(mCallback)
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.strict)
                .setWebLayout(new WebLayout(this))
                .openParallelDownload()//打开并行下载 , 默认串行下载
                .setNotifyIcon(R.mipmap.download) //下载图标
                .setOpenOtherAppWays(DefaultWebClient.OpenOtherAppWays.DISALLOW)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownScheme() //拦截找不到相关页面的Scheme
                .createAgentWeb()//
                .ready();
        mAgentWeb = preAgentWeb.go(getUrl());
        mAgentWeb.getDefaultMsgConfig().getChromeClientMsgCfg().getFileUploadMsgConfig().setMedias(getMedias());
        mAgentWeb.getDefaultMsgConfig().getChromeClientMsgCfg().getFileUploadMsgConfig().setMaxFileLengthLimit(getMaxFileLengthLimit());
        mAgentWeb.getDefaultMsgConfig().getWebViewClientMsgCfg().setLeaveApp(getLeaveApp());
        mAgentWeb.getDefaultMsgConfig().getWebViewClientMsgCfg().setConfirm(getConfirm());
        mAgentWeb.getDefaultMsgConfig().getWebViewClientMsgCfg().setCancel(getCancel());
        mAgentWeb.getDefaultMsgConfig().getWebViewClientMsgCfg().setTitle(getAgentTitle());

        long n = System.currentTimeMillis();
        Log.i("Info", "init used time:" + (n - p)+"   "+getUrl());
        mToolbar.setNavigationIcon(R.drawable.ic_close_gray);

        initWebView();
        initWebData();
    }

    protected abstract String[] getMedias();

    protected abstract String getMaxFileLengthLimit();
    protected abstract String getLeaveApp();
    protected abstract String getConfirm();
    protected abstract String getCancel();
    protected abstract String getAgentTitle();

    /**
     * 新增的方法  只要和webview有关的只重写这个方法代替原先的 initView（）方法
     */
    protected void initWebView() {
    }
    /**
     * 新增的方法  只要和webview有关的只重写这个方法代替原先的 initData（）方法
     */
    protected void initWebData() {
    }
    @Override
    protected void onStart() {
        super.onStart();
        findViewById(R.id.tool_bar).setOnClickListener(view -> {
            BaseWebActivity.this.finish();
        });
    }

    protected WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted");
        }
    };
    protected WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
//            Log.i("Info","progress:"+newProgress);
        }
    };

    /**
     * @return
     */
    public abstract String getUrl();

    protected ChromeClientCallbackManager.ReceivedTitleCallback mCallback = (view, title) -> {
        if (mToolbar != null)
            setToolBarTitle(title);
    };


    private void showDialog() {

        if (mAlertDialog == null)
            mAlertDialog = new AlertDialog.Builder(this)
                    .setMessage("您确定要关闭该页面吗?")
                    .setNegativeButton("再逛逛", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mAlertDialog != null)
                                mAlertDialog.dismiss();
                        }
                    })//
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (mAlertDialog != null)
                                mAlertDialog.dismiss();

                            BaseWebActivity.this.finish();
                        }
                    }).create();
        mAlertDialog.show();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("Info", "result:" + requestCode + " result:" + resultCode);
        mAgentWeb.uploadFileResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAgentWeb.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }
}
