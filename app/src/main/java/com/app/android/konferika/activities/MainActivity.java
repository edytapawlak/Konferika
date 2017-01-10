package com.app.android.konferika.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.android.konferika.ScheduleFragment;
import com.app.android.konferika.adapters.ViewPagerAdapter;
import com.app.android.konferika.adapters.AdapterSectionRecycler;
import com.app.android.konferika.adapters.ForecastAdapter;
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

       // mRecyclerView = (RecyclerView) findViewById(R.id.forecast_recycled_view);
        mErrorTextView = (TextView) findViewById(R.id.error_text_view);
        mLoadingProgrressBar = (ProgressBar) findViewById(R.id.loading_progress_bar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
       // mRecyclerView.setLayoutManager(layoutManager);
       // mRecyclerView.setHasFixedSize(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_view_pager);

        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        //loadData("all");
    }

    /**
     * This method adds tabs to a viewPager
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ScheduleFragment(), "Piątek");
        adapter.addFrag(new ScheduleFragment(), "Sobota");
        adapter.addFrag(new ScheduleFragment(), "Niedziela");
        viewPager.setAdapter(adapter);
    }

    /**
     * Load data and bind it with views
     */
    private void loadData() {
            mForecastAdapter = new ForecastAdapter(this, this);
            mRecyclerView.setAdapter(mForecastAdapter);
            new FetchRefTask().execute();
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
     * clicks. (Works when data is loaded with loadData() method)
     *
     * @param id The id of lecture that was clicked
     */
    @Override
    public void onClick(int id) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("id", id);
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

}