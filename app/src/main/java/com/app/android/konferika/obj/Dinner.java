package com.app.android.konferika.obj;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.view.ViewGroup;

import com.app.android.konferika.R;
import com.app.android.konferika.activities.DayScheduleFragment;
import com.app.android.konferika.adapters.ActivityViewHolder;

import java.io.Serializable;

public class Dinner implements Activity, Serializable {

    private String startTime;
    private String endTime;
    private String title;

    public Dinner(String title, String startTime) {
        this.title = title;
        this.startTime = startTime;
    }

    @Override
    public boolean isLecture() {
        return false;
    }

    @Override
    public void setContent(ActivityViewHolder holder) {
        int unicode = 0x1F357;
        String emoji = new String(Character.toChars(unicode));

        holder.mBreakTextTextView.setText(this.getTitle());
        holder.mBreakPicTextView.setText(emoji);

        ViewGroup.LayoutParams params = holder.actLayout.getLayoutParams();
// Changes the height and width to the specified *pixels*
        params.height = 0;
        holder.actLayout.setLayoutParams(params);
        holder.actLayout.setVisibility(View.INVISIBLE);
        Context con = holder.actLayout.getContext();
        int dinnerColor = R.color.dinner_color;
        holder.mCardView.setCardBackgroundColor(ResourcesCompat.getColor(con.getResources(), dinnerColor, null));
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void handleOnClick(Context context, DayScheduleFragment fragment) {

    }

    @Override
    public void setIsInUserSchedule(boolean isInUserSchedule) {

    }

    @Override
    public boolean getIsInUserSchedule() {
        return false;
    }
}

