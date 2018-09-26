package com.qbao.xproject.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qbao.xproject.app.R;
import com.qbao.xproject.app.entity.MyWalletResponse;

import java.util.List;

/**
 * @author Created by jackieyao on 2018/9/25 上午10:54.
 */

public class SpinnerAdapter extends BaseAdapter{
    private List<MyWalletResponse.MyWalletList> mCoinList;
    private Context mContext;

    public SpinnerAdapter(List<MyWalletResponse.MyWalletList> mList, Context mContext) {
        this.mCoinList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mCoinList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCoinList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            LayoutInflater _LayoutInflater = LayoutInflater.from(mContext);
            convertView = _LayoutInflater.inflate(R.layout.item_spinner, null);
            holder = new ViewHolder();
            holder.coinName = (TextView) convertView.findViewById(R.id.spinner_item_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.coinName.setText(mCoinList.get(position).getUnitName());
        return convertView;
    }

    static class ViewHolder {
        private TextView coinName;
    }

}
