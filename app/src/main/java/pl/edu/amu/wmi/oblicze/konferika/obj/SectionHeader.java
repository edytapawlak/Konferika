package pl.edu.amu.wmi.oblicze.konferika.obj;


import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import pl.edu.amu.wmi.oblicze.konferika.adapters.ActivityViewHolder;
import pl.edu.amu.wmi.oblicze.konferika.adapters.SectionViewHolder;

public class SectionHeader extends StatelessSection {
    List<Activity> childList;
    Cursor childrens;
    String title;
    boolean areLectures;


    public SectionHeader(Cursor childrens, String sectionText, boolean isLect) {
        super(pl.edu.amu.wmi.oblicze.konferika.R.layout.section_header, pl.edu.amu.wmi.oblicze.konferika.R.layout.forecast_list_item);
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
//            String tags = "tu będą tagi";
            boolean inUsrSched = (childrens.getInt(7) == 1);
            itemHolder.setIn(inUsrSched);
            float rating = childrens.getFloat(8);

            activ = new Lecture(((ActivityViewHolder) holder).getContext(), title, author, abs, date, activId, startTime, room, inUsrSched, rating);
        } else if ( activId == PosterSesion.ID) {
            activ = new PosterSesion();
        } else {
            String title = childrens.getString(1);
            String startTime = childrens.getString(2);
            int type = childrens.getInt(0);
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

    }

    void swapCursor(Cursor newCursor) {
        childrens = newCursor;
    }

}
