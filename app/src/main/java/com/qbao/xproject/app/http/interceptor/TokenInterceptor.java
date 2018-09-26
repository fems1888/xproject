package com.qbao.xproject.app.http.interceptor;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.qbao.xproject.app.XProjectApplication;
import com.qbao.xproject.app.entity.http.ApiResult;
import com.qbao.xproject.app.http.ExceptionHandle;
import com.qbao.xproject.app.manager.AccessTokenManager;
import com.qbao.xproject.app.manager.AccountManager;
import com.qbao.xproject.app.manager.Constants;
import com.qbao.xproject.app.request_body.UserLoginRequest;
import com.qbao.xproject.app.utility.AESUtil;
import com.qbao.xproject.app.utility.CommonUtility;
import com.qbao.xproject.app.utility.MD5Util;
import com.qbao.xproject.app.utility.RxSchedulers;
import com.qbao.xproject.app.viewmodel.RefreshTokenViewModel;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.reactivex.functions.Consumer;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Created by jackieyao on 2018/9/25 下午6:03
 */

public class TokenInterceptor extends BaseExpiredInterceptor {
    private static final String TAG = TokenInterceptor.class.getSimpleName();
    private ApiResult apiResult;

    @Override
    public boolean isResponseExpired(Response response, String bodyString) {
        try {
            JSONObject jsonObject = new JSONObject(bodyString);
            String resultJson = jsonObject.getString("result");
            if (!CommonUtility.isNull(resultJson)) {
                String result = AESUtil.decrypt(resultJson,AESUtil.KEY);//解密
                apiResult = new Gson().fromJson(result, ApiResult.class);
                if (apiResult != null) {
                    int code = apiResult.getStatus();
                    //Token 失效
                    if (code == ExceptionHandle.UNAUTHORIZED) {
                        CommonUtility.DebugLog.e(TAG, "Token 失效了》》》");
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            //每次进入先判断上次登录的时间是否和现在差一天  如果差一天就重新刷新token
            String time = AccountManager.getInstance().getAccountEntity().getLoginTime();
            long loginTime = TextUtils.isEmpty(time)?System.currentTimeMillis():CommonUtility.byTimeGetMillis(time);
            if (System.currentTimeMillis() - loginTime> Constants.TEN_MINUTE_MILLS){
                refreshToken();
            }
            return false;
        }

        return false;
    }

    String token;

    @Override
    public Response responseExpired(Chain chain, String bodyString) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        CommonUtility.DebugLog.e("接口返回的CODE=",apiResult.getStatus()+"");
        if (ExceptionHandle.UNAUTHORIZED == apiResult.getStatus()) {
            XProjectApplication.getInstance().logout();

        }
//            if (lock.tryLock()) {
//                try {
//                    CommonUtility.DebugLog.e(TAG, "获取Token中...，持有lock》》》");
//                    token = refreshToken();
//                    return processError(chain, chain.request());
//                } catch (Exception e) {
//                    lock.unlock();
//                } finally {
//                    CommonUtility.DebugLog.e(TAG, "Token获取完成，释放 lock》》》");
//                    lock.unlock();
//                }
//            } else {
//                CommonUtility.DebugLog.e(TAG, "等待Token获取完成》》》》");
//                lock.lock();
//                lock.unlock();
//                CommonUtility.DebugLog.e(TAG, "Token 刷新完成，重新发送请求》》》");
//                return processError(chain, chain.request());
//            }
//        }
        return response;
    }

    /**
     * 处理网络请求出现的业务错误
     *
     * @return
     * @throws IOException
     */
    private Response processError(Chain chain, Request request) {
        Context context = XProjectApplication.getInstance().getApplicationContext();
        String token = AccessTokenManager.getInstance().getAccessToken();
        Request.Builder builder = request.newBuilder();
        HttpUrl httpUrl = request.url();
        String decodeUrl = null;
        try {
            decodeUrl = URLDecoder.decode(httpUrl.toString(), "UTF-8");
            String timeStamp = String.valueOf(System.currentTimeMillis());
            int randomValue = (int) ((Math.random() * 9 + 1) * 1000);
            String signNative = MD5Util.encodeMD5(AESUtil.formatString(timeStamp, decodeUrl, CommonUtility.formatString(randomValue), token));

//            String buildName = getAppMetaData(context, "UMENG_CHANNEL");
//            CommonUtility.DebugLog.e(TAG, "buildName = " + buildName);
            builder.addHeader("X-S", signNative)
                    .addHeader("X-T", token)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("Connection", "close")
                    .addHeader("Accept", "*/*")
                    .addHeader("User-Agent", "Android Phone")
                    .addHeader("Device-Type", "Android")
                    .addHeader("APP-VersionName", "XProject")
                    .addHeader("APP-Version", "1.0")
                    .addHeader("APP-Code", "100")
                    .addHeader("Source-Type", "")
                    .addHeader("Accept-Language", "zh")
                    .addHeader("X-TS", timeStamp)
                    .addHeader("X-R", CommonUtility.formatString(randomValue));
            request = builder.build();
            return chain.proceed(request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Lock lock = new ReentrantLock();

    private void refreshToken() {
        UserLoginRequest request = new UserLoginRequest();
        request.setPhone("");
        RefreshTokenViewModel viewModel = new RefreshTokenViewModel(XProjectApplication.getInstance(),TAG);
        viewModel.refreshToken(request)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String str) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }
}
