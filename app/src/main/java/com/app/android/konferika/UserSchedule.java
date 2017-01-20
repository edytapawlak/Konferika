package com.app.android.konferika;

import android.content.Context;

import java.util.List;

public class UserSchedule {

    private UsersScheduleDay[] schedule;

    public UserSchedule(Context con) {
        schedule = new UsersScheduleDay[3];

        schedule[0] = new UsersScheduleDay(con, 1);
        schedule[1] = new UsersScheduleDay(con, 2);
        schedule[2] = new UsersScheduleDay(con, 3);
    }

    public void addActivity(Context con, Activity act, int date) {

        if (date == schedule[0].getDate()) {
            schedule[0].addActivity(con, act);
        } else if (date == schedule[1].getDate()) {
            schedule[1].addActivity(con, act);
        } else {
            schedule[2].addActivity(con, act);
        }
    }

    public void deleteActivity(Context con, Activity act, int date) {
        if (date == schedule[0].getDate()) {
            schedule[0].deleteActivity(con, act);
        } else if (date == schedule[1].getDate()) {
            schedule[1].deleteActivity(con, act);
        } else {
            // if (date == schedule[2].getDate()) {
            schedule[2].deleteActivity(con, act);
        }
    }

    public List<SectionHeader> getUserSchedForDay(Context con, int date) {
        List<SectionHeader> output = null;
        if (date == schedule[0].getDate()) {
            output = schedule[0].getUserSchedule();
        } else if (date == schedule[1].getDate()) {
            output = schedule[1].getUserSchedule();
        } else {
            output = schedule[2].getUserSchedule();
        }
        return output;
    }
}
