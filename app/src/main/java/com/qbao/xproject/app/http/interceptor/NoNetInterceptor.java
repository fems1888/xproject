package com.qbao.xproject.app.http.interceptor;


import com.qbao.xproject.app.utility.CheckNetwork;
import com.qbao.xproject.app.XProjectApplication;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Created by jackieyao on 2018/9/11 下午2:28
 */


public class NoNetInterceptor implements Interceptor {
    private final String TAG = NoNetInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        boolean connected = CheckNetwork.isNetworkConnected(XProjectApplication.getInstance());
        // 如果没有网络，则启用 FORCE_CACHE
        if (!connected) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
//            CommonUtility.DebugLog.e(TAG, "无网络设置_common");

            Response response = chain.proceed(request);
            return response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + 3600)
                    .removeHeader("Pragma")
                    .build();
        }
        //有网络的时候，这个拦截器不做处理，直接返回
        return chain.proceed(request);
    }
}
