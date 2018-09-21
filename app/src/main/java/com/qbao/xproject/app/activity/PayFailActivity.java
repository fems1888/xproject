package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.qbao.xproject.app.R;
import com.qbao.xproject.app.interf.StatusBarContentColor;
import com.qbao.xproject.app.utility.StatusBarUtils;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityPayFailBinding;

/**
 * @author Created by jackieyao on 2018/9/21 下午5:27
 */

public class PayFailActivity extends BaseRxActivity<ActivityPayFailBinding> {
    public static final String FAIL_REASON = "failReason";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_fail);
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.white, StatusBarContentColor.GRAY);
        setToolBarTitle(getString(R.string.pay_fail));
    }
    public static void goPayFailActivity(Context context,String failReason){
        Intent intent = new Intent(context,PayFailActivity.class);
        intent.putExtra(FAIL_REASON,failReason);
        context.startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sure,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void initData() {
        super.initData();
        String reason = getIntent().getStringExtra(FAIL_REASON);
        bindingView.textFailReason.setText(reason);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sure){
            Toast.makeText(activity,"ew",Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
