package com.qbao.xproject.app.fragment;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.activity.BetRedActivity;
import com.qbao.xproject.app.activity.BetResultActivity;
import com.qbao.xproject.app.activity.LoginActivity;
import com.qbao.xproject.app.activity.WebViewActivity;
import com.qbao.xproject.app.base.BaseRxFragment;
import com.qbao.xproject.app.databinding.LayoutFragmentArenaBinding;
import com.qbao.xproject.app.entity.CurrentGambleResult;
import com.qbao.xproject.app.entity.JoinGambleResponseEntity;
import com.qbao.xproject.app.entity.NextGambleResponseEntity;
import com.qbao.xproject.app.manager.AccessTokenManager;
import com.qbao.xproject.app.manager.AccountManager;
import com.qbao.xproject.app.manager.Constants;
import com.qbao.xproject.app.utility.CommonUtility;
import com.qbao.xproject.app.utility.MaterialDialogUtility;
import com.qbao.xproject.app.utility.RxSchedulers;
import com.qbao.xproject.app.utility.XProjectUtil;
import com.qbao.xproject.app.viewmodel.ArenaViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author Created by jackieyao on 2018/9/11 下午6:28.
 */

public class ArenaFragment extends BaseRxFragment<LayoutFragmentArenaBinding> {
    private ArenaViewModel viewModel;
    private CurrentGambleResult mCurrentGambleResult;
    private List<JoinGambleResponseEntity> mJoinGambleResult;
    /**
     * 当天是否中奖
     */
    private boolean mCurrentWinner;

    private NextGambleResponseEntity mNextGambleResponseEntity;
    @Override
    public int setContent() {
        return R.layout.layout_fragment_arena;
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(bindingView.textNext).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        XProjectUtil.eventReport(activity,getString(R.string.event_id_1035));
                        //已投注下一期，点击【下一期】进入投注结果页面
                        if (mNextGambleResponseEntity!=null&&mNextGambleResponseEntity.getGambleJoinList().size()>0){
                            BetResultActivity.goBetResultActivity(activity,mNextGambleResponseEntity.getGambleJoinList().get(0).getRedBallFirst(),mNextGambleResponseEntity.getGambleJoinList().get(0).getRedBallSecond()
                                    ,mNextGambleResponseEntity.getGambleJoinList().get(0).getRedBallThird(),mNextGambleResponseEntity.getGambleJoinList().get(0).getBlueBallEnd());
//                            BetRedActivity.goBetActivity(activity,String.valueOf(mCurrentGambleResult.getGambleNo()),mNextGambleResponseEntity.getId());
                        }else if (mNextGambleResponseEntity!=null&&mNextGambleResponseEntity.getGambleJoinList().size()==0){
                            //进入投注第一个页面

                            BetRedActivity.goBetActivity(activity,String.valueOf(mCurrentGambleResult.getGambleNo()),mNextGambleResponseEntity.getId());
                        }
                    }
                });
        RxView.clicks(bindingView.textRule).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        XProjectUtil.eventReport(activity,getString(R.string.event_id_1034));
                        WebViewActivity.goOpenIn(activity,Constants.getArenaRuleUrl());
                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();
        if (viewModel == null){
            viewModel = new ArenaViewModel(activity.getApplication(),TAG);
        }
            getCurrentGambleResult();
            getNextGambleInfo();

    }

    private void getNextGambleInfo() {
        viewModel.getNextGambleInfo()
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<NextGambleResponseEntity>() {
                    @Override
                    public void accept(NextGambleResponseEntity nextGambleResponseEntity) throws Exception {
                        mNextGambleResponseEntity = nextGambleResponseEntity;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private void getCurrentGambleResult() {
        viewModel.getCurrentGambleResult()
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<CurrentGambleResult>() {
                    @Override
                    public void accept(CurrentGambleResult currentGambleResult) throws Exception {
                        mCurrentGambleResult = currentGambleResult;
                        bindingView.textGambleNo.setText(String.format(getString(R.string.gamble_no),currentGambleResult.getGambleNo()));
                       fillData(true);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    public CurrentGambleResult getmCurrentGambleResult() {
        return mCurrentGambleResult;
    }

    public NextGambleResponseEntity getmNextGambleResponseEntity() {
        return mNextGambleResponseEntity;
    }

    private void fillData(boolean requestJoin) {

        if (TextUtils.isEmpty(mCurrentGambleResult.getRedBallFirst())){
            bindingView.textRedOne.setText("?");
        }else {
            bindingView.textRedOne.setText(mCurrentGambleResult.getRedBallFirst());
        }

        if (TextUtils.isEmpty(mCurrentGambleResult.getRedBallSecond())){
            bindingView.textRedTwo.setText("?");
        }else {
            bindingView.textRedTwo.setText(mCurrentGambleResult.getRedBallSecond());
        }

        if (TextUtils.isEmpty(mCurrentGambleResult.getRedBallThird())){
            bindingView.textRedThree.setText("?");
        }else {
            bindingView.textRedThree.setText(mCurrentGambleResult.getRedBallThird());
        }

        if (TextUtils.isEmpty(mCurrentGambleResult.getBlueBallEnd())){
            bindingView.textBlueOne.setText("?");
        }else {
            bindingView.textBlueOne.setText(mCurrentGambleResult.getBlueBallEnd());
        }
        //当天已经开奖完毕
        if (mCurrentGambleResult.isAwarded()){
            bindingView.textTipOne.setVisibility(View.GONE);
            bindingView.textTipTwo.setText(getString(R.string.detail_see_two));
        }
        if (requestJoin){

            getJoinGambleResult();
        }
    }

    private void getJoinGambleResult() {
        viewModel.getGambleJoinByGambleId(mCurrentGambleResult.getId())

                .flatMap(joinGambleResponseEntities ->
                        {
                            mJoinGambleResult = joinGambleResponseEntities;
                            for (JoinGambleResponseEntity entity: joinGambleResponseEntities){
                                if (entity.isWinner()){
                                    mCurrentWinner = true;
                                    break;
                                }
                            }
                          return   Observable.just(true);
                        })
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        fillDataJoin();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private void fillDataJoin() {
        //当天已经开奖完毕
        if (mCurrentGambleResult.isAwarded()){
            //当天已开奖，已投注，中奖
            if (mJoinGambleResult.size()>0&&mCurrentWinner){
              if (CommonUtility.SharedPreferencesUtility.getInt(activity, Constants.GAMBLE_ID,-1)!=mCurrentGambleResult.getId()){
                  MaterialDialogUtility.showWinPriceDialog(activity,mJoinGambleResult.get(0));
                  CommonUtility.SharedPreferencesUtility.put(activity,Constants.GAMBLE_ID,mCurrentGambleResult.getId());
              }
              showMyWinResult();
                String awardType = mJoinGambleResult.get(0).getAwardType(activity);
                SpannableString spannableString = new SpannableString(awardType);
                try {
                        String str1 = mJoinGambleResult.get(0).getAwardType(activity);
                        String str2 = CommonUtility.formatString(CommonUtility.getFormatDoubleTwo(mJoinGambleResult.get(0).getRewardAmount()),mJoinGambleResult.get(0).getRewardUnitName());

                        String format = String.format(getString(R.string.you_get_award), str1, str2);
                        spannableString = new SpannableString(format);
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#EF3713")),format.indexOf(str1),format.indexOf(str1)+str1.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE );
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#EF3713")),format.indexOf(str2),format.indexOf(str2)+str2.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE );
                }catch (Exception e){
                    spannableString = new SpannableString(awardType);
                }
                bindingView.textGambleDesc.setText(spannableString);

            }
            //当天已开奖，已投注，未中奖
            if (mJoinGambleResult.size()>0&&!mCurrentWinner){
                if (CommonUtility.SharedPreferencesUtility.getInt(activity, Constants.GAMBLE_ID,-1)!=mCurrentGambleResult.getId()){
                    MaterialDialogUtility.showNoWinPriceDialog(activity);
                    CommonUtility.SharedPreferencesUtility.put(activity,Constants.GAMBLE_ID,mCurrentGambleResult.getId());
                }
                showMyNoWinResult();

                bindingView.textGambleDesc.setText(R.string.current_no_winner);
            }
            //当天已开奖，未投注
            if (mJoinGambleResult.size()==0){
                showMyNoBet();
                bindingView.textGambleDesc.setText(R.string.current_no_bet);
            }
        }else {

            //当天还未开始开奖，已投注
            if (mJoinGambleResult.size()>0){
                bindingView.textGambleDesc.setText(R.string.no_award);
                showMyNoWinResult();
                fillData(false);

            }else{
                bindingView.textGambleDesc.setText(R.string.current_no_bet);
                showMyNoWinResult();
                fillData(false);
            }


        }
    }

    private void showMyNoBet() {
        bindingView.textRedOneMy.setText("?");
        bindingView.textRedTwoMy.setText("?");
        bindingView.textRedThreeMy.setText("?");
        bindingView.textBlueOneMy.setText("?");
    }

    private void showMyNoWinResult() {
        bindingView.textRedOneMy.setText(TextUtils.isEmpty(mJoinGambleResult.get(0).getRedBallFirst())?"?":mJoinGambleResult.get(0).getRedBallFirst());
        bindingView.textRedTwoMy.setText(TextUtils.isEmpty(mJoinGambleResult.get(0).getRedBallSecond())?"?":mJoinGambleResult.get(0).getRedBallSecond());
        bindingView.textRedThreeMy.setText(TextUtils.isEmpty(mJoinGambleResult.get(0).getRedBallThird())?"?":mJoinGambleResult.get(0).getRedBallThird());
        bindingView.textBlueOneMy.setText(TextUtils.isEmpty(mJoinGambleResult.get(0).getBlueBallEnd())?"?":mJoinGambleResult.get(0).getBlueBallEnd());
    }

    private void showMyWinResult() {
        bindingView.textRedOneMy.setText(mCurrentGambleResult.getRedBallFirst());
        bindingView.textRedTwoMy.setText(mCurrentGambleResult.getRedBallSecond());
        bindingView.textRedThreeMy.setText(mCurrentGambleResult.getRedBallThird());
        bindingView.textBlueOneMy.setText(mCurrentGambleResult.getBlueBallEnd());
    }


}
