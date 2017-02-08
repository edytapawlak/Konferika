package com.app.android.konferika.obj;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

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

        holder.mIdDataTextView.setText("");
        holder.mBreakTextTextView.setText(this.getTitle());
        holder.mBreakPicTextView.setText(emoji);

        ViewGroup.LayoutParams params = holder.actLayout.getLayoutParams();
// Changes the height and width to the specified *pixels*
        params.height = 0;
        holder.actLayout.setLayoutParams(params);
        holder.actLayout.setVisibility(View.INVISIBLE);
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

    @Override
    public void handleOnClick(Context context) {

    }
}
