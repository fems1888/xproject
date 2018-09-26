package com.qbao.xproject.app.base;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.utility.CommonUtility;

import java.util.List;

/**
 * @author Created by jackieyao on 2018/9/25 下午8:34
 */

public abstract class BaseRefreshActivity<SV extends ViewDataBinding, V extends BaseViewHolder, B> extends BaseRxActivity<SV> implements SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener {

    protected RecyclerView mRecyclerView;


    protected SwipeRefreshLayout mSwipeRefreshLayout;

    protected BaseQuickAdapter<B, V> mAdapter;
    private int page;
    protected int mPage;
    protected int size = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
    }


    @Override
    protected void initViews() {
        super.initViews();
        mRecyclerView = findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mAdapter = initAdapter();
        mRecyclerView.setLayoutManager(initLayoutManager());
        if (openAnimation()) {
            mAdapter.openLoadAnimation(new BaseAnimation() {
                @Override
                public Animator[] getAnimators(View view) {
                    return new Animator[]{
                            ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
                            ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
                    };
                }
            });
        }
        mAdapter.bindToRecyclerView(mRecyclerView);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
    }


    @Override
    protected void initData() {
        if (autoRefresh()) {
            refresh();
        }
    }

    /**
     * 可手动调用该 方法  刷新
     */
    protected void refresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        size = initPageSize();
        onRefreshCallback(page, size);
    }

    protected abstract int getLayoutId();

    /**
     * 默认RecyclerView为 LinearLayoutManager
     *
     * @return
     */
    protected RecyclerView.LayoutManager initLayoutManager() {
        return new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
    }

    /**
     * 初始化适配器
     *
     * @return
     */
    protected abstract BaseQuickAdapter<B, V> initAdapter();

    /**
     * 刷新的回调
     *
     * @param page
     * @param size
     */
    protected abstract void onRefreshCallback(int page, int size);

    /**
     * 加载更多的回调
     *
     * @param page
     * @param size
     */
    protected abstract void onLoadMoreCallback(int page, int size);


    /**
     * 分页的加载的数量
     *
     * @return
     */
    protected int initPageSize() {
        return size;
    }

    /**
     * 初始化page
     */
    protected void initialPage() {
        page = 0;
    }

    /**
     * 默认的动画
     *
     * @return
     */
    protected int openLoadAnimation() {
        return BaseQuickAdapter.SLIDEIN_BOTTOM;
    }

    /**
     * 是否自动刷新  默认true
     *
     * @return
     */
    protected boolean autoRefresh() {
        return true;
    }

    protected boolean isLongClick() {
        return false;
    }



    /**
     * Item Click
     *
     * @param entity
     */
    protected void onRecyclerViewItemClick(B entity) {
    }

    protected void onRecyclerViewItemClick(B entity, int position) {
    }

    /**
     * Item Long Click
     *
     * @param entity
     */
    protected void onRecyclerViewItemLongClick(B entity) {
    }

    protected void onRecyclerViewItemLongClick(B entity, int position) {
    }

    protected abstract boolean openAnimation();

    /**
     * 默认 为 page++ ; false为 page
     *
     * @return
     */
    protected boolean spanViewPage() {
        return false;
    }

    /**
     * Refresh
     */
    @Override
    public void onRefresh() {
        page = 0;
        mPage = 0;
        onRefreshCallback(page, size);
    }

    /**
     * LoadMore
     */
    @Override
    public void onLoadMoreRequested() {
        page += size + 1;
        mPage++;
        onLoadMoreCallback(!spanViewPage() ? mPage : page, size);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        B bean = (B) adapter.getItem(position);
        if (!CommonUtility.isNull(bean)) {
            onRecyclerViewItemClick(bean);
            onRecyclerViewItemClick(bean, position);
        }
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        B bean = (B) adapter.getItem(position);
        if (!CommonUtility.isNull(bean)) {
            onRecyclerViewItemLongClick(bean);
            onRecyclerViewItemLongClick(bean, position);
        }
        return isLongClick();
    }

    /**
     * 获取数据成功的处理
     *
     * @param list
     */
    protected void loadDataComplete(List<B> list) {

        mSwipeRefreshLayout.setRefreshing(false);
        CommonUtility.DebugLog.e(TAG, "size = " + list.size());
        if (page == 0) {
            if (list.size() == 0) {
            }
            mAdapter.setNewData(list);
            if (list.size() < size) {
                mAdapter.loadMoreEnd(true);
            } else {
                mAdapter.loadMoreComplete();
            }
        } else {
            if (list.size() < size) {
                mAdapter.loadMoreEnd(true);
            } else {
                mAdapter.loadMoreComplete();
            }
            mAdapter.addData(list);
        }
    }

    /**
     * 获取数据失败的处理
     *
     * @param throwable
     */
    protected void loadDataFailed(Throwable throwable) {
        mSwipeRefreshLayout.setRefreshing(false);
        //加载失败
        mAdapter.loadMoreFail();
        throwable.printStackTrace();
    }

}
