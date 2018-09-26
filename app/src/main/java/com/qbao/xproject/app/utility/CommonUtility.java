package com.qbao.xproject.app.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.qbao.xproject.app.BuildConfig;
import com.qbao.xproject.app.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Created by jackieyao on 2018/9/11 上午11:18.
 */

public class CommonUtility {
    /**
     * 将多个对象拼接成字符串
     *
     * @param object
     * @return
     */
    public static String formatString(Object... object) {
        StringBuilder builder = new StringBuilder();
        for (Object o : object) {
            if (o != null) {
                builder.append(o);
            }
        }
        return builder.toString();
    }


    /**
     * method desc：判断参数值是否为空 null，空字符串，或者全部空格字符串或者"null"字符串都视为空
     *
     * @param o
     * @return
     */
    public static boolean isNull(Object o) {
        try {
            return null == o || "".equals(o.toString().replaceAll(" ", ""))
                    || "null".equals(o.toString());
        } catch (Exception e) {
        }
        return true;
    }

    /**
     * 获取application中指定的meta-data。本例中，调用方法时key就是UMENG_CHANNEL
     *
     * @return 如果没有获取成功(没有对应值 ， 或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    /**
     * Get the screen width.
     *
     * @param context
     * @return the screen width
     */
    @SuppressLint("NewApi")
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * Get the screen height.
     *
     * @param context
     * @return the screen width
     */
    @SuppressLint("NewApi")
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * method desc：将dipValue换算成px
     *
     * @param context
     * @param dipValue
     * @return
     */

    public static int dip2px(Context context, float dipValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }

    public static class SharedPreferencesUtility {

        private static SharedPreferences mSharedPreferences = null;

        public static SharedPreferences getSharedPreferences(Context context) {
            if (isNull(mSharedPreferences)) {
                mSharedPreferences = context.getSharedPreferences("qbao_network", 0);
            }
            return mSharedPreferences;
        }

        public static SharedPreferences getSharedPreferences(Context context, String prefName) {
            return context.getSharedPreferences(prefName, 0);
        }

        /**
         * 通过PrefName放置相关的数据
         *
         * @param context
         * @param prefName
         * @param key
         * @param value
         */
        public static void put(Context context, String prefName, String key, Object value) {
            if (value != null) {
                if (value instanceof String) {
                    getSharedPreferences(context, prefName).edit().putString(key, value.toString())
                            .commit();
                } else if (value instanceof Integer) {
                    getSharedPreferences(context, prefName).edit()
                            .putInt(key, Integer.parseInt(value.toString()))
                            .commit();
                } else if (value instanceof Long) {
                    getSharedPreferences(context, prefName).edit()
                            .putLong(key, Long.parseLong(value.toString()))
                            .commit();
                } else if (value instanceof Boolean) {
                    getSharedPreferences(context, prefName).edit().putBoolean(key, Boolean.parseBoolean(value.toString())).commit();
                }
            } else {
                getSharedPreferences(context, prefName).edit().putString(key, null).commit();
            }
        }

        /**
         * @param key
         * @param value
         */
        public static void put(Context context, String key, Object value) {

            if (value != null) {
                if (value instanceof String) {
                    getSharedPreferences(context).edit().putString(key, value.toString())
                            .commit();
                } else if (value instanceof Integer) {
                    getSharedPreferences(context).edit()
                            .putInt(key, Integer.parseInt(value.toString()))
                            .commit();
                } else if (value instanceof Long) {
                    getSharedPreferences(context).edit()
                            .putLong(key, Long.parseLong(value.toString()))
                            .commit();
                } else if (value instanceof Boolean) {
                    getSharedPreferences(context).edit().putBoolean(key, Boolean.parseBoolean(value.toString())).commit();
                } else if (value instanceof Float) {
                    getSharedPreferences(context).edit().putFloat(key, Float.parseFloat(value.toString())).commit();
                } else {
                    getSharedPreferences(context).edit().putString(key, value.toString())
                            .commit();
                }
            } else {
                getSharedPreferences(context).edit().putString(key, null).commit();
            }
        }

        public static String getString(Context context, String prefName, String key, String defaultValue) {
            return getSharedPreferences(context, prefName).getString(key, defaultValue);
        }

        public static int getInt(Context context, String prefName, String key, int defaultValue) {
            return getSharedPreferences(context, prefName).getInt(key, defaultValue);
        }

        public static long getLong(Context context, String prefName, String key, long defaultValue) {
            return getSharedPreferences(context, prefName).getLong(key, defaultValue);
        }

        public static boolean getBoolean(Context context, String prefName, String key, boolean defaultValue) {
            return getSharedPreferences(context, prefName).getBoolean(key, defaultValue);
        }

        public static float getFloat(Context context, String prefName, String key, float defaultValue) {
            return getSharedPreferences(context, prefName).getFloat(key, defaultValue);
        }

        public static boolean contains(Context context, String prefName, String key) {
            return getSharedPreferences(context, prefName).contains(key);
        }

        public static void remove(Context context, String prefName, String key) {
            getSharedPreferences(context, prefName).edit().remove(key).commit();
        }

        public static String getString(Context context, String key, String defaultValue) {
            return getSharedPreferences(context).getString(key, defaultValue);
        }

        public static int getInt(Context context, String key, int defaultValue) {
            return getSharedPreferences(context).getInt(key, defaultValue);
        }

        public static long getLong(Context context, String key, long defaultValue) {
            return getSharedPreferences(context).getLong(key, defaultValue);
        }

        public static boolean getBoolean(Context context, String key, boolean defaultValue) {

            return getSharedPreferences(context).getBoolean(key, defaultValue);
        }

        public static boolean contains(Context context, String key) {
            return getSharedPreferences(context).contains(key);
        }

        public static void remove(Context context, String key) {
            getSharedPreferences(context).edit().remove(key).commit();
        }

        public static void clear(Context context) {
            getSharedPreferences(context).edit().clear().commit();
        }

        public static void clear(Context context, String prefName) {
            getSharedPreferences(context, prefName).edit().clear().commit();
        }

        public SharedPreferences getSharedPreference() {
            return mSharedPreferences;
        }
    }

    public static class DebugLog {

        private static final String TAG_LOG = "AetherCoder";

        private static final boolean DEBUG = true;

        static String className;
        static String methodName;
        static int lineNumber;

        public static void log(Object message) {
            if (!isDebuggable())
                return;

            // Throwable instance must be created before any methods
            getMethodNames(new Throwable().getStackTrace());
            Log.e(TAG_LOG, createLog(message));
        }

        public static boolean isDebuggable() {
            return !releaseBuild();
        }

        public static boolean releaseBuild() {
            return BuildConfig.BUILD_TYPE.equals("release");
        }

        private static String createLog(Object log) {
            return CommonUtility.formatString(className, ":", "[", methodName, ":", lineNumber, "]", log);
        }

        private static String createLogWithClassName(Object log) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("[");
            buffer.append(className);
            buffer.append(":");
            buffer.append(methodName);
            buffer.append(":");
            buffer.append(lineNumber);
            buffer.append("]");
            buffer.append(log);

            return buffer.toString();
        }

        private static void getMethodNames(StackTraceElement[] sElements) {
            className = sElements[1].getFileName();
            methodName = sElements[1].getMethodName();
            lineNumber = sElements[1].getLineNumber();
        }

        public static void e(Object message) {
            if (!isDebuggable())
                return;

            // Throwable instance must be created before any methods
            getMethodNames(new Throwable().getStackTrace());
            Log.e(className, createLog(message));
        }

        public static void e(String key, Object message) {
            if (CommonUtility.isNull(message)) {
                message = "null";
            }
            if (!isDebuggable())
                return;

            // Throwable instance must be created before any methods
            getMethodNames(new Throwable().getStackTrace());
            Log.e(key, createLogWithClassName(message));
        }


        public static void i(Object message) {
            if (!isDebuggable())
                return;

            getMethodNames(new Throwable().getStackTrace());
            Log.i(className, createLog(message));
        }

        public static void d(Object message) {
            if (!isDebuggable())
                return;

            getMethodNames(new Throwable().getStackTrace());
            Log.d(className, createLog(message));
        }

        public static void v(Object message) {
            if (!isDebuggable())
                return;

            getMethodNames(new Throwable().getStackTrace());
            Log.v(className, createLog(message));
        }

        public static void w(Object message) {
            if (!isDebuggable())
                return;

            getMethodNames(new Throwable().getStackTrace());
            Log.w(className, createLog(message));
        }

        public static void wtf(Object message) {
            if (!isDebuggable())
                return;

            getMethodNames(new Throwable().getStackTrace());
            Log.wtf(className, createLog(message));
        }

    }
    public static final class JSONObjectUtility {

        public static final Gson GSON = new Gson();

        public static String optString(JSONObject object, String key) {
            try {
                String text = object.optString(key);
                return isNull(text) ? null : text;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public static <T> ArrayList<T> convertJSONArray2Array(JSONArray jsonArray, Class<T> c) {
            ArrayList<T> objects = new ArrayList<>();
            if (!CommonUtility.isNull(jsonArray)) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    objects.add(convertJSONObject2Obj(jsonArray.optJSONObject(i), c));
                }
            }
            return objects;
        }

        public static <T> ArrayList<T> convertJSONArray2Array(JSONArray jsonArray, Class<T> c, String addKey, String addValueKey) {
            ArrayList<T> objects = new ArrayList<>();
            if (!CommonUtility.isNull(jsonArray)) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    objects.add(convertJSONObject2Obj(jsonArray.optJSONObject(i), c, addKey, addValueKey));
                }
            }
            return objects;
        }

        /**
         * @param jsonArray
         * @param c
         * @param <T>
         * @return
         */
        public static <T> ArrayList<T> convertJSONArray2ArrayReflect(JSONArray jsonArray, Class<T> c) {
            ArrayList<T> objects = new ArrayList<>();
            if (!CommonUtility.isNull(jsonArray)) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    objects.add(convertJSONObject2ObjReflect(jsonArray.optJSONObject(i), c));
                }
            }
            return objects;
        }

        public static <T> T convertJSONObject2Obj(JSONObject jsonObject, Class<T> c) {
            return convertJSONObject2Obj(jsonObject.toString(), c);
        }

        public static <T> T convertJSONObject2Obj(String jsonStr, Class<T> c) {
            if (!isNull(jsonStr)) {
                try {
                    return GSON.fromJson(jsonStr.toString(), c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                return c.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static <T> T convertJSONObject2Obj(JSONObject jsonObject, Class<T> c, String addKey, String addValueKey) {
            if (!isNull(addKey) && !isNull(addValueKey)) {
                try {
                    jsonObject.put(addKey, jsonObject.optString(addValueKey));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return GSON.fromJson(jsonObject.toString(), c);
        }

        public static <T> T convertJSONObject2ObjReflect(JSONObject jsonObject, Class<T> c) {

            T o = null;
            try {
                o = c.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!isNull(jsonObject)) {
                Field[] fields = c.getDeclaredFields();
                for (Field field : fields) {
                    String fieldName = field.getName();
                    if (!fieldName.equals("serialVersionUID") && !fieldName.equals("_id")) {
                        field.setAccessible(true);
                        String fieldValue = jsonObject.optString(fieldName);
                        try {
                            field.set(o, fieldValue);
                        } catch (Exception e) {
                            DebugLog.log(fieldName + "==========error");
                        }
                    }
                }
            }
            return o;
        }

        public static String map2JSONObject(HashMap<String, Object> params) {
            StringBuilder builder = new StringBuilder();
            for (Iterator it = params.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
                if (!isNull(entry.getValue())) {
                    builder.append(entry.getKey()).append(entry.getValue());
                }
            }
            return builder.toString();
        }

        public static <T> T clone(T t) {
            return (T) GSON.fromJson(GSON.toJson(t), t.getClass());
        }
    }

    /**
     * 根据时间获取时间戳
     *
     * @param time
     * @return
     */
    public static long byTimeGetMillis(String time) {
        //Date或者String转化为时间戳
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            return System.currentTimeMillis();
        }
        return date.getTime();
    }

    public static String getFormatDouble(double value){
        return new BigDecimal(value).setScale(6,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
    }
    public static String getFormatDoubleTwo(double value){
        return new BigDecimal(value).setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString();
    }


    /**
     * 获取磁盘上的bitmaps
     *
     * @param pathString
     * @return
     */
    public static Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
            CommonUtility.DebugLog.d("getDiskBitmap e" + e.getMessage());
        }
        return bitmap;
    }

    /**
     * 缩放Bitmap满屏
     *
     * @param bitmap
     * @param screenWidth
     * @param screenHeight
     * @return
     */
    public static Bitmap getBitmap(Bitmap bitmap, float screenWidth,
                                   float screenHeight) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        if (w == screenWidth && h == screenHeight) return bitmap;
        Matrix matrix = new Matrix();
        float widthScale = screenWidth / w;
        float heightScale = screenHeight / h;

        float scale = widthScale < heightScale ? widthScale : heightScale;
        matrix.postScale(scale, scale);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        if (bitmap != null && !bitmap.equals(bmp) && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        return bmp;
    }
    /**
     * 处理输入框0开头的情况
     *
     * @param editText
     */
    public static void setEditTextZeroStart(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    /**
     * 秒转化为天小时分秒字符串（dd-hh-mm-ss）
     *
     * @param seconds
     * @return String
     */
    public static String formatSecondsStandard(Context context, long seconds) {
        String timeStr = seconds + "";
        String secondStr = "", minStr = "", hourStr = "";
        if (seconds > 60) {
            long second = seconds % 60;
            long min = seconds / 60;
            timeStr = CommonUtility.formatString(min, ":", second);
            secondStr = formatString(second);
            if (second < 10) {
                secondStr = formatString("0", second);
            }
            if (min > 60) {
                min = (seconds / 60) % 60;
                minStr = formatString(min);
                if (min < 10) {
                    minStr = formatString("0", minStr);
                }
                long hour = (seconds / 60) / 60;
                hourStr = formatString(hour);
                if (hour < 10) {
                    hourStr = formatString("0", hour);
                }
                timeStr = CommonUtility.formatString(hourStr, ":", minStr, ":", secondStr);
                if (hour > 24) {
                    hour = ((seconds / 60) / 60) % 24;
                    hourStr = formatString(hour);
                    if (hour < 10) {
                        hourStr = formatString("0", hour);
                    }
                    long day = (((seconds / 60) / 60) / 24);
                    if (day>1){

                        timeStr = CommonUtility.formatString(day,context.getString(R.string.day), " ", hourStr, ":", minStr, ":", secondStr);
                    }else {
                        timeStr = CommonUtility.formatString(hourStr, ":", minStr, ":", secondStr);
                    }
                }
            } else {
                minStr = formatString(min);
                if (min < 10) {
                    minStr = formatString("0", minStr);
                }
                timeStr = CommonUtility.formatString("00", ":", minStr, ":", secondStr);
            }
        } else {
            secondStr = formatString(seconds);
            if (seconds < 10) {
                secondStr = formatString("0", seconds);
            }
            timeStr = CommonUtility.formatString("00", ":", "00", ":", secondStr);
        }
        return timeStr;
    }
}
