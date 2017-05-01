package com.app.android.konferika.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.android.konferika.R;
import com.app.android.konferika.data.DatabaseContract;

public class TagDataActivity extends AppCompatActivity {

    Context mActivity;
    int tagId;
    String tagTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_data);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        mActivity = this;
        Intent i = getIntent();
        tagId = i.getIntExtra("tagId", 0);
        tagTitle = i.getStringExtra("tagTitle");
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(tagTitle);
        }


//        final ArrayList<Poster> postersForTag = new ArrayList<>();//ActivityData.getPostersForTag(this, tagId);  To jakby się chciało dodawać też plakaty

        String[] projection = {DatabaseContract.LecturesEntry.COLUMN_ID, DatabaseContract.LecturesEntry.COLUMN_TITLE};
        Cursor lectCursor = getContentResolver().query(DatabaseContract.LectureTagsEntry.buildTagUriWithDate(tagId), projection, null, null, null);
        lectCursor.moveToFirst();

        LinearLayout lecturesLinearLayout = (LinearLayout) findViewById(R.id.tags_lectures_linear_layout);
//        LinearLayout posterLinearLayout = (LinearLayout) findViewById(R.id.tags_posters_linear_layout);
        TextView tagTitleTextView = (TextView) findViewById(R.id.tag_title_text_view);
//        tagTitleTextView.setText(tagTitle);

        TextView clickableTextView;
        while (!lectCursor.isAfterLast()) {
            final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(10, 5, 10, 5);
            TextView lect_title_tv = new TextView(this);
            lect_title_tv.setTextSize(15);
            lect_title_tv.setPadding(15, 15, 15, 15);
//            lect_title_tv.setBackgroundColor(Color.argb(100, 255, 255, 255));
            TextView separator = new TextView(this);
            separator.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            separator.setHeight(2);
            lect_title_tv.setId(lectCursor.getInt(0) + 200); // żeby były jednoznaczne, nie powtarzały się z posterami
            final int id_ = lect_title_tv.getId();
            lect_title_tv.setText(lectCursor.getString(1));
            lecturesLinearLayout.addView(lect_title_tv, params);
            lecturesLinearLayout.addView(separator, params);
            clickableTextView = ((TextView) findViewById(id_));

            clickableTextView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    Intent intent = new Intent(mActivity, ItemDetailsActivity.class);
                    intent.putExtra("lectID", id_-200);
                    mActivity.startActivity(intent);
                }
            });
            lectCursor.moveToNext();
        }
        lectCursor.close();

//---------------- To kod dla tagów dla posterów -----------------------------------------

//        TextView clickablePostersTextView;
//        for (Poster pos :
//                postersForTag) {
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            params.setMargins(10, 0, 10, 0);
//            TextView pos_title_tv = new TextView(this);
//            pos_title_tv.setPadding(8,8,8,8);
//            pos_title_tv.setBackgroundColor(Color.argb(100, 255, 255, 255));
//            TextView separator = new TextView(this);
//            separator.setBackgroundColor(ContextCompat.getColor(this, R.color.redd));
//            separator.setHeight(2);
//            pos_title_tv.setId(pos.getId());
//            final int id_ = pos_title_tv.getId();
//            pos_title_tv.setText(pos.getTitle());
//            posterLinearLayout.addView(pos_title_tv, params);
//            posterLinearLayout.addView(separator, params);
//            clickablePostersTextView = ((TextView) findViewById(id_));
//
//            clickablePostersTextView.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View view) {
////                    Toast.makeText(view.getContext(),
////                            "TextViewPos clicked index = " + id_, Toast.LENGTH_SHORT)
////                            .show();
//                    Poster pos = getPosterOfId(postersForTag, id_);
//                    pos.handleOnClick(mActivity);
//                }
//            });
//        }

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

}


