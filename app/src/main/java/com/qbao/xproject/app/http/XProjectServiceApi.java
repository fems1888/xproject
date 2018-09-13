package com.qbao.xproject.app.http;

import com.qbao.xproject.app.request_body.UserLoginRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author Created by jackieyao on 2018/9/11 下午5:48.
 */

public interface XProjectServiceApi {

    @POST("/security/account/userLogin")
    Observable<Object> userLogin(@Body UserLoginRequest request);

    @GET("core/s/sendVerificationCodeByPhone")
    Observable<Object> getVerifyCode(@Query("phone") String phone);
}
