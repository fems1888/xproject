package com.qbao.xproject.app.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.nfc.Tag;
import android.support.annotation.NonNull;

import com.qbao.xproject.app.R;
import com.qbao.xproject.app.XProjectApplication;
import com.qbao.xproject.app.entity.Account;
import com.qbao.xproject.app.http.ExceptionHandle;
import com.qbao.xproject.app.http.XProjectService;
import com.qbao.xproject.app.manager.AccessTokenManager;
import com.qbao.xproject.app.manager.AccountManager;
import com.qbao.xproject.app.manager.Constants;
import com.qbao.xproject.app.request_body.UserLoginRequest;
import com.qbao.xproject.app.utility.AESUtil;
import com.qbao.xproject.app.utility.CommonUtility;
import com.qbao.xproject.app.utility.Rx2Subscriber;
import com.qbao.xproject.app.utility.RxSchedulers;

import org.json.JSONObject;

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


    public Observable<String> userLogin(UserLoginRequest request) {
        return Observable.create(e -> XProjectService.newInstance().userLogin(request)
                .subscribe(new Rx2Subscriber<Response<ResponseBody>>(application,TAG) {
                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable responseThrowable) {
                            responseThrowable.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<ResponseBody> value) {
                        ResponseBody responseBody = value.body();
                        Headers headers = value.headers();
                        String qbaoToken = headers.get("X-T");
                        CommonUtility.DebugLog.e(TAG, "responseBody = " + responseBody);
                        CommonUtility.DebugLog.e(TAG, "headersBody = " + headers.toString());
                        CommonUtility.DebugLog.e(TAG, "qbaoToken = " + qbaoToken);
                        try {
                            if (!CommonUtility.isNull(responseBody)) {
                                String responseBodyJson = null;
                                responseBodyJson = responseBody.string();
                                JSONObject jsonObject = new JSONObject(responseBodyJson);
                                String resultJson = jsonObject.optString("result");
                                if (!CommonUtility.isNull(resultJson)) {
                                    String result = AESUtil.decrypt(resultJson, AESUtil.KEY);//解密
                                    CommonUtility.DebugLog.e(TAG, "responseBodyJson = " + result);
                                    if (!CommonUtility.isNull(result) && !CommonUtility.isNull(qbaoToken)) {
                                        Account account = CommonUtility.JSONObjectUtility.convertJSONObject2Obj(resultJson, Account.class);
                                        //保存Token 到本地
                                        AccessTokenManager.getInstance().saveAccessToken(qbaoToken);
                                        if (!CommonUtility.isNull(account)) {
                                            //save account information
                                            AccountManager.getInstance(application).saveAccount(account);
                                            e.onNext(Constants.SUCCESS);
                                            e.onComplete();

                                        }
                                    }else {
                                        e.onNext(application.getString(R.string.failed));
                                        e.onComplete();
                                    }

                                }else {
                                    e.onNext(application.getString(R.string.failed));
                                    e.onComplete();
                                }

                            } else {
                                String errorBody = value.errorBody().string();
                                JSONObject jsonObject = new JSONObject(errorBody);
                                String errorCode = jsonObject.optString("errorCode");
                                CommonUtility.DebugLog.e(TAG, "errorCode = " + errorCode);
                                if (errorCode.equals(Constants.REGISTER_CODE_IS_NULL)){
                                    e.onNext(errorCode);
                                }else {
                                    e.onNext(jsonObject.optString("message"));
                                }

                            }
                            e.onComplete();
                        } catch (Exception e1) {
                            e.onError(e1);
                            e1.printStackTrace();
                        }
                    }
                }));
    }

    public Observable<Object> getVerifyCode(String phone, String countryId) {
        return Observable.create(e -> XProjectService.newInstance().getVerifyCode(phone, countryId).compose(RxSchedulers.io_main()).subscribe(new Rx2Subscriber<Object>(application, "") {
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
