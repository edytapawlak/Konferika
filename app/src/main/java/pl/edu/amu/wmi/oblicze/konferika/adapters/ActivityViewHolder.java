package pl.edu.amu.wmi.oblicze.konferika.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import pl.edu.amu.wmi.oblicze.konferika.R;
import pl.edu.amu.wmi.oblicze.konferika.activities.ItemDetailsActivity;
import pl.edu.amu.wmi.oblicze.konferika.data.DatabaseContract;
import pl.edu.amu.wmi.oblicze.konferika.obj.Activity;
import pl.edu.amu.wmi.oblicze.konferika.obj.PosterSesion;

public class ActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final TextView mRefDataTextView;
    public final TextView mAuthorTextView;
    public final TextView mTimeTextView;
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
    public final ImageView imageTest;

    public boolean isIn;
    public int id;

    private Context context;

    public ActivityViewHolder(final View itemView) {
        super(itemView);
        this.isIn = false;
        mRefDataTextView = (TextView) itemView.findViewById(R.id.tv_ref);
        mAuthorTextView = (TextView) itemView.findViewById(R.id.tv_author);
        mTimeTextView = (TextView) itemView.findViewById(R.id.tv_time);
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

        imageTest = (ImageView) itemView.findViewById(R.id.starImage);
        myActCheckbox.setVisibility(View.INVISIBLE); //to w jakiś sposób rozwiązuje problem odświerzania a trzecim tabie

        mCardView.setLongClickable(true);
        itemView.setOnClickListener(this);
        context = itemView.getContext();

        myActCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = false;
                Context mContext = v.getContext();
                boolean isChanged;
                String text = mIdDataTextView.getText().toString();
                int id = Integer.parseInt(text);

                String[] projection = {
                        DatabaseContract.LecturesJoinScheduleEntry.COLUMN_IS_IN_USR,
                };
                Cursor lectCur = mContext.getContentResolver().query(DatabaseContract.LecturesJoinScheduleEntry.buildLecturesJoinScheduleUriWithDate(id),
                        projection, null, null, null);
                lectCur.moveToFirst();
                while (!lectCur.isAfterLast()) {
                    int isInUstDat = lectCur.getInt(0);
                    isChecked = (isInUstDat == 1);
                    lectCur.moveToNext();
                }
                lectCur.close();

                if (isChecked) {
                    Toast.makeText(mContext, "Usunięto z planu", Toast.LENGTH_SHORT).show();
                    isChanged = false;
                    imageTest.setImageResource(R.drawable.ic_heart_empty);
                    imageTest.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(mContext, "Dodano do planu", Toast.LENGTH_SHORT).show();
                    isChanged = true;
                    imageTest.setImageResource(R.drawable.ic_heart_fill);
                    imageTest.setVisibility(View.INVISIBLE);
                }
                DisplayActDataAdapter.getmClickHandler().onStarChanged(isChanged, id);
            }

        });

        imageTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = false;
                Context mContext = v.getContext();
                boolean isChanged;
                String text = mIdDataTextView.getText().toString();
                int id = Integer.parseInt(text);

                String[] projection = {
                        DatabaseContract.LecturesJoinScheduleEntry.COLUMN_IS_IN_USR,
                };
                Cursor lectCur = mContext.getContentResolver().query(DatabaseContract.LecturesJoinScheduleEntry.buildLecturesJoinScheduleUriWithDate(id),
                        projection, null, null, null);
                lectCur.moveToFirst();
                while (!lectCur.isAfterLast()) {
                    int isInUstDat = lectCur.getInt(0);
                    isChecked = (isInUstDat == 1);
                    lectCur.moveToNext();
                }
                lectCur.close();

                if (isChecked) {
                    Toast.makeText(mContext, "Usunięto z planu", Toast.LENGTH_SHORT).show();
                    isChanged = false;
                    imageTest.setImageResource(R.drawable.ic_heart_empty);
                    imageTest.setVisibility(View.INVISIBLE);
                    myActCheckbox.setChecked(false);
                    myActCheckbox.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(mContext, "Dodano do planu", Toast.LENGTH_SHORT).show();
                    isChanged = true;
                    imageTest.setImageResource(R.drawable.ic_heart_fill);
                    imageTest.setVisibility(View.INVISIBLE);
                    myActCheckbox.setChecked(true);
                    myActCheckbox.setVisibility(View.VISIBLE);
                }
                DisplayActDataAdapter.getmClickHandler().onStarChanged(isChanged, id);
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
                if (id!= 4 && id != 1 && id != 2 && id != 3) {
                    Intent intent = new Intent(context, ItemDetailsActivity.class);
                    intent.putExtra("lectID", id);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Wykład specjalny.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void setIn(boolean inUsr) {
        this.isIn = inUsr;
        if (inUsr) {
            this.imageTest.setImageResource(R.mipmap.ic_heart_fill2);
        } else {
            this.imageTest.setImageResource(R.mipmap.ic_heart_empty);

        }
    }

    public void setId(int id){
        this.id = id;
        if (id == 1 || id == 2 || id == 3 || id == 4) {
            myActCheckbox.setVisibility(View.INVISIBLE);
            imageTest.setVisibility(View.INVISIBLE);
        }
    }

    public void handlePlanId(){
        if(ViewPagerAdapter.getScheduleId() == ViewPagerAdapter.USER_SCHED){
            myActCheckbox.setVisibility(View.INVISIBLE);
            imageTest.setVisibility(View.INVISIBLE);
        }
    }
}
