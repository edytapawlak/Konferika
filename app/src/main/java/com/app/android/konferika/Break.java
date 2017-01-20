package com.app.android.konferika;

import android.graphics.Color;
import android.view.Gravity;

//import com.app.android.konferika.adapters.ForecastAdapter;
import com.app.android.konferika.adapters.MyItemViewHolder;

public class Break implements Activity {

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
    //public void setContent(ForecastAdapter.ForecastAdapterViewHolder holder) {
    public void setContent(MyItemViewHolder holder) {
        int unicode = 0x2615;
        String emoji = new String(Character.toChars(unicode));
        holder.mAuthorTextView.setGravity(Gravity.CENTER);
        holder.mAuthorTextView.setText(this.getTitle());
        holder.mRefDataTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        holder.mRefDataTextView.setTextSize(45);
        holder.mRefDataTextView.setText(emoji);
        holder.mIdDataTextView.setText("");
        int coffeColor = Color.rgb(228, 226, 217);
        holder.mCardView.setCardBackgroundColor(coffeColor);
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
}
