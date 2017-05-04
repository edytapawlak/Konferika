package pl.edu.amu.wmi.oblicze.konferika.adapters;


import android.content.Context;

import pl.edu.amu.wmi.oblicze.konferika.obj.Activity;
import pl.edu.amu.wmi.oblicze.konferika.obj.ConferencePlanData;
import pl.edu.amu.wmi.oblicze.konferika.obj.DisplayData;
import pl.edu.amu.wmi.oblicze.konferika.obj.SectionHeader;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class DisplayActDataAdapter extends SectionedRecyclerViewAdapter {

    private DisplayData activitiesData;
    private static DispalyAdapterOnClickHandler mClickHandler;
    private static Context context;


    public static DispalyAdapterOnClickHandler getmClickHandler() {
        return mClickHandler;
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface DispalyAdapterOnClickHandler {
        void onClick(Activity activity);

        // void onLongClick(Lecture lecture) ;
//        void onStarChanged(boolean isChecked, Lecture lecture);
        void onStarChanged(boolean isChecked, int lectureId);

    }

    public DisplayActDataAdapter(Context con, DispalyAdapterOnClickHandler listener) {
        this.mClickHandler = listener;
        context = con;

    }

    public void addSections() {
        SectionHeader sc;
        if (activitiesData == null) {
            activitiesData = new ConferencePlanData(context, 1);
        }
        for (int i = 0; i < activitiesData.getListSize(); i++) {
            sc = activitiesData.getSectionHeader(i);
            this.addSection(sc);
        }
    }

    public void setActivitiesData(DisplayData activitiesData) {
//        DisplayActDataAdapter.activitiesData = null;
        this.activitiesData = activitiesData;
        notifyDataSetChanged();
    }

    public void closeActData(){
        activitiesData.closeSectionList();
    }

    public void swapData(DisplayData newActDada){
        activitiesData.swapSectionList(newActDada.getSectionList());
    }

}
