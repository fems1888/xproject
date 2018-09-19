package com.qbao.xproject.app.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.qbao.xproject.app.http.ExceptionHandle;
import com.qbao.xproject.app.http.XProjectService;
import com.qbao.xproject.app.manager.AccessTokenManager;
import com.qbao.xproject.app.request_body.UserLoginRequest;
import com.qbao.xproject.app.utility.Rx2Subscriber;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author Created by jackieyao on 2018/9/18 上午11:00.
 */

public class RefreshTokenViewModel extends BaseViewModel {
    public RefreshTokenViewModel(@NonNull Application application, String tag) {
        super(application, tag);
    }
    public Observable<Object> refreshToken(UserLoginRequest request){
        return Observable.create(e -> {
            XProjectService.newInstance().refreshToken(request)
                    .subscribe(new Rx2Subscriber<Object>(application,TAG) {
                        @Override
                        public void onError(ExceptionHandle.ResponseThrowable responseThrowable) {
                            e.onError(responseThrowable);
                        }

                        @Override
                        public void onNext(Object value) {
                                e.onNext(value);
                        }
                    });
        });
    }
}
