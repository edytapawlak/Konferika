package com.app.android.konferika.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.app.android.konferika.Lecture;
import com.app.android.konferika.R;

public class DetailsActivity extends AppCompatActivity {


    private  TextView mDetailsAuthorTextView;
    private TextView mDetailsAbsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        mDetailsAuthorTextView = (TextView) findViewById(R.id.tv_details_author);
        mDetailsAbsTextView = (TextView) findViewById(R.id.tv_details_abs);
        Intent intent = getIntent();
        if (intent.hasExtra("lect")){
            Lecture lect = (Lecture) intent.getSerializableExtra("lect");
            mDetailsAuthorTextView.setText(lect.getAuthor());
            mDetailsAbsTextView.setText(lect.getTitle());
        }


    }
}
