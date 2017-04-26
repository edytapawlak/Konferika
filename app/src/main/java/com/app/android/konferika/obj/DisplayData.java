package com.app.android.konferika.obj;

import android.content.Context;
import android.database.Cursor;

import java.util.List;

public abstract class DisplayData {

    private int dateId;
    private List<SectionHeader> sectionList;


    //  public abstract void handleLongClick(Context context, Lecture lecture);

    public abstract void addActivityToList(Context context, Lecture activity);

    public abstract void deleteActivityFromList(Lecture lecture);

    public int getDate() {
        return dateId;
    }

    public void setDateId(int dateId) {
        this.dateId = dateId;
    }

    public List<SectionHeader> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<SectionHeader> sectionList) {
        this.sectionList = null;
        this.sectionList = sectionList;
    }


    public int getListSize() {
        return sectionList.size();
    }

    public SectionHeader getSectionHeader(int position) {
        return sectionList.get(position);
    }

    public void clear() {
        sectionList.clear();
    }

    public void addAll(DisplayData disp) {
        this.sectionList.addAll(disp.getSectionList());
    }
}
