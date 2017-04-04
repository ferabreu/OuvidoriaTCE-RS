package br.gov.rs.tce.ouvidoriatce_rs.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by ferabreu on 1/30/17.
 */

public class ComplaintTable {

    // Database table
    public static final String TABLE_COMPLAINT = "complaint";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_NOTICE_NUMBER = "notice_number";
    public static final String COLUMN_EVENT_ORGANIZER = "event_organizer";
    public static final String COLUMN_EVENT_LOCATION = "event_location";
    public static final String COLUMN_EVENT_DATE = "event_date";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_COMPLAINT_DATE = "complaint_date";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_COMPLAINT
            + "("
            + COLUMN_ID + " integer primary key autoincrement,"
            + COLUMN_CATEGORY + " text not null,"
            + COLUMN_SUBJECT + " text null"
            + COLUMN_STATUS + " text null"
            + COLUMN_NOTICE_NUMBER + " text null"
            + COLUMN_EVENT_ORGANIZER + " text null"
            + COLUMN_EVENT_LOCATION + " text null"
            + COLUMN_EVENT_DATE + " DATETIME NULL"
            + COLUMN_AMOUNT + " INTEGER NULL"
            + COLUMN_DESCRIPTION + " text not null"
            + COLUMN_COMPLAINT_DATE + " DATETIME DEFAULT (datetime('now', 'localtime'))"
            + ");";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ComplaintTable.class.getName(), "Upgrading database from version "
        + oldVersion + " to " + newVersion
        + ", which will destroy all old data.");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPLAINT);
        onCreate(db);
    }
}
