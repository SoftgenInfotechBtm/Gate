package com.softgen.gate;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.softgen.gate.databaseHandler.DaoMaster;
import com.softgen.gate.databaseHandler.DaoSession;


/**
 * Created by mahesha on 23-11-16.
 */

public class GateApplication extends Application {
    /**
     * A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher.
     */
    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,ENCRYPTED ? "gate-encrypted.db" : "gate.db",null);
        SQLiteDatabase db = helper.getWritableDatabase();
        daoSession = new DaoMaster(db).newSession();
//        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("$0ft6en") : helper.getWritableDb();
//        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
