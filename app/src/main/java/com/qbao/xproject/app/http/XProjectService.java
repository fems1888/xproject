package com.qbao.xproject.app.http;

import com.qbao.xproject.app.BuildConfig;
import com.qbao.xproject.app.XProjectApplication;
import com.qbao.xproject.app.entity.AccelerateFactorEntity;
import com.qbao.xproject.app.entity.Account;
import com.qbao.xproject.app.entity.BetNextResponseEntity;
import com.qbao.xproject.app.entity.BillResponseEntity;
import com.qbao.xproject.app.entity.CurrentGambleResult;
import com.qbao.xproject.app.entity.JoinGambleResponseEntity;
import com.qbao.xproject.app.entity.MyWalletResponse;
import com.qbao.xproject.app.entity.NextAirDropTimeEntity;
import com.qbao.xproject.app.entity.NextGambleResponseEntity;
import com.qbao.xproject.app.entity.ReceiveMineEntity;
import com.qbao.xproject.app.entity.UnReceiveAirDropEntity;
import com.qbao.xproject.app.entity.UnReceiveMineEntity;
import com.qbao.xproject.app.http.converter.JsonConverterFactory;
import com.qbao.xproject.app.http.interceptor.EncryptionInterceptor;
import com.qbao.xproject.app.http.interceptor.HttpHeadInterceptor;
import com.qbao.xproject.app.http.interceptor.HttpLoggingInterceptor;
import com.qbao.xproject.app.http.interceptor.NetInterceptor;
import com.qbao.xproject.app.http.interceptor.NoNetInterceptor;
import com.qbao.xproject.app.request_body.BetNextRequest;
import com.qbao.xproject.app.request_body.ReceiveMineRequest;
import com.qbao.xproject.app.request_body.ReceiveSpeedRequest;
import com.qbao.xproject.app.request_body.UserLoginOutRequest;
import com.qbao.xproject.app.request_body.UserLoginRequest;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author Created by jackieyao on 2018/9/11 下午2:14.
 */

public class XProjectService {
    //连接超时时间 Unit Seconds
    public static final int CONNECT_TIME_OUT = 20;
    //Read Time out
    public static final int READ_TIME_OUT = 20;
    public static final int WRITE_TIME_OUT = 20;
    private JsonConverterFactory mJsonConverterFactory;
    private HttpHeadInterceptor headInterceptor;
    private XProjectServiceApi mServiceApi;
    private EncryptionInterceptor mEncryptionInterceptor;

    public static XProjectService newInstance() {
        return XProjectHolder.XPROJECT_INSTANCE.setEncrypt(true);
    }


    public static final class XProjectHolder {
        private static XProjectService XPROJECT_INSTANCE = new XProjectService();
    }


    public XProjectService setEncrypt(boolean encrypt) {
        mEncryptionInterceptor.setEncrypt(encrypt);
        mJsonConverterFactory.setDecrypt(encrypt);
        return this;
    }

    private XProjectService() {
        initXProject();
    }

    private void initXProject() {
        mJsonConverterFactory = JsonConverterFactory.create();
        headInterceptor = new HttpHeadInterceptor(XProjectApplication.getInstance().getApplicationContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.URL_API_BASE)
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以体类返回)
//                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(mJsonConverterFactory)
                .client(getClient())
                .build();

        mServiceApi = retrofit.create(XProjectServiceApi.class);
    }

    public OkHttpClient getClient() {
        mEncryptionInterceptor = new EncryptionInterceptor();
        File httpCacheDirectory = new File(XProjectApplication.getInstance().getCacheDir(), "responses");
        int cacheSize = 50 * 1024 * 1024; // 50 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        final OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor("XProjectService").setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(headInterceptor)
                .addInterceptor(new NoNetInterceptor())
                //参数加密拦截器
                .addInterceptor(mEncryptionInterceptor)
                .addNetworkInterceptor(new NetInterceptor())
                .cache(cache);

        return builder.build();
    }


    public Observable<Response<ResponseBody>> userLogin(UserLoginRequest request) {
        return mServiceApi.userLogin(request);
    }

    public Observable<Object> getVerifyCode(String phone, String countryId) {
        return mServiceApi.getVerifyCode(phone, countryId);
    }

    public Observable<MyWalletResponse> getMyWallet( ) {
        return mServiceApi.getMyWallet( );
    }

    public Observable<Response<ResponseBody>> refreshToken(UserLoginRequest request) {
        return mServiceApi.refreshToken(request);
    }

    public Observable<UnReceiveAirDropEntity> findAllUnReceivedAirDrop() {
        return mServiceApi.findAllUnReceivedAirDrop();
    }

    public Observable<NextAirDropTimeEntity> getNextAirDropTime() {
        return mServiceApi.getNextAirDropTime();
    }

    public Observable<Object> loginOut(UserLoginOutRequest accountNo) {
        return mServiceApi.loginOut(accountNo);
    }

    public Observable<Object> receiveAirDrop() {
        return mServiceApi.receiveAirDrop();
    }

    public Observable<List<AccelerateFactorEntity>> findAllSpeedLog() {
        return mServiceApi.findAllSpeedLog();
    }

    public Observable<List<AccelerateFactorEntity>> findAllTaskCompleteList() {
        return mServiceApi.findAllTaskCompleteList();
    }

    public Observable<AccelerateFactorEntity> receiveSpeed(ReceiveSpeedRequest receiveSpeedRequest) {
        return mServiceApi.receiveSpeed(receiveSpeedRequest);
    }

    public Observable<List<UnReceiveMineEntity>> findAllUnReceivedMine() {
        return mServiceApi.findAllUnReceivedMine();
    }

    public Observable<ReceiveMineEntity> receiveMine(ReceiveMineRequest request) {
        return mServiceApi.receiveMine(request);
    }

    public Observable<List<BillResponseEntity>> findBillList(int page, int size) {
        return mServiceApi.findBillList(page, size);
    }

    public Observable<CurrentGambleResult> getCurrentGambleResult() {
        return mServiceApi.getCurrentGambleResult();
    }

    public Observable<List<JoinGambleResponseEntity>> getGambleJoinByGambleId(int id) {
        return mServiceApi.getGambleJoinByGambleId(id);
    }

    public Observable<BetNextResponseEntity> betNextGamble(BetNextRequest request) {
        return mServiceApi.betNextGamble(request);
    }

    public Observable<NextGambleResponseEntity> getNextGambleInfo() {
        return mServiceApi.getNextGambleInfo();
    }
}

