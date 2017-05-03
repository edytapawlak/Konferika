package pl.edu.amu.wmi.oblicze.konferika.obj;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

//import com.app.android.konferika.data.ActivityData;

import java.io.Serializable;
import java.util.List;

public class UserSchedule implements Schedule, Serializable {

    private UserDayData[] schedule;
//    private UserSchedule scheduleObject;


    public UserSchedule(Context con, Bundle savedState) {
//        if (savedState != null && savedState.getSerializable("userSchedule") != null) {
//            scheduleObject = (UserSchedule) savedState.getSerializable("userSchedule");
//        } else {
        if (this.schedule == null) {
            this.schedule = new UserDayData[3];

            this.schedule[0] = new UserDayData(con, 1);
            this.schedule[1] = new UserDayData(con, 2);
            this.schedule[2] = new UserDayData(con, 3);
        }
//    }
    }


    public void addActivity(Context con, Lecture act, int date) {
////        ActivityData.setIsUsrSched(con, act.getId(), true);
//        if (schedule == null) {
//            new UserSchedule(con, null);
//        }
//        if (date == schedule[0].getDate()) {
//            schedule[0].addActivityToList(con, act);
//        } else if (date == schedule[1].getDate()) {
//            schedule[1].addActivityToList(con, act);
//        } else if (date == schedule[2].getDate()) {
//            schedule[2].addActivityToList(con, act);
//
//        }
    }

    public void deleteActivity(Context con, Activity act, int date) {
//        if (act.isLecture()) {
//            Lecture lect = (Lecture) act;
////            ActivityData.setIsUsrSched(con, lect.getId(), false);
//            if (date == schedule[0].getDate()) {
//                schedule[0].deleteActivityFromList(lect);
//            } else if (date == schedule[1].getDate()) {
//                schedule[1].deleteActivityFromList(lect);
//            } else if (date == schedule[2].getDate()) {
//                schedule[2].deleteActivityFromList(lect);
//            }
//        }
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
            new UserSchedule(con, null);
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
            new UserSchedule(con, null);
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
        deleteActivity(context, lecture, lecture.getDate());
        Toast.makeText(context, "Usunięto z planu", Toast.LENGTH_SHORT).show();
    }


    /**
     * Zdaje się, że ta metoda jest nieużywana. Przynajmniej nie powinna.
     *
     * @param context
     * @param lecture
     * @param userSchedule
     */
    @Override
    public void handleStarChange(Context context, Lecture lecture, UserSchedule userSchedule) {
//        //Toast.makeText(context, "Błąt", Toast.LENGTH_SHORT).show();
//        int dayId = lecture.getDate();
//        if (scheduleObject == null) {
//            scheduleObject = UserSchedule.getInstance(context, null);
//        }
//        if (lecture.getIsInUserSchedule()) {
//            scheduleObject.addActivity(context, lecture, dayId);
//
//            Toast.makeText(context, "Dodano do planu 1", Toast.LENGTH_SHORT).show();
//        } else {
//            scheduleObject.deleteActivity(context, lecture, dayId);
//            Toast.makeText(context, "Usunięto z planu 1", Toast.LENGTH_SHORT).show();
//        }
    }
}
