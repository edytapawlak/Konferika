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

    private static ArrayList<Activity> lectures;

    private static List<SectionHeader> headers;

    public static ArrayList<Activity> getLectures(Context cont) {
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

    public static ArrayList<Activity> getLectures(Context cont, String date) {
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

    /*public ArrayList<Activity> getLectures() {
        return this.lectures;
    }

    public List<SectionHeader> getHeaders() {
        return headers;
    }
*/
}


