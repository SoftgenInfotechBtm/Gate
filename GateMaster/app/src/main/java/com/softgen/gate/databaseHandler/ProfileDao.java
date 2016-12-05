package com.softgen.gate.databaseHandler;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.softgen.gate.databaseHandler.Profile;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PROFILE".
*/
public class ProfileDao extends AbstractDao<Profile, Long> {

    public static final String TABLENAME = "PROFILE";

    /**
     * Properties of entity Profile.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ProfileName = new Property(1, String.class, "profileName", false, "PROFILE_NAME");
        public final static Property MobileNo = new Property(2, long.class, "mobileNo", false, "MOBILE_NO");
        public final static Property Email = new Property(3, String.class, "email", false, "EMAIL");
        public final static Property Password = new Property(4, short.class, "password", false, "PASSWORD");
        public final static Property City = new Property(5, String.class, "city", false, "CITY");
        public final static Property State = new Property(6, String.class, "state", false, "STATE");
        public final static Property Area = new Property(7, String.class, "area", false, "AREA");
        public final static Property OfferedServices = new Property(8, String.class, "offeredServices", false, "OFFERED_SERVICES");
        public final static Property RequiredServices = new Property(9, String.class, "requiredServices", false, "REQUIRED_SERVICES");
        public final static Property CreatedAt = new Property(10, java.util.Date.class, "createdAt", false, "CREATED_AT");
        public final static Property UpdatedAt = new Property(11, java.util.Date.class, "updatedAt", false, "UPDATED_AT");
    };

    private DaoSession daoSession;


    public ProfileDao(DaoConfig config) {
        super(config);
    }
    
    public ProfileDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PROFILE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"PROFILE_NAME\" TEXT NOT NULL ," + // 1: profileName
                "\"MOBILE_NO\" INTEGER NOT NULL ," + // 2: mobileNo
                "\"EMAIL\" TEXT NOT NULL ," + // 3: email
                "\"PASSWORD\" INTEGER NOT NULL ," + // 4: password
                "\"CITY\" TEXT NOT NULL ," + // 5: city
                "\"STATE\" TEXT NOT NULL ," + // 6: state
                "\"AREA\" TEXT NOT NULL ," + // 7: area
                "\"OFFERED_SERVICES\" TEXT NOT NULL ," + // 8: offeredServices
                "\"REQUIRED_SERVICES\" TEXT NOT NULL ," + // 9: requiredServices
                "\"CREATED_AT\" INTEGER NOT NULL ," + // 10: createdAt
                "\"UPDATED_AT\" INTEGER NOT NULL );"); // 11: updatedAt
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PROFILE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Profile entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getProfileName());
        stmt.bindLong(3, entity.getMobileNo());
        stmt.bindString(4, entity.getEmail());
        stmt.bindLong(5, entity.getPassword());
        stmt.bindString(6, entity.getCity());
        stmt.bindString(7, entity.getState());
        stmt.bindString(8, entity.getArea());
        stmt.bindString(9, entity.getOfferedServices());
        stmt.bindString(10, entity.getRequiredServices());
        stmt.bindLong(11, entity.getCreatedAt().getTime());
        stmt.bindLong(12, entity.getUpdatedAt().getTime());
    }

    @Override
    protected void attachEntity(Profile entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Profile readEntity(Cursor cursor, int offset) {
        Profile entity = new Profile( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // profileName
            cursor.getLong(offset + 2), // mobileNo
            cursor.getString(offset + 3), // email
            cursor.getShort(offset + 4), // password
            cursor.getString(offset + 5), // city
            cursor.getString(offset + 6), // state
            cursor.getString(offset + 7), // area
            cursor.getString(offset + 8), // offeredServices
            cursor.getString(offset + 9), // requiredServices
            new java.util.Date(cursor.getLong(offset + 10)), // createdAt
            new java.util.Date(cursor.getLong(offset + 11)) // updatedAt
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Profile entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setProfileName(cursor.getString(offset + 1));
        entity.setMobileNo(cursor.getLong(offset + 2));
        entity.setEmail(cursor.getString(offset + 3));
        entity.setPassword(cursor.getShort(offset + 4));
        entity.setCity(cursor.getString(offset + 5));
        entity.setState(cursor.getString(offset + 6));
        entity.setArea(cursor.getString(offset + 7));
        entity.setOfferedServices(cursor.getString(offset + 8));
        entity.setRequiredServices(cursor.getString(offset + 9));
        entity.setCreatedAt(new java.util.Date(cursor.getLong(offset + 10)));
        entity.setUpdatedAt(new java.util.Date(cursor.getLong(offset + 11)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Profile entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Profile entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
