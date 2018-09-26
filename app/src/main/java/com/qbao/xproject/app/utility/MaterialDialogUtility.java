package com.qbao.xproject.app.utility;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.qbao.xproject.app.R;
import com.qbao.xproject.app.entity.JoinGambleResponseEntity;

/**
 * @author Created by jackieyao on 2018/9/12 下午1:50.
 */

public class MaterialDialogUtility {
    /**
     * 展示中奖的dialog
     * @param context
     * @param joinGambleResponseEntity 中奖信息
     */
    public static void showWinPriceDialog(Context context, JoinGambleResponseEntity joinGambleResponseEntity){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialog);
        View view = View.inflate(context, R.layout.layout_win_price_dialog, null);
        builder.setView(view);
        builder.setTitle(null);
        builder.setCancelable(false);
        AlertDialog alert = builder.show();
        WindowManager.LayoutParams lp = alert.getWindow().getAttributes();
        lp.width = CommonUtility.getScreenWidth(context) * 4 / 5;
        alert.getWindow().setAttributes(lp);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable());
        view.findViewById(R.id.text_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XProjectUtil.eventReport(context,context.getString(R.string.event_id_1036));
                alert.dismiss();
            }
        });
        ((TextView)view.findViewById(R.id.text_award_type)).setText(joinGambleResponseEntity.getAwardType(context));
        ((TextView)view.findViewById(R.id.text_price_amount)).setText(CommonUtility.formatString(CommonUtility.getFormatDoubleTwo(joinGambleResponseEntity.getRewardAmount()),joinGambleResponseEntity.getRewardUnitName()));
    }

    /**
     * 展示没有中奖的dialog
     * @param context
     */
    public static void showNoWinPriceDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialog);
        View view = View.inflate(context, R.layout.layout_no_win_price_dialog, null);
        builder.setView(view);
        builder.setTitle(null);
        builder.setCancelable(false);
        AlertDialog alert = builder.show();
        WindowManager.LayoutParams lp = alert.getWindow().getAttributes();
        lp.width = CommonUtility.getScreenWidth(context) * 4 / 5;
        alert.getWindow().setAttributes(lp);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable());
        view.findViewById(R.id.text_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XProjectUtil.eventReport(context,context.getString(R.string.event_id_1036));
                alert.dismiss();
            }
        });
    }

    /**
     * 退出登录dialog
     * @param context
     */
    public static void showLoginOutDialog(Context context, View.OnClickListener outListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.login_out));
        builder.setMessage(R.string.login_out_flag);
        builder.setNegativeButton(context.getString(R.string.pic_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                XProjectUtil.eventReport(context,context.getString(R.string.event_id_1015));
            }
        });
        builder.setPositiveButton(context.getString(R.string.login_out_dialog), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                XProjectUtil.eventReport(context,context.getString(R.string.event_id_1016));
                if (outListener!=null){
                    outListener.onClick(null);
                }
            }
        });
        builder.show();
    }
}
