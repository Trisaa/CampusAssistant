package com.campus.android.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.campus.android.MainApplication;

public class SharedPreferencesUtils {
    public static final String KEY_IS_LOGINED = "KEY_IS_LOGINED";
    public static final String KEY_STUDENT_ID = "KEY_STUDENT_ID";

    public static SharedPreferences getSharedPreferenced(Context context) {
        return context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public static boolean getUserLogined() {
        return getSharedPreferenced(MainApplication.getAppContext()).getBoolean(KEY_IS_LOGINED, false);
    }

    /**
     * 保存boolean型到SharedPreferences
     *
     * @param key
     * @param value
     */
    public static void saveBoolean(String key, boolean value) {
        getSharedPreferenced(MainApplication.getAppContext()).edit().putBoolean(key, value).commit();
    }

    /**
     * 从SharedPreferences读取boolean型
     *
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(String key, boolean defValue) {
        return getSharedPreferenced(MainApplication.getAppContext()).getBoolean(key, defValue);
    }

    /**
     * 保存String型到SharedPreferences
     *
     * @param key
     * @param value
     */
    public static void saveString(String key, String value) {
        getSharedPreferenced(MainApplication.getAppContext()).edit().putString(key, value).commit();
    }

    /**
     * 从SharedPreferebces中获取String
     *
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(String key, String defValue) {
        return getSharedPreferenced(MainApplication.getAppContext()).getString(key, defValue);
    }

    public static void saveInt(String key, int value) {
        getSharedPreferenced(MainApplication.getAppContext()).edit().putInt(key, value).commit();
    }

    public static int getInt(String key, int defValue) {
        return getSharedPreferenced(MainApplication.getAppContext()).getInt(key, defValue);
    }

    public static void saveLong(String key, long value) {
        getSharedPreferenced(MainApplication.getAppContext()).edit().putLong(key, value).commit();
    }

    public static long getLong(String key, long defValue) {
        return getSharedPreferenced(MainApplication.getAppContext()).getLong(key, defValue);
    }
}
