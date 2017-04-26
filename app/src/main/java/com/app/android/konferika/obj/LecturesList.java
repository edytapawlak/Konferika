package com.app.android.konferika.obj;

import android.content.Context;
import android.util.Log;

//import com.app.android.konferika.data.ActivityData;

import java.util.HashMap;
import java.util.List;

public class LecturesList {

    //    private List<Lecture> mLectData;
    private HashMap<Integer, Lecture> mLectData;
    private HashMap<Integer, Activity> mBreakData;
    private static LecturesList lectList;

    public static LecturesList getInstance(Context con) {
        if (lectList == null) {
            lectList = new LecturesList(con);
        }
        return lectList;
    }

    public static LecturesList getInstance() {
        return lectList;
    }


    private LecturesList(Context context) {
        if (mLectData == null) {
            mLectData = new HashMap<>();//ActivityData.getLectures2(context);
            mBreakData = new HashMap<>();//ActivityData.getBreaks2(context);
        }
    }

    public void setCheckOnPos(int id, boolean isChecked) {
        int position = id - 1;
        mLectData.get(position).setIsInUserSchedule(isChecked);
    }

    public Activity getActivityOnPos(int id) {
        int position = id - 1;
        return mLectData.get(position);
    }

    public Lecture getLectOfId(int id){
        Log.v("MSG", print());
        return mLectData.get(id);
    }

    public Activity getBreakOfId(int id){
        Log.v("MSG", print());
        return mBreakData.get(id);
    }


    public String print() {
//        List<Lecture> list = mLectData;
        HashMap<Integer, Lecture> list = mLectData;
        String output = "";
        for (Lecture l :
                list.values()) {

                output = output + "\n ................................................\n" + l.getTitle() + " " + l.getId() + "\n " + l.getTags();
        }
        return output;
    }

    public String printChecked() {
//        List<Lecture> list = mLectData;
        HashMap<Integer, Lecture> list = new HashMap<>();
        String output = "";
        for (Lecture l :
                list.values()) {

            if (l.getIsInUserSchedule()) {
                output = output + "\n ................................................\n" + l.getTitle() + "\n " + l.getTags();
            }
        }
        return output;
    }

    /**
     * Tak wiem. Ale działa.
     *
     * @param activ
     * @return
     */

    public boolean containAct(Lecture activ) {
        return ((Lecture) mLectData.get(activ.getId() - 1)).getId() == activ.getId();
    }
}
