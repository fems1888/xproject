package com.qbao.xproject.app.http.interceptor;

import android.content.Context;


import com.qbao.xproject.app.Utility.CommonUtility;

import java.io.IOException;
import java.net.URLDecoder;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.qbao.xproject.app.Utility.CommonUtility.getAppMetaData;

/**
 * @author Created by jackieyao on 2018/9/11 下午2:28
 */

public class HttpHeadInterceptor implements Interceptor {
    private final String TAG = HttpHeadInterceptor.class.getSimpleName();

    private Context mContext;

    public HttpHeadInterceptor(Context context) {
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        HttpUrl httpUrl = request.url();
        String decodeUrl = URLDecoder.decode(httpUrl.toString(), "UTF-8");
//        String token = "";
//        try {
//            token = AccessTokenManager.getInstance().getAccessToken();
//            if (!CommonUtility.Utility.isNull(token)) {
//                builder.addHeader("X-T", token);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        String timeStamp = String.valueOf(System.currentTimeMillis());
        int randomValue = (int) ((Math.random() * 9 + 1) * 1000);
//        String signNative = BCrypt.hashpw(filterUIL(decodeUrl) ? AESUtil.formatPayString(timeStamp, decodeUrl, CommonUtility.formatString(randomValue), token): AESUtil.formatString(timeStamp, decodeUrl, CommonUtility.formatString(randomValue), token), BCrypt.gensalt());
//        String signNative = BCrypt.hashpw(AESUtil.formatString(timeStamp, decodeUrl, CommonUtility.formatString(randomValue), token), BCrypt.gensalt());
        String buildName = getAppMetaData(mContext, "UMENG_CHANNEL");
        CommonUtility.DebugLog.e(TAG, "buildName = " + buildName);
//        builder.addHeader("X-S", signNative);
        builder
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "close")
                .addHeader("Accept", "*/*")
                .addHeader("User-Agent", "Android Phone")
                .addHeader("Device-Type", "Android")
//                .addHeader("APP-VersionName", CommonUtility.UIUtility.getVersionName(mContext))
//                .addHeader("APP-Version", CommonUtility.UIUtility.getVersionName(mContext).substring(0, 3))
//                .addHeader("APP-Code", CommonUtility.UIUtility.getVersionCode(mContext) + "")
//                .addHeader("Source-Type", buildName)
//                .addHeader("Accept-Language", QtumSharedPreference.getInstance().getLanguage(mContext))
                .addHeader("X-TS", timeStamp)
                .addHeader("X-R", CommonUtility.formatString(randomValue));
        request = builder.build();
        return chain.proceed(request);
    }

}
