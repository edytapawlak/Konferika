package com.app.android.konferika;


import java.util.ArrayList;
import java.util.List;

public class SectionHeader {
    List<Activity> childList;
    String sectionText;


    public SectionHeader(List<Activity> childList, String sectionText) {
        this.childList = childList;
        this.sectionText = sectionText;
    }


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
