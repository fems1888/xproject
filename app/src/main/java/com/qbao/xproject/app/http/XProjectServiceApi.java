package com.qbao.xproject.app.http;

import com.qbao.xproject.app.entity.AccelerateFactorEntity;
import com.qbao.xproject.app.entity.Account;
import com.qbao.xproject.app.entity.BetResponseEntity;
import com.qbao.xproject.app.entity.MyWalletResponse;
import com.qbao.xproject.app.entity.NextAirDropTimeEntity;
import com.qbao.xproject.app.entity.UnReceiveAirDropEntity;
import com.qbao.xproject.app.entity.UnReceiveMineEntity;
import com.qbao.xproject.app.request_body.ReceiveMineRequest;
import com.qbao.xproject.app.request_body.ReceiveSpeedRequest;
import com.qbao.xproject.app.request_body.UserLoginOutRequest;
import com.qbao.xproject.app.request_body.UserLoginRequest;

import java.util.List;

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
    Observable<Response<ResponseBody>> refreshToken(@Body UserLoginRequest request);


    @GET("/core/myWallet/getWallet")
    Observable<MyWalletResponse> getMyWallet(@Query("accountNo") String accountNo);

    /**
     * 获得未领取的空投的List
     * @return
     */
    @GET("/core/airDrop/findAllUnReceivedAirDrop")
    Observable<UnReceiveAirDropEntity> findAllUnReceivedAirDrop();

    /**
     * 当没有空投时  获取下一次空投时间
     * @return
     */
    @GET("/core/airDrop/getNextAirDropActivity")
    Observable<NextAirDropTimeEntity> getNextAirDropTime();

    /**
     * 注销
     * @param accountNo
     * @return
     */
    @POST("/security/account/clearToken")
    Observable<Object> loginOut(@Body UserLoginOutRequest accountNo);

    /**
     * 领取空投
     * @return
     */
    @GET("/core/airDrop/receiveAllAirDrop")
    Observable<Object> receiveAirDrop();

    /**
     * 获取加速因子
     *
     * @return
     */
    @GET("/core/mine/findAllSpeedLog")
    Observable<List<AccelerateFactorEntity>> findAllSpeedLog();

    /**
     * 获取各任务的加速因子
     *
     * @return
     */
    @GET("/core/mine/findAllTaskCompleteList")
    Observable<List<AccelerateFactorEntity>> findAllTaskCompleteList();

    /**
     * 领取加速因子
     *
     * @return
     */
    @POST("/core/mine/receiveSpeed")
    Observable<AccelerateFactorEntity> receiveSpeed(@Body ReceiveSpeedRequest receiveSpeedRequest);

    /**
     * 获取未领取的矿石
     *
     * @return
     */
    @GET("/core/mine/findAllUnReceivedMine")
    Observable<List<UnReceiveMineEntity>> findAllUnReceivedMine();

    /**
     * 领取矿石
     *
     * @return
     * @param request
     */
    @GET("/core/mine/receiveMine")
    Observable<UnReceiveMineEntity> receiveMine(@Body ReceiveMineRequest request);
}
