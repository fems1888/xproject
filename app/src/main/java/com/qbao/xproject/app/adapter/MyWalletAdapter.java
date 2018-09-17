package com.qbao.xproject.app.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.callback.WithdrawItemCallback;

import java.util.List;

/**
 * @author Created by jackieyao on 2018/9/14 下午5:49
 */


public class MyWalletAdapter extends BaseQuickAdapter<String, MyWalletAdapter.WalletViewHolder> {

    public MyWalletAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(MyWalletAdapter.WalletViewHolder helper, String item) {
        helper.getView(R.id.text_withdraw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null){
                    callback.withdraw();
                }
            }
        });
    }


    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }

    public static class WalletViewHolder extends BaseViewHolder {
        public WalletViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }

    public void setCallback(WithdrawItemCallback callback) {
        this.callback = callback;
    }

    private WithdrawItemCallback callback;

}
