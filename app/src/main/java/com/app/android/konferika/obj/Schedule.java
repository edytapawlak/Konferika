package com.app.android.konferika.obj;

import android.content.Context;

import java.io.Serializable;
import java.util.List;

public interface Schedule{

    void dalayActivity();

    void cancelActivity();

    List<SectionHeader> getUserSchedForDayList(Context con, int date);

    DisplayData getUserSchedForDay(Context con, int date);

    void handleLongClick(Context context, Lecture lecture, UserSchedule schedul);

    void handleStarChange(Context context, boolean isCheck, Lecture lecture, UserSchedule userSchedule);

}
