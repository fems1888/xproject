package com.qbao.xproject.app.fragment;

import com.qbao.xproject.app.R;
import com.qbao.xproject.app.base.BaseRxFragment;
import com.qbao.xproject.app.databinding.LayoutFragmentArenaBinding;
import com.qbao.xproject.app.databinding.LayoutFragmentCoinMineBinding;

/**
 * @author Created by jackieyao on 2018/9/11 下午6:28.
 */

public class CoinMineFragment extends BaseRxFragment<LayoutFragmentCoinMineBinding> {
    @Override
    public int setContent() {
        return R.layout.layout_fragment_coin_mine;
    }
}
