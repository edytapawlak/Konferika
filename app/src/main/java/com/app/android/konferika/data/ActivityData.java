package com.app.android.konferika.data;

import android.content.Context;

import com.app.android.konferika.Lecture;
import com.app.android.konferika.SectionHeader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ActivityData {

    private ArrayList<Lecture> lectures = new ArrayList<>();

    public List<SectionHeader> getHeaders() {
        return headers;
    }

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

    public ArrayList<Lecture> getLectures() {
        return this.lectures;
    }
}


