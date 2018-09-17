package com.qbao.xproject.app.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.qbao.xproject.app.R;


/**
 * 提供一个浮层展示在屏幕中间, 一般使用   {@link UITipDialog.CustomBuilder} 生成。
 * <ul>
 * <li>{@link UITipDialog.CustomBuilder} 支持传入自定义的 layoutResId, 达到自定义 TipDialog 的效果。</li>
 * </ul>
 * @author Created by jackieyao on 2018/5/15.
 */

public class UITipDialog extends Dialog {

    public UITipDialog(Context context) {
        this(context, R.style.UI_TipDialog,false);
    }

    public UITipDialog(Context context, boolean cancel) {
        this(context, R.style.UI_TipDialog,cancel);
    }

    public UITipDialog(Context context, int themeResId, boolean cancel) {
        super(context, themeResId);
        setCanceledOnTouchOutside(cancel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialogWidth();
    }

    private void initDialogWidth() {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams wmLp = window.getAttributes();
            wmLp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            window.setAttributes(wmLp);
        }
    }

    /**
     * 传入自定义的布局并使用这个布局生成 TipDialog
     */
    public static class CustomBuilder {
        private Context mContext;
        private int mContentLayoutId;
        private boolean canceled;
        public CustomBuilder(Context context) {
            mContext = context;
        }

        public CustomBuilder(Context context,boolean cancel) {
            mContext = context;
            canceled = cancel;
        }

        public CustomBuilder setContent(@LayoutRes int layoutId) {
            mContentLayoutId = layoutId;
            return this;
        }

        /**
         * 创建 Dialog, 但没有弹出来, 如果要弹出来, 请调用返回值的 {@link Dialog#show()} 方法
         *
         * @return 创建的 Dialog
         */
        public UITipDialog create() {
            UITipDialog dialog = new UITipDialog(mContext,canceled);
            dialog.setContentView(R.layout.ui_tip_dialog_layout);
            ViewGroup contentWrap = (ViewGroup) dialog.findViewById(R.id.contentWrap);
            LayoutInflater.from(mContext).inflate(mContentLayoutId, contentWrap, true);
            return dialog;
        }
    }
}
