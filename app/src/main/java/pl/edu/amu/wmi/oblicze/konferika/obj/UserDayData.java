package pl.edu.amu.wmi.oblicze.konferika.obj;

import android.content.Context;
import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pl.edu.amu.wmi.oblicze.konferika.data.DatabaseContract;

//import com.app.android.konferika.data.ActivityData;

public class UserDayData extends DisplayData implements Serializable {

    /**
     * Construct UsersDayData object, sets default activities - only breaks.
     *
     * @param context
     * @param date
     */
    public UserDayData(Context context, int date) {
        super.setDateId(date);
        super.setSectionList(getUserSchedForDay(context, date));
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

//        String time = Utils.formatTime(activity.getStartTime());
//        List<Activity> actForTime;
//        if (mPlanData.containsKey(time)) {
////            actForTime = mPlanData.remove(time);
//        } else {
//            actForTime = new LinkedList<>();
//        }
//        if (!actForTime.contains(activity)) {
//            if (!actForTime.isEmpty()) {
//                //TODO: Rozwiązać problem bilokacji
//                Toast.makeText(context, "Czy masz zdolność bilokacji?", Toast.LENGTH_SHORT).show();
//            }
//            actForTime.add(activity);
//        }
//        mPlanData.put(time, actForTime);
//        super.setSectionList(createUserSchedule());
    }

    /**
     * Metoda usuwa podane Activity z planu dnia użytkownika.
     * (Tylko w przypadku, gdy jest to referat/wykład. Innych nie usuwa.)
     *
     * @param activity - to musi być Lecture, innych się nie usuwa.
     */
    public void deleteActivityFromList(Lecture activity) {
//        List<SectionHeader> sections = super.getSectionList();
//        int i = 0;
//        while (i < sections.size()) {
//            if (activity.getStartTime().equals(sections.get(i).getTitle())) {
//                sections.get(i).removeItem(activity);
//                if (sections.get(i).getChildItems().isEmpty()) {
//                    sections.remove(i);
//                }
//                i = sections.size();
//            }
//            i++;
//        }
//        super.setSectionList(sections);  //todo: tu coś się dzieje
    }

//    /**
//     * Przekształca treeMap w List<SectionHeader>
//     *
//     * @return
//     */
//    public List<SectionHeader> createUserSchedule() {
//
//        List<SectionHeader> planForDay = new LinkedList<>();
//        SectionHeader sc;
//        String key;
////        List<Activity> value;
//        Cursor value;
//        boolean isLec = false;
////        for (Map.Entry<String, List<Activity>> entry : mPlanData.entrySet()) {
//        for (Map.Entry<String, Cursor> entry : mPlanData.entrySet()) {
//
//            value = entry.getValue();
//            key = Utils.deFormatTime(entry.getKey());
//            if (value.getColumnCount() > 2) {
//                isLec = true;
//            }
//            sc = new SectionHeader(value, key, isLec);
//            planForDay.add(sc);
//        }
//        return planForDay;
//    }

    public List<SectionHeader> getUserSchedForDay(Context context, int dateId) {
        //Pobrać wszystkie startTime
        String[] timeSelArgs = {dateId + ""};
        Cursor startTimeCursor = context.getContentResolver().query(DatabaseContract.UserStartTimesEntry.CONTENT_URI, null,
                null, timeSelArgs, null);
        Cursor actForTimeCursor;
        startTimeCursor.moveToFirst();
        String time = "";
        String[] selectionArgsLect = new String[3];
        String[] selectionArgsBreak = new String[2];
        int isInUsr = 1;
        String[] lecturesProjection = {
                DatabaseContract.LecturesEntry.COLUMN_ID,
                DatabaseContract.LecturesEntry.COLUMN_TITLE,
                DatabaseContract.LecturesEntry.COLUMN_AUTHOR,
                DatabaseContract.LecturesEntry.COLUMN_ABSTRACT,
                DatabaseContract.LecturesEntry.COLUMN_DATE_ID,
                DatabaseContract.LecturesEntry.COLUMN_START_TIME,
                DatabaseContract.LecturesEntry.COLUMN_ROOM_ID,
                DatabaseContract.LecturesJoinScheduleEntry.COLUMN_IS_IN_USR,
                DatabaseContract.LecturesJoinScheduleEntry.COLUMN_RATE};
        String[] breakssProjection = {
                DatabaseContract.BreakEntry.COLUMN_TYPE,
                DatabaseContract.BreakEntry.COLUMN_TITLE,
                DatabaseContract.BreakEntry.COLUMN_START_TIME,
        };
        String selectionLect = DatabaseContract.LecturesJoinScheduleEntry.COLUMN_START_TIME + " = ? AND " + DatabaseContract.LecturesEntry.COLUMN_DATE_ID + " = ? AND " +
                DatabaseContract.LecturesJoinScheduleEntry.COLUMN_IS_IN_USR + " = ?";
        String selectionBreak = DatabaseContract.BreakEntry.COLUMN_START_TIME + " = ? AND " + DatabaseContract.BreakEntry.COLUMN_DATE_ID + " = ?";

        SectionHeader sc;
        boolean isLect;
        List<SectionHeader> list = new ArrayList<SectionHeader>();

        //Pobrac kursor dla każdej godziny
        while (!startTimeCursor.isAfterLast()) {
            time = startTimeCursor.getString(0);
            selectionArgsLect[0] = time + "";
            selectionArgsLect[1] = dateId + "";
            selectionArgsLect[2] = isInUsr + "";
            selectionArgsBreak[0] = time + "";
            selectionArgsBreak[1] = dateId + "";
//            actForTimeCursor = context.getContentResolver().query(DatabaseContract.LecturesEntry.CONTENT_URI,
//                    lecturesProjection, selectionLect, selectionArgsLect, "time(" + DatabaseContract.LecturesEntry.COLUMN_START_TIME + ")");

            actForTimeCursor = context.getContentResolver().query(DatabaseContract.UserLecturesEntry.CONTENT_URI,
                    lecturesProjection, selectionLect, selectionArgsLect, "time(" + DatabaseContract.LecturesEntry.COLUMN_START_TIME + ")");
            isLect = true;
            if (actForTimeCursor.getCount() < 1) {
                actForTimeCursor = context.getContentResolver().query(DatabaseContract.BreakEntry.CONTENT_URI,
                        breakssProjection, selectionBreak, selectionArgsBreak, "time(" + DatabaseContract.BreakEntry.COLUMN_START_TIME + ")");
                isLect = false;
            }
            sc = new SectionHeader(actForTimeCursor, time, isLect);
            list.add(sc);
            startTimeCursor.moveToNext();
        }
        startTimeCursor.close();
        return list;
    }

}
