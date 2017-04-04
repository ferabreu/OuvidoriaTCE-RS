package br.gov.rs.tce.ouvidoriatce_rs.contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

import br.gov.rs.tce.ouvidoriatce_rs.database.ComplaintDatabaseHelper;
import br.gov.rs.tce.ouvidoriatce_rs.database.ComplaintTable;

/**
 * Created by ferabreu on 1/31/17.
 */

public class ComplaintContentProvider extends ContentProvider {

    // Database
    private ComplaintDatabaseHelper database;

    // Used for the URIMatcher
    private static final int COMPLAINTS = 10;
    private static final int COMPLAINT_ID = 20;

    private static final String AUTHORITY = "br.gov.rs.tce.ouvidoriatce_rs.contentprovider";
    private static final String BASE_PATH = "complaints";

    public static final  Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/complaints";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/complaint";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, COMPLAINTS);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", COMPLAINT_ID);
    }

    @Override
    public boolean onCreate() {
        database = new ComplaintDatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Using SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // Check if the caller has requested a column which does not exist
        checkColumns(projection);

        // Set the table
        queryBuilder.setTables(ComplaintTable.TABLE_COMPLAINT);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case COMPLAINTS:
                break;
            case COMPLAINT_ID:
                // Adding the ID to the original query
                queryBuilder.appendWhere(ComplaintTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        // Make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = database.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case COMPLAINTS:
                id = db.insert(ComplaintTable.TABLE_COMPLAINT, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case COMPLAINTS:
                rowsDeleted = db.delete(ComplaintTable.TABLE_COMPLAINT, selection,
                        selectionArgs);
                break;
            case COMPLAINT_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(
                            ComplaintTable.TABLE_COMPLAINT,
                            ComplaintTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = db.delete(
                            ComplaintTable.TABLE_COMPLAINT,
                            ComplaintTable.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case COMPLAINTS:
                rowsUpdated = db.update(ComplaintTable.TABLE_COMPLAINT,
                        values,
                        selection,
                        selectionArgs);
                break;
            case COMPLAINT_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(ComplaintTable.TABLE_COMPLAINT,
                            values,
                            ComplaintTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = db.update(ComplaintTable.TABLE_COMPLAINT,
                            values,
                            ComplaintTable.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection) {
        String[] available = {ComplaintTable.COLUMN_ID, ComplaintTable.COLUMN_CATEGORY,
                ComplaintTable.COLUMN_DESCRIPTION};
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(
                    Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(
                    Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException(
                        "Unknown columns in projection.");
            }
        }
    }
}
