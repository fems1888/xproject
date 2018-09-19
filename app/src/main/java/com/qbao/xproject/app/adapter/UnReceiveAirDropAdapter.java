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
import com.qbao.xproject.app.entity.MyWalletResponse;
import com.qbao.xproject.app.entity.UnReceiveAirDropEntity;

import java.util.List;

/**
 * @author Created by jackieyao on 2018/9/14 下午5:49
 */


public class UnReceiveAirDropAdapter extends BaseQuickAdapter<UnReceiveAirDropEntity.UnReceiveAirDrop, UnReceiveAirDropAdapter.UnReceiveAirDropViewHolder> {

    public UnReceiveAirDropAdapter(int layoutResId, @Nullable List<UnReceiveAirDropEntity.UnReceiveAirDrop> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(UnReceiveAirDropAdapter.UnReceiveAirDropViewHolder helper, UnReceiveAirDropEntity.UnReceiveAirDrop item) {

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

    public static class UnReceiveAirDropViewHolder extends BaseViewHolder {
        public UnReceiveAirDropViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }

}
