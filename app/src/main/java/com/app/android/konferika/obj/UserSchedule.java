package com.app.android.konferika.obj;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

public class UserSchedule implements Schedule {

    private UserDayData[] schedule;
    protected Context context;
    private static UserSchedule scheduleObject;


    private UserSchedule(Context con) {
        context = con;
        if (this.schedule == null) {
            this.schedule = new UserDayData[3];

            this.schedule[0] = new UserDayData(con, 1);
            this.schedule[1] = new UserDayData(con, 2);
            this.schedule[2] = new UserDayData(con, 3);
        }
    }

    public static UserSchedule getInstance(Context context) {
        if (scheduleObject == null) {
            synchronized (UserSchedule.class) {
                if (scheduleObject == null) {
                    scheduleObject = new UserSchedule(context);
                }
            }
        }
        return scheduleObject;
    }


    public void addActivity(Context con, Activity act, int date) {
        if (schedule == null) {
            new UserSchedule(con);
        }
        if (date == schedule[0].getDate()) {
            schedule[0].addActivityToList(con, act);
        } else if (date == schedule[1].getDate()) {
            schedule[1].addActivityToList(con, act);
        } else if (date == schedule[2].getDate()) {
            schedule[2].addActivityToList(con, act);

        }
    }

    public void deleteActivity(Activity act, int date) {
        if (act.isLecture()) {
            Lecture lect = (Lecture) act;
            if (date == schedule[0].getDate()) {
                schedule[0].deleteActivityFromList(lect);
            } else if (date == schedule[1].getDate()) {
                schedule[1].deleteActivityFromList(lect);
            } else if (date == schedule[2].getDate()) {
                schedule[2].deleteActivityFromList(lect);
            }
        }
    }

    @Override
    public void dalayActivity() {

    }

    @Override
    public void cancelActivity() {

    }

    /**
     * Zwraca listę zajęć dla podanego dnia, jeżeli nie ma takiego dnia zwraca null.
     *
     * @param con
     * @param date
     * @return
     */
    public List<SectionHeader> getUserSchedForDayList(Context con, int date) {
        if (schedule == null) {
            new UserSchedule(con);
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
            new UserSchedule(con);
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
    public void handleLongClick(Context context, Lecture lecture, UserSchedule sched) {
        deleteActivity(lecture, lecture.getDate());
        Toast.makeText(context, "Usunięto z planu", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleStarChange(Context context, boolean isCheck, Lecture lecture, UserSchedule userSchedule) {
        Toast.makeText(context, "Błąt", Toast.LENGTH_SHORT).show();
    }
}
