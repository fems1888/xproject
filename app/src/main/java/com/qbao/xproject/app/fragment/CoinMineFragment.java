package com.qbao.xproject.app.fragment;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
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
import com.qbao.xproject.app.entity.MyWalletResponse;
import com.qbao.xproject.app.entity.ReceiveMineEntity;
import com.qbao.xproject.app.entity.UnReceiveMineEntity;
import com.qbao.xproject.app.http.XProjectServiceApi;
import com.qbao.xproject.app.manager.AccessTokenManager;
import com.qbao.xproject.app.manager.AccountManager;
import com.qbao.xproject.app.manager.RxBusManager;
import com.qbao.xproject.app.request_body.ReceiveMineRequest;
import com.qbao.xproject.app.request_body.UserLoginRequest;
import com.qbao.xproject.app.utility.CommonUtility;
import com.qbao.xproject.app.utility.RxBus;
import com.qbao.xproject.app.utility.RxSchedulers;
import com.qbao.xproject.app.viewmodel.MineViewModel;
import com.qbao.xproject.app.viewmodel.MyWalletViewModel;
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
    private List<UnReceiveMineEntity> mineList;
    private ObjectAnimator objectAnimator;
    private ObjectAnimator objectAnimator1;
    private ObjectAnimator objectAnimator2;
    private ObjectAnimator objectAnimator3;
    private ObjectAnimator objectAnimator4;
    private ObjectAnimator objectAnimator5;
    private ObjectAnimator objectAnimator6;
    private ObjectAnimator objectAnimator7;
    private ObjectAnimator objectAnimator8;
    private ObjectAnimator objectAnimator9;
    private ObjectAnimator objectAnimator10;
    private ObjectAnimator objectAnimator11;
    private ObjectAnimator objectAnimator12;
    private PropertyValuesHolder holderX;
    private PropertyValuesHolder holderY;
    private MyWalletViewModel walletViewModel;
    private double mineAmount = 0;
    private double mSpeedFactor = 0;
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
                        AccelerateActivity.goAccelerateActivity(activity);
                    }
                });

        RxView.clicks(bindingView.appCompatImageView1).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        receiveMine(bindingView.appCompatImageView1);
                    }
                });
        RxView.clicks(bindingView.appCompatImageView2).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        receiveMine(bindingView.appCompatImageView2);
                    }
                });
        RxView.clicks(bindingView.appCompatImageView3).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        receiveMine(bindingView.appCompatImageView3);
                    }
                });
        RxView.clicks(bindingView.appCompatImageView4).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        receiveMine(bindingView.appCompatImageView4);
                    }
                });
        RxView.clicks(bindingView.appCompatImageView5).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        receiveMine(bindingView.appCompatImageView5);
                    }
                });
        RxView.clicks(bindingView.appCompatImageView6).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        receiveMine(bindingView.appCompatImageView6);
                    }
                });
        RxView.clicks(bindingView.appCompatImageView7).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        receiveMine(bindingView.appCompatImageView7);
                    }
                });
        RxView.clicks(bindingView.appCompatImageView8).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        receiveMine(bindingView.appCompatImageView8);
                    }
                });
        RxView.clicks(bindingView.appCompatImageView9).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        receiveMine(bindingView.appCompatImageView9);
                    }
                });
        RxView.clicks(bindingView.appCompatImageView10).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        receiveMine(bindingView.appCompatImageView10);
                    }
                });
        RxView.clicks(bindingView.appCompatImageView11).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        receiveMine(bindingView.appCompatImageView11);
                    }
                });
        RxView.clicks(bindingView.appCompatImageView12).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        receiveMine(bindingView.appCompatImageView12);
                    }
                });
    }

    private void receiveMine(AppCompatImageView appCompatImageView) {
        Object tag = appCompatImageView.getTag();
        if (tag !=null){
            UnReceiveMineEntity entity = (UnReceiveMineEntity) tag;
            ReceiveMineRequest request = new ReceiveMineRequest();
            request.setId(entity.getId());
            viewModel.receiveMine(request)
                    .compose(RxSchedulers.io_main())
                    .subscribe(new Consumer<ReceiveMineEntity>() {
                        @Override
                        public void accept(ReceiveMineEntity receiveMineEntity) throws Exception {
                            setVisibility(appCompatImageView,false);
                            UnReceiveMineEntity unReceiveMineEntity = receiveMineEntity.getResult();
                            mineAmount+=unReceiveMineEntity.getAmount();
                            bindingView.textMineAmount.setText(CommonUtility.getFormatDoubleTwo(mineAmount));
                            if (receiveMineEntity.getNextMineCreateTime() <= receiveMineEntity.getSystemTime()){

                                findAllUnReceivedMine();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    });
        }
    }

    @Override
    protected void initData() {
        super.initData();
        if (viewModel == null){
            viewModel = new MineViewModel(activity.getApplication(),TAG);
        }
        setAllClickStatus(false);
        getMineAmount();
        viewModel.findAllSpeedLog().compose(RxSchedulers.io_main())
                .subscribe(new Consumer<List<AccelerateFactorEntity>>() {
                    @Override
                    public void accept(List<AccelerateFactorEntity> entities) throws Exception {
                        for (AccelerateFactorEntity entity : entities){
                            mSpeedFactor+=entity.getSpeedAdd();
                        }
                        bindingView.textFactor.setText(CommonUtility.formatString(getString(R.string.velocity_factor)," ",CommonUtility.getFormatDoubleTwo(mSpeedFactor)));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        if (holderX == null){
            holderX = PropertyValuesHolder.ofFloat("scaleX", 1,0.9f);
            holderY = PropertyValuesHolder.ofFloat("scaleY", 1,0.9f);
        }
        if (objectAnimator == null){

            objectAnimator = ObjectAnimator.ofPropertyValuesHolder(bindingView.appCompatImageView,holderX,holderY);
            objectAnimator.setDuration(800).setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator.setInterpolator(new LinearInterpolator());

            objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(bindingView.appCompatImageView1,holderX,holderY);
            objectAnimator1.setDuration(800).setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator1.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator1.setInterpolator(new LinearInterpolator());

            objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(bindingView.appCompatImageView2,holderX,holderY);
            objectAnimator2.setDuration(800).setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator2.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator2.setInterpolator(new LinearInterpolator());

            objectAnimator3 = ObjectAnimator.ofPropertyValuesHolder(bindingView.appCompatImageView3,holderX,holderY);
            objectAnimator3.setDuration(800).setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator3.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator3.setInterpolator(new LinearInterpolator());

            objectAnimator4 = ObjectAnimator.ofPropertyValuesHolder(bindingView.appCompatImageView4,holderX,holderY);
            objectAnimator4.setDuration(800).setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator4.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator4.setInterpolator(new LinearInterpolator());

            objectAnimator5 = ObjectAnimator.ofPropertyValuesHolder(bindingView.appCompatImageView5,holderX,holderY);
            objectAnimator5.setDuration(800).setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator5.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator5.setInterpolator(new LinearInterpolator());

            objectAnimator6 = ObjectAnimator.ofPropertyValuesHolder(bindingView.appCompatImageView6,holderX,holderY);
            objectAnimator6.setDuration(800).setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator6.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator6.setInterpolator(new LinearInterpolator());

            objectAnimator7 = ObjectAnimator.ofPropertyValuesHolder(bindingView.appCompatImageView7,holderX,holderY);
            objectAnimator7.setDuration(800).setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator7.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator7.setInterpolator(new LinearInterpolator());


            objectAnimator8 = ObjectAnimator.ofPropertyValuesHolder(bindingView.appCompatImageView8,holderX,holderY);
            objectAnimator8.setDuration(800).setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator8.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator8.setInterpolator(new LinearInterpolator());

            objectAnimator9 = ObjectAnimator.ofPropertyValuesHolder(bindingView.appCompatImageView9,holderX,holderY);
            objectAnimator9.setDuration(800).setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator9.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator9.setInterpolator(new LinearInterpolator());

            objectAnimator10 = ObjectAnimator.ofPropertyValuesHolder(bindingView.appCompatImageView10,holderX,holderY);
            objectAnimator10.setDuration(800).setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator10.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator10.setInterpolator(new LinearInterpolator());

            objectAnimator11 = ObjectAnimator.ofPropertyValuesHolder(bindingView.appCompatImageView11,holderX,holderY);
            objectAnimator11.setDuration(800).setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator11.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator11.setInterpolator(new LinearInterpolator());

            objectAnimator12 = ObjectAnimator.ofPropertyValuesHolder(bindingView.appCompatImageView12,holderX,holderY);
            objectAnimator12.setDuration(800).setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator12.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator12.setInterpolator(new LinearInterpolator());

        }
        objectAnimator.start();

        findAllUnReceivedMine();

        RxBus.getDefault().toFlowable(RxBusManager.EventSpeedFactor.class)
                .toObservable()
                .compose(RxSchedulers.io_main())
                .subscribe(eventUnReadCount -> {
                    initSpeed(eventUnReadCount.getCount());
                }, Throwable::printStackTrace);
    }

    private void initSpeed(double count) {
        mSpeedFactor+=count;
        bindingView.textFactor.setText(CommonUtility.formatString(getString(R.string.velocity_factor)," ",CommonUtility.getFormatDoubleTwo(mSpeedFactor)));
    }

    private void findAllUnReceivedMine() {
        viewModel.findAllUnReceivedMine()
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<List<UnReceiveMineEntity>>() {
                    @Override
                    public void accept(List<UnReceiveMineEntity> unReceiveMineEntities) throws Exception {
                        mineList = unReceiveMineEntities;
                        setMineStatus();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private void getMineAmount() {
        if (walletViewModel == null){

            walletViewModel = new MyWalletViewModel(activity.getApplication(),TAG);
        }
        walletViewModel.getMyWallet()
                .compose(RxSchedulers.io_main())
            .subscribe(new Consumer<MyWalletResponse>() {
        @Override
        public void accept(MyWalletResponse myWalletResponse) throws Exception {
            List<MyWalletResponse.MyWalletList> result = myWalletResponse.getResult();

            for (MyWalletResponse.MyWalletList list : result){
                if (!list.getUnitName().equalsIgnoreCase("eth")){
                    mineAmount+=list.getAmount();
                }
            }
            bindingView.textMineAmount.setText(CommonUtility.getFormatDoubleTwo(mineAmount));
        }
    }, new Consumer<Throwable>() {
        @Override
        public void accept(Throwable throwable) throws Exception {
            Log.e(TAG,throwable.getMessage());
        }
    });
    }

    /**
     *
     */
    private void setMineStatus() {
        if (mineList.size()==1){
            setVisibility(bindingView.appCompatImageView1,true);
            bindingView.appCompatImageView1.setTag(mineList.get(0));
            if (!objectAnimator1.isStarted()){
                objectAnimator1.start();
            }
            setVisibility(bindingView.appCompatImageView2,false);
            setVisibility(bindingView.appCompatImageView3,false);
            setVisibility(bindingView.appCompatImageView4,false);
            setVisibility(bindingView.appCompatImageView5,false);
            setVisibility(bindingView.appCompatImageView6,false);
            setVisibility(bindingView.appCompatImageView7,false);
            setVisibility(bindingView.appCompatImageView8,false);
            setVisibility(bindingView.appCompatImageView9,false);
            setVisibility(bindingView.appCompatImageView10,false);
            setVisibility(bindingView.appCompatImageView11,false);
            setVisibility(bindingView.appCompatImageView12,false);
        }else if (mineList.size()==2){
            if (!objectAnimator1.isStarted()){
                objectAnimator1.start();
            }
            if (!objectAnimator2.isStarted()){
                objectAnimator2.start();
            }
            setVisibility(bindingView.appCompatImageView1,true);
            bindingView.appCompatImageView1.setTag(mineList.get(0));
            setVisibility(bindingView.appCompatImageView2,true);
            bindingView.appCompatImageView2.setTag(mineList.get(1));
            setVisibility(bindingView.appCompatImageView3,false);
            setVisibility(bindingView.appCompatImageView4,false);
            setVisibility(bindingView.appCompatImageView5,false);
            setVisibility(bindingView.appCompatImageView6,false);
            setVisibility(bindingView.appCompatImageView7,false);
            setVisibility(bindingView.appCompatImageView8,false);
            setVisibility(bindingView.appCompatImageView9,false);
            setVisibility(bindingView.appCompatImageView10,false);
            setVisibility(bindingView.appCompatImageView11,false);
            setVisibility(bindingView.appCompatImageView12,false);
        }else if (mineList.size()==3){
            if (!objectAnimator1.isStarted()){
                objectAnimator1.start();
            }
            if (!objectAnimator2.isStarted()){
                objectAnimator2.start();
            }if (!objectAnimator3.isStarted()){
                objectAnimator3.start();
            }
            setVisibility(bindingView.appCompatImageView1,true);
            bindingView.appCompatImageView1.setTag(mineList.get(0));
            setVisibility(bindingView.appCompatImageView2,true);
            bindingView.appCompatImageView2.setTag(mineList.get(1));
            setVisibility(bindingView.appCompatImageView3,true);
            bindingView.appCompatImageView3.setTag(mineList.get(2));
            setVisibility(bindingView.appCompatImageView4,false);
            setVisibility(bindingView.appCompatImageView5,false);
            setVisibility(bindingView.appCompatImageView6,false);
            setVisibility(bindingView.appCompatImageView7,false);
            setVisibility(bindingView.appCompatImageView8,false);
            setVisibility(bindingView.appCompatImageView9,false);
            setVisibility(bindingView.appCompatImageView10,false);
            setVisibility(bindingView.appCompatImageView11,false);
            setVisibility(bindingView.appCompatImageView12,false);
        }else if (mineList.size()==4){
            if (!objectAnimator1.isStarted()){
                objectAnimator1.start();
            }
            if (!objectAnimator2.isStarted()){
                objectAnimator2.start();
            }if (!objectAnimator3.isStarted()){
                objectAnimator3.start();
            }if (!objectAnimator4.isStarted()){
                objectAnimator4.start();
            }
            setVisibility(bindingView.appCompatImageView1,true);
            bindingView.appCompatImageView1.setTag(mineList.get(0));
            setVisibility(bindingView.appCompatImageView2,true);
            bindingView.appCompatImageView2.setTag(mineList.get(1));
            setVisibility(bindingView.appCompatImageView3,true);
            bindingView.appCompatImageView3.setTag(mineList.get(2));
            setVisibility(bindingView.appCompatImageView4,true);
            bindingView.appCompatImageView4.setTag(mineList.get(3));
            setVisibility(bindingView.appCompatImageView5,false);
            setVisibility(bindingView.appCompatImageView6,false);
            setVisibility(bindingView.appCompatImageView7,false);
            setVisibility(bindingView.appCompatImageView8,false);
            setVisibility(bindingView.appCompatImageView9,false);
            setVisibility(bindingView.appCompatImageView10,false);
            setVisibility(bindingView.appCompatImageView11,false);
            setVisibility(bindingView.appCompatImageView12,false);
        }
        else if (mineList.size()==5){
            if (!objectAnimator1.isStarted()){
                objectAnimator1.start();
            }
            if (!objectAnimator2.isStarted()){
                objectAnimator2.start();
            }if (!objectAnimator3.isStarted()){
                objectAnimator3.start();
            }if (!objectAnimator4.isStarted()){
                objectAnimator4.start();
            }if (!objectAnimator5.isStarted()){
                objectAnimator5.start();
            }
            setVisibility(bindingView.appCompatImageView1,true);
            bindingView.appCompatImageView1.setTag(mineList.get(0));
            setVisibility(bindingView.appCompatImageView2,true);
            bindingView.appCompatImageView2.setTag(mineList.get(1));
            setVisibility(bindingView.appCompatImageView3,true);
            bindingView.appCompatImageView3.setTag(mineList.get(2));
            setVisibility(bindingView.appCompatImageView4,true);
            bindingView.appCompatImageView4.setTag(mineList.get(3));
            setVisibility(bindingView.appCompatImageView5,true);
            bindingView.appCompatImageView5.setTag(mineList.get(4));
            setVisibility(bindingView.appCompatImageView6,false);
            setVisibility(bindingView.appCompatImageView7,false);
            setVisibility(bindingView.appCompatImageView8,false);
            setVisibility(bindingView.appCompatImageView9,false);
            setVisibility(bindingView.appCompatImageView10,false);
            setVisibility(bindingView.appCompatImageView11,false);
            setVisibility(bindingView.appCompatImageView12,false);
        }else if (mineList.size()==6){
            if (!objectAnimator1.isStarted()){
                objectAnimator1.start();
            }
            if (!objectAnimator2.isStarted()){
                objectAnimator2.start();
            }if (!objectAnimator3.isStarted()){
                objectAnimator3.start();
            }if (!objectAnimator4.isStarted()){
                objectAnimator4.start();
            }if (!objectAnimator5.isStarted()){
                objectAnimator5.start();
            }if (!objectAnimator6.isStarted()){
                objectAnimator6.start();
            }

            setVisibility(bindingView.appCompatImageView1,true);
            bindingView.appCompatImageView1.setTag(mineList.get(0));
            setVisibility(bindingView.appCompatImageView2,true);
            bindingView.appCompatImageView2.setTag(mineList.get(1));
            setVisibility(bindingView.appCompatImageView3,true);
            bindingView.appCompatImageView3.setTag(mineList.get(2));
            setVisibility(bindingView.appCompatImageView4,true);
            bindingView.appCompatImageView4.setTag(mineList.get(3));
            setVisibility(bindingView.appCompatImageView5,true);
            bindingView.appCompatImageView5.setTag(mineList.get(4));

            setVisibility(bindingView.appCompatImageView6,true);
            bindingView.appCompatImageView6.setTag(mineList.get(5));
            setVisibility(bindingView.appCompatImageView7,false);
            setVisibility(bindingView.appCompatImageView8,false);
            setVisibility(bindingView.appCompatImageView9,false);
            setVisibility(bindingView.appCompatImageView10,false);
            setVisibility(bindingView.appCompatImageView11,false);
            setVisibility(bindingView.appCompatImageView12,false);
        }else if (mineList.size()==7){
            if (!objectAnimator1.isStarted()){
                objectAnimator1.start();
            }
            if (!objectAnimator2.isStarted()){
                objectAnimator2.start();
            }if (!objectAnimator3.isStarted()){
                objectAnimator3.start();
            }if (!objectAnimator4.isStarted()){
                objectAnimator4.start();
            }if (!objectAnimator5.isStarted()){
                objectAnimator5.start();
            }if (!objectAnimator6.isStarted()){
                objectAnimator6.start();
            }if (!objectAnimator7.isStarted()){
                objectAnimator7.start();
            }
            setVisibility(bindingView.appCompatImageView1,true);
            bindingView.appCompatImageView1.setTag(mineList.get(0));
            setVisibility(bindingView.appCompatImageView2,true);
            bindingView.appCompatImageView2.setTag(mineList.get(1));
            setVisibility(bindingView.appCompatImageView3,true);
            bindingView.appCompatImageView3.setTag(mineList.get(2));
            setVisibility(bindingView.appCompatImageView4,true);
            bindingView.appCompatImageView4.setTag(mineList.get(3));
            setVisibility(bindingView.appCompatImageView5,true);
            bindingView.appCompatImageView5.setTag(mineList.get(4));

            setVisibility(bindingView.appCompatImageView6,true);
            bindingView.appCompatImageView6.setTag(mineList.get(5));
            setVisibility(bindingView.appCompatImageView7,true);
            bindingView.appCompatImageView7.setTag(mineList.get(6));
            setVisibility(bindingView.appCompatImageView8,false);
            setVisibility(bindingView.appCompatImageView9,false);
            setVisibility(bindingView.appCompatImageView10,false);
            setVisibility(bindingView.appCompatImageView11,false);
            setVisibility(bindingView.appCompatImageView12,false);
        }else if (mineList.size()==8){
            if (!objectAnimator1.isStarted()){

                objectAnimator1.start();
            }
            if (!objectAnimator2.isStarted()){
                objectAnimator2.start();
            }if (!objectAnimator3.isStarted()){
                objectAnimator3.start();
            }if (!objectAnimator4.isStarted()){
                objectAnimator4.start();
            }if (!objectAnimator5.isStarted()){
                objectAnimator5.start();
            }if (!objectAnimator6.isStarted()){
                objectAnimator6.start();
            }if (!objectAnimator7.isStarted()){
                objectAnimator7.start();
            }if (!objectAnimator8.isStarted()){
                objectAnimator8.start();
            }
            setVisibility(bindingView.appCompatImageView1,true);
            bindingView.appCompatImageView1.setTag(mineList.get(0));
            setVisibility(bindingView.appCompatImageView2,true);
            bindingView.appCompatImageView2.setTag(mineList.get(1));
            setVisibility(bindingView.appCompatImageView3,true);
            bindingView.appCompatImageView3.setTag(mineList.get(2));
            setVisibility(bindingView.appCompatImageView4,true);
            bindingView.appCompatImageView4.setTag(mineList.get(3));
            setVisibility(bindingView.appCompatImageView5,true);
            bindingView.appCompatImageView5.setTag(mineList.get(4));

            setVisibility(bindingView.appCompatImageView6,true);
            bindingView.appCompatImageView6.setTag(mineList.get(5));
            setVisibility(bindingView.appCompatImageView7,true);
            bindingView.appCompatImageView7.setTag(mineList.get(6));
            setVisibility(bindingView.appCompatImageView8,true);
            bindingView.appCompatImageView8.setTag(mineList.get(7));
            setVisibility(bindingView.appCompatImageView9,false);
            setVisibility(bindingView.appCompatImageView10,false);
            setVisibility(bindingView.appCompatImageView11,false);
            setVisibility(bindingView.appCompatImageView12,false);
        }
        else if (mineList.size()==9){
            if (!objectAnimator1.isStarted()){
                objectAnimator1.start();
            }
            if (!objectAnimator2.isStarted()){
                objectAnimator2.start();
            }if (!objectAnimator3.isStarted()){
                objectAnimator3.start();
            }if (!objectAnimator4.isStarted()){
                objectAnimator4.start();
            }if (!objectAnimator5.isStarted()){
                objectAnimator5.start();
            }if (!objectAnimator6.isStarted()){
                objectAnimator6.start();
            }if (!objectAnimator7.isStarted()){
                objectAnimator7.start();
            }if (!objectAnimator8.isStarted()){
                objectAnimator8.start();
            }if (!objectAnimator9.isStarted()){
                objectAnimator9.start();
            }
            setVisibility(bindingView.appCompatImageView1,true);
            bindingView.appCompatImageView1.setTag(mineList.get(0));
            setVisibility(bindingView.appCompatImageView2,true);
            bindingView.appCompatImageView2.setTag(mineList.get(1));
            setVisibility(bindingView.appCompatImageView3,true);
            bindingView.appCompatImageView3.setTag(mineList.get(2));
            setVisibility(bindingView.appCompatImageView4,true);
            bindingView.appCompatImageView4.setTag(mineList.get(3));
            setVisibility(bindingView.appCompatImageView5,true);
            bindingView.appCompatImageView5.setTag(mineList.get(4));

            setVisibility(bindingView.appCompatImageView6,true);
            bindingView.appCompatImageView6.setTag(mineList.get(5));
            setVisibility(bindingView.appCompatImageView7,true);
            bindingView.appCompatImageView7.setTag(mineList.get(6));
            setVisibility(bindingView.appCompatImageView8,true);
            bindingView.appCompatImageView8.setTag(mineList.get(7));
            setVisibility(bindingView.appCompatImageView9,true);
            bindingView.appCompatImageView9.setTag(mineList.get(8));
            setVisibility(bindingView.appCompatImageView10,false);
            setVisibility(bindingView.appCompatImageView11,false);
            setVisibility(bindingView.appCompatImageView12,false);
        }else if (mineList.size()==10){
            if (!objectAnimator1.isStarted()){
                objectAnimator1.start();
            }
            if (!objectAnimator2.isStarted()){
                objectAnimator2.start();
            }if (!objectAnimator3.isStarted()){
                objectAnimator3.start();
            }if (!objectAnimator4.isStarted()){
                objectAnimator4.start();
            }if (!objectAnimator5.isStarted()){
                objectAnimator5.start();
            }if (!objectAnimator6.isStarted()){
                objectAnimator6.start();
            }if (!objectAnimator7.isStarted()){
                objectAnimator7.start();
            }if (!objectAnimator8.isStarted()){
                objectAnimator8.start();
            }if (!objectAnimator9.isStarted()){
                objectAnimator9.start();
            }if (!objectAnimator10.isStarted()){
                objectAnimator10.start();
            }
            setVisibility(bindingView.appCompatImageView1,true);
            bindingView.appCompatImageView1.setTag(mineList.get(0));
            setVisibility(bindingView.appCompatImageView2,true);
            bindingView.appCompatImageView2.setTag(mineList.get(1));
            setVisibility(bindingView.appCompatImageView3,true);
            bindingView.appCompatImageView3.setTag(mineList.get(2));
            setVisibility(bindingView.appCompatImageView4,true);
            bindingView.appCompatImageView4.setTag(mineList.get(3));
            setVisibility(bindingView.appCompatImageView5,true);
            bindingView.appCompatImageView5.setTag(mineList.get(4));

            setVisibility(bindingView.appCompatImageView6,true);
            bindingView.appCompatImageView6.setTag(mineList.get(5));
            setVisibility(bindingView.appCompatImageView7,true);
            bindingView.appCompatImageView7.setTag(mineList.get(6));
            setVisibility(bindingView.appCompatImageView8,true);
            bindingView.appCompatImageView8.setTag(mineList.get(7));
            setVisibility(bindingView.appCompatImageView9,true);
            bindingView.appCompatImageView9.setTag(mineList.get(8));
            setVisibility(bindingView.appCompatImageView10,true);
            bindingView.appCompatImageView10.setTag(mineList.get(9));
            setVisibility(bindingView.appCompatImageView11,false);
            setVisibility(bindingView.appCompatImageView12,false);
        }else if (mineList.size()==11){
            if (!objectAnimator1.isStarted()){
                objectAnimator1.start();
            }
            if (!objectAnimator2.isStarted()){
                objectAnimator2.start();
            }if (!objectAnimator3.isStarted()){
                objectAnimator3.start();
            }if (!objectAnimator4.isStarted()){
                objectAnimator4.start();
            }if (!objectAnimator5.isStarted()){
                objectAnimator5.start();
            }if (!objectAnimator6.isStarted()){
                objectAnimator6.start();
            }if (!objectAnimator7.isStarted()){
                objectAnimator7.start();
            }if (!objectAnimator8.isStarted()){
                objectAnimator8.start();
            }if (!objectAnimator9.isStarted()){
                objectAnimator9.start();
            }if (!objectAnimator10.isStarted()){
                objectAnimator10.start();
            }if (!objectAnimator11.isStarted()){
                objectAnimator11.start();
            }
            setVisibility(bindingView.appCompatImageView1,true);
            bindingView.appCompatImageView1.setTag(mineList.get(0));
            setVisibility(bindingView.appCompatImageView2,true);
            bindingView.appCompatImageView2.setTag(mineList.get(1));
            setVisibility(bindingView.appCompatImageView3,true);
            bindingView.appCompatImageView3.setTag(mineList.get(2));
            setVisibility(bindingView.appCompatImageView4,true);
            bindingView.appCompatImageView4.setTag(mineList.get(3));
            setVisibility(bindingView.appCompatImageView5,true);
            bindingView.appCompatImageView5.setTag(mineList.get(4));

            setVisibility(bindingView.appCompatImageView6,true);
            bindingView.appCompatImageView6.setTag(mineList.get(5));
            setVisibility(bindingView.appCompatImageView7,true);
            bindingView.appCompatImageView7.setTag(mineList.get(6));
            setVisibility(bindingView.appCompatImageView8,true);
            bindingView.appCompatImageView8.setTag(mineList.get(7));
            setVisibility(bindingView.appCompatImageView9,true);
            bindingView.appCompatImageView9.setTag(mineList.get(8));
            setVisibility(bindingView.appCompatImageView10,true);
            bindingView.appCompatImageView10.setTag(mineList.get(9));
            setVisibility(bindingView.appCompatImageView11,true);
            bindingView.appCompatImageView11.setTag(mineList.get(10));
            setVisibility(bindingView.appCompatImageView12,false);
        }else if (mineList.size()==12){
            if (!objectAnimator1.isStarted()){
                objectAnimator1.start();
            }
            if (!objectAnimator2.isStarted()){
                objectAnimator2.start();
            }if (!objectAnimator3.isStarted()){
                objectAnimator3.start();
            }if (!objectAnimator4.isStarted()){
                objectAnimator4.start();
            }if (!objectAnimator5.isStarted()){
                objectAnimator5.start();
            }if (!objectAnimator6.isStarted()){
                objectAnimator6.start();
            }if (!objectAnimator7.isStarted()){
                objectAnimator7.start();
            }if (!objectAnimator8.isStarted()){
                objectAnimator8.start();
            }if (!objectAnimator9.isStarted()){
                objectAnimator9.start();
            }if (!objectAnimator10.isStarted()){
                objectAnimator10.start();
            }if (!objectAnimator11.isStarted()){
                objectAnimator11.start();
            }if (!objectAnimator12.isStarted()){
                objectAnimator12.start();
            }
            setVisibility(bindingView.appCompatImageView1,true);
            bindingView.appCompatImageView1.setTag(mineList.get(0));
            setVisibility(bindingView.appCompatImageView2,true);
            bindingView.appCompatImageView2.setTag(mineList.get(1));
            setVisibility(bindingView.appCompatImageView3,true);
            bindingView.appCompatImageView3.setTag(mineList.get(2));
            setVisibility(bindingView.appCompatImageView4,true);
            bindingView.appCompatImageView4.setTag(mineList.get(3));
            setVisibility(bindingView.appCompatImageView5,true);
            bindingView.appCompatImageView5.setTag(mineList.get(4));

            setVisibility(bindingView.appCompatImageView6,true);
            bindingView.appCompatImageView6.setTag(mineList.get(5));
            setVisibility(bindingView.appCompatImageView7,true);
            bindingView.appCompatImageView7.setTag(mineList.get(6));
            setVisibility(bindingView.appCompatImageView8,true);
            bindingView.appCompatImageView8.setTag(mineList.get(7));
            setVisibility(bindingView.appCompatImageView9,true);
            bindingView.appCompatImageView9.setTag(mineList.get(8));
            setVisibility(bindingView.appCompatImageView10,true);
            bindingView.appCompatImageView10.setTag(mineList.get(9));
            setVisibility(bindingView.appCompatImageView11,true);
            bindingView.appCompatImageView11.setTag(mineList.get(10));
            setVisibility(bindingView.appCompatImageView12,true);
            bindingView.appCompatImageView12.setTag(mineList.get(11));
        }
    }

    private void setVisibility(AppCompatImageView view, boolean show){
        if (show){
            view.setVisibility(View.VISIBLE);
            view.setEnabled(true);
        }else {
            view.setVisibility(View.INVISIBLE);
            view.setEnabled(false);
        }
    }

    private void setAllClickStatus(boolean clickStatus){
        bindingView.appCompatImageView12.setEnabled(clickStatus);
        bindingView.appCompatImageView11.setEnabled(clickStatus);
        bindingView.appCompatImageView10.setEnabled(clickStatus);
        bindingView.appCompatImageView9.setEnabled(clickStatus);
        bindingView.appCompatImageView8.setEnabled(clickStatus);
        bindingView.appCompatImageView7.setEnabled(clickStatus);
        bindingView.appCompatImageView6.setEnabled(clickStatus);
        bindingView.appCompatImageView5.setEnabled(clickStatus);
        bindingView.appCompatImageView4.setEnabled(clickStatus);
        bindingView.appCompatImageView3.setEnabled(clickStatus);
        bindingView.appCompatImageView2.setEnabled(clickStatus);
        bindingView.appCompatImageView1.setEnabled(clickStatus);
    }

    public void stopAnima() {
//        objectAnimator.pause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(activity,"ss",Toast.LENGTH_LONG).show();
    }
}
