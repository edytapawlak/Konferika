package com.app.android.konferika.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.android.konferika.R;
import com.app.android.konferika.activities.ItemDetailsActivity;
import com.app.android.konferika.obj.Activity;
import com.app.android.konferika.obj.PosterSesion;

public class ActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
    public final RatingBar mRatingBar;

    private Context context;

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
        mRatingBar = (RatingBar) itemView.findViewById(R.id.lecture_list_rating_bar);
        mCardView.setLongClickable(true);
        itemView.setOnClickListener(this);
        context = itemView.getContext();

        myActCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked;
                Context mContext = v.getContext();
                boolean isChanged;
                isChecked = myActCheckbox.isChecked();
                String text = mIdDataTextView.getText().toString();

                if (!isChecked) {
                    Toast.makeText(mContext, "Usunięto z planu", Toast.LENGTH_SHORT).show();
                    isChanged = false;
                    myActCheckbox.setChecked(false);
                } else {
                    Toast.makeText(mContext, "Dodano do planu", Toast.LENGTH_SHORT).show();
                    isChanged = true;
                    myActCheckbox.setChecked(true);
                }
                Log.v("Clik in ViewHolder", "Clicked " + isChanged);
                    DisplayActDataAdapter.getmClickHandler().onStarChanged(isChanged, Integer.parseInt(text));
            }
        });
    }

    public Context getContext() {
        return context;
    }

    /**
     * W zależności od tego co  było kliknięte robią się różne rzeczy.
     * (To p oznacza sesje plakatową, może potem wymyślę coś lepszego)
     *
     * @param v
     */

    @Override
    public void onClick(View v) {
        String text = mIdDataTextView.getText().toString();
        Activity activ;
        if (text != "") {
            if (text.equals("p")) {
                activ = new PosterSesion();
                activ.handleOnClick(context, null);
            } else {
                int id = Integer.parseInt(text);
                if(id != 1 && id != 2 && id != 3) {
                    Intent intent = new Intent(context, ItemDetailsActivity.class);
                    intent.putExtra("lectID", id);
                    context.startActivity(intent);
                }
                else{
                    Toast.makeText(getContext(), "Ten wykład nie podlega głosowaniu", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
