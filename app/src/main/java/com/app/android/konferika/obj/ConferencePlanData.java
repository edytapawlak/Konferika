package com.app.android.konferika.obj;


import android.content.Context;

import com.app.android.konferika.data.ActivityData;

public class ConferencePlanData extends DisplayData {

    public ConferencePlanData(Context context, int dateId) {
        super.setDateId(dateId);
        super.setSectionList(ActivityData.getHeaders(context, dateId)); //todo zrobic to inaczej
    }


    @Override
    public void addActivityToList(Context context, Activity activity) {

    }

    @Override
    public void deleteActivityFromList(Lecture lecture) {

    }
}
