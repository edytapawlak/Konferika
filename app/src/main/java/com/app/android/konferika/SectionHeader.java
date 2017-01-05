package com.app.android.konferika;

import com.intrusoft.sectionedrecyclerview.Section;

import java.util.ArrayList;

public class SectionHeader implements Section<Lecture>{
    ArrayList<Lecture> childList;
    String sectionText;

    public SectionHeader(ArrayList<Lecture> childList, String sectionText) {
        this.childList = childList;
        this.sectionText = sectionText;
    }

    @Override
    public ArrayList<Lecture> getChildItems() {
        return childList;
    }

    public String getSectionText() {
        return sectionText;
    }

    public void setChildList(ArrayList<Lecture> childList) {
        this.childList = childList;
    }

    public void setSectionText(String sectionText) {
        this.sectionText = sectionText;
    }
}
