package com.app.android.konferika;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.app.android.konferika.data.ActivityData;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UsersSchedule {
    String date;
    private static TreeMap<String, List<Activity>> mPlanData;
    private String time;

    public UsersSchedule(Context context) {
        Activity[] actList = ActivityData.getBreaks(context); // Default user plan, only breaks.
        mPlanData = new TreeMap<>();
        for (int i = 0; i < actList.length; i++) {
            if (actList[i] != null) {
                this.addActivity(context, actList[i]);
                Log.v("Add Activity", "Dodaje " + actList[i].getStartTime());
            }
        }
    }

    public static void addActivity(Context con, Activity act) {
        if (mPlanData == null) {
            new UsersSchedule(con);
        }
        String time = formatTime(act.getStartTime());

        List<Activity> actForTime;
        if (mPlanData.containsKey(time)) {
            actForTime = mPlanData.remove(time);
        } else {
            actForTime = new LinkedList<>();
        }

        if (!actForTime.isEmpty() && !actForTime.contains(act)) {
            Toast.makeText(con, "Czy masz zdolność bilokacji?", Toast.LENGTH_SHORT).show();
            Log.v("Bi", "Zdolność bilokacji. ");
        }
        if (!actForTime.contains(act)) {
            actForTime.add(act);
        }

        mPlanData.put(time, actForTime);
    }

    private static String formatTime(String time) {
        if (time.length() < 5) {     //sprawdzam czy tekst ma długość 5, żeby segregowanie w treeMap było poprawne.
                                    // Tekst zawiera godzinę w formacie HH:MM.
            time = 0 + time;
            ;
        }
        return time;
    }

    private static String deFormatTime(String time) {
        if (time.charAt(0) == '0') {
            time = time.substring(1);
        }
        return time;
    }

    public static void deleteActivity(Context con, Activity act) {
        if (act.isLecture()) {
            Lecture lecture = (Lecture) act;
            if (mPlanData == null) {
                new UsersSchedule(con);
            }
            String time = formatTime(act.getStartTime());
            Lecture lect;
            if (mPlanData.containsKey(time)) {
                List<Activity> actForTime = mPlanData.remove(time);

                for (Iterator<Activity> iterator = actForTime.iterator(); iterator.hasNext(); ) {
                    Activity value = iterator.next();
                    if (value.isLecture()) {
                        lect = (Lecture) value;
                        if (lect.getId() == lecture.getId()) {
                            iterator.remove();
                        }
                    }
                }
                if(!actForTime.isEmpty()) {
                    mPlanData.put(time, actForTime);
                }

            }
        }
    }

    /**
     * This method converst data to ArrayList<SectionHeader> which are used in DayScheduleFragment to show Activities.
     *
     * @return
     */
    public static List<SectionHeader> getUserSchedule(Context con) {
        if (mPlanData == null) {
            new UsersSchedule(con);
        }
        List<SectionHeader> planForDay = new LinkedList<>();
        SectionHeader sc;
        for (Map.Entry<String, List<Activity>> entry : mPlanData.entrySet()) {
            List<Activity> value = entry.getValue();

            String key = deFormatTime(entry.getKey());

            sc = new SectionHeader(value, key);
            planForDay.add(sc);
        }
        return planForDay;
    }


}
