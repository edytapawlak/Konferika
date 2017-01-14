package com.app.android.konferika.data;

import android.content.Context;

import com.app.android.konferika.Activity;
import com.app.android.konferika.Lecture;
import com.app.android.konferika.SectionHeader;
import com.intrusoft.sectionedrecyclerview.Section;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ActivityData {

    private static List<Activity> lectures;

    private static List<SectionHeader> headers;

    private static Activity[] breaks;

    public static List<Activity> getLectures(Context cont) {
        if (lectures == null) {
            new ActivityData(cont);
        }
        return lectures;
    }

    public static List<SectionHeader> getHeaders(Context cont) {
        if (headers == null) {
            new ActivityData(cont);
        }
        return headers;
    }

    public static List<Activity> getLectures(Context cont, String date) {
        if (lectures == null) {
            new ActivityData(cont, date);
        }
        return lectures;
    }

    public static List<SectionHeader> getHeaders(Context cont, String date) {
        new ActivityData(cont, date);
        return headers;
    }

    private ActivityData(Context context) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        this.lectures = databaseAccess.getLectData();
        databaseAccess.close();
    }

    private ActivityData(Context context, String date) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();

        this.lectures = databaseAccess.getLectData();
        this.headers = databaseAccess.getChildForDate(date);

        databaseAccess.close();

    }

    public static Activity[] getBreaks(Context context) {
        if (breaks == null) {
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
            databaseAccess.open();
            breaks = databaseAccess.getBrakes();
            databaseAccess.close();
        }
        return breaks;
    }

    /*public ArrayList<Activity> getLectures() {
        return this.lectures;
    }

    public List<SectionHeader> getHeaders() {
        return headers;
    }
*/
}


