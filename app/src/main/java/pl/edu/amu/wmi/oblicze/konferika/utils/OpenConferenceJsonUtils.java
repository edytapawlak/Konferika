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
package pl.edu.amu.wmi.oblicze.konferika.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.edu.amu.wmi.oblicze.konferika.data.DatabaseContract;

//import com.app.android.konferika.data.ActivityData;

/**
 * Utility functions to handle OpenWeatherMap JSON data.
 */
public final class OpenConferenceJsonUtils {

    private static String firstDay = "11 05 2018";
    private static String secondDay = "12 05 2018";
    private static String thirdDay = "13 05 2018";
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
        String[] result = new String[1];
        result[0] = "";

        JSONArray jsonArray = new JSONArray(forecastJsonStr);
//        parsedLecturesData = new String[jsonArray.length()];

        List<ContentValues> toadd = new ArrayList<>();
        List<ContentValues> scheduleToadd = new ArrayList<>();
        String deleteWhere = "( ";
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
            String tagSelection = " ";

            /* Get the JSON object representing the day */
            JSONObject lectureForecast = jsonArray.getJSONObject(i);
            id = lectureForecast.getInt(OWM_ID);
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
            int tagId;
            tags = new String[tmp.length()];
            ContentValues[] contentValues = new ContentValues[tmp.length()];

//------------------------------Wypełnianie tabeli Tags---------------------------------

            for (int j = 0; j < tmp.length(); j++) {
                contentValues[j] = new ContentValues();

                if (!tmp.getString(j).equals("")) {
                    contentValues[j].put(DatabaseContract.TagsEntry.COLUMN_TITLE, tmp.getString(j));
                }
                tags[j] = tmp.getString(j);
                if (j != tmp.length() - 1) {
                    tagSelection += DatabaseContract.TagsEntry.COLUMN_TITLE + " =? OR ";
                } else {
                    tagSelection += DatabaseContract.TagsEntry.COLUMN_TITLE + " =? ";
                }
            }
            context.getContentResolver().bulkInsert(DatabaseContract.TagsEntry.CONTENT_URI, contentValues);

//------------------------ Wypełnianie tabeli Lecturres_tags -----------------------------------

            String[] tagProjection = {DatabaseContract.TagsEntry.COLUMN_ID};
            Cursor tagsCursor = context.getContentResolver().query(DatabaseContract.TagsEntry.CONTENT_URI, tagProjection, tagSelection, tags, null);
//            String debug = "";
//            for (int j = 0; j < tags.length; j++) {
//                debug += ", " + tags[j];
//            }
            tagsCursor.moveToFirst();
            ContentValues[] tags_lect = new ContentValues[tagsCursor.getCount()];
            int k = 0;
            while (!tagsCursor.isAfterLast()) {
                ContentValues cv = new ContentValues();
                cv.put(DatabaseContract.LectureTagsEntry.COLUMN_LECT_ID, id);
                cv.put(DatabaseContract.LectureTagsEntry.COLUMN_TAG_ID, tagsCursor.getInt(0));
                tags_lect[k] = cv;
                k++;
                tagsCursor.moveToNext();
            }
            tagsCursor.close();
            context.getContentResolver().bulkInsert(DatabaseContract.LectureTagsEntry.CONTENT_URI, tags_lect);

//-------------------------- Dane o miejscu i czasie referatów ---------------------------------------------------

            schedule = lectureForecast.getJSONObject(OWM_SCHEDULE);
            startTime = schedule.getString(OWM_STARTTIME);
            endTime = schedule.getString(OWM_ENDTIME);
            String dateString = schedule.getString(OWM_DATE);
            place = schedule.getString(OWM_ROOM);

// ------- Zamiana daty na inty
            if (dateString.equals(firstDay)) {
                date = 1;
            } else if (dateString.equals(secondDay)) {
                date = 2;
            } else {
                date = 3;
            }

// -------- Tworze WHERE dla usuwania nieaktualnych danych. To ma być ciąg postaci ( id1, id2, id3, ..., 1, 2, 3 ) {1, 2, 3 bo to są id referatów, które muszą być zawsze}
//           celem jest zapytanie typu DELETE costam FROM costam WHERE costam NOT IN (id1, ....)
            if (i != jsonArray.length() - 1) {
                deleteWhere += id + ", ";
            } else {
                deleteWhere += id;
            }

            ContentValues sched_cv = new ContentValues();
            sched_cv.put(DatabaseContract.ScheduleEntry.COLUMN_ID, id);

//            sched_cv.put(DatabaseContract.ScheduleEntry.COLUMN_IS_IN_USR, "0");

            scheduleToadd.add(sched_cv);
//------------Wybieram autorów, w stringu zapisuje ich po przecinku
            String aut = "";
            for (int j = 0; j < authors.length - 1; j++) {
                aut += authors[j] + ", ";
            }
            aut += authors[authors.length - 1];

            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.LecturesEntry.COLUMN_ID, id);
            cv.put(DatabaseContract.LecturesEntry.COLUMN_TITLE, title);
            cv.put(DatabaseContract.LecturesEntry.COLUMN_AUTHOR, aut);
            cv.put(DatabaseContract.LecturesEntry.COLUMN_ABSTRACT, description);
            cv.put(DatabaseContract.LecturesEntry.COLUMN_START_TIME, startTime);
            cv.put(DatabaseContract.LecturesEntry.COLUMN_DATE_ID, date);
            cv.put(DatabaseContract.LecturesEntry.COLUMN_ROOM_ID, place);
            toadd.add(cv);
        }

        deleteWhere += ", 1, 2, 3)";
        String[] whereArr = {deleteWhere};
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

//-------------------------- Usuwam nieaktualne dane, czyli te, które znikneły z JSONA -----------------------------------------
        context.getContentResolver().delete(DatabaseContract.LecturesEntry.CONTENT_URI, DatabaseContract.LecturesEntry.COLUMN_ID, whereArr);

        return result;
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

        String deleteWhere = "( ";
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
//-------------- Dodaje autorów ---------------------
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

//------------- Dodaje tagi ---------------------
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

            parsedLecturesData[i] = " - " + title + " - " + date + "--";

            if (i != jsonArray.length() - 1) {
                deleteWhere += id + ", ";
            } else {
                deleteWhere += id;
            }
            String authorsText = "";
                for (int k = 0; k < authors.length - 1 ; k++) {
                    authorsText += authors[k] + ", ";
                }
                authorsText += authors[authors.length - 1];

                ContentValues cv = new ContentValues();
                cv.put(DatabaseContract.PostersEntry.COLUMN_ID, id);
                cv.put(DatabaseContract.PostersEntry.COLUMN_TITLE, title);
                cv.put(DatabaseContract.PostersEntry.COLUMN_AUTHOR, authorsText);
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

        deleteWhere += " )";
        String[] whereArr = {deleteWhere};
        context.getContentResolver().bulkInsert(DatabaseContract.PostersEntry.CONTENT_URI, arr);
        context.getContentResolver().delete(DatabaseContract.PostersEntry.CONTENT_URI, DatabaseContract.LecturesEntry.COLUMN_ID, whereArr);

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
        String deleteWhere = "( ";

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
            if (dateString.equals(firstDay)) {
                date_id = 1;
            } else if (dateString.equals(secondDay)) {
                date_id = 2;
            } else {
                date_id = 3;
            }
            place = schedule.getString(OWM_ROOM);
            parsedBreaksData[i] = " - " + title + " - " + date + "-" + place + "--";
//            TU TRZEBA BĘDZIE TWORZYĆ Breaks
            int type = 3;
            if (title.equals("Przerwa obiadowa")) {
                type = 2;
            }
            if (title.equals("Sesja plakatowa")) {
                type = 1;
            }
            if(title.contains("Spacer po Poznaniu")){
                type = 4;
            }
            if(title.contains("integra")){
                type = 5;
            }
            if(title.contains("gier")){
                type = 6;
            }
            if(title.contains("Zakończenie")){
                type = 7;
            }

            if (i != jsonArray.length() - 1) {
                deleteWhere += id + ", ";
            } else {
                deleteWhere += id;
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

        deleteWhere += " )";
        String[] whereArr = {deleteWhere};
        ContentValues[] arr = new ContentValues[toadd.size()];
        int i = 0;
        for (ContentValues c :
                toadd) {
            arr[i] = c;
            i++;
        }

        context.getContentResolver().delete(DatabaseContract.BreakEntry.CONTENT_URI, DatabaseContract.BreakEntry.COLUMN_ID, whereArr);
        context.getContentResolver().bulkInsert(DatabaseContract.BreakEntry.CONTENT_URI, arr);


        return parsedBreaksData;
    }

    public static String[] getSpecialLectStringsFromJson(Context context, String forecastJsonStr)
            throws JSONException {

        final String OWM_ID = "lecture_id";
        final String OWM_TITLE = "title";
        final String OWM_AUTHORS = "authors";
        final String OWM_SCHEDULE = "schedule";
        final String OWM_STARTTIME = "start";
        final String OWM_ENDTIME = "end";
        final String OWM_DATE = "date";
        final String OWM_ROOM = "place";

        String[] parsedSpecialData = null;
        List<ContentValues> toadd = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(forecastJsonStr);
        parsedSpecialData = new String[jsonArray.length()];
        String deleteWhere = "( ";

        for (int i = 0; i < jsonArray.length(); i++) {
            /* These are the values that will be collected */
            int id;
            String title;
            String startTime;
            String endTime;
            String dateString;
            int date_id;
            String place;
            String authors;
            JSONArray tmp;
            JSONObject schedule;

            JSONObject lectureForecast = jsonArray.getJSONObject(i);
            id = lectureForecast.getInt(OWM_ID);
            title = lectureForecast.getString(OWM_TITLE);

//            startTime = lectureForecast.getString(OWM_STARTTIME);
//            endTime = lectureForecast.getString(OWM_ENDTIME);
//            dateString = lectureForecast.getString(OWM_DATE);
//            place = lectureForecast.getString(OWM_ROOM);

            authors = lectureForecast.getString(OWM_AUTHORS);
            schedule = lectureForecast.getJSONObject(OWM_SCHEDULE);

            startTime = schedule.getString(OWM_STARTTIME);
            endTime = schedule.getString(OWM_ENDTIME);
            dateString = schedule.getString(OWM_DATE);
            if (dateString.equals(firstDay)) {
                date_id = 1;
            } else if (dateString.equals(secondDay)) {
                date_id = 2;
            } else {
                date_id = 3;
            }
            place = schedule.getString(OWM_ROOM);

            parsedSpecialData[i] = " - " + title + " - " + dateString + "-" + place + "--";

            if (i != jsonArray.length() - 1) {
                deleteWhere += id + ", ";
            } else {
                deleteWhere += id;
            }

            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.LecturesEntry.COLUMN_ID, id);
            cv.put(DatabaseContract.LecturesEntry.COLUMN_START_TIME, startTime);
            cv.put(DatabaseContract.LecturesEntry.COLUMN_END_TIME, endTime);
            cv.put(DatabaseContract.LecturesEntry.COLUMN_TITLE, title);
            cv.put(DatabaseContract.LecturesEntry.COLUMN_DATE_ID, date_id);
            cv.put(DatabaseContract.LecturesEntry.COLUMN_AUTHOR, authors);
            cv.put(DatabaseContract.LecturesEntry.COLUMN_ROOM_ID, place);

            toadd.add(cv);
        }

        deleteWhere += " )";
        String[] whereArr = {deleteWhere};
        ContentValues[] arr = new ContentValues[toadd.size()];
        int i = 0;
        for (ContentValues c :
                toadd) {
            arr[i] = c;
            i++;
        }

        context.getContentResolver().delete(DatabaseContract.LecturesEntry.CONTENT_URI, DatabaseContract.LecturesEntry.COLUMN_ID, whereArr);
        context.getContentResolver().bulkInsert(DatabaseContract.LecturesEntry.CONTENT_URI, arr);

        return parsedSpecialData;
    }


}