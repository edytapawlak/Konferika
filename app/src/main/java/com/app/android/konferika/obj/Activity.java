package com.app.android.konferika.obj;

import android.content.Context;

import com.app.android.konferika.activities.DayScheduleFragment;
import com.app.android.konferika.adapters.MyItemViewHolder;

public interface Activity{
        boolean isLecture();

    /**
     * This method sets data to layout.
     * @param holder
     */
    void setContent(MyItemViewHolder holder);

    String getStartTime();
    String getTitle();
    void handleOnClick(Context context, DayScheduleFragment fragment);
    void setIsInUserSchedule(boolean isInUserSchedule);
    boolean getIsInUserSchedule();
}

