package com.qbao.xproject.app.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.entity.BillResponseEntity;
import com.qbao.xproject.app.entity.MyWalletResponse;
import com.qbao.xproject.app.interf.WithdrawItemCallback;
import com.qbao.xproject.app.manager.Constants;

import java.util.List;

/**
 * @author Created by jackieyao on 2018/9/14 下午5:49
 */


public class BillAdapter extends BaseQuickAdapter<BillResponseEntity, BillAdapter.BillViewHolder> {

    public BillAdapter(int layoutResId, @Nullable List<BillResponseEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BillViewHolder helper, BillResponseEntity item) {
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

    public static class BillViewHolder extends BaseViewHolder {
        public BillViewHolder(View view) {
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
