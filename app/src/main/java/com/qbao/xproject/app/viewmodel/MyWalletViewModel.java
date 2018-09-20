package com.qbao.xproject.app.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.nfc.Tag;
import android.support.annotation.NonNull;

import com.qbao.xproject.app.entity.MyWalletResponse;
import com.qbao.xproject.app.http.ExceptionHandle;
import com.qbao.xproject.app.http.XProjectService;
import com.qbao.xproject.app.utility.Rx2Subscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author Created by jackieyao on 2018/9/18 上午10:32.
 */

public class MyWalletViewModel extends BaseViewModel {

    public MyWalletViewModel(@NonNull Application application, String tag) {
        super(application, tag);
    }

    public Observable<MyWalletResponse> getMyWallet( ) {
        return Observable.create(new ObservableOnSubscribe<MyWalletResponse>() {
            @Override
            public void subscribe(ObservableEmitter<MyWalletResponse> e) throws Exception {
                XProjectService.newInstance().getMyWallet( )
                        .subscribe(new Rx2Subscriber<MyWalletResponse>(application, TAG) {
                            @Override
                            public void onError(ExceptionHandle.ResponseThrowable responseThrowable) {
                                e.onError(responseThrowable);
                            }

                            @Override
                            public void onNext(MyWalletResponse value) {
                                e.onNext(value);
                            }
                        });
            }
        });
    }
}
