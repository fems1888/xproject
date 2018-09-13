package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.qbao.xproject.app.R;
import com.qbao.xproject.app.Utility.StatusBarUtils;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityPayFailBinding;

public class PayFailActivity extends BaseRxActivity<ActivityPayFailBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_fail);
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.white,StatusBarUtils.GRAY);
        setToolBarTitle(getString(R.string.pay_fail));
    }
    public static void goPayFailActivity(Context context){
        Intent intent = new Intent(context,PayFailActivity.class);
        context.startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sure,menu);
        return super.onCreateOptionsMenu(menu);
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
