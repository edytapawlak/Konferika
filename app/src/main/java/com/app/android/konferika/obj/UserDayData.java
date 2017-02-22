package com.app.android.konferika.obj;


import android.content.Context;
import android.widget.Toast;

import com.app.android.konferika.Utils;
import com.app.android.konferika.data.ActivityData;

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
        Activity[] actList = ActivityData.getBreaks(context); // Default user plan, only breaks. //Todo tu trzeba poprawić, żeby nie pobierać za każdym razem z bazy.
        mPlanData = new TreeMap<>();
        for (int i = 0; i < actList.length; i++) {
            if (actList[i] != null) {
                this.addActivityToList(context, actList[i]);
            }
        }
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

    public void addActivityToList(Context context, Activity activity) {
       /* List<SectionHeader> sections = super.getSectionList();
        int i = 0;
        while (i < sections.size()) {
            if (activity.getStartTime().equals(sections.get(i).getTitle())) {
                sections.get(i).addItem(activity);
                i = sections.size();
            }
            i++;
        }*/

        String time = Utils.formatTime(activity.getStartTime());
        // Log.v("Add Activity", "Dodaje " + time);
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
        for (Map.Entry<String, List<Activity>> entry : mPlanData.entrySet()) {
            value = entry.getValue();
            key = Utils.deFormatTime(entry.getKey());

            sc = new SectionHeader(value, key);
            planForDay.add(sc);
        }
        return planForDay;
    }



}
