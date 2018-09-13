package com.qbao.xproject.app.fragment.dialog_fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qbao.xproject.app.R;


/**
 * @author Created by jackieyao on 2018/9/12 下午7:27
 */


@SuppressLint("ValidFragment")
public class BetSureDialogFragment extends AppCompatDialogFragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.pay_dialog_fragment);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_bet_sure, container, false);
        view.findViewById(R.id.image_close).setOnClickListener(this);
        view.findViewById(R.id.button_sure).setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        getDialog().getWindow().getAttributes().width = getResources().getDisplayMetrics().widthPixels;
        getDialog().getWindow().setGravity(Gravity.BOTTOM);//对齐方式
        super.onStart();

    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_close:
                dismiss();
                break;
            case R.id.button_sure:
                dismiss();
                if (clickListener!=null){
                    clickListener.onClick(null);
                }
                break;
            default:
                break;
        }
    }

    private View.OnClickListener clickListener;

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
