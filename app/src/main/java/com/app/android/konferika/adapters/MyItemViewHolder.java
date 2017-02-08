package com.app.android.konferika.adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.android.konferika.activities.ItemDetailsFragment;
import com.app.android.konferika.data.ActivityData;
import com.app.android.konferika.obj.Activity;
import com.app.android.konferika.obj.Lecture;
import com.app.android.konferika.R;

import java.util.List;

public class MyItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public final TextView mRefDataTextView;
    public final TextView mAuthorTextView;
    public final TextView mIdDataTextView;
    public final TextView mBreakPicTextView;
    public final TextView mBreakTextTextView;
    public final CardView mCardView;
    public final LinearLayout actLayout;
    public final LinearLayout breakLayout;
    public final CheckBox myActCheckbox;

    private List<Activity> mRefData;

    public MyItemViewHolder(final View itemView) {
        super(itemView);
        mRefDataTextView = (TextView) itemView.findViewById(R.id.tv_ref);
        mAuthorTextView = (TextView) itemView.findViewById(R.id.tv_author);
        mIdDataTextView = (TextView) itemView.findViewById(R.id.tv_id);
        mCardView = (CardView) itemView.findViewById(R.id.forecast_card_view);
        mBreakPicTextView = (TextView) itemView.findViewById(R.id.tv_break_pic);
        mBreakTextTextView = (TextView) itemView.findViewById(R.id.tv_break_text);
        myActCheckbox = (CheckBox) itemView.findViewById(R.id.check_box_myAct);
        actLayout = (LinearLayout) itemView.findViewById(R.id.act_layotu);
        breakLayout = (LinearLayout) itemView.findViewById(R.id.break_layout);
        mCardView.setLongClickable(true);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        mRefData = ActivityData.getLectures(itemView.getContext());

        myActCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String text = mIdDataTextView.getText().toString();
                if (text != "") {
                    int id = Integer.parseInt(text);
                    Lecture activ = (Lecture) mRefData.get(id);
                    DisplayDataAdapter.getmClickHandler().onStarChanged(isChecked, activ);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {

        String text = mIdDataTextView.getText().toString();
        if (text != "") {
            int id = Integer.parseInt(text);
            Activity activ = mRefData.get(id);
            DisplayDataAdapter.getmClickHandler().onClick(activ);

            if(activ.isLecture()) {
                ItemDetailsFragment fragmentDemo = new ItemDetailsFragment();
                Bundle args = new Bundle();
                args.putSerializable("item", (Lecture) activ);
                fragmentDemo.setArguments(args);
            }
        } else {
            Toast.makeText(v.getContext(), "Przerwa", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Po LongClick dodaje lub usuwa reterat/wykład z planu, w zależności od ViewPager.scheduleId.
     *
     * @param v
     * @return
     */

    @Override
    public boolean onLongClick(View v) {
        String text = mIdDataTextView.getText().toString();
        if (text != "") {
            int id = Integer.parseInt(text);
            Activity activ = mRefData.get(id);

            if (activ.isLecture()) {
                Lecture lecture = (Lecture) activ;
                DisplayDataAdapter.getmClickHandler().onLongClick(lecture);

                Vibrator vb = (Vibrator) v.getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(100);
            }
        }
        return true;
    }

}
