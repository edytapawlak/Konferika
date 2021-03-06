package pl.edu.amu.wmi.oblicze.konferika.obj;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

import pl.edu.amu.wmi.oblicze.konferika.activities.SchedFragment;
import pl.edu.amu.wmi.oblicze.konferika.adapters.ActivityViewHolder;

public class Dinner implements Activity, Serializable {

    private String startTime;
    private String endTime;
    private String title;
    public static final int ID = 2;

    public Dinner(String title, String startTime) {
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
        int dinnerColor = pl.edu.amu.wmi.oblicze.konferika.R.color.dinner_color;
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
    public int getId() {
        return ID;
    }

    @Override
    public void handleOnClick(Context context, SchedFragment fragment) {

    }

    @Override
    public void setIsInUserSchedule(boolean isInUserSchedule) {

    }

    @Override
    public boolean getIsInUserSchedule() {
        return false;
    }
}

