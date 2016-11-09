package com.softgen.gate.provider;

import android.content.Context;

/**
 * Created by mahesha on 25-10-16.
 */

public class SharedUtils {
    public static final String USER_NAME = "username";
    public static final String PASSWORD = "password";

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
}
