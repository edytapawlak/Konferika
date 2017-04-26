package com.app.android.konferika.obj;


import android.content.Context;
import android.database.Cursor;

import com.app.android.konferika.data.DatabaseContract;

import java.util.ArrayList;
import java.util.List;

//import com.app.android.konferika.data.ActivityData;

public class ConferencePlanData extends DisplayData {


    public ConferencePlanData(Context context, int dateId) {
        super.setDateId(dateId);
//
//        String[] projection = {DatabaseContract.LecturesEntry.COLUMN_TITLE,
//                DatabaseContract.LecturesEntry.COLUMN_AUTHOR,
//                DatabaseContract.LecturesEntry.COLUMN_ABSTRACT,
//                DatabaseContract.LecturesEntry.COLUMN_DATE_ID,
//                DatabaseContract.LecturesEntry.COLUMN_START_TIME,
//                DatabaseContract.LecturesEntry.COLUMN_ROOM_ID,
//                DatabaseContract.LecturesEntry.COLUMN_IS_IN_USR};
//        String selection = DatabaseContract.LecturesEntry.COLUMN_DATE_ID + "= ?";
//        String[] selectionArgs = {dateId + ""};
//        String sortOrder = null;
//        context.getContentResolver().query(DatabaseContract.LecturesEntry.CONTENT_URI, projection, selection, selectionArgs, sortOrder);

        super.setSectionList(getConferencePlan(context, dateId));//ActivityData.getHeaders(context, dateId));

    }

    @Override
    public void addActivityToList(Context context, Lecture activity) {
    }

    @Override
    public void deleteActivityFromList(Lecture lecture) {

    }


    /**
     * Tworzy liste zajęć, pogrupowanych względem godzin dla nadego dnia
     *
     * @param context
     * @param dateId
     * @return
     */
    public List<SectionHeader> getConferencePlan(Context context, int dateId) {
        //Pobrać wszystkie startTime
        String[] timeSelArgs = {dateId+""};
        Cursor startTimeCursor = context.getContentResolver().query(DatabaseContract.StartTimesEntry.CONTENT_URI, null,
                null, timeSelArgs, null);
        Cursor actForTimeCursor;
        startTimeCursor.moveToFirst();
        String time = "";
        String[] selectionArgs = new String[2];
        String[] lecturesProjection = {
                DatabaseContract.LecturesEntry.COLUMN_ID,
                DatabaseContract.LecturesEntry.COLUMN_TITLE,
                DatabaseContract.LecturesEntry.COLUMN_AUTHOR,
                DatabaseContract.LecturesEntry.COLUMN_ABSTRACT,
                DatabaseContract.LecturesEntry.COLUMN_DATE_ID,
                DatabaseContract.LecturesEntry.COLUMN_START_TIME,
                DatabaseContract.LecturesEntry.COLUMN_ROOM_ID,
                DatabaseContract.LecturesEntry.COLUMN_IS_IN_USR};
        String[] breakssProjection = {
                DatabaseContract.BreakEntry.COLUMN_TYPE,
                DatabaseContract.BreakEntry.COLUMN_TITLE,
                DatabaseContract.BreakEntry.COLUMN_START_TIME,
                };
        String selectionLect = DatabaseContract.LecturesEntry.COLUMN_START_TIME + " = ? AND " + DatabaseContract.LecturesEntry.COLUMN_DATE_ID + " = ?";
        String selectionBreak = DatabaseContract.BreakEntry.COLUMN_START_TIME + " = ? AND " + DatabaseContract.BreakEntry.COLUMN_DATE_ID + " = ?";

        SectionHeader sc;
        boolean isLect;
        List<SectionHeader> list = new ArrayList<SectionHeader>();

        //Pobrac kursor dla każdej godziny
        while (!startTimeCursor.isAfterLast()) {
            time = startTimeCursor.getString(0);
            selectionArgs[0] = time + "";
            selectionArgs[1] = dateId + "";
            actForTimeCursor = context.getContentResolver().query(DatabaseContract.LecturesEntry.CONTENT_URI,
                    lecturesProjection, selectionLect, selectionArgs, "time(" + DatabaseContract.LecturesEntry.COLUMN_START_TIME + ")");
            isLect = true;
            if (actForTimeCursor.getCount() < 1) {
                actForTimeCursor = context.getContentResolver().query(DatabaseContract.BreakEntry.CONTENT_URI,
                        breakssProjection, selectionBreak, selectionArgs, "time(" + DatabaseContract.BreakEntry.COLUMN_START_TIME + ")");
                isLect = false;

            }
            sc = new SectionHeader(actForTimeCursor, time, isLect);
            list.add(sc);
            startTimeCursor.moveToNext();
        }
        startTimeCursor.close();
        return list;
    }
}
