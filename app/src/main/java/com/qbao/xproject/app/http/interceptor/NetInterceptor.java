package com.qbao.xproject.app.http.interceptor;

import com.qbao.xproject.app.Utility.CheckNetwork;
import com.qbao.xproject.app.Utility.CommonUtility;
import com.qbao.xproject.app.XProjectApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Created by jackieyao on 2018/9/11 下午2:28
 */


public class NetInterceptor implements Interceptor {
    private final String TAG = NetInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        boolean connected = CheckNetwork.isNetworkConnected(XProjectApplication.getInstance());
        if (connected) {
            //如果有网络，缓存90s
            CommonUtility.DebugLog.e(TAG, "print");
            Response response = chain.proceed(request);
            int maxTime = 0;
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxTime)
                    .build();
        }
        //如果没有网络，不做处理，直接返回
        return chain.proceed(request);
    }
}
