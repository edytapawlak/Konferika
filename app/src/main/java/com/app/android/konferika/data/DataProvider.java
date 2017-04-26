package com.app.android.konferika.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.util.Log;

/**
 * Created by edyta on 25.04.17.
 */

public class DataProvider extends ContentProvider {

    public static final int CODE_DATA = 100;
    public static final int CODE_SPECYFIC_DATA = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private DatabaseOpenHelper mOpenHelper;
    private Context context;
//    SQLiteDatabase db;
//    private DatabaseAccess db;
    @Override
    public boolean onCreate() {
        this.context = this.getContext();
        this.mOpenHelper = new DatabaseOpenHelper(context);

        return true;
    }

    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DatabaseContract.CONTENT_AUTHORITY;

        /* This URI is content://com.example.android.sunshine/weather/ */
        matcher.addURI(authority, DatabaseContract.PATH_LECTURES, CODE_DATA);

        /*
         * This URI would look something like content://com.example.android.sunshine/weather/1472214172
         * The "/#" signifies to the UriMatcher that if PATH_WEATHER is followed by ANY number,
         * that it should return the CODE_WEATHER_WITH_DATE code
         */
        matcher.addURI(authority, DatabaseContract.PATH_LECTURES + "/#", CODE_SPECYFIC_DATA);

        return matcher;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case CODE_DATA:
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(DatabaseContract.LecturesEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;

            default:
                return super.bulkInsert(uri, values);
        }
    }

    /**
     * @param uri           The URI to query
     * @param projection    The list of columns to put into the cursor. If null, all columns are
     *                      included.
     * @param selection     A selection criteria to apply when filtering rows. If null, then all
     *                      rows are included.
     * @param selectionArgs You may include ?s in selection, which will be replaced by
     *                      the values from selectionArgs, in order that they appear in the
     *                      selection.
     * @param sortOrder     How the rows in the cursor should be sorted.
     * @return A Cursor containing the results of the query. In our implementation,
     */
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor cursor;
        switch (sUriMatcher.match(uri)) {

            /*
             * When sUriMatcher's match method is called with a URI that looks something like this
             *
             *      content://com.example.android.sunshine/weather/1472214172

             * In this case, we want to return a cursor that contains one row of data for
             * a particular id.
             */
            case CODE_SPECYFIC_DATA: {

                String data_id = uri.getLastPathSegment();

                String[] selectionArguments = new String[]{data_id};

                cursor = mOpenHelper.getReadableDatabase().query(
                        /* Table we are going to query */
                        DatabaseContract.LecturesEntry.TABLE_NAME,
                        /*
                         * A projection designates the columns we want returned in our Cursor.
                         * Passing null will return all columns of data within the Cursor.
                         * However, if you don't need all the data from the table, it's best
                         * practice to limit the columns returned in the Cursor with a projection.
                         */
                        projection,
                        /*
                         * The URI that matches CODE_WEATHER_WITH_DATE contains a date at the end
                         * of it. We extract that date and use it with these next two lines to
                         * specify the row of weather we want returned in the cursor. We use a
                         * question mark here and then designate selectionArguments as the next
                         * argument for performance reasons. Whatever Strings are contained
                         * within the selectionArguments array will be inserted into the
                         * selection statement by SQLite under the hood.
                         */
                        DatabaseContract.LecturesEntry.COLUMN_ID + " = ? ",
                        selectionArguments,
                        null,
                        null,
                        sortOrder);

                break;
            }

            /*
             * When sUriMatcher's match method is called with a URI that looks EXACTLY like this
             *
             *      content://com.example.android.sunshine/weather/
             *
             * In this case, we want to return a cursor that contains every row of lect data
             * in our lect table.
             */
            case CODE_DATA: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        DatabaseContract.LecturesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    /**
     * Deletes data at a given URI with optional arguments for more fine tuned deletions.
     *
     * @param uri           The full URI to query
     * @param selection     An optional restriction to apply to rows when deleting.
     * @param selectionArgs Used in conjunction with the selection statement
     * @return The number of rows deleted
     */
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int numRowsDeleted;
        if (null == selection) selection = "1";
        switch (sUriMatcher.match(uri)) {
            case CODE_DATA:
                numRowsDeleted = mOpenHelper.getWritableDatabase().delete(
                        DatabaseContract.LecturesEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numRowsDeleted;
    }

    /**
     * @param uri the URI to query.
     * @return nothing in Sunshine, but normally a MIME type string, or null if there is no type.
     */
    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("We are not implementing getType in Sunshine.");
    }

    /**
     * @param uri    The URI of the insertion request. This must not be null.
     * @param values A set of column_name/value pairs to add to the database.
     *               This must not be null
     * @return nothing in Sunshine, but normally the URI for the newly inserted item.
     */
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        int match = sUriMatcher.match(uri);
        Uri returnUri; // URI to be returned
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        switch (match) {
            case CODE_DATA:
                long id = db.insert(DatabaseContract.LecturesEntry.TABLE_NAME, null, values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(DatabaseContract.LecturesEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new RuntimeException("We are not implementing update in Sunshine");
    }

    /**
     * You do not need to call this method. This is a method specifically to assist the testing
     * framework in running smoothly. You can read more at:
     * http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
     */
    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
