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
import com.qbao.xproject.app.entity.BetResponseEntity;

import java.util.List;

/**
 * @author Created by jackieyao on 2018/9/13 上午10:15.
 */

public class RedBallBetAdapter extends BaseQuickAdapter<BetResponseEntity,RedBallBetAdapter.ViewHolder>{


    public RedBallBetAdapter(int layoutResId, @Nullable List<BetResponseEntity> data) {
        super(layoutResId, data);
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

    @Override
    protected void convert(ViewHolder helper, BetResponseEntity item) {
        TextView textView = (TextView) helper.getView(R.id.text_ball);
        textView.setText(item.getNum());
        if (item.isChosed()){
            textView.setBackgroundResource(R.drawable.ic_bet_red_ball);
        }else {
            textView.setBackgroundResource(R.mipmap.red_chose_num_bg);
        }
    }


    public static class ViewHolder extends BaseViewHolder {

        public ViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }

    }
}
