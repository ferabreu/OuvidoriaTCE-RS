package br.gov.rs.tce.ouvidoriatce_rs.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ferabreu on 1/30/17.
 */

public class ComplaintDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ComplaintTable.db";
    private static final int DATABASE_VERSION = 1;

    public ComplaintDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        ComplaintTable.onCreate(db);
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ComplaintTable.onUpgrade(db, oldVersion, newVersion);
    }
}
