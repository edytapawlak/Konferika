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
import android.util.Log;

/**
 * Created by edyta on 25.04.17.
 */

public class DataProvider extends ContentProvider {

    public static final int CODE_LECTURES = 100;
    public static final int CODE_SPECYFIC_LECTURE = 101;
    public static final int CODE_POSTERS = 200;
    public static final int CODE_SPECYFIC_POSTER = 201;
    public static final int CODE_BREAKS = 300;
    public static final int CODE_SPECYFIC_BREAK = 301;
    public static final int CODE_GET_ALL_START_TIMES = 400;
    public static final int CODE_GET_ALL_USERS_START_TIMES = 401;
    public static final int CODE_LECT_TAGS = 500;
    public static final int CODE_GET_SPECYFIC_TAG_LECT = 501;
    public static final int CODE_GET_POS_TAGS = 600;
    public static final int CODE_GET_SPECYFIC_TAG_POS = 601;
    public static final int CODE_TAGS = 700;
    public static final int CODE_LECTURES_JOIN_SCHED = 800;
    public static final int CODE_SPECYFIC_LECTURES_JOIN_SCHED = 801;
    public static final int CODE_USER_LECTURES = 802;
    public static final int CODE_SPECYFIC_SCHED = 901;
    public static final int CODE_SCHED = 900;
    public static final int CODE_TAG = 1000;
    public static final int CODE_SPECYFIC_TAG = 1001;


    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private DatabaseOpenHelper mOpenHelper;
    private Context context;
    @Override
    public boolean onCreate() {
        this.context = this.getContext();
        this.mOpenHelper = new DatabaseOpenHelper(context);

        return true;
    }

    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DatabaseContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, DatabaseContract.PATH_LECTURES, CODE_LECTURES);
        matcher.addURI(authority, DatabaseContract.PATH_POSTERS, CODE_POSTERS);
        matcher.addURI(authority, DatabaseContract.PATH_BREAK, CODE_BREAKS);
        matcher.addURI(authority, DatabaseContract.PATH_ALL_START_TIME, CODE_GET_ALL_START_TIMES);
        matcher.addURI(authority, DatabaseContract.PATH_ALL_USERS_START_TIME, CODE_GET_ALL_USERS_START_TIMES);
        matcher.addURI(authority, DatabaseContract.PATH_TAGS_LECT, CODE_LECT_TAGS);
        matcher.addURI(authority, DatabaseContract.PATH_TAGS_POS, CODE_GET_POS_TAGS);
        matcher.addURI(authority, DatabaseContract.PATH_TAGS, CODE_TAGS);
        matcher.addURI(authority, DatabaseContract.PATH_LECTURES_JOIN_SCHED, CODE_LECTURES_JOIN_SCHED);
        matcher.addURI(authority, DatabaseContract.PATH_SCHED, CODE_SCHED);
        matcher.addURI(authority, DatabaseContract.PATH_USERS_LECTURES, CODE_USER_LECTURES);

        /*
         * This URI would look something like content://com.example.android.sunshine/weather/1472214172
         * The "/#" signifies to the UriMatcher that if PATH_WEATHER is followed by ANY number,
         * that it should return the CODE_WEATHER_WITH_DATE code
         */
        matcher.addURI(authority, DatabaseContract.PATH_LECTURES + "/#", CODE_SPECYFIC_LECTURE);
        matcher.addURI(authority, DatabaseContract.PATH_POSTERS + "/#", CODE_SPECYFIC_POSTER);
        matcher.addURI(authority, DatabaseContract.PATH_BREAK + "/#", CODE_SPECYFIC_BREAK);
        matcher.addURI(authority, DatabaseContract.PATH_TAGS_LECT + "/#", CODE_GET_SPECYFIC_TAG_LECT);
        matcher.addURI(authority, DatabaseContract.PATH_TAGS_POS + "/#", CODE_GET_SPECYFIC_TAG_POS);
        matcher.addURI(authority, DatabaseContract.PATH_SCHED + "/#", CODE_SPECYFIC_SCHED);
        matcher.addURI(authority, DatabaseContract.PATH_LECTURES_JOIN_SCHED + "/#", CODE_SPECYFIC_LECTURES_JOIN_SCHED);
        matcher.addURI(authority, DatabaseContract.PATH_TAGS + "/#", CODE_SPECYFIC_TAG);

        return matcher;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        String selection;
        String[] selectionArgs;
        int rowsInserted;

        switch (sUriMatcher.match(uri)) {
            case CODE_LECTURES:
                selectionArgs = new String[1];
                db.beginTransaction();
                rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        selection = DatabaseContract.LecturesEntry.COLUMN_ID + " = ?";
                        selectionArgs[0] = value.getAsString(DatabaseContract.LecturesEntry.COLUMN_ID);

                        int affected = db.update(DatabaseContract.LecturesEntry.TABLE_NAME,
                                value, selection, selectionArgs);
                        if (affected == 0) {
                            long rowId = db.insert(DatabaseContract.LecturesEntry.TABLE_NAME, null, value);
                            if (rowId > 0) rowsInserted++;
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

            case CODE_POSTERS:
                selectionArgs = new String[1];
                db.beginTransaction();
                rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        selection = DatabaseContract.PostersEntry.COLUMN_ID + " = ?";
                        selectionArgs[0] = value.getAsString(DatabaseContract.PostersEntry.COLUMN_ID);

                        int affected = db.update(DatabaseContract.PostersEntry.TABLE_NAME,
                                value, selection, selectionArgs);
                        if (affected == 0) {
                            long rowId = db.insert(DatabaseContract.PostersEntry.TABLE_NAME, null, value);
                            if (rowId > 0) rowsInserted++;
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
            case CODE_BREAKS:
                selectionArgs = new String[1];
                db.beginTransaction();
                rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        selection = DatabaseContract.BreakEntry.COLUMN_ID + " = ?";
                        selectionArgs[0] = value.getAsString(DatabaseContract.BreakEntry.COLUMN_ID);

                        int affected = db.update(DatabaseContract.BreakEntry.TABLE_NAME,
                                value, selection, selectionArgs);
                        if (affected == 0) {
                            long rowId = db.insert(DatabaseContract.BreakEntry.TABLE_NAME, null, value);
                            if (rowId > 0) rowsInserted++;
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
            case CODE_TAGS:
                selectionArgs = new String[1];
                db.beginTransaction();
                rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        selection = DatabaseContract.TagsEntry.COLUMN_TITLE + " = ?";
                        selectionArgs[0] = value.getAsString(DatabaseContract.TagsEntry.COLUMN_TITLE);

                        int affected = db.update(DatabaseContract.TagsEntry.TABLE_NAME,
                                value, selection, selectionArgs);
                        if (affected == 0) {
                            long rowId = db.insert(DatabaseContract.TagsEntry.TABLE_NAME, null, value);
                            if (rowId > 0) rowsInserted++;
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
            case CODE_SCHED:
                selectionArgs = new String[1];
                db.beginTransaction();
                rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        selection = DatabaseContract.ScheduleEntry.COLUMN_ID + " = ?";
                        selectionArgs[0] = value.getAsString(DatabaseContract.ScheduleEntry.COLUMN_ID);

                        int affected = db.update(DatabaseContract.ScheduleEntry.TABLE_NAME,
                                value, selection, selectionArgs);
                        if (affected == 0) {
                            long rowId = db.insert(DatabaseContract.ScheduleEntry.TABLE_NAME, null, value);
                            if (rowId > 0) rowsInserted++;
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
            case CODE_LECT_TAGS:
                db.beginTransaction();
                rowsInserted = 0;
                try {
                    for (ContentValues value : values) {

                            long rowId = db.insert(DatabaseContract.LectureTagsEntry.TABLE_NAME, null, value);
                            if (rowId > 0) rowsInserted++;
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
            case CODE_SPECYFIC_LECTURE: {

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
            case CODE_LECTURES: {
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
            case CODE_SPECYFIC_LECTURES_JOIN_SCHED: {
                String id = uri.getLastPathSegment();
                String projectionText = "";
                for (int i = 0; i < projection.length - 1; i++) {
                    projectionText += projection[i] + ", ";
                }
                projectionText = projectionText + projection[projection.length - 1];

                SQLiteDatabase db = mOpenHelper.getReadableDatabase();
                cursor = db.rawQuery("SELECT " + projectionText + " FROM " +
                        "Ref LEFT JOIN UserSchedule ON Ref._id = UserSchedule.id " +
                        "WHERE _id = " + id, null);
                break;
            }

            case CODE_LECTURES_JOIN_SCHED: {
                String projectionText = "";
                for (int i = 0; i < projection.length - 1; i++) {
                    projectionText += projection[i] + ", ";
                }
                projectionText = projectionText + projection[projection.length - 1];

                SQLiteDatabase db = mOpenHelper.getReadableDatabase();
                cursor = db.rawQuery("SELECT " + projectionText + " FROM " +
                        "Ref LEFT JOIN UserSchedule ON Ref._id = UserSchedule.id " +
                        "WHERE startTime = \"" + selectionArgs[0] + "\" AND date_id = " + selectionArgs[1] + " ORDER BY " +
                        sortOrder, null);
                break;
            }

            case CODE_USER_LECTURES: {
                String projectionText = "";
                for (int i = 0; i < projection.length - 1; i++) {
                    projectionText += projection[i] + ", ";
                }
                projectionText = projectionText + projection[projection.length - 1];

//                SQLiteDatabase db = mOpenHelper.getReadableDatabase();
//                cursor = db.rawQuery("SELECT " + projectionText + " FROM " +
//                        "Ref LEFT JOIN UserSchedule ON Ref._id = UserSchedule.id " +
//                        "WHERE startTime = \"" + selectionArgs[0] + "\" AND date_id = " + selectionArgs[1] + " AND " +
//                        DatabaseContract.UserLecturesEntry.COLUMN_IS_IN_USR
//                        + " = 1 ORDER BY " +
//                        sortOrder, null);
                SQLiteDatabase db = mOpenHelper.getReadableDatabase();
                cursor = db.rawQuery("SELECT " + projectionText + " FROM " +
                        "Ref LEFT JOIN UserSchedule ON Ref._id = UserSchedule.id " +
                        "WHERE startTime = \"" + selectionArgs[0] + "\" AND date_id = " + selectionArgs[1] + " AND is_in_usr_sched = 1 "+ " ORDER BY " +
                        sortOrder, null);
                break;
            }

            case CODE_SPECYFIC_POSTER: {

                String data_id = uri.getLastPathSegment();

                String[] selectionArguments = new String[]{data_id};

                cursor = mOpenHelper.getReadableDatabase().query(
                        DatabaseContract.PostersEntry.TABLE_NAME,
                        projection,
                        DatabaseContract.PostersEntry.COLUMN_ID + " = ? ",
                        selectionArguments,
                        null,
                        null,
                        sortOrder);

                break;
            }

            case CODE_POSTERS: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        DatabaseContract.PostersEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            }
            case CODE_GET_ALL_START_TIMES:
                SQLiteDatabase db = mOpenHelper.getReadableDatabase();
                cursor = db.rawQuery("SELECT startTime FROM (SELECT startTime, date_id FROM Ref UNION SELECT startTime, date_id FROM Break) WHERE date_id = "
                        + selectionArgs[0] +
                        " ORDER BY time(startTime)", null);
                break;
            case CODE_GET_ALL_USERS_START_TIMES:
                SQLiteDatabase dab = mOpenHelper.getReadableDatabase();
                cursor = dab.rawQuery("SELECT startTime FROM (SELECT startTime, date_id FROM Ref LEFT JOIN UserSchedule ON Ref._id = UserSchedule.id " +
                        " WHERE is_in_usr_sched = 1 UNION SELECT startTime, date_id FROM Break) WHERE date_id = "
                        + selectionArgs[0] +
                        " ORDER BY time(startTime)", null);

                break;
            case CODE_BREAKS: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        DatabaseContract.BreakEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case CODE_TAGS: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        DatabaseContract.TagsEntry.TABLE_NAME,
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
        Cursor cur;
        SQLiteDatabase db;
        switch (sUriMatcher.match(uri)) {
            case CODE_LECTURES:
                db = mOpenHelper.getWritableDatabase();
                cur = db.rawQuery("DELETE FROM Ref WHERE _id NOT IN " + selectionArgs[0], null);
                numRowsDeleted = cur.getCount();
                break;
                case CODE_POSTERS:
                    db = mOpenHelper.getWritableDatabase();
                    cur = db.rawQuery("DELETE FROM Posters WHERE id NOT IN " + selectionArgs[0], null);
                    numRowsDeleted = cur.getCount();
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
            case CODE_LECTURES:
                long id = db.insert(DatabaseContract.LecturesEntry.TABLE_NAME, null, values);
                if (id > 0) {
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
        int match = sUriMatcher.match(uri);
        int updatedRows;
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        switch (match) {
//            case CODE_SPECYFIC_LECTURE:
//                String data_id = uri.getLastPathSegment();
//                selection = DatabaseContract.LecturesEntry.COLUMN_ID + "= ? ";
//                String[] selectionArguments = new String[]{data_id};
//                updatedRows = db.update(DatabaseContract.LecturesEntry.TABLE_NAME,
//                        values, selection, selectionArguments);
//                break;
            case CODE_SPECYFIC_SCHED:
                String id = uri.getLastPathSegment();
                selection = DatabaseContract.ScheduleEntry.COLUMN_ID + "= ? ";
                String[] selectionArguments2 = new String[]{id};
                updatedRows = db.update(DatabaseContract.ScheduleEntry.TABLE_NAME,
                        values, selection, selectionArguments2);
                break;
            case CODE_SPECYFIC_POSTER:
                String poster_id = uri.getLastPathSegment();
                selection = DatabaseContract.PostersEntry.COLUMN_ID + "= ? ";
                String[] selectionArguments3 = new String[]{poster_id};
                updatedRows = db.update(DatabaseContract.PostersEntry.TABLE_NAME,
                        values, selection, selectionArguments3);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return updatedRows;

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
