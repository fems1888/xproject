package com.aether.coder.qbao.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.aether.coder.qbao.R;
import com.aether.coder.qbao.datastorage.QtumSharedPreference;
import com.aether.coder.qbao.manager.AccessTokenManager;
import com.aether.coder.qbao.ui.chat.SelfGroupDetailActivity;
import com.aether.coder.qbao.ui.main.activity.PushDialogActivity;
import com.aether.coder.qbao.ui.main.entity.RongCloudExtraEntity;
import com.aether.coder.qbao.ui.small_application.CoinCalendarActivity;
import com.aether.coder.qbao.ui.view.UITipDialog;
import com.aether.coder.qbao.ui.webview.EventWebView;
import com.aether.coder.qbao.ui.webview.QbaoWebViewActivity;
import com.aether.coder.qbao.utils.MaterialDialogUtility;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.qbao.library.utility.AESUtil;
import com.qbao.library.utility.CommonUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 * type // 0 币月历 1 游戏 2 活动 3 公告 4 跳转到指定群
 */
public class JPushNotificationReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
                String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
                RongCloudExtraEntity rongCloudExtraEntity = CommonUtility.JSONObjectUtility.GSON.fromJson(extra, RongCloudExtraEntity.class);

                if (rongCloudExtraEntity != null && isRunningForeground(context)) {
                    //弹框提示.传 notificationId 点击弹框里的按钮后取消通知栏上的通知
                    Intent i = new Intent(context, PushDialogActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(PushDialogActivity.PUSH_CONTENT, bundle.getString(JPushInterface.EXTRA_ALERT));
                    i.putExtra(PushDialogActivity.EXTRA, rongCloudExtraEntity);
                    i.putExtra(PushDialogActivity.EXTRA_NOTIFICATION_ID,bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID));
                    context.startActivity(i);
                }

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

                String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
                RongCloudExtraEntity rongCloudExtraEntity = CommonUtility.JSONObjectUtility.GSON.fromJson(extra, RongCloudExtraEntity.class);
                String language = QtumSharedPreference.getInstance().getLanguage(context);
                if (rongCloudExtraEntity != null && !isRunningForeground(context)) {
                    if (rongCloudExtraEntity.getType().equals("0")) {
                        CoinCalendarActivity.goCoinCalendarActivity(context, true);
                    } else if (rongCloudExtraEntity.getType().equals("1")) {
                        String url = CommonUtility.formatString(rongCloudExtraEntity.getUrl(), ("?token="), AESUtil.encryptSignH5(AccessTokenManager.getInstance().getAccessToken()), "&lang=", language, "&id=", AESUtil.encryptSignH5(String.valueOf(rongCloudExtraEntity.getGameId())));
                        QbaoWebViewActivity.goToBaseWebViewFromJPush(context, url, true, true);
                    } else if (rongCloudExtraEntity.getType().equals("2")) {
                        EventWebView.goToEventWebViewFromJPush(context, CommonUtility.formatString(rongCloudExtraEntity.getUrl(), rongCloudExtraEntity.getUrl().contains("?") ? ("token=") : ("?token="), AESUtil.encryptSignH5(AccessTokenManager.getInstance().getAccessToken()), "&lang=", language, "&eventId=", AESUtil.encryptSignH5(String.valueOf(rongCloudExtraEntity.getGameId()))), 0, false, true);
                    } else if (rongCloudExtraEntity.getType().equals("3")) {
                        EventWebView.goToEventWebViewFromJPush(context, rongCloudExtraEntity.getUrl(), true, true);
                    } else if (rongCloudExtraEntity.getType().equals("4")) {
                        SelfGroupDetailActivity.goToSelfGroupDetailActivityFromJPush(context, rongCloudExtraEntity.getGroupNo(), true);
                    }else {
                        EventWebView.goToEventWebView(context,rongCloudExtraEntity.getUrl(),false,false);
                    }
                }

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            } else {
                Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }


    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    private boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (currentPackageName != null && currentPackageName.equals(context.getPackageName())) {
            return true;
        }
        return false;
    }
}
