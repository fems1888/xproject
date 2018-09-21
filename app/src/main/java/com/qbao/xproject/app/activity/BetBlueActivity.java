package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.jakewharton.rxbinding2.view.RxView;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.entity.BetNextResponseEntity;
import com.qbao.xproject.app.entity.MyWalletResponse;
import com.qbao.xproject.app.manager.AccountManager;
import com.qbao.xproject.app.request_body.BetNextRequest;
import com.qbao.xproject.app.utility.CommonUtility;
import com.qbao.xproject.app.utility.RxSchedulers;
import com.qbao.xproject.app.utility.StatusBarUtils;
import com.qbao.xproject.app.adapter.BetBlueBallAdapter;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityBetRedBinding;
import com.qbao.xproject.app.entity.BetResponseEntity;
import com.qbao.xproject.app.fragment.dialog_fragment.BetSureDialogFragment;
import com.qbao.xproject.app.viewmodel.ArenaViewModel;
import com.qbao.xproject.app.viewmodel.MyWalletViewModel;
import com.qbao.xproject.app.widget.UITipDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * 投注红球界面
 *
 * @author Created by jackieyao on 2018/9/12 下午6:21
 */

public class BetBlueActivity extends BaseRxActivity<ActivityBetRedBinding> implements View.OnClickListener {
    private BetBlueBallAdapter mAdapter;
    public static final String BALL_ONE = "Ball_one";
    public static final String BALL_TWO = "Ball_two";
    public static final String BALL_THREE = "Ball_three";
    public static final String GAMBLE_NO = "GambleNo";
    public static final String NEXT_GAMBLE_ID = "mNextgambleId";
    private int mNextgambleId;
    private String mGambleNo;
    private String mRedBallOne;
    private String mRedBallTwo;
    private String mRedBallThree;
    private boolean mHasChose;
    /**
     * 是否博彩成功
     */
    private boolean mIfBetSuccess;
    private MyWalletViewModel walletViewModel;
    private UITipDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_red);
        StatusBarUtils.setWindowStatusBarColor(activity, R.color.bar_one_color);
        setToolBarTitle(getString(R.string.bet));
    }

    public static void goBetRedActivity(Context context, String redBallOne, String redBallTwo, String redBallThree, String mGambleNo, int mNextgambleId) {
        Intent intent = new Intent(context, BetBlueActivity.class);
        intent.putExtra(BALL_ONE, redBallOne);
        intent.putExtra(BALL_TWO, redBallTwo);
        intent.putExtra(BALL_THREE, redBallThree);
        intent.putExtra(GAMBLE_NO, mGambleNo);
        intent.putExtra(NEXT_GAMBLE_ID, mNextgambleId);
        context.startActivity(intent);
    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        mRedBallOne = getIntent().getStringExtra(BALL_ONE);
        mRedBallTwo = getIntent().getStringExtra(BALL_TWO);
        mRedBallThree = getIntent().getStringExtra(BALL_THREE);
        mGambleNo = getIntent().getStringExtra(GAMBLE_NO);
        mNextgambleId = getIntent().getIntExtra(NEXT_GAMBLE_ID,-1);
    }

    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(bindingView.buttonSure).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        getMyWallet();

                    }
                });
    }

    private void getMyWallet() {
        dialog = new UITipDialog.CustomBuilder(activity,false).setContent(R.layout.view_loading).create();
        dialog.show();
        if (walletViewModel == null) {

            walletViewModel = new MyWalletViewModel(activity.getApplication(), TAG);
        }
        walletViewModel.getMyWallet()
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<MyWalletResponse>() {
                    @Override
                    public void accept(MyWalletResponse myWalletResponse) throws Exception {
                        List<MyWalletResponse.MyWalletList> result = myWalletResponse.getResult();
                        double mineAmount = 0;
                        for (MyWalletResponse.MyWalletList list : result) {
                            if (!list.getUnitName().equalsIgnoreCase("eth")) {
                                mineAmount += list.getAmount();
                            }
                        }
                        if (mineAmount<100){
                            mIfBetSuccess = false;
                        }else {
                            mIfBetSuccess = true;
                        }

                        BetSureDialogFragment dialogFragment = new BetSureDialogFragment(mGambleNo);
                        dialogFragment.setClickListener(BetBlueActivity.this);
                        dialogFragment.show(getSupportFragmentManager(), dialogFragment.getClass().getCanonicalName());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, throwable.getMessage());
                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();
        bindingView.textBlue.setVisibility(View.INVISIBLE);
        bindingView.buttonSure.setEnabled(false);
        String[] array = getResources().getStringArray(R.array.red_ball);
        int length = array.length;
        List<BetResponseEntity> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            BetResponseEntity entity = new BetResponseEntity();
            entity.setNum(array[i]);
            list.add(entity);
        }
        bindingView.recyclerRed.setHasFixedSize(true);
        mAdapter = new BetBlueBallAdapter(R.layout.layout_item_bet, list);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(activity);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setAlignItems(AlignItems.BASELINE);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setAutoMeasureEnabled(true);
        bindingView.recyclerRed.setLayoutManager(layoutManager);
        mAdapter.bindToRecyclerView(bindingView.recyclerRed);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            bindingView.textBlue.setText(list.get(position).getNum());
            refresh(list, position);
        });
    }

    private void refresh(List<BetResponseEntity> list, int position) {
        Observable.create(new ObservableOnSubscribe<List<BetResponseEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BetResponseEntity>> e) throws Exception {
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    if (position == i) {
                        if (list.get(i).isChosed()) {
                            mHasChose = false;
                            list.get(i).setChosed(false);
                        } else {
                            mHasChose = true;
                            list.get(i).setChosed(true);
                        }
                    } else {
                        list.get(i).setChosed(false);
                    }
                }
                e.onNext(list);
            }
        }).compose(RxSchedulers.io_main()).subscribe(new Consumer<List<BetResponseEntity>>() {
            @Override
            public void accept(List<BetResponseEntity> betResponseEntities) throws Exception {
                mAdapter.setNewData(list);
                if (mHasChose) {
                    bindingView.textBlue.setVisibility(View.VISIBLE);
                    bindingView.buttonSure.setEnabled(true);
                } else {
                    bindingView.buttonSure.setEnabled(false);
                    bindingView.textBlue.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (mIfBetSuccess){
            betRequest();

        }else {
            dialog.dismiss();
           PayFailActivity.goPayFailActivity(activity,getString(R.string.no_balance));
        }
    }

    private void betRequest() {
        BetNextRequest request = new BetNextRequest();
        request.setGambleId(mNextgambleId);
        request.setAccountNo(AccountManager.getInstance().getAccountEntity().getAccountNo());
        request.setRedBallFirst(mRedBallOne);
        request.setRedBallSecond(mRedBallTwo);
        request.setRedBallThird(mRedBallThree);
        request.setBlueBallEnd(bindingView.textBlue.getText().toString());
        ArenaViewModel viewModel = new ArenaViewModel(activity.getApplication(), TAG);
        viewModel.betNextGamble(request)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<BetNextResponseEntity>() {
                    @Override
                    public void accept(BetNextResponseEntity betNextResponseEntity) throws Exception {
                        dialog.dismiss();
                        BetResultActivity.goBetResultActivity(activity,mRedBallOne,mRedBallTwo,mRedBallThree,bindingView.textBlue.getText().toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dialog.dismiss();
                        PayFailActivity.goPayFailActivity(activity,throwable.getMessage());
                    }
                });
    }
}
