package com.app.android.konferika.obj;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.view.ViewGroup;

import com.app.android.konferika.R;
import com.app.android.konferika.activities.DayScheduleFragment;
import com.app.android.konferika.adapters.ActivityViewHolder;

import java.io.Serializable;

public class Break implements Activity, Serializable {

    private String startTime;
    private String endTime;
    private String title;

    public Break(String title, String startTime) {
        this.title = title;
        this.startTime = startTime;
    }



    @Override
    public boolean isLecture() {
        return false;
    }

    @Override
    public String getTags() {
        return "";
    }

    @Override
    public void setContent(ActivityViewHolder holder) {
        int unicode = 0x2615;
        String emoji = new String(Character.toChars(unicode));

        holder.mIdDataTextView.setText("");
        holder.mBreakTextTextView.setText(this.getTitle());
        holder.mBreakPicTextView.setText(emoji);

        ViewGroup.LayoutParams params = holder.actLayout.getLayoutParams();
// Changes the height and width to the specified *pixels*
        params.height = 0;
        holder.actLayout.setLayoutParams(params);
        holder.actLayout.setVisibility(View.INVISIBLE);
        int coffeColor = R.color.coffe_color;
        Context con = holder.actLayout.getContext();
        holder.mCardView.setCardBackgroundColor(ResourcesCompat.getColor(con.getResources(), coffeColor, null));
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
