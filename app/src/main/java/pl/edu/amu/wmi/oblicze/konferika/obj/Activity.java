package pl.edu.amu.wmi.oblicze.konferika.obj;

import android.content.Context;

import pl.edu.amu.wmi.oblicze.konferika.activities.SchedFragment;
import pl.edu.amu.wmi.oblicze.konferika.adapters.ActivityViewHolder;

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

