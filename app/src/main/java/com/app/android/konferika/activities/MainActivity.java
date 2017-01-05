package com.app.android.konferika.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.android.konferika.AdapterSectionRecycler;
import com.app.android.konferika.ForecastAdapter;
import com.app.android.konferika.Lecture;
import com.app.android.konferika.R;
import com.app.android.konferika.data.ActivityData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ForecastAdapter.ForecastAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private TextView mErrorTextView;
    private ProgressBar mLoadingProgrressBar;
    private ForecastAdapter mForecastAdapter;

    private AdapterSectionRecycler adapterRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.forecast_recycled_view);
        mErrorTextView = (TextView) findViewById(R.id.error_text_view);
        mLoadingProgrressBar = (ProgressBar) findViewById(R.id.loading_progress_bar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        loadData("24-01-17");
        //loadData("all");
    }

    /**
     * @param date if "all" all Lectures are displaied without sectioning
     *             if String in format DD-MM-YY only lectures for this date are displayed, grouped by startTime
     */
    private void loadData(String date) {

        if (date.equals("all")) {
            mForecastAdapter = new ForecastAdapter(this, this);
            mRecyclerView.setAdapter(mForecastAdapter);
            new FetchRefTask().execute();
        } else {
            ActivityData act = new ActivityData(this, date);
            adapterRecycler = new AdapterSectionRecycler(this, act.getHeaders(), this);
            mRecyclerView.setAdapter(adapterRecycler);
            new FetchRefTaskForDate().execute(date);
        }
    }

    private void showRefDataView() {
        mErrorTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    /**
     * This method is overridden by our MainActivity class in order to handle RecyclerView item
     * clicks.
     *
     * @param text The title for the lecture that was clicked??
     */
    @Override
    public void onClick(String text) {
        Context context = this;
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(intent);
    }


    public class FetchRefTask extends AsyncTask<Void, Void, ArrayList<Lecture>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgrressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Lecture> doInBackground(Void... params) {
            Context context = getApplicationContext();
            ActivityData act = new ActivityData(context);

            return act.getLectures();
        }

        @Override
        protected void onPostExecute(ArrayList<Lecture> refData) {
            mLoadingProgrressBar.setVisibility(View.INVISIBLE);
            if (refData != null) {
                mForecastAdapter.setmRefData(refData);
                showRefDataView();
            } else {
                showErrorMessage();
            }
        }
    }

    public class FetchRefTaskForDate extends AsyncTask<String, Void, ArrayList<Lecture>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgrressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Lecture> doInBackground(String... params) {
            Context context = getApplicationContext();
            ActivityData act = new ActivityData(context, params[0]);

            return act.getLectures();
        }

        @Override
        protected void onPostExecute(ArrayList<Lecture> refData) {
            mLoadingProgrressBar.setVisibility(View.INVISIBLE);
            if (refData != null) {
                adapterRecycler.setmRefData(refData);
                showRefDataView();
            } else {
                showErrorMessage();
            }
        }
    }
}