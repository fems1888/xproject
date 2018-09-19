package com.qbao.xproject.app.http;

import android.content.res.Resources;

import com.google.gson.JsonParseException;
import com.qbao.xproject.app.utility.CommonUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;


/**
 * Created by hubert on 2017/9/20.
 */

public class ExceptionHandle {
    public static final int BAD_REQUEST = 400;//服务器不理解请求的语法。
    public static final int UNAUTHORIZED = 401;//请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应
    private static final int FORBIDDEN = 403;//服务器拒绝请求
    private static final int NOT_FOUND = 404;//服务器找不到请求的网页
    private static final int REQUEST_TIMEOUT = 408;//服务器等候请求时发生超时。
    private static final int INTERNAL_SERVER_ERROR = 500;//服务器遇到错误，无法完成请求。
    private static final int BAD_GATEWAY = 502;//服务器充当网关或代理，从上游服务器收到无效响应
    private static final int SERVICE_UNAVAILABLE = 503;//服务器目前无法使用（由于超载或停机维护）。 通常，这只是暂时状态。
    private static final int GATEWAY_TIMEOUT = 504;//服务器充当网关或代理，但没有及时从上游服务器收到请求。
    private static final String TAG = "ResponseThrowable";

    public static ResponseThrowable handleException(Throwable e, Resources resources) {
        ResponseThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseThrowable(e.getMessage(), ERROR.HTTP_ERROR);
            CommonUtility.DebugLog.e(TAG, "Http Code = " + httpException.code());
            switch (httpException.code()) {
                //Client 请求参数错误
                case BAD_REQUEST:
                    try {
                        String errorBody = ((HttpException) e).response().errorBody().string();
                        CommonUtility.DebugLog.e(TAG, "errorBody = " + errorBody);
                        if (!CommonUtility.isNull(errorBody)) {
                            JSONObject jsonObject = new JSONObject(errorBody);
                            String message = jsonObject.optString("message");
                            CommonUtility.DebugLog.e(TAG, "message = " + message);
                            ex.errorCode = jsonObject.optInt("errorCode");
                            CommonUtility.DebugLog.e(TAG, "errorCode = " + ex.errorCode);
                            ex.message = message;
                        } else {
                            ex.errorCode = BAD_REQUEST;
                            ex.message = "失败";
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        ex.message = e1.getMessage();
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                        ex.message = e1.getMessage();
                    }
                    break;
                case UNAUTHORIZED:
                    ex.errorCode = UNAUTHORIZED;
                    CommonUtility.DebugLog.e(TAG, "Token Failed");
                    break;
                case FORBIDDEN:
                case NOT_FOUND:
                    ex.message = "";
                    break;
                case REQUEST_TIMEOUT:
                    ex.message = "";
                    break;
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                case GATEWAY_TIMEOUT:
                    ex.message = "";
                    break;
                default:
                    //ex.code = httpException.code();
                    ex.message = "";
                    break;
            }
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            ex = new ResponseThrowable(e.getMessage(), REQUEST_TIMEOUT);
            ex.message = "";
            return ex;
        } else {
            if (e instanceof ResponseThrowable) {
                ex = (ResponseThrowable) e;
                ex.message = e.getMessage();
                return ex;
            } else if (e instanceof RuntimeException) {
                ex = new ResponseThrowable(e.getMessage(), ERROR.RUN_TIME_EXCEPTION);
                ex.message = e.getMessage();
                return ex;
            } else if (e instanceof ServerException) {
                ServerException resultException = (ServerException) e;
                ex = new ResponseThrowable(resultException.getMessage(), resultException.code);
                ex.message = resultException.message;
                return ex;
            } else if (e instanceof JsonParseException
                    || e instanceof JSONException
                /*|| e instanceof ParseException*/) {
                ex = new ResponseThrowable(e.getMessage(), ERROR.PARSE_ERROR);
//                ex.message = resources.getString(R.string.parse_the_error);
                return ex;
            } else if (e instanceof ConnectException) {
                ex = new ResponseThrowable(e.getMessage(), ERROR.NETWORD_ERROR);
//                ex.message = resources.getString(R.string.connection_failed);
                return ex;
            } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
                ex = new ResponseThrowable(e.getMessage(), ERROR.SSL_ERROR);
//                ex.message = resources.getString(R.string.certificate_failed);
                return ex;
            } else {
                ex = new ResponseThrowable(e.getMessage(), ERROR.UNKNOWN);
                ex.message = "";
                return ex;
            }
        }
    }


    /**
     * 约定异常
     */
    public static class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;

        public static final int RUN_TIME_EXCEPTION = 1006;
    }

    public static class ResponseThrowable extends Exception {
        public int code;
        public String message;
        public int errorCode;

        private BigDecimal feeBigDecimal;

        private String amount;

        private String toAddress;

        public String getAmount() {
            return amount;
        }

        public String getToAddress() {
            return toAddress;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        @Override
        public String getMessage() {
            return message;
        }

        public BigDecimal getFeeBigDecimal() {
            return feeBigDecimal;
        }

        public ResponseThrowable(String message, int code) {
            this.message = message;
            this.code = code;
        }

        public ResponseThrowable(String message, int code, BigDecimal feeBigDecimal, String amount, String toAddress) {
            this.message = message;
            this.code = code;
            this.feeBigDecimal = feeBigDecimal;
            this.amount = amount;
            this.toAddress = toAddress;
        }
    }

    /**
     * ServerException发生后，将自动转换为ResponeThrowable返回
     */
    class ServerException extends RuntimeException {
        int code;
        String message;
    }

}
