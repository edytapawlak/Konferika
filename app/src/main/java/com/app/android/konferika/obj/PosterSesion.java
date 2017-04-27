package com.app.android.konferika.obj;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.view.ViewGroup;

import com.app.android.konferika.R;
import com.app.android.konferika.activities.DayScheduleFragment;
import com.app.android.konferika.activities.PosterActivity;
import com.app.android.konferika.activities.SchedFragment;
import com.app.android.konferika.adapters.ActivityViewHolder;
//import com.app.android.konferika.data.ActivityData;

import java.io.Serializable;
import java.util.ArrayList;

public class PosterSesion implements Activity, Serializable {
    private String title = "Sesja plakatowa";
    private int date;
    private String time;
    private static ArrayList<Poster> postersList;
    private static PosterSesion posters;
    public static final int  ID = 1;

    public PosterSesion(Context context) {
        if (postersList == null) {
            postersList = new ArrayList<>();//ActivityData.getPosters(context);
        }
    }
    public PosterSesion(int date, String time, String title){
        this.title = title;
        this.time = time;
        this.date = date;
    }

    public static ArrayList<Poster> getPosterList(Context context) {
        if (postersList == null) {
            posters = new PosterSesion(context);
        }
        return postersList;
    }

    public static void setMarkOnPos(Context context, int posterId, float mark) {

        if(postersList == null){
            new PosterSesion(context);
        }
        int pos = posterId - 1;
        postersList.get(pos).setMark(mark);
        //ActivityData.setMarkPoster(context, posterId, mark);
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
        int unicode = 0x1F4C3;
        String emoji = new String(Character.toChars(unicode));

        holder.mIdDataTextView.setText("p"); // mało mądre, popraw to.
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

    @Override
    public String getStartTime() {
        return null;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void handleOnClick(Context context, SchedFragment fragment) {
        Intent intent = new Intent(context, PosterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(intent);
    }

    @Override
    public void setIsInUserSchedule(boolean isInUserSchedule) {

    }

    @Override
    public boolean getIsInUserSchedule() {
        return false;
    }
}