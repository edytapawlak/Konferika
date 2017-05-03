package pl.edu.amu.wmi.oblicze.konferika.obj;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

import pl.edu.amu.wmi.oblicze.konferika.R;
import pl.edu.amu.wmi.oblicze.konferika.activities.SchedFragment;
import pl.edu.amu.wmi.oblicze.konferika.adapters.ActivityViewHolder;

public class Break implements Activity, Serializable {

    private String startTime;
    private String endTime;
    private String title;
    public static final int ID = 3;
    private int type;
    private int dateId;

    public Break(String title, String startTime, int type) {
        this.title = title;
        this.startTime = startTime;
        this.type = type;
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
        int unicode = 0x2615; //przerwa kawowa
        if(type == 2){
            unicode = 0x1F357; //przerwa obiadowa
        } else if (type == 4){
            unicode = 0x1F463; //spacer
        } else if(type == 5){
            unicode = 0x1F37A;
        } else if(type == 6){
            unicode = 0x1F3B2;
        }else if(type == 7){
            unicode = 0;
        }
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
