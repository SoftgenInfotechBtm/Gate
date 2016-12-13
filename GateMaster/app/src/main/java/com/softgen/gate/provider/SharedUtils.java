package com.softgen.gate.provider;

import android.content.Context;

/**
 * Created by mahesha on 25-10-16.
 */

public class SharedUtils {
    public static final String USER_NAME = "username";
    public static final String PASSWORD = "password";
    public static final String IS_LOGOUT = "is_logout";
    public static final String IS_USER_PASSWORD = "user_password";
    public static final String USER_ID = "user_id";

    public static String getUserName(Context context) {
        return context.getSharedPreferences(ConstantProperties.USER_FILE,
                Context.MODE_PRIVATE).getString(
                USER_NAME, null);
    }

    public static void storeUserName(Context context, String name) {
        context.getSharedPreferences(ConstantProperties.USER_FILE,
                Context.MODE_PRIVATE).edit()
                .putString(USER_NAME, name).apply();
    }

    public static int getUserID(Context context) {
        return context.getSharedPreferences(ConstantProperties.USER_FILE,
                Context.MODE_PRIVATE).getInt(
                USER_ID, 0);
    }

    public static void storeUserID(Context context, int name) {
        context.getSharedPreferences(ConstantProperties.USER_FILE,
                Context.MODE_PRIVATE).edit()
                .putInt(USER_ID, name).apply();
    }


    public static String getPassword(Context context) {
        return context.getSharedPreferences(ConstantProperties.USER_FILE,
                Context.MODE_PRIVATE).getString(
                PASSWORD, null);
    }

    public static void storePasword(Context context, String name) {
        context.getSharedPreferences(ConstantProperties.USER_FILE,
                Context.MODE_PRIVATE).edit()
                .putString(PASSWORD, name).apply();
    }

    public static void saveLoginDisabled(Context context, boolean isLoginDisabled) {
        context.getSharedPreferences(ConstantProperties.USER_FILE, Context.MODE_PRIVATE)
                .edit().putBoolean(IS_LOGOUT, isLoginDisabled).apply();
    }

    public static boolean isLoginDisabled(Context context) {
        return context.getSharedPreferences(ConstantProperties.USER_FILE, Context.MODE_PRIVATE)
                .getBoolean(IS_LOGOUT, false);
    }

    public static void saveUserPassword(Context context, boolean isLoginDisabled) {
        context.getSharedPreferences(ConstantProperties.USER_FILE, Context.MODE_PRIVATE)
                .edit().putBoolean(IS_USER_PASSWORD, isLoginDisabled).apply();
    }

    public static boolean isUserPassword(Context context) {
        return context.getSharedPreferences(ConstantProperties.USER_FILE, Context.MODE_PRIVATE)
                .getBoolean(IS_USER_PASSWORD, false);
    }

}
