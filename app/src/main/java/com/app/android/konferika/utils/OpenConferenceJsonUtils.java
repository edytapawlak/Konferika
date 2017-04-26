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
            for (int j = 0; j < tmp.length(); j++) {
                tags[j] = tmp.getString(j);
            }
            schedule = lectureForecast.getJSONObject(OWM_SCHEDULE);
            id = lectureForecast.getInt(OWM_ID);

            startTime = schedule.getString(OWM_STARTTIME);
            endTime = schedule.getString(OWM_ENDTIME);
            if(schedule.getString(OWM_DATE).equals("23 05 2042")) {
                date = 1;
            } else{date = 2;}
            place = schedule.getString(OWM_ROOM);
            if (tags.length > 0) {
                parsedLecturesData[i] = " - " + title + " - " + date + "-" + place + "--" + tags[0];
            } else {
                parsedLecturesData[i] = " - " + title + " - " + date + "-" + place + "--";
            }
//            TU TRZEBA BĘDZIE TWORZYĆ LECTURES

            Lecture lectToAdd = new Lecture(title, authors[0], description, date, id, startTime, place, new ArrayList<Tag>(), false);
//            ActivityData.updateLecture(context, lectToAdd);

            ContentValues cv = new ContentValues();
            cv.put("_id", id);
            cv.put("title", title);
            cv.put("author", authors[0]);
            cv.put("abstract", description);
            cv.put("startTime", startTime);
            cv.put("date_id", date);
            cv.put("room_id", place);
            toadd.add(cv);

        }
        ContentValues[] arr = new ContentValues[toadd.size()];
        int i = 0;
        for (ContentValues c :
                toadd) {
            arr[i] = c;
            i++;
        }

        context.getContentResolver().bulkInsert(DatabaseContract.LecturesEntry.CONTENT_URI, arr);

        return parsedLecturesData;
    }

    public static String[] getPostersStringsFromJson(Context context, String forecastJsonStr)
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

        /* String array to hold each posters String */
        String[] parsedLecturesData = null;

        JSONArray jsonArray = new JSONArray(forecastJsonStr);
        parsedLecturesData = new String[jsonArray.length()];
        String startTime = null;
        String endTime = null;
        String date = null;

        for (int i = 0; i < jsonArray.length(); i++) {
            /* These are the values that will be collected */
            String title;
            String[] authors;
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
            tmp = lectureForecast.getJSONArray(OWM_TAGS);
            tags = new String[tmp.length()];
            for (int j = 0; j < tmp.length(); j++) {
                tags[j] = tmp.getString(j);
            }

            if(startTime == null || endTime == null || date == null) {
                schedule = lectureForecast.getJSONObject(OWM_SCHEDULE);
                startTime = schedule.getString(OWM_STARTTIME);
                endTime = schedule.getString(OWM_ENDTIME);
                date = schedule.getString(OWM_DATE);
            }
            if (tags.length > 0) {
                parsedLecturesData[i] = " - " + title + " - " + date + "--" + tags[0];
            } else {
                parsedLecturesData[i] = " - " + title + " - " + date +  "--";
            }
//            TU TRZEBA BĘDZIE TWORZYĆ LECTURES
        }

        return parsedLecturesData;
    }

    public static String[] getBreaksStringsFromJson(Context context, String forecastJsonStr)
            throws JSONException {

        final String OWM_TITLE = "title";
        final String OWM_SCHEDULE = "schedule";
        final String OWM_STARTTIME = "start";
        final String OWM_ENDTIME = "end";
        final String OWM_DATE = "date";
        final String OWM_ROOM = "place";

        String[] parsedLecturesData = null;

        JSONArray jsonArray = new JSONArray(forecastJsonStr);
        parsedLecturesData = new String[jsonArray.length()];
        Log.v("Arrrrr", jsonArray.length()+"");

        for (int i = 0; i < jsonArray.length(); i++) {
            /* These are the values that will be collected */
            String title;
            String startTime;
            String endTime;
            String date;
            String place;
            JSONArray tmp;
            JSONObject schedule;

            JSONObject lectureForecast = jsonArray.getJSONObject(i);
            title = lectureForecast.getString(OWM_TITLE);
            schedule = lectureForecast.getJSONObject(OWM_SCHEDULE);

            startTime = schedule.getString(OWM_STARTTIME);
            endTime = schedule.getString(OWM_ENDTIME);
            date = schedule.getString(OWM_DATE);
            place = schedule.getString(OWM_ROOM);
            parsedLecturesData[i] = " - " + title + " - " + date + "-" + place + "--";
//            TU TRZEBA BĘDZIE TWORZYĆ LECTURES
        }
        return parsedLecturesData;
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
}