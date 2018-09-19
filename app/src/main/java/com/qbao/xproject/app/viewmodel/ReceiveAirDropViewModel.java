package com.qbao.xproject.app.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.qbao.xproject.app.entity.NextAirDropTimeEntity;
import com.qbao.xproject.app.entity.UnReceiveAirDropEntity;
import com.qbao.xproject.app.http.ExceptionHandle;
import com.qbao.xproject.app.http.XProjectService;
import com.qbao.xproject.app.utility.Rx2Subscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author Created by jackieyao on 2018/9/18 下午4:01.
 */

public class ReceiveAirDropViewModel extends BaseViewModel {
    public ReceiveAirDropViewModel(@NonNull Application application, String tag) {
        super(application, tag);
    }

    public Observable<UnReceiveAirDropEntity> findAllUnReceivedAirDrop(String accountNo) {
        return Observable.create(new ObservableOnSubscribe<UnReceiveAirDropEntity>() {
            @Override
            public void subscribe(ObservableEmitter<UnReceiveAirDropEntity> e) throws Exception {
                XProjectService.newInstance().findAllUnReceivedAirDrop(accountNo)
                        .subscribe(new Rx2Subscriber<UnReceiveAirDropEntity>(application, TAG) {
                            @Override
                            public void onError(ExceptionHandle.ResponseThrowable responseThrowable) {
                                e.onError(responseThrowable);
                            }

                            @Override
                            public void onNext(UnReceiveAirDropEntity value) {
                                e.onNext(value);
                            }
                        });
            }
        });
    }

    public Observable<NextAirDropTimeEntity> getNextAirDropTime() {
        return Observable.create(new ObservableOnSubscribe<NextAirDropTimeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<NextAirDropTimeEntity> e) throws Exception {
                XProjectService.newInstance().getNextAirDropTime().subscribe(new Rx2Subscriber<NextAirDropTimeEntity>(application, TAG) {
                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable responseThrowable) {
                        e.onError(responseThrowable);
                    }

                    @Override
                    public void onNext(NextAirDropTimeEntity value) {
                        e.onNext(value);
                    }
                });
            }
        });
    }
}
