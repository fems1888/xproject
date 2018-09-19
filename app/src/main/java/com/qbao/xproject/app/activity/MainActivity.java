package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.qbao.xproject.app.BuildConfig;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.entity.Account;
import com.qbao.xproject.app.manager.AccountManager;
import com.qbao.xproject.app.manager.Constants;
import com.qbao.xproject.app.receiver.TagAliasOperatorHelper;
import com.qbao.xproject.app.request_body.UserLoginRequest;
import com.qbao.xproject.app.utility.CommonUtility;
import com.qbao.xproject.app.utility.RxSchedulers;
import com.qbao.xproject.app.utility.StatusBarUtils;
import com.qbao.xproject.app.XProjectApplication;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.fragment.ArenaFragment;
import com.qbao.xproject.app.fragment.CoinMineFragment;
import com.qbao.xproject.app.fragment.MineFragment;
import com.qbao.xproject.app.viewmodel.RefreshTokenViewModel;
import com.umeng.commonsdk.UMConfigure;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import io.reactivex.functions.Consumer;

import static com.qbao.xproject.app.receiver.TagAliasOperatorHelper.ACTION_SET;
import static com.qbao.xproject.app.receiver.TagAliasOperatorHelper.TagAliasBean;
import static com.qbao.xproject.app.receiver.TagAliasOperatorHelper.sequence;
/**
 * @author Created by jackieyao on 2018/9/12 下午5:10
 */

public class MainActivity extends BaseRxActivity {
    BottomNavigationView mNavigationView;


    private FragmentManager mFragmentManager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private Fragment mFragmentWallet, mFragmentCompetition, mFragmentMine;
    private long[] mHits = new long[2];


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (mFragmentManager == null)
                mFragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            hideFragments(transaction);

            switch (item.getItemId()) {
                case R.id.navigation_arena:
                    if (mFragmentWallet == null) {
                        mFragmentWallet = new ArenaFragment();
                        mFragments.add(mFragmentWallet);
                        transaction.add(R.id.content_layout, mFragmentWallet);
                    } else {
                        transaction.show(mFragmentWallet);
                    }
                    StatusBarUtils.setWindowStatusBarColor(activity,R.color.bar_one_color);
                    break;
                case R.id.navigation_coin_mine:

                    if (mFragmentCompetition == null) {
                        mFragmentCompetition = new CoinMineFragment();
                        mFragments.add(mFragmentCompetition);
                        transaction.add(R.id.content_layout, mFragmentCompetition);
                    } else {
                        transaction.show(mFragmentCompetition);
                    }
                    StatusBarUtils.setWindowStatusBarColor(activity,R.color.bar_two_color);
                    break;
                case R.id.navigation_mine:
                    if (mFragmentMine == null) {
                        mFragmentMine = new MineFragment();
                        mFragments.add(mFragmentMine);
                        transaction.add(R.id.content_layout, mFragmentMine);
                    } else {
                        transaction.show(mFragmentMine);
                    }
                    StatusBarUtils.setWindowStatusBarColor(activity,R.color.text_main_color);
                    break;
                    default:
                        break;
            }
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
            return true;
        }
    };

    public static void go(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mNavigationView.setSelectedItemId(R.id.navigation_arena);
    }


    private void hideFragments(FragmentTransaction transaction) {
        if (!CommonUtility.isNull(mFragments)) {
            for (Fragment fragment : mFragments) {
                if (!CommonUtility.isNull(fragment)) {
                    transaction.hide(fragment);
                }
            }
        }
    }


    @Override
    protected void initData() {
        super.initData();
        //每次进入先判断上次登录的时间是否和现在差一天  如果差一天就重新刷新token
        String time = AccountManager.getInstance().getAccountEntity().getLoginTime();
        long loginTime = TextUtils.isEmpty(time)?System.currentTimeMillis():CommonUtility.byTimeGetMillis(time);
        if (System.currentTimeMillis() - loginTime> Constants.A_DAY_MILLS){
            refreshToken();
        }

        initJpushTag();
        initJpushAlias();
        Log.e(TAG, "initData: "+UMConfigure.getUMIDString(activity));
    }

    private void refreshToken() {
        UserLoginRequest request = new UserLoginRequest();
        request.setPhone("");
        RefreshTokenViewModel viewModel = new RefreshTokenViewModel(activity.getApplication(),TAG);
        viewModel.refreshToken(request)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<Account>() {
                    @Override
                    public void accept(Account account) throws Exception {
                        AccountManager.getInstance().cleanAccount();
                        AccountManager.getInstance().saveAccount(account);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(activity,"error22",Toast.LENGTH_LONG).show();
                    }
                });
    }

    /**
     * 极光别名
     */
    private void initJpushAlias() {

        int action = ACTION_SET;

        TagAliasBean tagAliasBean = new TagAliasBean();
        tagAliasBean.action = action;
        sequence++;
        tagAliasBean.alias = "demo";
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(),sequence,tagAliasBean);
    }

    /**
     * 极光标签
     */
    private void initJpushTag() {
        String language = null;
        if (BuildConfig.BUILD_TYPE.equals("release")) {
            language = "zh";
        } else {
            language = "zh_test";
        }
        int action = ACTION_SET;
        TagAliasBean tagAliasBean = new TagAliasBean();
        tagAliasBean.action = action;
        sequence++;
        Set<String> tagSet = new LinkedHashSet<String>();
        tagSet.add(language);
        tagAliasBean.tags = tagSet;
        tagAliasBean.isAliasAction = false;
        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), sequence, tagAliasBean);
    }

    @ColorInt
    protected int getColorByAttributeId(@AttrRes int attrIdForColor) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(attrIdForColor, typedValue, true);
        return typedValue.data;
    }
    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            exitBy2Click();
            finish();
        }
        return false;
    }


    /**
     * 双击退出函数
     */
    private void exitBy2Click() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mHits[0] < 2000) {
            exitApp();
        } else {
            mHits[0] = currentTime;
            System.arraycopy(mHits, 0, mHits, 1, mHits.length - 1);
            Toast.makeText(this, getString(R.string.login_out_tips), Toast.LENGTH_SHORT).show();
        }
    }

    private void exitApp() {
        finish();
        XProjectApplication.getInstance().exit(0);
    }
}
