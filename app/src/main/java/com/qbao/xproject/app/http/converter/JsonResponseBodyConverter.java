package com.qbao.xproject.app.http.converter;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.qbao.xproject.app.utility.AESUtil;
import com.qbao.xproject.app.utility.CommonUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author Created by jackieyao on 2018/9/11 下午2:16
 */


public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final String TAG = JsonResponseBodyConverter.class.getSimpleName();
    private final Gson gson;
    private final Type type;
    private boolean isDecrypt = true;//是否需要解密

    /**
     * 构造器
     */
    public JsonResponseBodyConverter(Gson gson, Type type, boolean isDecrypt) {
        this.gson = gson;
        this.type = type;
        this.isDecrypt = isDecrypt;
    }

    /**
     * 转换
     *
     * @param responseBody
     * @return
     * @throws IOException
     */
    @Override
    public T convert(@NonNull ResponseBody responseBody) throws IOException {
        if (responseBody.contentType() != null && isPlaintext(responseBody.contentType())) {
            String body = responseBody.string();
//            if (isDecrypt) {
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    String resultJson = jsonObject.getString("result");
                    if (!CommonUtility.isNull(resultJson)) {
                        String result = AESUtil.decrypt(resultJson,AESUtil.KEY);//解密
                        CommonUtility.DebugLog.e(TAG, "result = " + result);
//                        return gson.fromJson(resultJson, type);
                    }
                } catch (Exception e) {
                    CommonUtility.DebugLog.e(TAG, "Exception" );
                    e.printStackTrace();
                }
//            } else {
                CommonUtility.DebugLog.e(TAG, "不用解密 服务器返回数据：" + body);
                return gson.fromJson(body, type);
//            }
        } else {
            CommonUtility.DebugLog.log("\tbody: maybe [file part] , too large too print , ignored!");
        }
        return null;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        String subtype = mediaType.subtype();
        if (subtype != null) {
            subtype = subtype.toLowerCase();
            if (subtype.contains("x-www-form-urlencoded") ||
                    subtype.contains("json") ||
                    subtype.contains("xml") ||
                    subtype.contains("html")) //
                return true;
        }
        return false;
    }

}
