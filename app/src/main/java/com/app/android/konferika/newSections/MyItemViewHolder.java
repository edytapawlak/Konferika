package com.app.android.konferika.newSections;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.android.konferika.Activity;
import com.app.android.konferika.Lecture;
import com.app.android.konferika.R;
import com.app.android.konferika.UsersSchedule;
import com.app.android.konferika.activities.DayScheduleFragment;
import com.app.android.konferika.activities.MyScheduleActivity;
import com.app.android.konferika.adapters.ViewPagerAdapter;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class MyItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public final TextView mRefDataTextView;
    public final TextView mAuthorTextView;
    public final TextView mIdDataTextView;
    public final CardView mCardView;

    public MyItemViewHolder(View itemView) {
        super(itemView);
        mRefDataTextView = (TextView) itemView.findViewById(R.id.tv_ref);
        mAuthorTextView = (TextView) itemView.findViewById(R.id.tv_author);
        mIdDataTextView = (TextView) itemView.findViewById(R.id.tv_id);
        mCardView = (CardView) itemView.findViewById(R.id.forecast_card_view);
        mCardView.setLongClickable(true);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String text = mIdDataTextView.getText().toString();
        if (text != "") {
            int id = Integer.parseInt(text);
            Activity activ = DisplayData.getmRefData().get(id);
            if (activ.isLecture()) {
                Lecture lect = (Lecture) activ;
                DisplayData.getmClickHandler().onClick(lect);
            }
        } else {
            Toast.makeText(v.getContext(), "Przerwa", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        String text = mIdDataTextView.getText().toString();
        if (text != "") {
            int id = Integer.parseInt(text);
            Activity activ = DisplayData.getmRefData().get(id);

            if (ViewPagerAdapter.getScheduleId() == 0) {
                addLectToUserSchedule(v, activ);
                Toast.makeText(v.getContext(), "Dodano do planu", Toast.LENGTH_SHORT).show();

            } else {
                deleteLectFromUserSchedule(v, activ);
                Toast.makeText(v.getContext(), "Usuwam", Toast.LENGTH_SHORT).show();
                DisplayData.getmClickHandler().onLongClick(DisplayData.activitiesData);
                //Intent in = new Intent(v.getContext(), MyScheduleActivity.class);
                // v.getContext().startActivity(in);
            }
        }

        return true;
    }

    private void addLectToUserSchedule(View v, Activity activ) {
        if (activ.isLecture()) {
            UsersSchedule.addActivity(v.getContext(), activ);
            Vibrator vb = (Vibrator) v.getContext().getSystemService(Context.VIBRATOR_SERVICE);
            vb.vibrate(100);
        }
    }

    private void deleteLectFromUserSchedule(View v, Activity activ) {
        if (activ.isLecture()) {
            UsersSchedule.deleteActivity(v.getContext(), activ);
            Vibrator vb = (Vibrator) v.getContext().getSystemService(Context.VIBRATOR_SERVICE);
            vb.vibrate(100);
        }
    }
}
