package com.app.android.konferika.data;

import android.content.Context;

import com.app.android.konferika.Activity;
import com.app.android.konferika.Lecture;
import com.app.android.konferika.SectionHeader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ActivityData {

    private ArrayList<Activity> lectures = new ArrayList<>();

    private List<SectionHeader> headers = new LinkedList<>();


    public ActivityData(Context context) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        this.lectures = databaseAccess.getLectData();
        databaseAccess.close();
    }

    public ActivityData(Context context, String date) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();

        this.lectures = databaseAccess.getLectData();
        this.headers = databaseAccess.getChildForDate(date);

        databaseAccess.close();

    }

    public ArrayList<Activity> getLectures() {
        return this.lectures;
    }
    public List<SectionHeader> getHeaders() {
        return headers;
    }
}


