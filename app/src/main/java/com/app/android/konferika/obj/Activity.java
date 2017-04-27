package com.app.android.konferika.obj;

import android.content.Context;

import com.app.android.konferika.activities.DayScheduleFragment;
import com.app.android.konferika.activities.SchedFragment;
import com.app.android.konferika.adapters.ActivityViewHolder;

public interface Activity {
    boolean isLecture();

    String getTags();

    /**
     * This method sets data to layout.
     *
     * @param holder
     */
    void setContent(ActivityViewHolder holder);

    String getStartTime();

    String getTitle();

    int getId();

    void handleOnClick(Context context, SchedFragment fragment);

    void setIsInUserSchedule(boolean isInUserSchedule);

    boolean getIsInUserSchedule();
}

