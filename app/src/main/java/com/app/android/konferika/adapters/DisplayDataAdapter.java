package com.app.android.konferika.adapters;


import android.content.Context;

import com.app.android.konferika.obj.Activity;
import com.app.android.konferika.obj.ConferencePlanData;
import com.app.android.konferika.obj.DisplayData;
import com.app.android.konferika.obj.Lecture;
import com.app.android.konferika.obj.SectionHeader;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class DisplayDataAdapter extends SectionedRecyclerViewAdapter {

    private static DisplayData activitiesData;
    private static DispalyAdapterOnClickHandler mClickHandler;
    private Context context;

    public static DispalyAdapterOnClickHandler getmClickHandler() {
        return mClickHandler;
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface DispalyAdapterOnClickHandler {
        void onClick(Activity activity);
        void onLongClick(Lecture lecture) ;
        void onStarChanged(boolean isChecked, Lecture lecture);
    }

    public DisplayDataAdapter(Context con, DispalyAdapterOnClickHandler listener) {
        this.mClickHandler = listener;
        context = con;
    }

    public void addSections() {
        SectionHeader sc;
        if(activitiesData == null){
            activitiesData = new ConferencePlanData(context , 1);
        }
        for (int i = 0; i < activitiesData.getListSize(); i++) {
            sc = activitiesData.getSectionHeader(i);
            this.addSection(sc);
        }
    }

    public void setActivitiesData(DisplayData activitiesData) {
        DisplayDataAdapter.activitiesData = null;
        DisplayDataAdapter.activitiesData = activitiesData;
        notifyDataSetChanged();
    }

}
