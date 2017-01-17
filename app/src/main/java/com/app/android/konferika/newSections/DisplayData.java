package com.app.android.konferika.newSections;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

import com.app.android.konferika.Activity;
import com.app.android.konferika.Lecture;
import com.app.android.konferika.SectionHeader;
import com.app.android.konferika.UsersSchedule;
import com.app.android.konferika.activities.DayScheduleFragment;
import com.app.android.konferika.adapters.ViewPagerAdapter;
import com.app.android.konferika.data.ActivityData;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class DisplayData extends SectionedRecyclerViewAdapter {

    private static List<Activity> mRefData;

    public static List<SectionHeader> activitiesData;

    private static DispalyAdapterOnClickHandler mClickHandler;

    public static DispalyAdapterOnClickHandler getmClickHandler() {
        return mClickHandler;
    }

    public static List<Activity> getmRefData() {
        return mRefData;
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface DispalyAdapterOnClickHandler {
        void onClick(Lecture lect);
        void onLongClick(List<SectionHeader> list);
    }


    public DisplayData(Context con, DispalyAdapterOnClickHandler listener) {
        this.mRefData = ActivityData.getLectures(con);
        this.mClickHandler = listener;
    }

    public void setActivityData(Context context, String date) {
        if (activitiesData == null) {
            activitiesData = new ArrayList<SectionHeader>();
        } else {
            activitiesData.clear();
        }
        int scheduleId = ViewPagerAdapter.getScheduleId();
        if (scheduleId == 0) {
            activitiesData.addAll(ActivityData.getHeaders(context, date));
        } else {
            activitiesData.addAll(UsersSchedule.getUserSchedule(context));
        }
        notifyDataSetChanged();
    }

    public static void addSections(Context con, DispalyAdapterOnClickHandler listener, SectionedRecyclerViewAdapter sectionAdapter) {
        new DisplayData(con, listener);

        SectionForTime mySection;
        SectionHeader sc;
        for (int i = 0; i < activitiesData.size(); i++) {
            sc = activitiesData.get(i);
            mySection = new SectionForTime(sc.getSectionText(), sc.getChildItems());
            sectionAdapter.addSection(mySection);
        }
    }

    //public void setActivitiesData(List<SectionHeader> activitiesData) {
    //    DisplayData.activitiesData = activitiesData;

    //}
}
