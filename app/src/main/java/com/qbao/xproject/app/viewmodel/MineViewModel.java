package com.qbao.xproject.app.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.qbao.xproject.app.entity.AccelerateFactorEntity;
import com.qbao.xproject.app.entity.ReceiveMineEntity;
import com.qbao.xproject.app.entity.UnReceiveMineEntity;
import com.qbao.xproject.app.http.ExceptionHandle;
import com.qbao.xproject.app.http.XProjectService;
import com.qbao.xproject.app.request_body.ReceiveMineRequest;
import com.qbao.xproject.app.request_body.ReceiveSpeedRequest;
import com.qbao.xproject.app.utility.Rx2Subscriber;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * 挖矿相关的model
 *
 * @author Created by jackieyao on 2018/9/19 下午5:52.
 */

public class MineViewModel extends BaseViewModel {
    public MineViewModel(@NonNull Application application, String tag) {
        super(application, tag);
    }

    public Observable<List<AccelerateFactorEntity>> findAllSpeedLog() {
        return Observable.create(e -> {
            XProjectService.newInstance().findAllSpeedLog().subscribe(new Rx2Subscriber<List<AccelerateFactorEntity>>(application, TAG) {
                @Override
                public void onError(ExceptionHandle.ResponseThrowable responseThrowable) {
                    e.onError(responseThrowable);
                }

                @Override
                public void onNext(List<AccelerateFactorEntity> value) {
                    e.onNext(value);
                }
            });
        });
    }

    public Observable<List<AccelerateFactorEntity>> findAllTaskCompleteList() {
        return Observable.create(e -> {
            XProjectService.newInstance().findAllTaskCompleteList().subscribe(new Rx2Subscriber<List<AccelerateFactorEntity>>(application, TAG) {
                @Override
                public void onError(ExceptionHandle.ResponseThrowable responseThrowable) {
                    e.onError(responseThrowable);
                }

                @Override
                public void onNext(List<AccelerateFactorEntity> value) {
                    e.onNext(value);
                }
            });
        });
    }

    public Observable<AccelerateFactorEntity> receiveSpeed(ReceiveSpeedRequest request) {
        return Observable.create(e -> {
            XProjectService.newInstance().receiveSpeed(request)
                    .subscribe(new Rx2Subscriber<AccelerateFactorEntity>(application, TAG) {
                        @Override
                        public void onError(ExceptionHandle.ResponseThrowable responseThrowable) {
                            e.onError(responseThrowable);
                        }

                        @Override
                        public void onNext(AccelerateFactorEntity value) {
                            e.onNext(value);
                        }
                    });
        });
    }

    public Observable<List<UnReceiveMineEntity>> findAllUnReceivedMine() {
        return Observable.create(e -> {
            XProjectService.newInstance().findAllUnReceivedMine()
                    .subscribe(new Rx2Subscriber<List<UnReceiveMineEntity>>(application, TAG) {
                        @Override
                        public void onError(ExceptionHandle.ResponseThrowable responseThrowable) {
                            e.onError(responseThrowable);
                        }

                        @Override
                        public void onNext(List<UnReceiveMineEntity> value) {
                            e.onNext(value);
                        }
                    });
        });
    }

    public Observable<ReceiveMineEntity> receiveMine(ReceiveMineRequest request) {
        return Observable.create(e -> {
            XProjectService.newInstance().receiveMine(request).subscribe(new Rx2Subscriber<ReceiveMineEntity>(application, TAG) {
                @Override
                public void onError(ExceptionHandle.ResponseThrowable responseThrowable) {
                    e.onError(responseThrowable);
                }

                @Override
                public void onNext(ReceiveMineEntity value) {
                    e.onNext(value);
                }
            });
        });
    }
}
