package com.app.android.konferika;
import android.graphics.Color;
import android.view.Gravity;

//import com.app.android.konferika.adapters.ForecastAdapter;
import com.app.android.konferika.adapters.MyItemViewHolder;

import java.io.Serializable;

public class Lecture implements Activity, Serializable {
    private  String title;
    private String author;
    private String abs;
    //private String date;
    private int dateId;
    private int id;
    private String startTime;


    public Lecture(String title, String author, String abs, int date, String id, String startTime) {
        this.title = title;
        this.author = author;
        this.abs = abs;
        this.dateId = date;
        int idd = Integer.parseInt(id);
        this.id = idd;
        this.startTime = startTime;
    }

    public String getTitle() {
        return title;
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


    @Override
    public boolean isLecture() {
        return true;
    }

    @Override
    public void setContent(MyItemViewHolder holder) {

        holder.mRefDataTextView.setTextSize(25);
        holder.mRefDataTextView.setGravity(Gravity.LEFT);
        holder.mCardView.setCardBackgroundColor(Color.WHITE);
        holder.mRefDataTextView.setText(this.getTitle());
        holder.mAuthorTextView.setGravity(Gravity.LEFT);
        holder.mAuthorTextView.setText(this.getAuthor());
        int id = this.getId() - 1;
        holder.mIdDataTextView.setText(id + "");
    }

    @Override
    public String getStartTime() {
        return startTime;
    }
}
