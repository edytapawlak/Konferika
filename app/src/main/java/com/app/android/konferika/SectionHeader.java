package com.app.android.konferika;

import com.intrusoft.sectionedrecyclerview.Section;

import java.util.ArrayList;

public class SectionHeader implements Section<Activity>{
    ArrayList<Activity> childList;
    String sectionText;


    public SectionHeader(ArrayList<Activity> childList, String sectionText) {
        this.childList = childList;
        this.sectionText = sectionText;
    }

    @Override
    public ArrayList<Activity> getChildItems() {
        return childList;
    }

    public String getSectionText() {
        return sectionText;
    }

    public void setChildList(ArrayList<Activity> childList) {
        this.childList = childList;
    }

    public void setSectionText(String sectionText) {
        this.sectionText = sectionText;
    }
}
