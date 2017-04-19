package com.app.android.konferika.obj;

import android.content.Context;
import com.app.android.konferika.data.ActivityData;

import java.util.List;

public class LecturesList {

    private List<Lecture> mLectData;
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
            mLectData = ActivityData.getLectures(context);
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

    public String printChecked() {
        List<Lecture> list = mLectData;
        String output = "";
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getIsInUserSchedule()) {
                output = output + "\n ................................................\n" + list.get(i).getTitle() + "\n "+ list.get(i).getTags();
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
