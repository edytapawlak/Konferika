package com.app.android.konferika.obj;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.app.android.konferika.activities.DetailsActivity;
import com.app.android.konferika.activities.ItemDetailsActivity;
import com.app.android.konferika.activities.ItemDetailsFragment;
import com.app.android.konferika.adapters.MyItemViewHolder;
import com.app.android.konferika.adapters.ViewPagerAdapter;

import java.io.Serializable;

public class Lecture implements Activity, Serializable {
    private String title;
    private String author;
    private String abs;
    //private String date;
    private int dateId;
    private int id;
    private String startTime;
    private boolean isInUserSched;


    public Lecture(String title, String author, String abs, int date, String id, String startTime) {
        this.title = title;
        this.author = author;
        this.abs = abs;
        this.dateId = date;
        int idd = Integer.parseInt(id);
        this.id = idd;
        this.startTime = startTime;
        isInUserSched = false;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void handleOnClick(Context context) {
        Intent intent = new Intent(context, ItemDetailsActivity.class);
        intent.putExtra("lect", this);

        context.startActivity(intent);
    }

    public String getAuthor() {
        return author;
    }

    public String getAbs() {
        return abs;
    }

    public int getDate() {
        return dateId;
    }

    public int getId() {
        return id;
    }


    @Override
    public boolean isLecture() {
        return true;
    }

    @Override
    public void setContent(MyItemViewHolder holder) {

        holder.mCardView.setCardBackgroundColor(Color.WHITE);
        holder.mRefDataTextView.setText(this.getTitle());
        holder.mAuthorTextView.setText(this.getAuthor());
        holder.myActCheckbox.setChecked(isInUserSched);

        /*if(isInUserSched) {
            holder.myActCheckbox.toggle();
        }*/
        if (ViewPagerAdapter.getScheduleId() != 0) {
            holder.myActCheckbox.setVisibility(View.INVISIBLE);
        } else {
            holder.myActCheckbox.setVisibility(View.VISIBLE);
        }

        ViewGroup.LayoutParams breakParams = holder.breakLayout.getLayoutParams();
        breakParams.height = 1;
        holder.actLayout.setLayoutParams(breakParams);
        holder.actLayout.setVisibility(View.INVISIBLE);

        ViewGroup.LayoutParams actParams = holder.actLayout.getLayoutParams();
// Changes the height and width to the specified *pixels*
        actParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        holder.actLayout.setLayoutParams(actParams);
        holder.actLayout.setVisibility(View.VISIBLE);
        int id = this.getId() - 1;
        holder.mIdDataTextView.setText(id + "");
    }

    @Override
    public String getStartTime() {
        return startTime;
    }
}
