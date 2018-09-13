package com.qbao.xproject.app.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.qbao.xproject.app.Utility.RxSchedulers;
import com.qbao.xproject.app.XProjectApplication;
import com.qbao.xproject.app.http.XProjectService;
import com.qbao.xproject.app.request_body.UserLoginRequest;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * @author Created by jackieyao on 2018/9/13 下午5:19.
 */

public class LoginViewModel extends AndroidViewModel {
    private XProjectApplication application;
    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.application = (XProjectApplication) application;
    }

    public Observable<Object> userLogin(UserLoginRequest request) {
        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                XProjectService.newInstance().userLogin(request)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                e.onNext(o);
                            }
                        });
            }
        });
    }

    public Observable<Object> getVerifyCode(String phone){
        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                XProjectService.newInstance().getVerifyCode(phone).subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        e.onNext(o);
                    }
                });
            }
        });
    }
}
