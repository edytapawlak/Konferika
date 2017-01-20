package com.app.android.konferika.adapters;


import android.content.Context;

import com.app.android.konferika.Activity;
import com.app.android.konferika.Lecture;
import com.app.android.konferika.SectionHeader;
import com.app.android.konferika.UserSchedule;
import com.app.android.konferika.data.ActivityData;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class DisplayDataAdapter extends SectionedRecyclerViewAdapter {

    private static List<Activity> mRefData;

    public static List<SectionHeader> activitiesData;

    private static UserSchedule userSchedule;

    private static DispalyAdapterOnClickHandler mClickHandler;

    public static DispalyAdapterOnClickHandler getmClickHandler() {
        return mClickHandler;
    }

    public static List<Activity> getmRefData() {
        return mRefData;
    }
    private static Context context;

    /**
     * The interface that receives onClick messages.
     */
    public interface DispalyAdapterOnClickHandler {
        void onClick(Activity activity);

        void onLongClick(List<SectionHeader> list);
    }


    public DisplayDataAdapter(Context con, DispalyAdapterOnClickHandler listener) {
        this.mRefData = ActivityData.getLectures(con);
        this.mClickHandler = listener;
        context = con;
    }

    public void addSections() {
        //new DisplayDataAdapter(context, mClickHandler);
        SectionForTime mySection;
        SectionHeader sc;
        for (int i = 0; i < activitiesData.size(); i++) {
            sc = activitiesData.get(i);
            mySection = new SectionForTime(sc.getSectionText(), sc.getChildItems());
            this.addSection(mySection);
        }
    }

    public void setActivitiesData(List<SectionHeader> activitiesData) {
        DisplayDataAdapter.activitiesData = activitiesData;
        notifyDataSetChanged();
    }

    public static UserSchedule getUserSchedule() {
        return userSchedule;
    }

    public static void setUserSchedule(UserSchedule userSched) {
        userSchedule = userSched;
    }
}
