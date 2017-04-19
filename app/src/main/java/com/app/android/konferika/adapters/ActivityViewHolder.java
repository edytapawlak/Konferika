package com.app.android.konferika.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.android.konferika.R;
import com.app.android.konferika.obj.Activity;
import com.app.android.konferika.obj.Lecture;
import com.app.android.konferika.obj.LecturesList;
import com.app.android.konferika.obj.PosterSesion;

public class ActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener { //View.OnLongClickListener {

    public final TextView mRefDataTextView;
    public final TextView mAuthorTextView;
    public final TextView mIdDataTextView;
    public final TextView mRoomTextView;
    public final TextView mTagsTextView;
    public final TextView mBreakPicTextView;
    public final TextView mBreakTextTextView;
    public final CardView mCardView;
    public final LinearLayout actLayout;
    public final LinearLayout breakLayout;
    public final CheckBox myActCheckbox;

    private LecturesList mLectList;

    public ActivityViewHolder(final View itemView) {
        super(itemView);
        mRefDataTextView = (TextView) itemView.findViewById(R.id.tv_ref);
        mAuthorTextView = (TextView) itemView.findViewById(R.id.tv_author);
        mIdDataTextView = (TextView) itemView.findViewById(R.id.tv_id);
        mRoomTextView = (TextView) itemView.findViewById(R.id.tv_room);
        mTagsTextView = (TextView) itemView.findViewById(R.id.tv_tags);
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
                mLectList = LecturesList.getInstance(v.getContext());

                isChecked = myActCheckbox.isChecked();
                String text = mIdDataTextView.getText().toString();
                if (text != "") {
                    int id = Integer.parseInt(text);

                    mLectList.setCheckOnPos(id, isChecked);
                    Lecture activ = (Lecture) mLectList.getActivityOnPos(id);
                    DisplayActDataAdapter.getmClickHandler().onStarChanged(isChecked, activ);
                    // Log.v("Checked activ ", mLectList.printChecked());
                }
            }
        });
    }

    /**
     * W zależności od tego co  było kliknięte robią się różne rzeczy.
     * (To p oznacza sesje plakatową, może potem wymyślę coś lepszego)
     * @param v
     */

    @Override
    public void onClick(View v) {
        mLectList = LecturesList.getInstance(v.getContext());
        String text = mIdDataTextView.getText().toString();
        Activity activ;
        if (text != "") {
            if (text.equals("p")) {
                activ = new PosterSesion(v.getContext());
            } else {
                int id = Integer.parseInt(text);
                activ = mLectList.getActivityOnPos(id);
            }
            DisplayActDataAdapter.getmClickHandler().onClick(activ);
        }
    }

    /**
     * Po LongClick ...
     *
     * @param v
     * @return
     */

   /* @Override
    public boolean onLongClick(View v) {

        Vibrator vb = (Vibrator) v.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(100);
        return true;
    }*/

}
