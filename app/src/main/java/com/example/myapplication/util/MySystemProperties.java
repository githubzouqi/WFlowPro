package com.example.myapplication.util;

import java.lang.reflect.Method;

/**
 * 获取机顶盒相关信息
 */
public class MySystemProperties {

    private static final String TAG = "MySystemProperties";

    public static String get(String key) {
        init();

        String value = null;

        try {
            value = (String) mGetMethod.invoke(mClassType, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public static int getInt(String key, int def) {
        init();

        int value = def;
        try {
            Integer v = (Integer) mGetIntMethod.invoke(mClassType, key, def);
            value = v.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static int getSdkVersion() {
        return getInt("ro.build.version.sdk", -1);
    }

    //-------------------------------------------------------------------
    private static Class<?> mClassType = null;
    private static Method mGetMethod = null;
    private static Method mGetIntMethod = null;

    private static void init() {
        try {
            if (mClassType == null) {
                mClassType = Class.forName("android.os.SystemProperties");

                mGetMethod = mClassType.getDeclaredMethod("get", String.class);
                mGetIntMethod = mClassType.getDeclaredMethod("getInt", String.class, int.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
