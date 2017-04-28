package com.app.android.konferika.activities;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.android.konferika.R;
import com.app.android.konferika.adapters.DisplayActDataAdapter;
import com.app.android.konferika.data.DatabaseContract;
import com.app.android.konferika.obj.Lecture;
import com.app.android.konferika.obj.LecturesList;
import com.app.android.konferika.obj.Tag;
import com.app.android.konferika.obj.UserSchedule;

;import java.util.ArrayList;
import java.util.List;

public class ItemDetailsActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Context mContext;
    private Lecture item;
    private UserSchedule userSched;
    private FloatingActionButton fabulousBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        mContext = this;
//        item = (Lecture) getIntent().getIntExtra("lectID");
        int lectureId = getIntent().getIntExtra("lectID", -1);
        String[] projection = {
                DatabaseContract.LecturesEntry.COLUMN_TITLE,
                DatabaseContract.LecturesEntry.COLUMN_AUTHOR,
                DatabaseContract.LecturesEntry.COLUMN_ABSTRACT,
                DatabaseContract.LecturesEntry.COLUMN_START_TIME,
                DatabaseContract.LecturesEntry.COLUMN_DATE_ID,
                DatabaseContract.LecturesEntry.COLUMN_ROOM_ID,
                DatabaseContract.LecturesJoinScheduleEntry.COLUMN_IS_IN_USR
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

            item = new Lecture(title,author, abtract, date, lectureId, startTime, room, new ArrayList<Tag>(), inUsrSched);
            lectCur.moveToNext();
        }
        lectCur.close();


        initToolbar();
        if (item == null) {
            Toast.makeText(this, "Jest null,sorry", Toast.LENGTH_SHORT).show();
        }

        TextView tvTitle = (TextView) findViewById(R.id.tv_lect_title);
        TextView tvBody = (TextView) findViewById(R.id.tv_lect_description);
        TextView tvAuthor = (TextView) findViewById(R.id.tv_lect_author);

        TextView tvDay = (TextView) findViewById(R.id.tv_day);
        TextView tvTime = (TextView) findViewById(R.id.tv_time);
        TextView tvRoom = (TextView) findViewById(R.id.tv_room);

        fabulousBtn = (FloatingActionButton) findViewById(R.id.button_fabulous);
        if (item.getIsInUserSchedule()) {
            fabulousBtn.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.fill_star));
        } else {
            fabulousBtn.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.empty_star));
        }

        fabulousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LecturesList mRefData = LecturesList.getInstance(v.getContext());
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
                    fabulousBtn.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.empty_star));
                } else {
//                    userSched.addActivity(mContext, item, item.getDate());
                    Toast.makeText(mContext, "Dodano do planu", Toast.LENGTH_SHORT).show();
                    isChanged = true;
                    fabulousBtn.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.fill_star));
                }

                DisplayActDataAdapter.getmClickHandler().onStarChanged(isChanged, id);
//                mRefData.setCheckOnPos(id, isChanged);
//                ContentValues cv = new ContentValues();
//                cv.put(DatabaseContract.LecturesEntry.COLUMN_IS_IN_USR, isChanged);
//                mContext.getContentResolver().update(DatabaseContract.LecturesEntry.buildLecturesUriWithDate(id), cv ,null, null);
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

