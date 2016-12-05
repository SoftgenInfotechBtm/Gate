package com.softgen.gate.provider;

import android.content.Context;

/**
 * Created by mahesha on 25-10-16.
 */

public class SharedUtils {
    public static final String USER_NAME = "username";
    public static final String PASSWORD = "password";
    public static final String IS_LOGOUT = "is_logout";

    public static String getUserName(Context context) {
        return context.getSharedPreferences(ConstantProperties.USER_FILE,
                Context.MODE_PRIVATE).getString(
                USER_NAME, null);
    }

    public static void storeUserName(Context context, String name) {
        context.getSharedPreferences(ConstantProperties.USER_FILE,
                Context.MODE_PRIVATE).edit()
                .putString(USER_NAME, name).commit();
    }


    public static String getPassword(Context context) {
        return context.getSharedPreferences(ConstantProperties.USER_FILE,
                Context.MODE_PRIVATE).getString(
                PASSWORD, null);
    }

    public static void storePasword(Context context, String name) {
        context.getSharedPreferences(ConstantProperties.USER_FILE,
                Context.MODE_PRIVATE).edit()
                .putString(PASSWORD, name).commit();
    }

    public static void saveLoginDisabled(Context context, boolean isLoginDisabled) {
        context.getSharedPreferences(ConstantProperties.USER_FILE, Context.MODE_PRIVATE)
                .edit().putBoolean(IS_LOGOUT, isLoginDisabled).commit();
    }

    public static boolean isLoginDisabled(Context context) {
        return context.getSharedPreferences(ConstantProperties.USER_FILE, Context.MODE_PRIVATE)
                .getBoolean(IS_LOGOUT, false);
    }
}
