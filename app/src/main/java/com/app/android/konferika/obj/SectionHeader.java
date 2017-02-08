package com.app.android.konferika.obj;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.android.konferika.R;
import com.app.android.konferika.adapters.MyItemViewHolder;
import com.app.android.konferika.adapters.SectionViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class SectionHeader extends StatelessSection {
    List<Activity> childList;
    String title;


    public SectionHeader(List<Activity> childList, String sectionText) {
        super(R.layout.section_header, R.layout.forecast_list_item);
        this.childList = childList;
        this.title = sectionText;
    }


    public List<Activity> getChildItems() {
        return childList;
    }

    public String getTitle() {
        return title;
    }

    public void setChildList(ArrayList<Activity> childList) {
        this.childList = childList;
    }

    public void setTitle(String sectionText) {
        this.title = sectionText;
    }


    public void addItem(Activity item) {
        childList.add(item);
    }

    public void removeItem(Lecture activity) {
        int i = 0;
        Lecture lecture;
        while (i < childList.size()) {
            if (childList.get(i).isLecture()) {
                lecture = (Lecture) childList.get(i);
                if (lecture.getId() == activity.getId()) {
                    childList.remove(i);
                    i = childList.size();
                }
            }
            i++;
        }
    }

    public void removeItemById(int id) {
        int i = 0;
        Lecture lect;
        while (i < childList.size()) {
            if (childList.get(i).isLecture()) {
                lect = (Lecture) childList.get(i);
                if (lect.getId() == id) {
                    childList.remove(i);
                }
            }
        }
    }

    @Override
    public int getContentItemsTotal() {
        if (childList != null) {
            return childList.size();
        } else
            return 0;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyItemViewHolder itemHolder = (MyItemViewHolder) holder;

        // bind your view here
        childList.get(position).setContent(itemHolder);

    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        SectionViewHolder headerHolder = (SectionViewHolder) holder;
        headerHolder.name.setText(title);

    }
}