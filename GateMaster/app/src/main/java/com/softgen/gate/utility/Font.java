package com.softgen.gate.utility;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by selva on 6/29/2016.
 */
public class Font {
    public static Typeface getRobotMedium(Context con) {
        return Typeface.createFromAsset(con.getResources().getAssets(), "robotomedium.ttf");
    }
}
