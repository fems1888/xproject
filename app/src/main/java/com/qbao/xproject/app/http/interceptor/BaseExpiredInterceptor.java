package com.qbao.xproject.app.http.interceptor;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by hubert on 2018/1/9.
 * 请求是否有效的拦截处理
 */

public abstract class BaseExpiredInterceptor implements Interceptor {
    private final String TAG = "BaseExpiredInterceptor";
    public static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }
        String bodyString = buffer.clone().readString(charset);
        boolean isText = isText(contentType);
        if (!isText) {
            return response;
        }
        //判断响应是否过期（无效）
        if (isResponseExpired(response, bodyString)) {
            return responseExpired(chain, bodyString);
        }
        return response;
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType == null)
            return false;
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 处理响应是否有效
     */
    public abstract boolean isResponseExpired(Response response, String bodyString);

    /**
     * 无效响应处理
     */
    public abstract Response responseExpired(Chain chain, String bodyString) throws IOException;
}
