package com.qbao.xproject.app.fragment;

import android.util.Log;
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
import com.qbao.xproject.app.http.XProjectServiceApi;
import com.qbao.xproject.app.manager.AccessTokenManager;
import com.qbao.xproject.app.request_body.UserLoginRequest;
import com.qbao.xproject.app.utility.RxSchedulers;
import com.qbao.xproject.app.viewmodel.RefreshTokenViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Created by jackieyao on 2018/9/11 下午6:28.
 */

public class CoinMineFragment extends BaseRxFragment<LayoutFragmentCoinMineBinding> {
    @Override
    public int setContent() {
        return R.layout.layout_fragment_coin_mine;
    }
    private RefreshTokenViewModel viewModel;
    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(bindingView.text).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
//
//                        refreshToken();
                        UserLoginRequest request = new UserLoginRequest();
                        request.setPhone("");
                        viewModel.refreshToken(request)
                                .compose(RxSchedulers.io_main())
                                .subscribe(new Consumer<Object>() {
                                    @Override
                                    public void accept(Object o) throws Exception {
                                        Log.e("====>>22",o.toString());
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Toast.makeText(activity,"error22",Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                });
        RxView.clicks(bindingView.textAccelerate).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        AccelerateActivity.goAccelerateActivity(activity);
                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();
        viewModel = new RefreshTokenViewModel(activity.getApplication(),TAG);

    }

//    private void refreshToken() {
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.URL_API_BASE)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        XProjectServiceApi api = retrofit.create(XProjectServiceApi.class);
//        api.refreshToken(AccessTokenManager.getInstance().getAccessToken())
//                .compose(RxSchedulers.io_main())
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object o) throws Exception {
//                        Log.e("====>>",o.toString());
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Toast.makeText(activity,"error",Toast.LENGTH_LONG).show();
//                    }
//                });
//    }
}
