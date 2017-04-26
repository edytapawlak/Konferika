package com.app.android.konferika.obj;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.app.android.konferika.utils.Utils;
//import com.app.android.konferika.data.ActivityData;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UserDayData extends DisplayData implements Serializable{

    private TreeMap<String, List<Activity>> mPlanData;
    /**
     * Construct UsersDayData object, sets default activities - only breaks.
     *
     * @param context
     * @param date
     */
    public UserDayData(Context context, int date) {
        super.setDateId(date);
        mPlanData = new TreeMap<>();//ActivityData.getUserSchedForDay(context, date);
        super.setSectionList(createUserSchedule());
    }

    /**
     * This method adds activity to UserSchedulesDay object.
     * (Activities are in TreeMap, not in List<SectionHeader>.
     * Na końcu zmienia pole sectionList)
     *
     * @param - narazie jest potrzebny tylko do tosta.
     * @param
     */

    public void addActivityToList(Context context, Lecture activity) {

        String time = Utils.formatTime(activity.getStartTime());
        List<Activity> actForTime;
        if (mPlanData.containsKey(time)) {
            actForTime = mPlanData.remove(time);
        } else {
            actForTime = new LinkedList<>();
        }
        if (!actForTime.contains(activity)) {
            if (!actForTime.isEmpty()) {
                //TODO: Rozwiązać problem bilokacji
                Toast.makeText(context, "Czy masz zdolność bilokacji?", Toast.LENGTH_SHORT).show();
            }
            actForTime.add(activity);
        }
        mPlanData.put(time, actForTime);
        super.setSectionList(createUserSchedule());
    }

    /**
     * Metoda usuwa podane Activity z planu dnia użytkownika.
     * (Tylko w przypadku, gdy jest to referat/wykład. Innych nie usuwa.)
     *
     * @param activity - to musi być Lecture, innych się nie usuwa.
     */
    public void deleteActivityFromList(Lecture activity) {
        List<SectionHeader> sections = super.getSectionList();
        int i = 0;
        while (i < sections.size()) {
            if (activity.getStartTime().equals(sections.get(i).getTitle())) {
                sections.get(i).removeItem(activity);
                if(sections.get(i).getChildItems().isEmpty()){
                    sections.remove(i);
                }
                i = sections.size();
            }
            i++;
        }
        super.setSectionList(sections);  //todo: tu coś się dzieje
    }

    /**
     * Przekształca treeMap w List<SectionHeader>
     * @return
     */
    public List<SectionHeader> createUserSchedule() {

        List<SectionHeader> planForDay = new LinkedList<>();
        SectionHeader sc;
        String key;
        List<Activity> value;
        boolean isLec = false;
        for (Map.Entry<String, List<Activity>> entry : mPlanData.entrySet()) {
            value = entry.getValue();
            key = Utils.deFormatTime(entry.getKey());
            if(!value.isEmpty()) {
                isLec = value.get(0).isLecture();
            }
            sc = new SectionHeader(value, key, isLec);
            planForDay.add(sc);
        }
        return planForDay;
    }
}
