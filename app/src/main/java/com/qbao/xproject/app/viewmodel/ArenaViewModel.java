package com.qbao.xproject.app.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.qbao.xproject.app.entity.BetNextResponseEntity;
import com.qbao.xproject.app.entity.CurrentGambleResult;
import com.qbao.xproject.app.entity.JoinGambleResponseEntity;
import com.qbao.xproject.app.entity.NextGambleResponseEntity;
import com.qbao.xproject.app.http.ExceptionHandle;
import com.qbao.xproject.app.http.XProjectService;
import com.qbao.xproject.app.request_body.BetNextRequest;
import com.qbao.xproject.app.utility.Rx2Subscriber;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author Created by jackieyao on 2018/9/21 上午10:49.
 */

public class ArenaViewModel extends BaseViewModel {
    public ArenaViewModel(@NonNull Application application, String tag) {
        super(application, tag);
    }

    public Observable<CurrentGambleResult> getCurrentGambleResult() {
        return Observable.create(e -> {
            XProjectService.newInstance().getCurrentGambleResult().subscribe(new Rx2Subscriber<CurrentGambleResult>(application, TAG) {
                @Override
                public void onError(ExceptionHandle.ResponseThrowable responseThrowable) {
                    e.onError(responseThrowable);
                }

                @Override
                public void onNext(CurrentGambleResult value) {
                    e.onNext(value);
                }
            });
        });
    }

    public Observable<List<JoinGambleResponseEntity>> getGambleJoinByGambleId(int id) {
        return Observable.create(e -> {
            XProjectService.newInstance().getGambleJoinByGambleId(id).subscribe(new Rx2Subscriber<List<JoinGambleResponseEntity>>(application, TAG) {
                @Override
                public void onError(ExceptionHandle.ResponseThrowable responseThrowable) {
                    e.onError(responseThrowable);
                }

                @Override
                public void onNext(List<JoinGambleResponseEntity> value) {
                    e.onNext(value);
                }
            });
        });
    }

    public Observable<NextGambleResponseEntity> getNextGambleInfo() {

        return Observable.create(e -> {
            XProjectService.newInstance().getNextGambleInfo()
                    .subscribe(new Rx2Subscriber<NextGambleResponseEntity>(application, TAG) {
                        @Override
                        public void onError(ExceptionHandle.ResponseThrowable responseThrowable) {
                            e.onError(responseThrowable);
                        }

                        @Override
                        public void onNext(NextGambleResponseEntity value) {
                            e.onNext(value);
                        }
                    });
        });
    }

    public Observable<BetNextResponseEntity> betNextGamble(BetNextRequest request) {
        return Observable.create(e -> {
            XProjectService.newInstance().betNextGamble(request)
                    .subscribe(new Rx2Subscriber<BetNextResponseEntity>(application, TAG) {
                        @Override
                        public void onError(ExceptionHandle.ResponseThrowable responseThrowable) {
                            e.onError(responseThrowable);
                        }

                        @Override
                        public void onNext(BetNextResponseEntity value) {
                            e.onNext(value);
                        }
                    });
        });
    }
}
