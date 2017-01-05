package com.app.android.konferika.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.app.android.konferika.R;

public class DetailsActivity extends AppCompatActivity {

    private  TextView mDetailsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mDetailsTextView = (TextView) findViewById(R.id.tv_details);
        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)){
            mDetailsTextView.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
        }
    }
}
