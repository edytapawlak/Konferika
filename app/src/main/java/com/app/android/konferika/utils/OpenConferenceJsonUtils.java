/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.app.android.konferika.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

//import com.app.android.konferika.data.ActivityData;
import com.app.android.konferika.data.DataProvider;
import com.app.android.konferika.data.DatabaseContract;
import com.app.android.konferika.obj.Lecture;
import com.app.android.konferika.obj.Tag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility functions to handle OpenWeatherMap JSON data.
 */
public final class OpenConferenceJsonUtils {

    /**
     * This method parses JSON from a web response and returns an array of Strings
     * describing lectures
     * <p/>
     *
     * @param forecastJsonStr JSON response from server
     * @return Array of Strings describing weather data
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static String[] getLecturesStringsFromJson(Context context, String forecastJsonStr)
            throws JSONException {

        final String OWM_TITLE = "title";
        final String OWM_AUTHORS = "authors";
        final String OWM_ABSTRACT = "abstract";
        final String OWM_SCHEDULE = "schedule";
        final String OWM_STARTTIME = "start";
        final String OWM_ENDTIME = "end";
        final String OWM_DATE = "date";
        final String OWM_ROOM = "place";
        final String OWM_TAGS = "tags";
        final String OWM_ID = "id";

        /* String array to hold each lecture String */
        String[] parsedLecturesData = null;

        JSONArray jsonArray = new JSONArray(forecastJsonStr);
        parsedLecturesData = new String[jsonArray.length()];

//        long localDate = System.currentTimeMillis();
//        long utcDate = SunshineDateUtils.getUTCDateFromLocal(localDate);
//        long startDay = SunshineDateUtils.normalizeDate(utcDate);

        List<ContentValues> toadd = new ArrayList<>();
        List<ContentValues> scheduleToadd = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            /* These are the values that will be collected */
            String title;
            String[] authors;
            String startTime;
            String endTime;
            int date;
            String description;
            String place;
            String[] tags;
            int id;
            JSONArray tmp;
            JSONArray authorsArr;
            JSONObject schedule;

            /* Get the JSON object representing the day */
            JSONObject lectureForecast = jsonArray.getJSONObject(i);
            authorsArr = lectureForecast.getJSONArray(OWM_AUTHORS);
            authors = new String[authorsArr.length()];
            JSONObject authorJson;
            //Tablica autorów, bo może być ich więcej
            for (int j = 0; j < authorsArr.length(); j++) {
                authorJson = authorsArr.getJSONObject(j);
                authors[j] = authorJson.getString("fname") + " " + authorJson.getString("sname");
            }
            title = lectureForecast.getString(OWM_TITLE);
            description = lectureForecast.getString(OWM_ABSTRACT);
            tmp = lectureForecast.getJSONArray(OWM_TAGS);
            tags = new String[tmp.length()];
            ContentValues[] contentValues = new ContentValues[tmp.length()];
            for (int j = 0; j < tmp.length(); j++) {
                contentValues[j] = new ContentValues();
                if(!tmp.getString(j).equals("")) {
                    contentValues[j].put(DatabaseContract.TagsEntry.COLUMN_TITLE, tmp.getString(j));
                }
                tags[j] = tmp.getString(j);
                Log.v("TAGS ADD", "Dodaje " + tags[j]);
            }
                context.getContentResolver().bulkInsert(DatabaseContract.TagsEntry.CONTENT_URI, contentValues);
            schedule = lectureForecast.getJSONObject(OWM_SCHEDULE);
            id = lectureForecast.getInt(OWM_ID);

            startTime = schedule.getString(OWM_STARTTIME);
            endTime = schedule.getString(OWM_ENDTIME);
            String dateString = schedule.getString(OWM_DATE);
            if (dateString.equals("12 05 2017")) {
                date = 1;
            } else if (dateString.equals("13 05 2017")) {
                date = 2;
            } else {
                date = 3;
            }
            place = schedule.getString(OWM_ROOM);
            if (tags.length > 0) {
                parsedLecturesData[i] = " - " + title + " - " + date + "-" + place + "--" + tags[0];
            } else {
                parsedLecturesData[i] = " - " + title + " - " + date + "-" + place + "--";
            }
//            TU TRZEBA BĘDZIE TWORZYĆ LECTURES

//            Lecture lectToAdd = new Lecture(title, authors[0], description, date, id, startTime, place, new ArrayList<Tag>(), false);
//            ActivityData.updateLecture(context, lectToAdd);

            ContentValues sched_cv = new ContentValues();
            sched_cv.put(DatabaseContract.ScheduleEntry.COLUMN_ID, id);
            sched_cv.put(DatabaseContract.ScheduleEntry.COLUMN_IS_IN_USR, "0");
            scheduleToadd.add(sched_cv);

            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.LecturesEntry.COLUMN_ID, id);
            cv.put(DatabaseContract.LecturesEntry.COLUMN_TITLE, title);
            cv.put(DatabaseContract.LecturesEntry.COLUMN_AUTHOR, authors[0]);
            cv.put(DatabaseContract.LecturesEntry.COLUMN_ABSTRACT, description);
            cv.put(DatabaseContract.LecturesEntry.COLUMN_START_TIME, startTime);
            cv.put(DatabaseContract.LecturesEntry.COLUMN_DATE_ID, date);
            cv.put(DatabaseContract.LecturesEntry.COLUMN_ROOM_ID, place);
            toadd.add(cv);

        }
        ContentValues[] arr = new ContentValues[toadd.size()];
        ContentValues[] scheduleArr = new ContentValues[toadd.size()];

        int i = 0;
        for (ContentValues c :
                toadd) {
            arr[i] = c;
            i++;
        }
        i = 0;
        for (ContentValues c :
                scheduleToadd) {
            scheduleArr[i] = c;
            i++;
        }

        context.getContentResolver().bulkInsert(DatabaseContract.LecturesEntry.CONTENT_URI, arr);
        context.getContentResolver().bulkInsert(DatabaseContract.ScheduleEntry.CONTENT_URI, scheduleArr);

        return parsedLecturesData;
    }

    public static String[] getPostersStringsFromJson(Context context, String forecastJsonStr)
            throws JSONException {

        final String OWM_TITLE = "title";
        final String OWM_ID = "id";
        final String OWM_AUTHORS = "authors";
        final String OWM_ABSTRACT = "abstract";
        final String OWM_SCHEDULE = "schedule";
        final String OWM_STARTTIME = "start";
        final String OWM_ENDTIME = "end";
        final String OWM_DATE = "date";
        final String OWM_ROOM = "place";
        final String OWM_TAGS = "tags";

        /* String array to hold each posters String */
        String[] parsedLecturesData = null;

        JSONArray jsonArray = new JSONArray(forecastJsonStr);
        parsedLecturesData = new String[jsonArray.length()];
        String startTime = null;
        String endTime = null;
        String date = null;

        List<ContentValues> toadd = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            /* These are the values that will be collected */
            String title;
            String[] authors;
            int id;
            String description;
            String[] tags;
            JSONArray tmp;
            JSONObject schedule;
            JSONArray authorsArr;

            /* Get the JSON object representing the day */
            JSONObject lectureForecast = jsonArray.getJSONObject(i);

            authorsArr = lectureForecast.getJSONArray(OWM_AUTHORS);
            authors = new String[authorsArr.length()];
            JSONObject authorJson;
            //Tablica autorów, bo może być ich więcej
            for (int j = 0; j < authorsArr.length(); j++) {
                authorJson = authorsArr.getJSONObject(j);
                authors[j] = authorJson.getString("fname") + " " + authorJson.getString("sname");
            }
            title = lectureForecast.getString(OWM_TITLE);
            id = lectureForecast.getInt(OWM_ID);
            description = lectureForecast.getString(OWM_ABSTRACT);
            tmp = lectureForecast.getJSONArray(OWM_TAGS);
            tags = new String[tmp.length()];
            for (int j = 0; j < tmp.length(); j++) {
                tags[j] = tmp.getString(j);
            }

            if (startTime == null || endTime == null || date == null) {
                schedule = lectureForecast.getJSONObject(OWM_SCHEDULE);
                startTime = schedule.getString(OWM_STARTTIME);
                endTime = schedule.getString(OWM_ENDTIME);
                date = schedule.getString(OWM_DATE);
            }
            if (tags.length > 0) {
                parsedLecturesData[i] = " - " + title + " - " + date + "--" + tags[0];
            } else {
                parsedLecturesData[i] = " - " + title + " - " + date + "--";
            }
//            TU TRZEBA BĘDZIE TWORZYĆ POSTERS

            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.PostersEntry.COLUMN_ID, id);
            cv.put(DatabaseContract.PostersEntry.COLUMN_TITLE, title);
            cv.put(DatabaseContract.PostersEntry.COLUMN_AUTHOR, authors[0]);
            cv.put(DatabaseContract.PostersEntry.COLUMN_ABSTRACT, description);
            toadd.add(cv);

        }
        ContentValues[] arr = new ContentValues[toadd.size()];
        int i = 0;
        for (ContentValues c :
                toadd) {
            arr[i] = c;
            i++;
        }

        context.getContentResolver().bulkInsert(DatabaseContract.PostersEntry.CONTENT_URI, arr);

        return parsedLecturesData;
    }

    public static String[] getBreaksStringsFromJson(Context context, String forecastJsonStr)
            throws JSONException {

        final String OWM_ID = "id";
        final String OWM_TITLE = "title";
        final String OWM_SCHEDULE = "schedule";
        final String OWM_STARTTIME = "start";
        final String OWM_ENDTIME = "end";
        final String OWM_DATE = "date";
        final String OWM_ROOM = "place";

        String[] parsedBreaksData = null;
        List<ContentValues> toadd = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(forecastJsonStr);
        parsedBreaksData = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            /* These are the values that will be collected */
            int id;
            String title;
            String startTime;
            String endTime;
            String date;
            int date_id;
            String place;
            JSONArray tmp;
            JSONObject schedule;

            JSONObject lectureForecast = jsonArray.getJSONObject(i);
            id = lectureForecast.getInt(OWM_ID);
            title = lectureForecast.getString(OWM_TITLE);
            schedule = lectureForecast.getJSONObject(OWM_SCHEDULE);

            startTime = schedule.getString(OWM_STARTTIME);
            endTime = schedule.getString(OWM_ENDTIME);
            date = schedule.getString(OWM_DATE);
            String dateString = schedule.getString(OWM_DATE);
            if (dateString.equals("12 05 2017")) {
                date_id = 1;
            } else if (dateString.equals("13 05 2017")) {
                date_id = 2;
            } else {
                date_id = 3;
            }
            place = schedule.getString(OWM_ROOM);
            parsedBreaksData[i] = " - " + title + " - " + date + "-" + place + "--";
//            TU TRZEBA BĘDZIE TWORZYĆ Breaks
            int type = 3;
            if(title.equals("Przerwa obiadowa")){
                type = 2;
            }
            if(title.equals("Sesja plakatowa")){
                type = 1;
            }


            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.BreakEntry.COLUMN_ID, id);
            cv.put(DatabaseContract.BreakEntry.COLUMN_START_TIME, startTime);
            cv.put(DatabaseContract.BreakEntry.COLUMN_END_TIME, endTime);
            cv.put(DatabaseContract.BreakEntry.COLUMN_TITLE, title);
            cv.put(DatabaseContract.BreakEntry.COLUMN_DATE_ID, date_id);
            cv.put(DatabaseContract.BreakEntry.COLUMN_TYPE, type);


            toadd.add(cv);
        }

        ContentValues[] arr = new ContentValues[toadd.size()];
        int i = 0;
        for (ContentValues c :
                toadd) {
            arr[i] = c;
            i++;
        }

        context.getContentResolver().bulkInsert(DatabaseContract.BreakEntry.CONTENT_URI, arr);


        return parsedBreaksData;
    }

    /**
     * Parse the JSON and convert it into ContentValues that can be inserted into our database.
     *
     * @param context         An application context, such as a service or activity context.
     * @param forecastJsonStr The JSON to parse into ContentValues.
     * @return An array of ContentValues parsed from the JSON.
     */
    public static ContentValues[] getFullWeatherDataFromJson(Context context, String forecastJsonStr) {
        /** This will be implemented in a future lesson **/
        return null;
    }

    /**
     * In case of a conflict when inserting the values, another update query is sent.
     *
     * @param db     Database to insert to.
     * @param uri    Content provider uri.
     * @param table  Table to insert to.
     * @param values The values to insert to.
     * @param column Column to identify the object.
     * @throws android.database.SQLException
     */

}