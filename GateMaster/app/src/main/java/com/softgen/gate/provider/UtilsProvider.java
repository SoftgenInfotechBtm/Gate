package com.softgen.gate.provider;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.softgen.gate.gatedb.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by selva on 10/25/2015.
 */
public class UtilsProvider {

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
    private MaterialDialog progressDialog;

    public UtilsProvider(Context con) {
        mContext = con;
    }

    public static boolean checkPasss(String password) {
        String EMAIL_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static InputFilter[] getAlphabetWithSpaceFilter() {
        InputFilter[] inputFilters = new InputFilter[]
                {
                        new InputFilter() {
                            @Override
                            public CharSequence filter(CharSequence cs, int i, int i1, Spanned spanned, int i2, int i3) {
                                if (cs.equals("")) { // for backspace
                                    return cs;
                                }
                                if (cs.toString().matches("[a-zA-Z ]+")) {
                                    return cs;
                                }
                                return "";
                            }
                        }
                };
        return inputFilters;
    }


    public static boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }


    public static JSONArray remove(final int idx, final JSONArray from) {
        final List<JSONObject> objs = asList(from);
        objs.remove(idx);

        final JSONArray ja = new JSONArray();
        for (final JSONObject obj : objs) {
            ja.put(obj);
        }

        return ja;
    }

    public static List<JSONObject> asList(final JSONArray ja) {
        final int len = ja.length();
        final ArrayList<JSONObject> result = new ArrayList<JSONObject>(len);
        for (int i = 0; i < len; i++) {
            final JSONObject obj = ja.optJSONObject(i);
            if (obj != null) {
                result.add(obj);
            }
        }
        return result;
    }

    public static String getCurrentTimeStamp(String ipDF) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(ipDF);//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }


    public static String getCurrentTimeStamp(String ipDF, Calendar date) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(ipDF);//dd/MM/yyyy
        String strDate = sdfDate.format(date.getTime());
        return strDate;
    }


    public static void alertBox(Context context, String msg) {
        new MaterialDialog.Builder(context)
                .content(msg)
                .backgroundColorRes(R.color.white)
                .contentColorRes(R.color.black)
                .positiveColorRes(R.color.colorPrimaryDark)
                .positiveText("OK")
                .show();
    }

    public static void alertBox(Context context, int id) {
        new MaterialDialog.Builder(context)
                .content(context.getResources().getString(id))
                .backgroundColorRes(R.color.white)
                .contentColorRes(R.color.black)
                .positiveColorRes(R.color.colorPrimaryDark)
                .positiveText("OK")
                .show();
    }

    public static String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public static int getPhotoOrientation(Context context, Uri imageUri) {
        int rotate = 0;
        try {
            String imagePath = imageUri.getPath();
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);

            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            Log.i("RotateImage", "Exif orientation: " + orientation);
            Log.i("RotateImage", "Rotate value: " + rotate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }


 /*   public static DisplayImageOptions getOptionsForRoundImg() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(1000)).cacheInMemory(true)
                .cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnLoading(R.drawable.placeholder_circle)
                .showImageForEmptyUri(R.drawable.placeholder_circle)
                .showImageOnFail(R.drawable.placeholder_circle)
                .resetViewBeforeLoading(true).considerExifParams(true).build();
        return options;

    }*/

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static String getDistance(double sLat, double sLong, double dLat, double dLong) {
        Location locationA = new Location("point A");
        locationA.setLatitude(sLat);
        locationA.setLongitude(sLong);

        Location locationB = new Location("point B");
        locationB.setLatitude(dLat);
        locationB.setLongitude(dLong);

        float distance = locationA.distanceTo(locationB);
        Log.e("distance: ", "" + distance);
        distance = distance / 1000;
        Log.e("distance-> ", "" + distance);
        // distance = Math.round(distance);
        String s = String.format("%.2f", distance);
        Log.e("distance s-> ", "" + s);
        return s;
    }

    public static String convertTime(String tim, String inFormat, String outFormat) {
        String time = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat(outFormat);
            SimpleDateFormat parseFormat = new SimpleDateFormat(inFormat);
            Date date = parseFormat.parse(tim);
            time = displayFormat.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static float convertTimeFormat(String tim, String inFormat, String outFormat) {
        String time = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat(outFormat);
            SimpleDateFormat parseFormat = new SimpleDateFormat(inFormat);
            Date date = parseFormat.parse(tim);
            time = displayFormat.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Float.parseFloat(time);
    }

    public static float convertTimeFormat(String tim) {
        String time = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("HH.mm");
            SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
            Date date = parseFormat.parse(tim);
            time = displayFormat.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Float.parseFloat(time);
    }

    public static boolean isFromLaysForPricing(String from, String to, String time) {
        boolean flag = false;
        float from24 = convertTimeFormat(from);
        float to24 = convertTimeFormat(to);
        float time24 = convertTimeFormat(time);

        try {
            if (from.contains("AM") && to.contains("AM") && time.contains("AM")) {
                if (time24 >= from24 && time24 <= to24) {
                    flag = true;
                }
            } else if (from.contains("PM") && to.contains("PM") && time.contains("PM")) {
                if (time24 >= from24 && time24 <= to24) {
                    flag = true;
                }
            } else if (from.contains("AM") && to.contains("PM") && time.contains("AM")) {
                if (time24 >= from24) {
                    flag = true;
                }
            } else if (from.contains("AM") && to.contains("PM") && time.contains("PM")) {
                if (time24 <= to24) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean isFromLays(String from, String to, String time) {
        boolean flag = false;
        float from24 = convertTimeFormat(from);
        float to24 = convertTimeFormat(to);
        float time24 = convertTimeFormat(time);

        try {
            if (from.contains("AM") && to.contains("AM") && time.contains("AM")) {
                if (time24 >= from24 && time24 < to24) {
                    flag = true;
                }
            } else if (from.contains("PM") && to.contains("PM") && time.contains("PM")) {
                if (time24 >= from24 && time24 < to24) {
                    flag = true;
                }
            } else if (from.contains("AM") && to.contains("PM") && time.contains("AM")) {
                if (time24 > from24) {
                    flag = true;
                }
            } else if (from.contains("AM") && to.contains("PM") && time.contains("PM")) {
                if (time24 < to24) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean isToLays(String from, String to, String time) {
        boolean flag = false;
        float from24 = convertTimeFormat(from);
        float to24 = convertTimeFormat(to);
        float time24 = convertTimeFormat(time);

        try {
            if (from.contains("AM") && to.contains("AM") && time.contains("AM")) {
                if (time24 > from24 && time24 < to24) {
                    flag = true;
                }
            } else if (from.contains("PM") && to.contains("PM") && time.contains("PM")) {
                if (time24 > from24 && time24 < to24) {
                    flag = true;
                }
            } else if (from.contains("AM") && to.contains("PM") && time.contains("AM")) {
                if (time24 > from24) {
                    flag = true;
                }
            } else if (from.contains("AM") && to.contains("PM") && time.contains("PM")) {
                if (time24 < to24) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static Date addDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        return date;
    }

    public static String addDay(String dateStr, SimpleDateFormat sDF, int count) {

        String day = "";
        try {
            Date date = sDF.parse(dateStr);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, count);
            day = sDF.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

    public static boolean isGreaterThan24(String from, String to) {
        boolean flag = false;
        try {
            float from24 = convertTimeFormat(from);
            float to24 = convertTimeFormat(to);
            float value = to24 - from24;
            Log.e("from-to-value", "" + from + "" + to + " " + value);
            if (value <= 0) {
                flag = true;
            }

           /* float from24 = convertTimeFormat(from);
            float to24 = convertTimeFormat(to);
            Log.e("from-to", "" + from + "" + to);

            if (from.contains("AM") && to.contains("AM")) {
                if (from24 > to24) {
                    flag = true;
                }
            } else if (from.contains("PM") && to.contains("PM")) {
                if (from24 > to24) {
                    flag = true;
                }
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static int getBetweenTimeinMin(String t, String time, String format) {
        int min = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date date1 = simpleDateFormat.parse(t);
            Date date2 = simpleDateFormat.parse(time);

            long difference = date2.getTime() - date1.getTime();
            int days = (int) (difference / (1000 * 60 * 60 * 24));
            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            if (hours >= 1) min = 60;

            return min;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return min;
    }

    public static JSONArray sortJsonArray(JSONArray array, final String sortBy) {
        List<JSONObject> jsons = new ArrayList<JSONObject>();
        try {
            for (int i = 0; i < array.length(); i++) {
                jsons.add(array.getJSONObject(i));
            }
            Collections.sort(jsons, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject lhs, JSONObject rhs) {
                    String lid = "";
                    String rid = "";
                    try {
                        lid = lhs.getString(sortBy);
                        lid = lid.substring(0, 1).toUpperCase() + lid.substring(1);
                        rid = rhs.getString(sortBy);
                        rid = rid.substring(0, 1).toUpperCase() + rid.substring(1);
                    } catch (Exception e) {

                    }
                    return lid.compareTo(rid);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONArray(jsons);

    }

    public static void LogMine(String MODULE, Object obj) {
        int maxLogSize = 1000;
        for (int i = 0; i <= obj.toString().length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > obj.toString().length() ? obj.toString().length() : end;
            Log.v(MODULE, obj.toString().substring(start, end));
        }
    }

    public static int calculateAge(String format, String in) {
        int age = 0;
        try {
            Date date = new SimpleDateFormat(format).parse(in);
            age = (int) (System.currentTimeMillis() -
                    date.getTime()) / (24 * 60 * 60 * 1000);
            age = Math.abs(age);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return age;
    }

    public static boolean isAfterToday(String date, String format) {
        boolean flag = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date strDate = sdf.parse(date);
            if (new Date().after(strDate)) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public void showProgress(String title, String mes) {
        progressDialog = new MaterialDialog.Builder(mContext)
                .backgroundColorRes(R.color.white)
                .contentColorRes(R.color.black)
                .content(mes)
                .progress(true, 0)
                .show();
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void hideProgress() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }


}
