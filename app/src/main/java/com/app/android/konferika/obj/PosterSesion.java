package com.app.android.konferika.obj;

import android.content.Context;

import com.app.android.konferika.activities.DayScheduleFragment;
import com.app.android.konferika.adapters.ActivityViewHolder;

import java.util.ArrayList;

public class PosterSesion implements Activity{
   private String title = "Sesja plakatowa";
    private ArrayList<Poster> postersList;

    @Override
    public boolean isLecture() {
        return false;
    }

    @Override
    public void setContent(ActivityViewHolder holder) {

    }

    @Override
    public String getStartTime() {
        return null;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void handleOnClick(Context context, DayScheduleFragment fragment) {

    }

    @Override
    public void setIsInUserSchedule(boolean isInUserSchedule) {

    }

    @Override
    public boolean getIsInUserSchedule() {
        return false;
    }
}
