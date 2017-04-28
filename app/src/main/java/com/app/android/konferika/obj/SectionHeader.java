package com.app.android.konferika.obj;


import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.app.android.konferika.R;
import com.app.android.konferika.adapters.ActivityViewHolder;
import com.app.android.konferika.adapters.SectionViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class SectionHeader extends StatelessSection {
    List<Activity> childList;
    Cursor childrens;
    //    List<Cursor> childList;
    List<Integer> childIdes;
    String title;
    boolean areLectures;


    public SectionHeader(Cursor childrens, String sectionText, boolean isLect) {
        super(R.layout.section_header, R.layout.forecast_list_item);
        this.childrens = childrens;

        this.title = sectionText;
        this.areLectures = isLect;
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

    public Cursor getChildrens(){
        return childrens;
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
        if (childrens != null) {
            return childrens.getCount();
        } else
            return 0;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ActivityViewHolder itemHolder = (ActivityViewHolder) holder;
//        // bind view
//        LecturesList mRefData = LecturesList.getInstance(itemHolder.getContext());
////        Activity activ = childList.get(position);
//        int activId = childIdes.get(position);
        Activity activ;
        childrens.moveToPosition(position);
        int activId = childrens.getInt(0);
        if (areLectures) {
            String title = childrens.getString(1);
            String author = childrens.getString(2);
            String abs = childrens.getString(3);
            int date = childrens.getInt(5);
            String startTime = childrens.getString(4);
            String room = childrens.getString(6);
            String tags = "tu będą tagi"; //TODO
            boolean inUsrSched = (childrens.getInt(7) == 1); //todo
//            Log.v("Holder! ", "Tworze Lecture! " + childrens.getInt(7) + ", " + title);

            activ = new Lecture(((ActivityViewHolder) holder).getContext(), title, author, abs, date, activId, startTime, room, inUsrSched);
        } else if ( activId == PosterSesion.ID) {
            activ = new PosterSesion(itemHolder.getContext());
//            Log.v("Holder! ", "Tworze PosterSesion");
        } else {
//            Log.v("Holder! ", "Tworze Break!");
            String title = childrens.getString(1);
            String startTime = childrens.getString(2);
            int type = childrens.getInt(0);
//            Log.v("Break typ ", type + "");
            activ = new Break(title, startTime, type);
        }

        activ.setContent(itemHolder);

    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        SectionViewHolder headerHolder = (SectionViewHolder) holder;
        headerHolder.name.setText(title);
//        Log.v("BIND", "BindHeader");

    }

    void swapCursor(Cursor newCursor) {
        childrens = newCursor;
    }

}
