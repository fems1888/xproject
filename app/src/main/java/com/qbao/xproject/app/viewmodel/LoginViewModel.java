package com.qbao.xproject.app.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.nfc.Tag;
import android.support.annotation.NonNull;

import com.qbao.xproject.app.XProjectApplication;
import com.qbao.xproject.app.http.ExceptionHandle;
import com.qbao.xproject.app.http.XProjectService;
import com.qbao.xproject.app.manager.AccessTokenManager;
import com.qbao.xproject.app.request_body.UserLoginRequest;
import com.qbao.xproject.app.utility.CommonUtility;
import com.qbao.xproject.app.utility.Rx2Subscriber;
import com.qbao.xproject.app.utility.RxSchedulers;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author Created by jackieyao on 2018/9/13 下午5:19.
 */

public class LoginViewModel extends BaseViewModel {

    public LoginViewModel(@NonNull Application application, String tag) {
        super(application, tag);
    }


    public Observable<Response<ResponseBody>> userLogin(UserLoginRequest request) {
        return Observable.create(e -> XProjectService.newInstance().userLogin(request)
                .subscribe(new Rx2Subscriber<Response<ResponseBody>>(application, TAG) {
                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable responseThrowable) {
                        e.onError(responseThrowable);
                    }

                    @Override
                    public void onNext(Response<ResponseBody> value) {
                        ResponseBody responseBody = value.body();
                        Headers headers = value.headers();
                        String qbaoToken = headers.get("X-T");
                        CommonUtility.DebugLog.e(TAG, "responseBody = " + responseBody);
                        CommonUtility.DebugLog.e(TAG, "qbaoToken = " + qbaoToken);
                        if (!CommonUtility.isNull(responseBody)) {
                            String responseBodyJson = null;
                            try {
                                responseBodyJson = responseBody.string();
                                CommonUtility.DebugLog.e(TAG, "responseBodyJson = " + responseBodyJson);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                        }
                            e.onNext(value);
                         if (!CommonUtility.isNull(qbaoToken)){
                             //保存Token 到本地
                             AccessTokenManager.getInstance().saveAccessToken(qbaoToken);
                         }
                    }
                }));
    }

    public Observable<Object> getVerifyCode(String phone,String countryId){
        return Observable.create(e -> XProjectService.newInstance().getVerifyCode(phone,countryId).compose(RxSchedulers.io_main()).subscribe(new Rx2Subscriber<Object>(application,"") {
            @Override
            public void onError(ExceptionHandle.ResponseThrowable responseThrowable) {
                e.onError(responseThrowable);
            }

            @Override
            public void onNext(Object value) {
                    e.onNext(value);
            }
        }));
    }
}
