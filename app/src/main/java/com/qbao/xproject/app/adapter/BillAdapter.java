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
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.entity.BillResponseEntity;
import com.qbao.xproject.app.entity.BillSection;
import com.qbao.xproject.app.entity.MyWalletResponse;
import com.qbao.xproject.app.interf.WithdrawItemCallback;
import com.qbao.xproject.app.manager.Constants;
import com.qbao.xproject.app.utility.CommonUtility;

import java.util.List;

/**
 * @author Created by jackieyao on 2018/9/14 下午5:49
 */


//public class BillAdapter extends BaseQuickAdapter<BillResponseEntity, BillAdapter.BillViewHolder> {
//
//    public BillAdapter(int layoutResId, @Nullable List<BillResponseEntity> data) {
//        super(layoutResId, data);
//    }
//
//    @Override
//    protected void convert(BillViewHolder helper, BillResponseEntity item) {
//    }
//
//
//    @Override
//    protected View getItemView(int layoutResId, ViewGroup parent) {
//        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
//        if (binding == null) {
//            return super.getItemView(layoutResId, parent);
//        }
//        View view = binding.getRoot();
//        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
//        return view;
//    }
//
//    public static class BillViewHolder extends BaseViewHolder {
//        public BillViewHolder(View view) {
//            super(view);
//        }
//
//        public ViewDataBinding getBinding() {
//            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
//        }
//    }
//
//    public void setCallback(WithdrawItemCallback callback) {
//        this.callback = callback;
//    }
//
//    private WithdrawItemCallback callback;
//    }

public class BillAdapter extends BaseSectionQuickAdapter<BillSection, BillAdapter.BillViewHolder> {

    public BillAdapter(int layoutRes, int res, List<BillSection> data) {
        super(layoutRes, res, data);
    }

    @Override
    protected void convertHead(com.qbao.xproject.app.adapter.BillAdapter.BillViewHolder helper, BillSection item) {
        helper.setText(R.id.text_time, item.header);
    }

    @Override
    protected void convert(BillAdapter.BillViewHolder helper, BillSection item) {
        Glide.with(mContext).load(Constants.formatHttpUrl(item.t.getIcon())).into(((AppCompatImageView)helper.getView(R.id.image_icon)));
        helper.setText(R.id.text_type,item.t.getTypeContent());
        if (item.t.getType() == BillResponseEntity.WITHDRAW){
            helper.getView(R.id.text_status).setVisibility(View.VISIBLE);
            helper.setText(R.id.text_status,((BillResponseEntity.BillContentResponse)item.t).getStatus(mContext));
        }else {
            helper.getView(R.id.text_status).setVisibility(View.GONE);
        }
        helper.setText(R.id.text_time,item.t.getExchangeTime());
        helper.setText(R.id.text_unit,item.t.getUnitName());
        helper.setText(R.id.text_amount, CommonUtility.formatString(item.t.getSymbol(),CommonUtility.getFormatDouble(item.t.getAmount())));
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

}


