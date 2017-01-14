package com.app.android.konferika;

import com.intrusoft.sectionedrecyclerview.Section;

import java.util.ArrayList;
import java.util.List;

public class SectionHeader implements Section<Activity>{
    List<Activity> childList;
    String sectionText;


    public SectionHeader(List<Activity> childList, String sectionText) {
        this.childList = childList;
        this.sectionText = sectionText;
    }

    @Override
    public List<Activity> getChildItems() {
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
