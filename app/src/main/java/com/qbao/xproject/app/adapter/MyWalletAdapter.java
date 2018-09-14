package com.aether.coder.qbao.ui.red_new.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aether.coder.qbao.BR;
import com.aether.coder.qbao.R;
import com.aether.coder.qbao.model.db.RechargeResponse;
import com.aether.coder.qbao.model.response.redpacket.ExchangeLogResponse;
import com.aether.coder.qbao.model.response.redpacket.SentRedPacket;
import com.aether.coder.qbao.ui.red_new.entity.SentRedListResponse;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qbao.library.utility.CommonUtility;

import java.util.List;

/**
 * @author Created by jackieyao on 2018/8/2 下午2:34
 */

public class SentRedAdapter extends BaseQuickAdapter<SentRedListResponse.SentRedListContent, SentRedAdapter.RedViewHolder> {

    public SentRedAdapter(int layoutResId, @Nullable List<SentRedListResponse.SentRedListContent> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(SentRedAdapter.RedViewHolder helper, SentRedListResponse.SentRedListContent item) {
        ((TextView)helper.getView(R.id.text_red_type)).setText(item.getType() == SentRedPacket.LUCKY?mContext.getString(R.string.luck_red_packet):mContext.getString(R.string.normal_red));
        ((TextView)helper.getView(R.id.text_red_amount)).setText(CommonUtility.formatString(item.getAmount()," ",item.getContract().getName()));
        ((TextView)helper.getView(R.id.text_red_time)).setText(item.getSendTime());
        ((TextView)helper.getView(R.id.text_red_over)).setText(CommonUtility.formatString(item.getReceiveNumberPhone(),"/",item.getNumber(),mContext.getString(R.string.unit)));
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

    public static class RedViewHolder extends BaseViewHolder {
        public RedViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }
}
