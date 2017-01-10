package com.app.android.konferika.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.app.android.konferika.Lecture;
import com.app.android.konferika.R;
import com.app.android.konferika.data.ActivityData;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {


    private ArrayList<Lecture> lectures;
    private  TextView mDetailsAuthorTextView;
    private TextView mDetailsAbsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActivityData act = new ActivityData(this);
        lectures = act.getLectures();

        mDetailsAuthorTextView = (TextView) findViewById(R.id.tv_details_author);
        mDetailsAbsTextView = (TextView) findViewById(R.id.tv_details_abs);
        Intent intent = getIntent();
        if (intent.hasExtra("id")){
            int clickedId = intent.getIntExtra("id", 0);
            mDetailsAuthorTextView.setText(lectures.get(clickedId).getAuthor());
            mDetailsAbsTextView.setText(lectures.get(clickedId).getAbs());
        }


    }
}
