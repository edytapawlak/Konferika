package com.app.android.konferika.adapters;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.android.konferika.Activity;
import com.app.android.konferika.Lecture;
import com.app.android.konferika.R;
import com.app.android.konferika.UserSchedule;

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
            Activity activ = DisplayDataAdapter.getmRefData().get(id);
            DisplayDataAdapter.getmClickHandler().onClick(activ);
        } else {
            Toast.makeText(v.getContext(), "Przerwa", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Po LongClick dodaje lub usuwa reterat/wykład z planu, w zależności od ViewPager.scheduleId.
     * @param v
     * @return
     */

    @Override
    public boolean onLongClick(View v) {
        String text = mIdDataTextView.getText().toString();
        if (text != "") {
            int id = Integer.parseInt(text);
            Activity activ = DisplayDataAdapter.getmRefData().get(id);
            if (activ.isLecture()) {
                Lecture activity = (Lecture) activ;
                if (ViewPagerAdapter.getScheduleId() == 0) {
                    //addLectToUserSchedule(v, activ);
                    if (DisplayDataAdapter.getUserSchedule() == null) {
                        DisplayDataAdapter.setUserSchedule(new UserSchedule(v.getContext()));
                        Log.v("NIE MA PLANU", "ROBIE NOWY");
                    }
                    DisplayDataAdapter.getUserSchedule().addActivity(v.getContext(), activity, activity.getDate());
                    Toast.makeText(v.getContext(), "Dodano do planu", Toast.LENGTH_SHORT).show();

                } else {
                    //deleteLectFromUserSchedule(v, activ);
                    DisplayDataAdapter.getUserSchedule().deleteActivity(v.getContext(), activity, activity.getDate());
                    Toast.makeText(v.getContext(), "Usuwam", Toast.LENGTH_SHORT).show();
                    DisplayDataAdapter.getmClickHandler().onLongClick(DisplayDataAdapter.getUserSchedule().getUserSchedForDay(v.getContext(), 1)); // tu będzie odświeżanie.
                }//Todo wróć tu zaraz
                Vibrator vb = (Vibrator) v.getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(100);
            }
        }
        return true;
    }

   /* private void addLectToUserSchedule(View v, Activity act) { // TODO ???
        if (act.isLecture()) {
            Lecture activ = (Lecture) act;
            if (DisplayDataAdapter.getUserSchedule() == null) {
                //DisplayDataAdapter.setUserSchedule(new UserSchedule(v.getContext()));
                Log.v("NIE MA PLANU", "ROBIE NOWY");
            }
            DisplayDataAdapter.getUserSchedule().addActivity(v.getContext(), activ, activ.getDate());
            Vibrator vb = (Vibrator) v.getContext().getSystemService(Context.VIBRATOR_SERVICE);
            vb.vibrate(100);
        }
    }

        private void deleteLectFromUserSchedule (View v, Activity act){
            if (act.isLecture()) {
                Lecture activ = (Lecture) act;
                DisplayDataAdapter.getUserSchedule().deleteActivity(v.getContext(), activ, activ.getDate());
                Vibrator vb = (Vibrator) v.getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(100);
            }*/

}
