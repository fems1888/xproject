package com.qbao.xproject.app.http.interceptor;


import android.support.annotation.NonNull;

import com.qbao.xproject.app.utility.AESUtil;
import com.qbao.xproject.app.utility.CommonUtility;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

import static com.qbao.xproject.app.http.interceptor.BaseExpiredInterceptor.UTF8;


/**
 * Created by hubert on 2018/1/9.
 * 加密 拦截器
 */

public class EncryptionInterceptor implements Interceptor {
    private static final String TAG = EncryptionInterceptor.class.getSimpleName();
    private HttpUrl httpUrl;
    private boolean isEncrypt = true;    //是否需要加密

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl httpUrl = request.url();
        if (isEncrypt()) {
            RequestBody oldBody = request.body();
            if (!CommonUtility.isNull(oldBody)) {
                Buffer buffer = new Buffer();
                assert oldBody != null;
                oldBody.writeTo(buffer);
                String strOldBody = buffer.readUtf8();
                MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                String strNewBody = AESUtil.encryptSign(strOldBody);
                RequestBody body = RequestBody.create(mediaType, strNewBody);
                request = request.newBuilder().header("Content-Type", body.contentType().toString()).header("Content-Length", String.valueOf(body.contentLength())).method(request.method(), body).build();
            }
            if (request.method().equals("GET")) {
                this.httpUrl = HttpUrl.parse(parseUrl(request.url().url().toString()));
                request = addGetParamsSign(request);
            } else if (request.method().equals("POST")) {
                this.httpUrl = request.url();
                request = addPostParamsSign(request);
            }
        } else {
            CommonUtility.DebugLog.e(TAG, "不加密请求 Request url = " + httpUrl.toString());
        }

        return chain.proceed(request);
    }


    //GET
    private Request addGetParamsSign(Request request) {
        HttpUrl httpUrl = request.url();
        //获取原有的参数
        Set<String> nameSet = httpUrl.queryParameterNames();
        ArrayList<String> nameList = new ArrayList<>(nameSet);
        LinkedHashMap<String, String> oldParams = new LinkedHashMap<>();
        for (int i = 0; i < nameList.size(); i++) {
            String value = httpUrl.queryParameterValues(nameList.get(i)) != null && httpUrl.queryParameterValues(nameList.get(i)).size() > 0 ? httpUrl.queryParameterValues(nameList.get(i)).get(0) : "";
            oldParams.put(nameList.get(i), value);
        }
        //拼装新的参数
        LinkedHashMap<String, String> newParams = dynamic(oldParams);
//        HttpsUtility.checkNotNull(newParams, "newParams==null");
        httpUrl = HttpUrl.parse(parseUrl(request.url().url().toString()));
        assert httpUrl != null;
        HttpUrl.Builder newBuilder = httpUrl.newBuilder();
        for (Map.Entry<String, String> entry : newParams.entrySet()) {
            newBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        httpUrl = newBuilder.build();
        request = request.newBuilder().url(httpUrl).build();
        return request;
    }

    //post
    private Request addPostParamsSign(Request request) throws UnsupportedEncodingException {
        if (request.body() instanceof FormBody) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            FormBody formBody = (FormBody) request.body();

            //原有的参数
            LinkedHashMap<String, String> oldParams = new LinkedHashMap<>();
            assert formBody != null;
            for (int i = 0; i < formBody.size(); i++) {
                oldParams.put(formBody.encodedName(i), formBody.encodedValue(i));
            }

            //拼装新的参数
            LinkedHashMap<String, String> newParams = dynamic(oldParams);
//            HttpsUtility.checkNotNull(newParams, "newParams==null");
            for (Map.Entry<String, String> entry : newParams.entrySet()) {
                String value = URLDecoder.decode(entry.getValue(), UTF8.name());
                bodyBuilder.addEncoded(entry.getKey(), value);
            }
//            String url = HttpsUtility.createUrlFromParams(httpUrl.url().toString(), newParams);
//            CommonUtility.DebugLog.e(TAG, "url = " + url);
            formBody = bodyBuilder.build();
            request = request.newBuilder().post(formBody).build();
        } else if (request.body() instanceof MultipartBody) {
            MultipartBody multipartBody = (MultipartBody) request.body();
            MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            List<MultipartBody.Part> oldParts = multipartBody.parts();

            //拼装新的参数
            List<MultipartBody.Part> newParts = new ArrayList<>(oldParts);
            LinkedHashMap<String, String> oldParams = new LinkedHashMap<>();
            LinkedHashMap<String, String> newParams = dynamic(oldParams);
            for (Map.Entry<String, String> stringStringEntry : newParams.entrySet()) {
                MultipartBody.Part part = MultipartBody.Part.createFormData(stringStringEntry.getKey(), stringStringEntry.getValue());
                newParts.add(part);
            }
            for (MultipartBody.Part part : newParts) {
                bodyBuilder.addPart(part);
            }
            multipartBody = bodyBuilder.build();
            request = request.newBuilder().post(multipartBody).build();
        }
        return request;
    }

    //解析前：https://xxx.xxx.xxx/app/chairdressing/skinAnalyzePower/skinTestResult?appId=10101
    //解析后：https://xxx.xxx.xxx/app/chairdressing/skinAnalyzePower/skinTestResult
    private String parseUrl(String url) {
        if (!"".equals(url) && url.contains("?")) {// 如果URL不是空字符串
            url = url.substring(0, url.indexOf('?'));
        }
        return url;
    }

    public LinkedHashMap<String, String> dynamic(LinkedHashMap<String, String> oldParams) {
        LinkedHashMap<String, String> newParams = new LinkedHashMap<>();
        for (Map.Entry<String, String> stringStringEntry : oldParams.entrySet()) {
            newParams.put(stringStringEntry.getKey(), AESUtil.encryptSign(stringStringEntry.getValue()));
        }
        return newParams;
    }


    public boolean isEncrypt() {
        return isEncrypt;
    }

    public EncryptionInterceptor setEncrypt(boolean encrypt) {
        isEncrypt = encrypt;
        return this;
    }
}
