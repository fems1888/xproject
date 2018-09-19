package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.adapter.UnReceiveAirDropAdapter;
import com.qbao.xproject.app.base.BaseRefreshActivity;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityReceiveAirDropBinding;
import com.qbao.xproject.app.entity.NextAirDropTimeEntity;
import com.qbao.xproject.app.entity.UnReceiveAirDropEntity;
import com.qbao.xproject.app.interf.StatusBarContentColor;
import com.qbao.xproject.app.manager.AccountManager;
import com.qbao.xproject.app.utility.RxSchedulers;
import com.qbao.xproject.app.utility.StatusBarUtils;
import com.qbao.xproject.app.viewmodel.ReceiveAirDropViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @author Created by jackieyao on 2018/9/17 下午1:21
 */

public class ReceiveAirDropActivity extends BaseRxActivity<ActivityReceiveAirDropBinding> {
    private ReceiveAirDropViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_air_drop);
        setToolBarTitle(getString(R.string.text_space));
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.white, StatusBarContentColor.GRAY);
    }
    public static void go(Context context){
        Intent intent = new Intent(context,ReceiveAirDropActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {
        super.initData();
        getUnReceiveAirDrop();
    }

    private void getUnReceiveAirDrop() {
        List<UnReceiveAirDropEntity.UnReceiveAirDrop> list = new ArrayList<>();
        for (int i = 0 ; i < 2 ;i++){
            UnReceiveAirDropEntity.UnReceiveAirDrop unReceiveAirDrop = new UnReceiveAirDropEntity.UnReceiveAirDrop();
            list.add(unReceiveAirDrop);
        }

        UnReceiveAirDropAdapter airDropAdapter = new UnReceiveAirDropAdapter(R.layout.layout_item_unreceive_air_drop,list);

        bindingView.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        airDropAdapter.bindToRecyclerView(bindingView.recyclerView);

        viewModel = new ReceiveAirDropViewModel(activity.getApplication(),TAG);
        viewModel.findAllUnReceivedAirDrop("103")
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<UnReceiveAirDropEntity>() {
                    @Override
                    public void accept(UnReceiveAirDropEntity unReceiveAirDropEntity) throws Exception {
                        if (unReceiveAirDropEntity.getResult().size()>0){

                            airDropAdapter.setNewData(unReceiveAirDropEntity.getResult());
                        }else {
                            bindingView.linearHasAirdrop.setVisibility(View.GONE);
                            bindingView.linearNoAirdrop.setVisibility(View.VISIBLE);
                            getNextAirDropTime();

                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private void getNextAirDropTime() {
        viewModel.getNextAirDropTime().compose(RxSchedulers.io_main())
                .subscribe(new Consumer<NextAirDropTimeEntity>() {
                    @Override
                    public void accept(NextAirDropTimeEntity nextAirDropTimeEntity) throws Exception {
                        bindingView.textNextTime.setText(nextAirDropTimeEntity.getBeginTime());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("===",throwable.getMessage());
                        bindingView.textNextTime.setVisibility(View.GONE);
                        bindingView.textNext.setVisibility(View.GONE);
                    }
                });
    }

}
