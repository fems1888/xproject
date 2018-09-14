package com.aether.coder.qbao.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.aether.coder.qbao.R;
import com.aether.coder.qbao.http.utils.ExceptionHandle;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qbao.library.utility.CommonUtility;

import java.util.List;

/**
 * Created by hubert on 2017/8/31.
 */

public abstract class BaseRefreshActivity<SV extends ViewDataBinding, V extends BaseViewHolder, B> extends BaseRxActivity<SV> implements SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener {

    protected RecyclerView mRecyclerView;

    protected ImageView mImageViewEmpty;

    protected SwipeRefreshLayout mSwipeRefreshLayout;

    protected BaseQuickAdapter<B, V> mAdapter;

    private int page;
    private int mPage;
    protected int size = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_refresh);
    }


    @Override
    protected void initViews() {
        super.initViews();
        mRecyclerView = findViewById(R.id.recycler_view);
        mImageViewEmpty = findViewById(R.id.image_empty);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mAdapter = initAdapter();
        mSwipeRefreshLayout.setColorSchemeColors(getColorByAttributeId(R.attr.colorPrimary));
        mRecyclerView.setLayoutManager(initLayoutManager());
        if (openAnimation()) {
            mAdapter.openLoadAnimation(openLoadAnimation());
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
        super.initData();
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
        return BaseQuickAdapter.SCALEIN;
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
     * 空数据的UI
     *
     * @return
     */
    protected int getEmptyView() {
        return R.layout.view_nothing;
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

    protected boolean openAnimation() {
        return false;
    }

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
        if (!CommonUtility.Utility.isNull(bean)) {
            onRecyclerViewItemClick(bean);
            onRecyclerViewItemClick(bean, position);
        }
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        B bean = (B) adapter.getItem(position);
        if (!CommonUtility.Utility.isNull(bean)) {
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
        if (mImageViewEmpty != null) {
            mImageViewEmpty.setVisibility(View.GONE);
        }
        mSwipeRefreshLayout.setRefreshing(false);
        CommonUtility.DebugLog.e(TAG, "size = " + list.size());
        if (page == 0) {
            if (list.size() == 0) {
                mAdapter.setEmptyView(getEmptyView());
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
        if (mImageViewEmpty != null) {
            mImageViewEmpty.setVisibility(View.VISIBLE);
            try {
                if (throwable instanceof ExceptionHandle.ResponseThrowable){
                    if (((ExceptionHandle.ResponseThrowable) throwable).code == ExceptionHandle.ERROR.NETWORD_ERROR){
                        mImageViewEmpty.setImageResource(R.drawable.no_net_web_image);
                    }else {
                        mImageViewEmpty.setImageResource(R.drawable.net_request_error_image);
                    }
                }
            }catch (Exception e){

            }
        }
        mSwipeRefreshLayout.setRefreshing(false);
        //加载失败
        mAdapter.loadMoreFail();
        throwable.printStackTrace();
    }

}
