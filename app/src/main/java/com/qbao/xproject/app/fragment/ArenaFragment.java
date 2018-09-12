package com.qbao.xproject.app.fragment;

import android.view.View;

import com.qbao.xproject.app.R;
import com.qbao.xproject.app.activity.LoginActivity;
import com.qbao.xproject.app.base.BaseRxFragment;
import com.qbao.xproject.app.databinding.LayoutFragmentArenaBinding;

/**
 * @author Created by jackieyao on 2018/9/11 下午6:28.
 */

public class ArenaFragment extends BaseRxFragment<LayoutFragmentArenaBinding> {
    @Override
    public int setContent() {
        return R.layout.layout_fragment_arena;
    }

    @Override
    protected void initViews() {
        super.initViews();
        bindingView.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.goLoginActivity(activity);
            }
        });
    }
}
