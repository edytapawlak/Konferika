package com.app.android.konferika.data;

import android.content.Context;

import com.app.android.konferika.obj.Activity;
import com.app.android.konferika.obj.Poster;
import com.app.android.konferika.obj.SectionHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ActivityData {

    private static List<Activity> lectures;
    private static List<SectionHeader> headers;
    private static Activity[] breaks;
    private static ArrayList<Poster> posters;
    private static TreeMap<String, List<Activity>>[] userSchedTree = new TreeMap[3];
    private static DatabaseAccess databaseAccess;


    private ActivityData(Context context) {
        databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        this.lectures = databaseAccess.getLectData();
        this.posters = databaseAccess.getPosters();
        databaseAccess.close();
    }

    private ActivityData(Context context, int dateId) {
        databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();

        this.lectures = databaseAccess.getLectData();
        this.headers = databaseAccess.getChildForDate(dateId);

        databaseAccess.close();
    }

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


    public static List<Activity> getLectures(Context cont, int dateId) {
        if (lectures == null) {
            new ActivityData(cont, dateId);
        }
        return lectures;
    }

    public static List<SectionHeader> getHeaders(Context cont, int dateId) {
        new ActivityData(cont, dateId);
        return headers;
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

    public static ArrayList<Poster> getPosters(Context context) {
        if (posters == null) {
            databaseAccess = DatabaseAccess.getInstance(context);
            databaseAccess.open();
            posters = databaseAccess.getPosters();
            databaseAccess.close();
        }
        return posters;
    }

    public static void setMarkPoster(Context context, int id, float mark) {
        databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        databaseAccess.updatePosterMark(id, mark);
        databaseAccess.close();
    }

    public static void setIsUsrSched(Context context, int id, boolean isSched) {
        databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        databaseAccess.setIsInSched(id, isSched);
        databaseAccess.close();
    }

    public static TreeMap<String, List<Activity>> getUserSchedForDay(Context context, int date) {

        int pos = date - 1;
        if (userSchedTree[pos] == null) {
            userSchedTree[pos] = new TreeMap<>();
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
            databaseAccess.open();
            userSchedTree[pos] = databaseAccess.getUserChildForDate(date);
            databaseAccess.close();
        }
        return userSchedTree[pos];
    }
}


