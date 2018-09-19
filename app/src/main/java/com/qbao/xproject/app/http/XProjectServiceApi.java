package com.qbao.xproject.app.http;

import com.qbao.xproject.app.entity.BetResponseEntity;
import com.qbao.xproject.app.entity.MyWalletResponse;
import com.qbao.xproject.app.entity.NextAirDropTimeEntity;
import com.qbao.xproject.app.entity.UnReceiveAirDropEntity;
import com.qbao.xproject.app.request_body.UserLoginRequest;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author Created by jackieyao on 2018/9/11 下午5:48.
 */

public interface XProjectServiceApi {

    @POST("/security/account/userLogin")
    Observable<Response<ResponseBody>> userLogin(@Body UserLoginRequest request);

    @GET("/core/s/sendVerificationCodeByPhone")
    Observable<Object> getVerifyCode(@Query("phone") String phone,@Query("countryId") String countryId);

    @POST("/security/account/refreshToken")
    Observable<Object> refreshToken(@Body UserLoginRequest request);


    @GET("/core/myWallet/getWallet")
    Observable<MyWalletResponse> getMyWallet(@Query("accountNo") String accountNo);

    /**
     * 获得未领取的空投的List
     * @param accountNo
     * @return
     */
    @GET("/airDrop/findAllUnReceivedAirDrop")
    Observable<UnReceiveAirDropEntity> findAllUnReceivedAirDrop(@Query("accountNo") String accountNo);

    /**
     * 当没有空投时  获取下一次空投时间
     * @return
     */
    @GET("/airDrop/getNextAirDropActivity")
    Observable<NextAirDropTimeEntity> getNextAirDropTime();

}
