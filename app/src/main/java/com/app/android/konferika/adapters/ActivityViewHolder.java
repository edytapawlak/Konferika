package com.app.android.konferika.adapters;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.android.konferika.R;
import com.app.android.konferika.activities.ItemDetailsFragment;
import com.app.android.konferika.obj.Activity;
import com.app.android.konferika.obj.Lecture;
import com.app.android.konferika.obj.LecturesList;

public class ActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener { //View.OnLongClickListener {

    public final TextView mRefDataTextView;
    public final TextView mAuthorTextView;
    public final TextView mIdDataTextView;
    public final TextView mRoomTextView;
    public final TextView mBreakPicTextView;
    public final TextView mBreakTextTextView;
    public final CardView mCardView;
    public final LinearLayout actLayout;
    public final LinearLayout breakLayout;
    public final CheckBox myActCheckbox;

    private LecturesList mRefData;


    public ActivityViewHolder(final View itemView) {
        super(itemView);
        mRefDataTextView = (TextView) itemView.findViewById(R.id.tv_ref);
        mAuthorTextView = (TextView) itemView.findViewById(R.id.tv_author);
        mIdDataTextView = (TextView) itemView.findViewById(R.id.tv_id);
        mRoomTextView = (TextView) itemView.findViewById(R.id.tv_room);
        mCardView = (CardView) itemView.findViewById(R.id.forecast_card_view);
        mBreakPicTextView = (TextView) itemView.findViewById(R.id.tv_break_pic);
        mBreakTextTextView = (TextView) itemView.findViewById(R.id.tv_break_text);
        myActCheckbox = (CheckBox) itemView.findViewById(R.id.check_box_myAct);
        actLayout = (LinearLayout) itemView.findViewById(R.id.act_layotu);
        breakLayout = (LinearLayout) itemView.findViewById(R.id.break_layout);
        mCardView.setLongClickable(true);
        itemView.setOnClickListener(this);
       // itemView.setOnLongClickListener(this);


        myActCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked;
                mRefData = LecturesList.getInstance(v.getContext());
                isChecked = myActCheckbox.isChecked();
                String text = mIdDataTextView.getText().toString();
                if (text != "") {
                    int id = Integer.parseInt(text);

                    mRefData.setCheckOnPos(id,isChecked);
                    Lecture activ = (Lecture) mRefData.getActivityOnPos(id);
                    String nazwa = activ.getTitle();
                    boolean changedBollean = activ.getIsInUserSchedule();
                    DisplayActDataAdapter.getmClickHandler().onStarChanged(isChecked, activ);
                   // Log.v("Checked activ ", mRefData.printChecked());
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        mRefData = LecturesList.getInstance(v.getContext());
        String text = mIdDataTextView.getText().toString();
        if (text != "") {
            int id = Integer.parseInt(text);
            Activity activ = mRefData.getActivityOnPos(id);
            DisplayActDataAdapter.getmClickHandler().onClick(activ);

            if (activ.isLecture()) {
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

   /* @Override
    public boolean onLongClick(View v) {
        mRefData = DisplayActDataAdapter.getmRefData();
        String text = mIdDataTextView.getText().toString();
        if (text != "") {
            int id = Integer.parseInt(text);
            Activity activ = mRefData.get(id);

            if (activ.isLecture()) {
                Lecture lecture = (Lecture) activ;
                DisplayActDataAdapter.getmClickHandler().onLongClick(lecture);

                Vibrator vb = (Vibrator) v.getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(100);
            }
        }
        return true;
    }*/

}
