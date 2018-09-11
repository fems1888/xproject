package com.qbao.xproject.app.http.converter;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author Created by jackieyao on 2018/9/11 下午2:16
 */

public class JsonConverterFactory extends Converter.Factory {
    private boolean isDecrypt = true;

    public static JsonConverterFactory create() {
        return create(new Gson(), true);
    }

    public static JsonConverterFactory create(boolean isDecrypt) {
        return create(new Gson(), isDecrypt);
    }

    public static JsonConverterFactory create(Gson gson, boolean isDecrypt) {
        return new JsonConverterFactory(gson, isDecrypt);
    }

    private final Gson gson;

    private JsonConverterFactory(Gson gson, boolean isDecrypt) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
        this.isDecrypt = isDecrypt;
    }

    public void setDecrypt(boolean isDecrypt) {
        this.isDecrypt = isDecrypt;
    }


    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new JsonRequestBodyConverter<>(gson, adapter);
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new JsonResponseBodyConverter<>(gson, type, isDecrypt);  //响应
    }
}
