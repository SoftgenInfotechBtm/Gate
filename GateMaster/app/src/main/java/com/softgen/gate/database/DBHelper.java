package com.softgen.gate.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.softgen.gate.model.OTPMaster;
import com.softgen.gate.model.PersonMaster;
import com.softgen.gate.model.PlacesMaster;
import com.softgen.gate.model.ProfileMaster;
import com.softgen.gate.provider.SharedUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 9Jeevan on 30-08-2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "GateServices.db";
    public static final int DATABASE_VERSION = 3;
    //profile table of profile in gate db
    public static final String TABLE_PROFILE = "Profile";
    public static final String TABLE_OFFERED = "offered";
    public static final String TABLE_RECEIVED = "received";

    //otp table of otp in gate db
    public static final String TABLE_OTP = "otp";

    //person table of details in gate db
    public static final String TABLE_PERSONNEL_DETAILS = "personnelDetails";

    //person table of details in gate db
    public static final String TABLE_PLACES_VISITED = "placesVisited";

    // columns for profile table in db
    private static final String KEY_LOGIN_NAME = "username";
    private static final String KEY_CITY = "city";
    private static final String KEY_STATE = "state";
    private static final String KEY_AREA = "area";
    private static final String KEY_OFFERED = "offered";
    private static final String KEY_REQUIRED = "required";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_END_TIME = "end_time";
    private static final String KEY_COST = "cost";
    private static final String KEY_DURATION = "duration";

    private static final String KEY_PASSWORD = "password";

    //common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_UPDATED_AT = "updated_at";
    private static final String KEY_MOBILE_NO = "mobileNo";
    private static final String KEY_EMAIL = "email";

    // columns for otp table in db
    private static final String KEY_OTP = "otpNumber";

    //columns for person table in db
    private static final String KEY_NAME = "name";
    private static final String KEY_PID = "pid";
    private static final String KEY_ABOUT = "about";

    //column for places visited
    private static final String KEY_PLACES = "placesVisited";

    /**
     * Create Table query for profile
     */
    private static final String CREATE_TABLE_PROFILE = "CREATE TABLE " + TABLE_PROFILE + "(" + KEY_ID
            + " INTEGER PRIMARY KEY," + KEY_LOGIN_NAME + " TEXT, " + KEY_MOBILE_NO + " TEXT, " + KEY_EMAIL + " TEXT, "
            + KEY_CITY + " TEXT, " + KEY_STATE + " TEXT, " + KEY_AREA + " TEXT, "
            + KEY_DURATION + " TEXT, " + KEY_COST + " TEXT, " + KEY_START_TIME + " TEXT, " + KEY_END_TIME + " TEXT, "
            + KEY_PASSWORD + " TEXT, " + KEY_CREATED_AT + " DATETIME, " + KEY_UPDATED_AT + " DATETIME" + ")";


    private static final String CREATE_TABLE_OFFERED = "CREATE TABLE " + TABLE_OFFERED + "(" + KEY_ID
            + " INTEGER PRIMARY KEY," + KEY_MOBILE_NO + " TEXT, " + KEY_OFFERED + " TEXT, " + KEY_CREATED_AT + " DATETIME, " + KEY_UPDATED_AT + " DATETIME" + ")";

    private static final String CREATE_TABLE_REQUIRED = "CREATE TABLE " + TABLE_RECEIVED + "(" + KEY_ID
            + " INTEGER PRIMARY KEY," + KEY_MOBILE_NO + " TEXT, " + KEY_REQUIRED + " TEXT, " + KEY_CREATED_AT + " DATETIME, " + KEY_UPDATED_AT + " DATETIME" + ")";

    /**
     * Create Table query for otp
     */
    private static final String CREATE_TABLE_OTP = "CREATE TABLE " + TABLE_OTP + "(" + KEY_ID
            + " INTEGER PRIMARY KEY," + KEY_OTP + " TEXT, " + KEY_EMAIL + " TEXT, " + KEY_MOBILE_NO + " TEXT, "
            + KEY_CREATED_AT + " DATETIME," + KEY_UPDATED_AT + " DATETIME" + ")";

    /**
     * Create Table query for person details
     */
    private static final String CREATE_TABLE_PERSONNEL = "CREATE TABLE " + TABLE_PERSONNEL_DETAILS + "(" + KEY_ID
            + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT, " + KEY_EMAIL + " TEXT, " + KEY_MOBILE_NO + " TEXT, "
            + KEY_ABOUT + " TEXT, " + KEY_PID + " BLOB " + ")";

    /**
     * Create Table query for places visited
     */
    private static final String CREATE_TABLE_PLACES = "CREATE TABLE " + TABLE_PLACES_VISITED + "(" + KEY_ID
            + " INTEGER PRIMARY KEY," + KEY_PLACES + " TEXT, " + KEY_CREATED_AT + " DATETIME " + ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_PROFILE);
        db.execSQL(CREATE_TABLE_OTP);
        db.execSQL(CREATE_TABLE_OFFERED);
        db.execSQL(CREATE_TABLE_REQUIRED);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OTP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFERED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECEIVED);
//        switch (oldVersion) {
//            case 1:
//                upgradeVersion1(db, oldVersion, newVersion);
//                break;
//        }
        onCreate(db);
    }

    public void upgradeVersion1(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OTP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONNEL_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES_VISITED);
        onUpgrade(db, ++oldVersion, newVersion);
    }

    public long createProfileUser(ProfileMaster userList) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("values inserted", userList + "");
        ContentValues values = new ContentValues();
        values.put(KEY_ID, userList.getUserID());
        values.put(KEY_LOGIN_NAME, userList.getUsername());
        values.put(KEY_MOBILE_NO, userList.getMobile());
        values.put(KEY_EMAIL, userList.getEmail());
        values.put(KEY_CITY, userList.getCity());
        values.put(KEY_STATE, userList.getState());
        values.put(KEY_AREA, userList.getArea());
        values.put(KEY_COST, userList.getCost());
        values.put(KEY_DURATION, userList.getDuration());
        values.put(KEY_START_TIME, userList.getStartTime());
        values.put(KEY_END_TIME, userList.getEndTime());
        values.put(KEY_CREATED_AT, userList.getCreatedAt().toString());
        values.put(KEY_UPDATED_AT, userList.getUpdatedAt().toString());
        values.put(KEY_PASSWORD, userList.getPassword());

        // insert row
        db.insert(TABLE_PROFILE, null, values);
        return 1;
    }

    public long createOfferedList(ProfileMaster userList, ArrayList<String> services) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; i < services.size(); i++) {
            values.put(KEY_ID, userList.getUserID());
            values.put(KEY_MOBILE_NO, userList.getMobile());
            values.put(KEY_OFFERED, services.get(i).toString());
            values.put(KEY_CREATED_AT, userList.getCreatedAt().toString());
            values.put(KEY_UPDATED_AT, userList.getUpdatedAt().toString());
            db.insert(TABLE_OFFERED, null, values);
        }
        return 1;
    }

    public long createRequiredList(ProfileMaster userList, ArrayList<String> services) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; i < services.size(); i++) {
            values.put(KEY_ID, userList.getUserID());
            values.put(KEY_MOBILE_NO, userList.getMobile());
            values.put(KEY_REQUIRED, services.get(i).toString());
            values.put(KEY_CREATED_AT, userList.getCreatedAt().toString());
            values.put(KEY_UPDATED_AT, userList.getUpdatedAt().toString());
            db.insert(TABLE_RECEIVED, null, values);
        }
        return 1;
    }

    public long updateLoginUser(ProfileMaster userList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, userList.getUserID());
        values.put(KEY_LOGIN_NAME, userList.getUsername());
        values.put(KEY_EMAIL, userList.getEmail());
        values.put(KEY_CITY, userList.getCity());
        values.put(KEY_STATE, userList.getState());
        values.put(KEY_AREA, userList.getArea());
        values.put(KEY_COST, userList.getCost());
        values.put(KEY_DURATION, userList.getDuration());
        values.put(KEY_START_TIME, userList.getStartTime());
        values.put(KEY_END_TIME, userList.getEndTime());
        values.put(KEY_UPDATED_AT, userList.getUpdatedAt().toString());
        values.put(KEY_PASSWORD, userList.getPassword());
        // insert row
        long login_id = db.update(TABLE_PROFILE, values, (KEY_ID + " = '" + userList.getUserID() + "'"), null);

        return login_id;
    }

    public long editProfileUser(ProfileMaster userList) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("values inserted", userList + "");
        ContentValues values2 = new ContentValues();
        values2.put(KEY_ID, userList.getUserID());
        values2.put(KEY_LOGIN_NAME, userList.getUsername());
        values2.put(KEY_MOBILE_NO, userList.getMobile());
        values2.put(KEY_EMAIL, userList.getEmail());
        values2.put(KEY_CITY, userList.getCity());
        values2.put(KEY_STATE, userList.getState());
        values2.put(KEY_AREA, userList.getArea());
        values2.put(KEY_CREATED_AT, userList.getCreatedAt().toString());
        values2.put(KEY_UPDATED_AT, userList.getUpdatedAt().toString());
        values2.put(KEY_PASSWORD, userList.getPassword());
        // insert row
        db.replace(TABLE_PROFILE, null, values2);
        return 1;
    }

    public List<ProfileMaster> getUserDetails(int mobileNo) {
        List<ProfileMaster> userdetail = new ArrayList<ProfileMaster>();
        String selectQuery = "SELECT * FROM " + TABLE_PROFILE + " WHERE "
                + KEY_ID + " = '" + mobileNo + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                try {
                    String userName = c.getString(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    do {
                        ProfileMaster master = new ProfileMaster();
                        master.setUsername(c.getString(c.getColumnIndex(KEY_LOGIN_NAME)));
                        master.setEmail((c.getString(c.getColumnIndex(KEY_EMAIL))));
                        master.setMobile((c.getString(c.getColumnIndex(KEY_MOBILE_NO))));
                        master.setCity((c.getString(c.getColumnIndex(KEY_CITY))));
                        master.setState((c.getString(c.getColumnIndex(KEY_STATE))));
                        master.setArea((c.getString(c.getColumnIndex(KEY_AREA))));
                        master.setCost((c.getString(c.getColumnIndex(KEY_COST))));
                        master.setDuration((c.getString(c.getColumnIndex(KEY_DURATION))));
                        master.setStartTime((c.getString(c.getColumnIndex(KEY_START_TIME))));
                        master.setEndTime((c.getString(c.getColumnIndex(KEY_END_TIME))));
                        master.setPassword((c.getString(c.getColumnIndex(KEY_PASSWORD))));
                        userdetail.add(master);
                    } while (c.moveToNext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return userdetail;
    }

    public long createOTPUser(OTPMaster otpList) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("values1 inserted", otpList + "");
        ContentValues values1 = new ContentValues();
        values1.put(KEY_ID, otpList.getUserID());
        values1.put(KEY_OTP, otpList.getOtp());
        values1.put(KEY_EMAIL, otpList.getEmail());
        values1.put(KEY_MOBILE_NO, otpList.getMobile());
        values1.put(KEY_CREATED_AT, otpList.getCreatedAt().toString());
        values1.put(KEY_UPDATED_AT, otpList.getUpdatedAt().toString());

        // insert row
        db.insert(TABLE_OTP, null, values1);
        return 1;
    }

    public long createPersonDetails(PersonMaster personList) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("values inserted", personList + "");
        ContentValues values2 = new ContentValues();
        values2.put(KEY_ID, personList.getUser_id());
        values2.put(KEY_NAME, personList.getName());
        values2.put(KEY_EMAIL, personList.getEmail());
        values2.put(KEY_MOBILE_NO, personList.getMob_no());
        values2.put(KEY_ABOUT, personList.getAbout());
        values2.put(KEY_PID, personList.getPid());

        // insert row
        db.insert(TABLE_PERSONNEL_DETAILS, null, values2);

        return 1;
    }

    public long createPlacesVisited(PlacesMaster placesList) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("values inserted", placesList + "");
        ContentValues values3 = new ContentValues();
        values3.put(KEY_ID, placesList.getUser_id());
        values3.put(KEY_PLACES, placesList.getPlacesVisited());
        values3.put(KEY_CREATED_AT, placesList.getCreatedAt().toString());

        // insert row
        db.insert(TABLE_PLACES_VISITED, null, values3);

        return 1;
    }

//    public void addEntry(String id,byte[] image)throws SQLiteException {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values3 = new  ContentValues();
//        values3.put(KEY_NAME, id);
//        values3.put(KEY_IMAGE, image);
//        db.insert(TABLE_PERSON_DETAILS , null, values3);
//    }
//    byte[] image = cursor.getBlob(1);

    public String getMyOTP(String email, String mobileno) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql2 = "SELECT " + KEY_OTP + " FROM " + TABLE_OTP + " WHERE " + KEY_EMAIL + " = ? AND " + KEY_MOBILE_NO + " = ? ";
        Cursor c2 = db.rawQuery(sql2, new String[]{email, mobileno});
        String rotp = "";
        try {
            c2.moveToFirst();
            rotp = c2.getString(c2.getColumnIndex(KEY_OTP));
            Log.e("ropt", rotp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotp;
    }

    public String getValidUser(String email, String mobNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql4 = "SELECT * FROM " + TABLE_PROFILE + " WHERE " + KEY_EMAIL + " = ? OR " + KEY_MOBILE_NO + " = ? ";
        Cursor c4 = db.rawQuery(sql4, new String[]{email, mobNo});
        if (c4.getCount() < 1) {
            c4.close();
            return "NOT EXIST";
        }
        c4.moveToFirst();
        email = c4.getString(c4.getColumnIndex(KEY_EMAIL));
        mobNo = c4.getString(c4.getColumnIndex(KEY_MOBILE_NO));
        c4.close();
        return "";
    }

    public boolean getUserData(String userInput, String password, Context context) {
        boolean isPresent = false;
        String sql3 = "SELECT * FROM " + TABLE_PROFILE + " WHERE " + KEY_EMAIL + " = ? OR " + KEY_MOBILE_NO + " = ? OR " + KEY_LOGIN_NAME + "= ?";
        Log.e("sql is ", sql3);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c3 = db.rawQuery(sql3, new String[]{userInput, userInput, userInput});
        String user = "";
        String pswd = "";
        String mail = "";
        String mobNo = "";
        int id = 0;
        try {
            c3.moveToFirst();
            user = c3.getString(c3.getColumnIndex(KEY_LOGIN_NAME));
            id = c3.getInt(c3.getColumnIndex(KEY_ID));
            pswd = c3.getString(c3.getColumnIndex(KEY_PASSWORD));
            mail = c3.getString(c3.getColumnIndex(KEY_EMAIL));
            mobNo = c3.getString(c3.getColumnIndex(KEY_MOBILE_NO));

            if (pswd.equals(password)) {
                SharedUtils.storeUserID(context, id);
                isPresent = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isPresent;
    }

    public List<ProfileMaster> getuserList(String username) {
        List<ProfileMaster> userdetail = new ArrayList<ProfileMaster>();
        String selectQuery = "SELECT * FROM " + TABLE_PROFILE + " WHERE " + KEY_LOGIN_NAME + " = ? '" + username + "'";
//                 "' AND " + KEY_PASSWORD + " = ? '" + Password + "'";
        Log.e("Sql Query", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ProfileMaster userdata = new ProfileMaster();
                userdata.setUsername(c.getString((c.getColumnIndex(KEY_LOGIN_NAME))));
//                userdata.setPassword((c.getString(c.getColumnIndex(KEY_PASSWORD))));
                userdetail.add(userdata);
            } while (c.moveToNext());
        }
        return userdetail;
    }

    public List<OTPMaster> getOtpList(String otp) {
        List<OTPMaster> otpdetail = new ArrayList<OTPMaster>();
        String selectQuery = "SELECT * FROM " + TABLE_OTP + " WHERE " + KEY_OTP + " = '" + otp + "'";

        Log.e("Sql Query", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                OTPMaster userdata = new OTPMaster();
                userdata.setOtp(c.getString((c.getColumnIndex(KEY_OTP))));
//                userdata.setPassword((c.getString(c.getColumnIndex(KEY_PASSWORD))));
                otpdetail.add(userdata);
            } while (c.moveToNext());
        }
        return otpdetail;

    }

    public String getValidUser1(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_PROFILE, null, " username=?", new String[]{userName}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String username = cursor.getString(cursor.getColumnIndex("username"));
        cursor.close();
        return username;
    }//both are same i think

    public String getAllEntry1(String otp) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor1 = db.query(TABLE_OTP, null, " otp=?", new String[]{otp}, null, null, null);
        if (cursor1.getCount() < 1) // otp Not Exist
        {
            cursor1.close();
            return "NOT EXIST";
        }
        cursor1.moveToFirst();
        String otp1 = cursor1.getString(cursor1.getColumnIndex("otp"));
        cursor1.close();
        return otp1;
    }

    public long getforgotpasswordCount(String username, String email) {
        long subtotal;

        String selectQuery = "SELECT " + " " + KEY_PASSWORD + " " + " FROM " + TABLE_PROFILE + " WHERE "
                + KEY_LOGIN_NAME + " = '" + username + "' AND " + KEY_EMAIL + " = '" + email + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        subtotal = c.getCount();
        return subtotal;
    }

    public List<ProfileMaster> getForgotPassword(String mobileNo, String email) {
        List<ProfileMaster> userPassword = new ArrayList<ProfileMaster>();
        String selectQuery = "SELECT " + " " + KEY_PASSWORD + " " + " FROM " + TABLE_PROFILE + " WHERE "
                + KEY_MOBILE_NO + " = ? '" + mobileNo + "' AND " + KEY_EMAIL + " = ? '" + email + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ProfileMaster userdata = new ProfileMaster();
                userdata.setPassword((c.getString(c.getColumnIndex(KEY_PASSWORD))));
                userPassword.add(userdata);
            } while (c.moveToNext());
        }
        return userPassword;
    }

    public List<ProfileMaster> getPassword(String Password) {
        List<ProfileMaster> userdetail = new ArrayList<ProfileMaster>();
        String selectQuery = "SELECT * FROM " + TABLE_PROFILE + " WHERE " + KEY_PASSWORD + " = '" + Password + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ProfileMaster userdata = new ProfileMaster();

                userdata.setUserID(c.getString((c.getColumnIndex(KEY_ID))));
//                userdata.setPassword((c.getString(c.getColumnIndex(KEY_PASSWORD))));

                userdetail.add(userdata);
            } while (c.moveToNext());
        }
        return userdetail;
    }

    /*
     * updating a otp
	 */
    public long updateOtpUser(OTPMaster otpList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_OTP, otpList.getOtp());

        // insert row
        long otp = db.update(TABLE_OTP, values, " WHERE " + KEY_EMAIL + " = ? AND " + KEY_MOBILE_NO + " = ? ",
                new String[]{String.valueOf(otpList.getEmail()), String.valueOf(otpList.getMobile())});

        return otp;
    }
}
