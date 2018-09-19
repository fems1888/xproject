package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.adapter.MyWalletAdapter;
import com.qbao.xproject.app.base.BaseRefreshActivity;
import com.qbao.xproject.app.callback.WithdrawItemCallback;
import com.qbao.xproject.app.databinding.ActivityMyWalletBinding;
import com.qbao.xproject.app.entity.MyWalletResponse;
import com.qbao.xproject.app.interf.StatusBarContentColor;
import com.qbao.xproject.app.utility.RxSchedulers;
import com.qbao.xproject.app.utility.StatusBarUtils;
import com.qbao.xproject.app.viewmodel.MyWalletViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class MyWalletActivity extends BaseRefreshActivity<ActivityMyWalletBinding,MyWalletAdapter.WalletViewHolder,MyWalletResponse.MyWalletList> implements WithdrawItemCallback {
    private MyWalletViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.text_main_color, StatusBarContentColor.WHITE);
        setToolBarTitle(getString(R.string.my_wallet));
    }

    public static void goOpenIn(Context context){
        Intent intent = new Intent(context,MyWalletActivity.class);
        context.startActivity(intent);
    }

    /**
     * @return
     */
    @Override
    protected BaseQuickAdapter<MyWalletResponse.MyWalletList, MyWalletAdapter.WalletViewHolder> initAdapter() {
        MyWalletAdapter adapter = new MyWalletAdapter(R.layout.layout_item_my_wallet, new ArrayList<>());
        adapter.setCallback(MyWalletActivity.this);
        return adapter;
    }

    @Override
    protected void onRefreshCallback(int page, int size) {
            getWalletData();
    }

    @Override
    protected void onLoadMoreCallback(int page, int size) {

    }

    private void getWalletData() {
        if (viewModel == null){
            viewModel = new MyWalletViewModel(activity.getApplication(),TAG);
        }
        viewModel.getMyWallet("103")
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<MyWalletResponse>() {
                    @Override
                    public void accept(MyWalletResponse myWalletResponse) throws Exception {
                        loadDataComplete(myWalletResponse.getResult());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG,throwable.getMessage());
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sure,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.sure).setTitle(R.string.bill);
        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sure){
            BillActivity.go(activity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void withdraw() {
        WithdrawActivity.go(activity);
    }
}
