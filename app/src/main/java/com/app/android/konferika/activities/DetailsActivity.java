package com.app.android.konferika.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.app.android.konferika.R;
import com.app.android.konferika.obj.Lecture;

public class DetailsActivity extends AppCompatActivity {


    private  TextView mDetailsAuthorTextView;
    private TextView mDetailsAbsTextView;
    private TextView mDetailsTitleTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        mDetailsAuthorTextView = (TextView) findViewById(R.id.tv_details_author);
        mDetailsAbsTextView = (TextView) findViewById(R.id.tv_details_abs);
        mDetailsTitleTextView = (TextView) findViewById(R.id.tv_details_title);
        Intent intent = getIntent();
        if (intent.hasExtra("lect")){
            Lecture lect = (Lecture) intent.getSerializableExtra("lect");
            mDetailsAuthorTextView.setText(lect.getAuthor());
            mDetailsTitleTextView.setText(lect.getTitle());
            mDetailsAbsTextView.setText(lect.getAbs() + lect.getAbs());
        }


    }
}
