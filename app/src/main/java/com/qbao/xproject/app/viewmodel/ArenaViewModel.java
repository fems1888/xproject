package com.qbao.xproject.app.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.qbao.xproject.app.entity.CurrentGambleResult;
import com.qbao.xproject.app.entity.JoinGambleResponseEntity;
import com.qbao.xproject.app.http.ExceptionHandle;
import com.qbao.xproject.app.http.XProjectService;
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
}
