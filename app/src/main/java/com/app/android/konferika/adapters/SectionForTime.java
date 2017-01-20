package com.app.android.konferika.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.android.konferika.Activity;
import com.app.android.konferika.R;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class SectionForTime extends StatelessSection implements View.OnClickListener {

    List<Activity> childList;
    String title;

    public SectionForTime(String title, List<Activity> list){
        super(R.layout.section_header, R.layout.forecast_list_item);
        childList = list;
        this.title = title;
    }

    public void addItem(int position, Activity item) {
        childList.add(position, item);
    }

    public void removeItem(int position) {
        childList.remove(position);
    }

    @Override
    public int getContentItemsTotal() {
        if(childList != null){
            return childList.size();
        }else
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

    @Override
    public void onClick(View v) {

    }
}
