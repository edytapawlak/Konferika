package pl.edu.amu.wmi.oblicze.konferika.obj;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

import pl.edu.amu.wmi.oblicze.konferika.activities.ItemDetailsActivity;
import pl.edu.amu.wmi.oblicze.konferika.activities.SchedFragment;
import pl.edu.amu.wmi.oblicze.konferika.adapters.ActivityViewHolder;
import pl.edu.amu.wmi.oblicze.konferika.adapters.ViewPagerAdapter;
import pl.edu.amu.wmi.oblicze.konferika.data.DatabaseContract;

public class Lecture implements Activity, Serializable {
    private String title;
    private String author;
    private String abs;
    private int dateId;
    private int id;
    private String startTime;
    private String room;
    private boolean isInUserSched;
    private float rating;
    //    private List<Tag> tags;
    private String tags;


    public Lecture(Context context, String title, String author, String abs, int date, int id, String startTime, String room, boolean isInSched, float rating) {
        this.title = title;
        this.author = author;
        this.abs = abs;
        this.dateId = date;
//        int idd = Integer.parseInt(id);
        this.id = id;
        this.startTime = startTime;
        this.room = room;
        this.tags = tags;
        this.isInUserSched = isInSched;
        this.rating = rating;

        String[] projection = {DatabaseContract.TagsEntry.COLUMN_TITLE};
        String[] selectionArgs = {id + ""};
        Cursor tagCur = context.getContentResolver().query(DatabaseContract.LectureTagsEntry.CONTENT_URI, projection, null, selectionArgs, null);
        tagCur.moveToFirst();
        String t = "";
        while (!tagCur.isAfterLast()) {
            t += tagCur.getString(0) + " ";
            tagCur.moveToNext();
        }
        tags = t;
        tagCur.close();
    }


    public String getTitle() {
        return title;
    }

    public String getTags() {
//        String out = "";
//        for (Tag t :
//                this.tags) {
//            out += t.getTitle() + " ";
//        }
//        return out;
        return tags;
    }

    @Override
    public void handleOnClick(Context context, SchedFragment fragment) {
        if(this.id != 1 && this.id != 2 && this.id != 3) {
            Intent intent = new Intent(context, ItemDetailsActivity.class);
            intent.putExtra("lectID", this.getId());
            Log.v("przesyłam:", this.getTags());
            context.startActivity(intent);
        }
    }

    @Override
    public void setIsInUserSchedule(boolean isInUserSchedule) {
        this.isInUserSched = isInUserSchedule;
    }

    @Override
    public boolean getIsInUserSchedule() {
        return isInUserSched;
    }

    public String getAuthor() {
        return author;
    }

    public String getAbs() {
        return abs;
    }

    public int getDate() {
        return dateId;
    }

    public int getId() {
        return id;
    }

    public String getRoom() {
        return room;
    }

    public float getRating() {
        return rating;
    }

    public String getWeekDay() {
        switch (dateId) {
            case 1:
                return "Piątek";
            case 2:
                return "Sobota";
            case 3:
                return "Niedziela";
        }
        return "";
    }

    @Override
    public boolean isLecture() {
        return true;
    }

    @Override
    public void setContent(ActivityViewHolder holder) {

        holder.mCardView.setCardBackgroundColor(Color.WHITE);
        holder.mRefDataTextView.setText(this.getTitle());
        holder.mAuthorTextView.setText(this.getAuthor());
        holder.mRoomTextView.setText(this.getRoom());
        holder.mTagsTextView.setText(this.getTags());
        holder.myActCheckbox.setChecked(this.isInUserSched);


        if (ViewPagerAdapter.getScheduleId() != 0) {
            holder.myActCheckbox.setVisibility(View.INVISIBLE);
        } else {
            holder.myActCheckbox.setVisibility(View.VISIBLE);
        }

        if(this.id != 1 && this.id != 2 && this.id != 3) {
            holder.mRatingBar.setVisibility(View.VISIBLE);
            holder.mRatingBar.setRating(this.getRating());
        }else{
            holder.mRatingBar.setVisibility(View.INVISIBLE);
            holder.myActCheckbox.setVisibility(View.INVISIBLE);
        }

        ViewGroup.LayoutParams breakParams = holder.breakLayout.getLayoutParams();
        breakParams.height = 1;
        holder.actLayout.setLayoutParams(breakParams);
        holder.actLayout.setVisibility(View.INVISIBLE);

        ViewGroup.LayoutParams actParams = holder.actLayout.getLayoutParams();

        actParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        holder.actLayout.setLayoutParams(actParams);
        holder.actLayout.setVisibility(View.VISIBLE);
        int id = this.getId();
        holder.mIdDataTextView.setText(id + "");
    }

    @Override
    public String getStartTime() {
        return startTime;
    }
}
