package com.app.android.konferika.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.android.konferika.R;
import com.app.android.konferika.data.ActivityData;
import com.app.android.konferika.obj.Lecture;
import com.app.android.konferika.obj.Poster;

import java.util.ArrayList;

public class TagDataActivity extends AppCompatActivity {

    Context mActivity;
    int tagId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_data);

        mActivity = this;
        tagId = getIntent().getIntExtra("tagId", 0);

        final ArrayList<Lecture> lectForTag = ActivityData.getLectForTag(this, tagId);
        final ArrayList<Poster> postersForTag = ActivityData.getPostersForTag(this, tagId);

        LinearLayout lecturesLinearLayout = (LinearLayout) findViewById(R.id.tags_lectures_linear_layout);
        LinearLayout posterLinearLayout = (LinearLayout) findViewById(R.id.tags_posters_linear_layout);

        TextView clickableTextView;
        for (Lecture lect :
                lectForTag) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(10, 0, 10, 0);
            TextView lect_title_tv = new TextView(this);
            lect_title_tv.setTextSize(15);
            lect_title_tv.setPadding(8,8,8,8);
            lect_title_tv.setBackgroundColor(Color.argb(100, 255, 255, 255));
            TextView separator = new TextView(this);
            separator.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            separator.setHeight(2);
            lect_title_tv.setId(lect.getId() + 200); // żeby były jednoznaczne, nie powtarzały się z posterami
            final int id_ = lect_title_tv.getId();
            lect_title_tv.setText(lect.getTitle());
            lecturesLinearLayout.addView(lect_title_tv, params);
            lecturesLinearLayout.addView(separator, params);
            clickableTextView = ((TextView) findViewById(id_));

            clickableTextView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
//                    Toast.makeText(view.getContext(),
//                            "TextView clicked index = " + (id_ - 200), Toast.LENGTH_SHORT)
//                            .show();
                    Lecture l =getLectOfId(lectForTag, (id_-200));
                    l.handleOnClick(mActivity, null);
                }
            });
        }

        TextView clickablePostersTextView;
        for (Poster pos :
                postersForTag) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(10, 0, 10, 0);
            TextView pos_title_tv = new TextView(this);
            pos_title_tv.setPadding(8,8,8,8);
            pos_title_tv.setBackgroundColor(Color.argb(100, 255, 255, 255));
            TextView separator = new TextView(this);
            separator.setBackgroundColor(ContextCompat.getColor(this, R.color.redd));
            separator.setHeight(2);
            pos_title_tv.setId(pos.getId());
            final int id_ = pos_title_tv.getId();
            pos_title_tv.setText(pos.getTitle());
            posterLinearLayout.addView(pos_title_tv, params);
            posterLinearLayout.addView(separator, params);
            clickablePostersTextView = ((TextView) findViewById(id_));

            clickablePostersTextView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
//                    Toast.makeText(view.getContext(),
//                            "TextViewPos clicked index = " + id_, Toast.LENGTH_SHORT)
//                            .show();
                    Poster pos = getPosterOfId(postersForTag, id_);
                    pos.handleOnClick(mActivity);
                }
            });
        }

    }

    private Lecture getLectOfId(ArrayList<Lecture> arr, int id){
        for (Lecture lect:
             arr) {
            if(lect.getId() == id){
                return lect;
            }
        }
        return null;
    }

    private Poster getPosterOfId(ArrayList<Poster> arr, int id){
        for (Poster pos:
                arr) {
            if(pos.getId() == id){
                return pos;
            }
        }
        return null;
    }
}


