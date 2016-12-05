package com.softgen.gate.utility;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;

import java.util.regex.Pattern;

/**
 * Created by mahesha on 18-11-16.
 */

public class Utils {
    public static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );
    private Context mContext;

    public Utils(Context con) {
        mContext = con;
    }

    public static boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public static boolean isMarshmallow() {
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? true : false;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static void showTimeDropDown(final AutoCompleteTextView atv) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                atv.showDropDown();
            }
        }, 100);
    }
}
