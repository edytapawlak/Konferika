package com.app.android.konferika.obj;

import android.content.Context;

import com.app.android.konferika.activities.DayScheduleFragment;
import com.app.android.konferika.adapters.ActivityViewHolder;
import com.app.android.konferika.data.ActivityData;

import java.util.ArrayList;

public class PosterSesion implements Activity {
    private String title = "Sesja plakatowa";
    private static ArrayList<Poster> postersList;
    private static PosterSesion posters;

    public PosterSesion(Context context) {
        if (postersList == null) {
            postersList = ActivityData.getPosters(context);
        }
    }

    public static ArrayList<Poster> getPosterList(Context context) {
        if (postersList == null) {
            posters = new PosterSesion(context);
        }
        return postersList;
    }

    public static void setMarkOnPos(int posterId, float mark) {
        int pos = posterId - 1;
        postersList.get(pos).setMark(mark);
    }

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
