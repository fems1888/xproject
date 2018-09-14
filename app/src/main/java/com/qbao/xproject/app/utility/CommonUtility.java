package com.qbao.xproject.app.Utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.qbao.xproject.app.BuildConfig;

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
//            return true;
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
}
