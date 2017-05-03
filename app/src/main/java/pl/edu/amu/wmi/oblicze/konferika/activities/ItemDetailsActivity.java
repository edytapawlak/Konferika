package pl.edu.amu.wmi.oblicze.konferika.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import pl.edu.amu.wmi.oblicze.konferika.R;
import pl.edu.amu.wmi.oblicze.konferika.adapters.DisplayActDataAdapter;
import pl.edu.amu.wmi.oblicze.konferika.data.DatabaseContract;
import pl.edu.amu.wmi.oblicze.konferika.obj.Lecture;
import pl.edu.amu.wmi.oblicze.konferika.obj.UserSchedule;

import io.github.kexanie.library.MathView;

;

public class ItemDetailsActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Context mContext;
    private Lecture item;
    private UserSchedule userSched;
    private FloatingActionButton fabulousBtn;
    private RatingBar lectRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        mContext = this;
//        item = (Lecture) getIntent().getIntExtra("lectID");
        final int lectureId = getIntent().getIntExtra("lectID", -1);
        String[] projection = {
                DatabaseContract.LecturesEntry.COLUMN_TITLE,
                DatabaseContract.LecturesEntry.COLUMN_AUTHOR,
                DatabaseContract.LecturesEntry.COLUMN_ABSTRACT,
                DatabaseContract.LecturesEntry.COLUMN_START_TIME,
                DatabaseContract.LecturesEntry.COLUMN_DATE_ID,
                DatabaseContract.LecturesEntry.COLUMN_ROOM_ID,
                DatabaseContract.LecturesJoinScheduleEntry.COLUMN_IS_IN_USR,
                DatabaseContract.LecturesJoinScheduleEntry.COLUMN_RATE
        };
//        Cursor lectCur = mContext.getContentResolver().query(DatabaseContract.LecturesEntry.buildLecturesUriWithDate(lectureId), projection, null, null, null);
        Cursor lectCur = mContext.getContentResolver().query(DatabaseContract.LecturesJoinScheduleEntry.buildLecturesJoinScheduleUriWithDate(lectureId),
                projection, null, null, null);
        lectCur.moveToFirst();
        while (!lectCur.isAfterLast()){
            String title = lectCur.getString(0);
            String author = lectCur.getString(1);
            String abtract = lectCur.getString(2);
            String startTime = lectCur.getString(3);
            int date = lectCur.getInt(4);
            String room = lectCur.getString(5);
            int isInUstDat = lectCur.getInt(6);
            boolean inUsrSched = (isInUstDat == 1);
            String tags = "";
            float rate = lectCur.getFloat(7);

            item = new Lecture(mContext, title,author, abtract, date, lectureId, startTime, room, inUsrSched, rate);
            lectCur.moveToNext();
        }
        lectCur.close();


        initToolbar();
        if (item == null) {
            Toast.makeText(this, "Jest null,sorry", Toast.LENGTH_SHORT).show();
        }

        TextView tvTitle = (TextView) findViewById(R.id.tv_lect_title);
//        TextView tvBody = (TextView) findViewById(R.id.tv_lect_description);
        MathView tvBody = (MathView) findViewById(R.id.tv_lect_description);

        TextView tvAuthor = (TextView) findViewById(R.id.tv_lect_author);
        TextView tvDay = (TextView) findViewById(R.id.tv_day);
        TextView tvTime = (TextView) findViewById(R.id.tv_time);
        TextView tvRoom = (TextView) findViewById(R.id.tv_room);


        lectRatingBar = (RatingBar) findViewById(R.id.lectures_details_rating_bar);

        lectRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                PosterSesion.setMarkOnPos(mContext, poster.getId(), rating);
                ContentValues cv = new ContentValues();
                cv.put(DatabaseContract.ScheduleEntry.COLUMN_RATE, rating);
                mContext.getContentResolver().update(DatabaseContract.ScheduleEntry.buildScheduleUriWithDate(lectureId), cv, null, null);
            }
        });

        fabulousBtn = (FloatingActionButton) findViewById(R.id.button_fabulous);
        if (item.getIsInUserSchedule()) {
            fabulousBtn.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_heart_fill));
        } else {
            fabulousBtn.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_heart_empty));
        }

        fabulousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = item.getId();

                boolean isChanged;
//                userSched = UserSchedule.getInstance(mContext, null);

                String[] projection = {DatabaseContract.LecturesJoinScheduleEntry.COLUMN_IS_IN_USR};
                Cursor checkedCursor = mContext.getContentResolver().query(DatabaseContract.LecturesJoinScheduleEntry.buildLecturesJoinScheduleUriWithDate(id),
                        projection, null, null, null);
                checkedCursor.moveToFirst();
                boolean isInSched = (checkedCursor.getInt(0) == 1);
                checkedCursor.close();

                if (isInSched) {
//                    userSched.deleteActivity(mContext, item, item.getDate());
                    Toast.makeText(mContext, "Usunięto z planu", Toast.LENGTH_SHORT).show();
                    isChanged = false;
                    fabulousBtn.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_heart_empty));
                } else {
//                    userSched.addActivity(mContext, item, item.getDate());
                    Toast.makeText(mContext, "Dodano do planu", Toast.LENGTH_SHORT).show();
                    isChanged = true;
                    fabulousBtn.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_heart_fill));
                }

                DisplayActDataAdapter.getmClickHandler().onStarChanged(isChanged, id);
                item.setIsInUserSchedule(isChanged);
//                Log.v("Clik", "Clicked " + isChanged);
            }
        });

        tvTitle.setText(item.getTitle());
        tvBody.setText(item.getAbs());
        tvAuthor.setText(item.getAuthor());
        tvDay.setText(item.getWeekDay());
        tvTime.setText(item.getStartTime());
        tvRoom.setText(item.getRoom());
        lectRatingBar.setRating(item.getRating());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" ");

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(item.getTitle());
                    //collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(mContext, android.R.color.transparent));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}

