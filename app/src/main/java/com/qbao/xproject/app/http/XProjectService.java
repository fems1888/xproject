package com.qbao.xproject.app.http;

import com.qbao.xproject.app.BuildConfig;
import com.qbao.xproject.app.XProjectApplication;
import com.qbao.xproject.app.http.converter.JsonConverterFactory;
import com.qbao.xproject.app.http.interceptor.HttpHeadInterceptor;
import com.qbao.xproject.app.http.interceptor.HttpLoggingInterceptor;
import com.qbao.xproject.app.http.interceptor.NetInterceptor;
import com.qbao.xproject.app.http.interceptor.NoNetInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
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
    public static XProjectService newInstance() {
        return XProjectHolder.XPROJECT_INSTANCE;
    }


    public static final class XProjectHolder {
        private static XProjectService XPROJECT_INSTANCE = new XProjectService();
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
        File httpCacheDirectory = new File(XProjectApplication.getInstance().getCacheDir(), "responses");
        int cacheSize = 50 * 1024 * 1024; // 50 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        final OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor("ChainService").setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(headInterceptor)
                .addInterceptor(new NoNetInterceptor())
                .addNetworkInterceptor(new NetInterceptor())
                .cache(cache);

        return builder.build();
    }
}