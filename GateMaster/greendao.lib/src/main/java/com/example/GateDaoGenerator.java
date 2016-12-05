package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class GateDaoGenerator {
    public static void main(String[] args) throws Exception {

        //place where db folder will be created inside the project folder
        Schema schema = new Schema(1, "com.softgen.gate.databaseHandler");
        schema.enableKeepSectionsByDefault();
        addTables(schema);

        try {
            new DaoGenerator().generateAll(schema, "./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {
        Entity Profile = addProfile(schema);
        Entity otpTable = addOTP(schema);

        Property ProfileId = otpTable.addLongProperty("ProfileId").notNull().getProperty();
        Profile.addToMany(otpTable, ProfileId, "gateDatabase");
    }

    private static Entity addProfile(final Schema schema) {
        Entity Profile = schema.addEntity("Profile");
        Profile.addIdProperty().primaryKey().autoincrement();
        Profile.addStringProperty("profileName").notNull();
        Profile.addLongProperty("mobileNo").notNull();
        Profile.addStringProperty("email").notNull();
        Profile.addStringProperty("password").notNull();
        Profile.addStringProperty("city").notNull();
        Profile.addStringProperty("state").notNull();
        Profile.addStringProperty("area").notNull();
        Profile.addStringProperty("offeredServices").notNull();
        Profile.addStringProperty("requiredServices").notNull();
        Profile.addDateProperty("createdAt").notNull();
        Profile.addDateProperty("updatedAt").notNull();

        return Profile;
    }

    private static Entity addOTP(final Schema schema) {
        Entity otpTable = schema.addEntity("Otp");
        otpTable.addIdProperty().primaryKey().autoincrement();
        otpTable.addIntProperty("otpNumber").notNull();
        otpTable.addStringProperty("mobileNo").notNull();
        otpTable.addStringProperty("email").notNull();
        otpTable.addDateProperty("createdAt").notNull();
        otpTable.addDateProperty("updatedAt").notNull();
        return otpTable;
    }
}
