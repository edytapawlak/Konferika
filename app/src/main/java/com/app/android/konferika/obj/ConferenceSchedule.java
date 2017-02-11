package com.app.android.konferika.obj;


import android.content.Context;
import android.widget.Toast;

import java.util.List;

public class ConferenceSchedule implements Schedule {


    private ConferencePlanData[] schedule;
    protected Context context;

    public ConferenceSchedule(Context con) {
        if (this.schedule == null) {
            this.schedule = new ConferencePlanData[3];

            this.schedule[0] = new ConferencePlanData(con, 1);
            this.schedule[1] = new ConferencePlanData(con, 2);
            this.schedule[2] = new ConferencePlanData(con, 3);
        }
    }

    @Override
    public void dalayActivity() {

    }

    @Override
    public void cancelActivity() {

    }

    public List<SectionHeader> getUserSchedForDayList(Context con, int date) {
        if (schedule == null) {
            new ConferenceSchedule(con);
        }

        DisplayData outputSchedule = null;
        if (date == schedule[0].getDate()) {
            outputSchedule = schedule[0];
        } else if (date == schedule[1].getDate()) {
            outputSchedule = schedule[1];
        } else if (date == schedule[2].getDate()) {
            outputSchedule = schedule[2];
        }

        if (outputSchedule != null) {
            return outputSchedule.getSectionList();
        }
        return null;
    }

    public DisplayData getUserSchedForDay(Context con, int date) {
        if (schedule == null) {
            new ConferenceSchedule(con);
        }

        DisplayData outputSchedule = null;
        if (date == schedule[0].getDate()) {
            outputSchedule = schedule[0];
        } else if (date == schedule[1].getDate()) {
            outputSchedule = schedule[1];
        } else {
            outputSchedule = schedule[2];
        }
        return outputSchedule;
    }

    @Override
    public void handleLongClick(Context context, Lecture lecture, UserSchedule scheduleU) {
        int dayId = lecture.getDate();
        scheduleU.addActivity(context, lecture, dayId);

        Toast.makeText(context, "Dodano do planu", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleStarChange(Context context, boolean isCheck, Lecture lecture, UserSchedule userSchedule) {
        int dayId = lecture.getDate();
        String nazwa = lecture.getTitle();
        boolean changedBollean = lecture.getIsInUserSchedule();

        if(lecture.getIsInUserSchedule()) {
            userSchedule.addActivity(context, lecture, dayId);

            Toast.makeText(context, "Dodano do planu", Toast.LENGTH_SHORT).show();
        } else {
            userSchedule.deleteActivity(lecture, dayId);
            Toast.makeText(context, "Usunięto z planu", Toast.LENGTH_SHORT).show();
        }
    }


}
