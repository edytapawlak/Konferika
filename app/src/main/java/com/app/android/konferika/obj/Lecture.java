package com.app.android.konferika.obj;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.app.android.konferika.activities.DayScheduleFragment;
import com.app.android.konferika.activities.ItemDetailsActivity;
import com.app.android.konferika.adapters.ActivityViewHolder;
import com.app.android.konferika.adapters.ViewPagerAdapter;

import java.io.Serializable;

public class Lecture implements Activity, Serializable {
    private String title;
    private String author;
    private String abs;
    private int dateId;
    private int id;
    private String startTime;
    private String room;
    private boolean isInUserSched;


    public Lecture(String title, String author, String abs, int date, String id, String startTime, String room) {
        this.title = title;
        this.author = author;
        this.abs = abs;
        this.dateId = date;
        int idd = Integer.parseInt(id);
        this.id = idd;
        this.startTime = startTime;
        this.room = room;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void handleOnClick(Context context, DayScheduleFragment fragment) {

        Intent intent = new Intent(context, ItemDetailsActivity.class);
        intent.putExtra("lect", this);

        context.startActivity(intent);

        //fragment.startActivityForResult(intent, 1);
    }

    @Override
    public void setIsInUserSchedule(boolean isInUserSchedule) {
        this.isInUserSched = isInUserSchedule;
    }

    @Override
    public boolean getIsInUserSchedule() {
        return isInUserSched;
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

    public String getRoom() {
        return room;
    }

    public String getWeekDay() {
        switch (dateId) {
            case 1:
                return "Piątek";

            case 2:
                return "Sobota";

            case 3 :
                return "Niedziela";

        }
        return "";
    }

    @Override
    public boolean isLecture() {
        return true;
    }

    @Override
    public void setContent(ActivityViewHolder holder) {


        holder.mCardView.setCardBackgroundColor(Color.WHITE);
        holder.mRefDataTextView.setText(this.getTitle());
        holder.mAuthorTextView.setText(this.getAuthor());
        holder.mRoomTextView.setText(this.getRoom());

        holder.myActCheckbox.setChecked(isInUserSched);

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

        actParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        holder.actLayout.setLayoutParams(actParams);
        holder.actLayout.setVisibility(View.VISIBLE);
        int id = this.getId();
        holder.mIdDataTextView.setText(id + "");
    }

    @Override
    public String getStartTime() {
        return startTime;
    }
}
