package com.qbao.xproject.app.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;
import com.qbao.xproject.app.BuildConfig;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.activity.AccelerateActivity;
import com.qbao.xproject.app.activity.LoginActivity;
import com.qbao.xproject.app.activity.WebViewActivity;
import com.qbao.xproject.app.base.BaseRxFragment;
import com.qbao.xproject.app.databinding.LayoutFragmentArenaBinding;
import com.qbao.xproject.app.databinding.LayoutFragmentCoinMineBinding;
import com.qbao.xproject.app.entity.AccelerateFactorEntity;
import com.qbao.xproject.app.entity.UnReceiveMineEntity;
import com.qbao.xproject.app.http.XProjectServiceApi;
import com.qbao.xproject.app.manager.AccessTokenManager;
import com.qbao.xproject.app.manager.AccountManager;
import com.qbao.xproject.app.request_body.UserLoginRequest;
import com.qbao.xproject.app.utility.CommonUtility;
import com.qbao.xproject.app.utility.RxSchedulers;
import com.qbao.xproject.app.viewmodel.MineViewModel;
import com.qbao.xproject.app.viewmodel.RefreshTokenViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Created by jackieyao on 2018/9/11 下午6:28.
 */

public class CoinMineFragment extends BaseRxFragment<LayoutFragmentCoinMineBinding> {
    private MineViewModel viewModel;
    @Override
    public int setContent() {
        return R.layout.layout_fragment_coin_mine;
    }
    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(bindingView.textAccelerate).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        AccountManager.getInstance().getAccountEntity().setAccountName("123");
                        AccountManager.getInstance().saveAccount(AccountManager.getInstance().getAccountEntity());
                        AccelerateActivity.goAccelerateActivity(activity);
                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();
        if (viewModel == null){
            viewModel = new MineViewModel(activity.getApplication(),TAG);
        }
        viewModel.findAllSpeedLog().compose(RxSchedulers.io_main())
                .subscribe(new Consumer<List<AccelerateFactorEntity>>() {
                    @Override
                    public void accept(List<AccelerateFactorEntity> entities) throws Exception {
                        double allFactor = 0;
                        for (AccelerateFactorEntity entity : entities){
                            allFactor+=entity.getSpeedAdd();
                        }
                        bindingView.textFactor.setText(CommonUtility.formatString(getString(R.string.velocity_factor)," ",CommonUtility.getFormatDoubleTwo(allFactor)));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        viewModel.findAllUnReceivedMine()
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<List<UnReceiveMineEntity>>() {
                    @Override
                    public void accept(List<UnReceiveMineEntity> unReceiveMineEntities) throws Exception {


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        ImageView imageView = new ImageView(activity);
        imageView.setBackgroundResource(R.drawable.ic_bet_red_ball);
        imageView.measure(View.MeasureSpec.makeMeasureSpec(100,View.MeasureSpec.EXACTLY),View.MeasureSpec.makeMeasureSpec(100,View.MeasureSpec.EXACTLY));
        imageView.layout(40,100,140,200);

    }

}
