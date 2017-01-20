package com.app.android.konferika;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.app.android.konferika.activities.DayScheduleFragment;
import com.app.android.konferika.data.ActivityData;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UsersScheduleDay {


    private int dateId;
    private TreeMap<String, List<Activity>> mPlanData;

    public int getDate() {
        return dateId;
    }

    /**
     * Construct UsersScheduleDay object, sets default activities - only breaks.
     *
     * @param context
     * @param date
     */

    public UsersScheduleDay(Context context, int date) {
        this.dateId = date;
        Activity[] actList = ActivityData.getBreaks(context); // Default user plan, only breaks.
        mPlanData = new TreeMap<>();
        for (int i = 0; i < actList.length; i++) {
            if (actList[i] != null) {
                this.addActivity(context, actList[i]);
            }
        }
    }

    /**
     * This method adds activity to UserSchedulesDay object.
     *(Activities are in TreeMap, not in List<SectionHeader>)
     * @param context - narazie jest potrzebny tylko do tosta.
     * @param act
     */

    public void addActivity(Context context, Activity act) {

        String time = formatTime(act.getStartTime());
       // Log.v("Add Activity", "Dodaje " + time);
        List<Activity> actForTime;
        if (mPlanData.containsKey(time)) {
            actForTime = mPlanData.remove(time);
        } else {
            actForTime = new LinkedList<>();
        }
        if (!actForTime.contains(act)) {
            if (!actForTime.isEmpty()) {
                //TODO: Rozwiązać problem bilokacji
                Toast.makeText(context, "Czy masz zdolność bilokacji?", Toast.LENGTH_SHORT).show();
            }
            actForTime.add(act);
        }

        mPlanData.put(time, actForTime);
    }

    /**
     * Metoda sprawdza czy tekst ma długość 5. Jeżeli nie to dopisuje 0 na początku.
     * Tekst zawiera godzine w formacie HH:MM.
     * Robię tak, żeby segregowanie w TreeMap było poprawne.
     * @param time
     * @return
     */

    private String formatTime(String time) {
        if (time.length() < 5) {
            time = 0 + time;
        }
        return time;
    }

    /**
     * Metoda zmienia godzine z klucza w treeMapie tak, żeby przy wyświetleniu, nie było 0 na początku.
     * @param time
     * @return
     */
    private String deFormatTime(String time) {
        if (time.charAt(0) == '0') {
            time = time.substring(1);
        }
        return time;
    }

    /**
     * Metoda usuwa podane Activity z planu dnia użytkownika.
     * (Tylko w przypadku, gdy jest to referat/wykład. Innych nie usuwa.)
     * @param con - potrzebny tylko do Tosta.
     * @param act
     */

    public void deleteActivity(Context con, Activity act) {
        if (act.isLecture()) {
            Lecture lecture = (Lecture) act;
            String time = formatTime(act.getStartTime());
            Lecture lect;
            if (mPlanData.containsKey(time)) {
                List<Activity> actForTime = mPlanData.remove(time);

                for (Iterator<Activity> iterator = actForTime.iterator(); iterator.hasNext(); ) { // przeszukuje całą liste dla danej godziny.
                    Activity value = iterator.next();
                    if (value.isLecture()) {
                        lect = (Lecture) value;
                        if (lect.getId() == lecture.getId()) {
                            iterator.remove();
                        }
                    }
                }
                if (!actForTime.isEmpty()) {
                    mPlanData.put(time, actForTime);
                }
            }
        }
    }

    /**
     * This method converst data to ArrayList<SectionHeader> which are used in DayScheduleFragment to show Activities.
     * Zwraca je posortowane względem godziny, bo przepisuje z TreeMap.
     *
     * @return
     */
    public List<SectionHeader> getUserSchedule() {

        List<SectionHeader> planForDay = new LinkedList<>();
        SectionHeader sc;
        String key;
        List<Activity> value;
        for (Map.Entry<String, List<Activity>> entry : mPlanData.entrySet()) {
            value = entry.getValue();
            key = deFormatTime(entry.getKey());

            sc = new SectionHeader(value, key);
            planForDay.add(sc);
        }
        return planForDay;
    }


}
