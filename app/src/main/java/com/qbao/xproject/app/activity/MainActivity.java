package com.qbao.xproject.app.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.qbao.xproject.app.R;
import com.qbao.xproject.app.Utility.CommonUtility;
import com.qbao.xproject.app.Utility.StatusBarUtils;
import com.qbao.xproject.app.XProjectApplication;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.fragment.ArenaFragment;
import com.qbao.xproject.app.fragment.CoinMineFragment;
import com.qbao.xproject.app.fragment.MineFragment;

import java.util.ArrayList;

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
            exitBy2Click();
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
