package com.app.android.konferika.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.android.konferika.Activity;
import com.app.android.konferika.Lecture;
import com.app.android.konferika.R;
import com.app.android.konferika.SectionHeader;
import com.app.android.konferika.UserSchedule;
import com.app.android.konferika.adapters.ViewPagerAdapter;
import com.app.android.konferika.data.ActivityData;
import com.app.android.konferika.adapters.DisplayDataAdapter;

import java.util.ArrayList;
import java.util.List;

public class DayScheduleFragment extends Fragment implements DisplayDataAdapter.DispalyAdapterOnClickHandler {

    private RecyclerView recyclerView;
    private TextView mErrorTextView;
    private ProgressBar mLoadingProgrressBar;

    private DisplayDataAdapter sectionAdapter;

    public DayScheduleFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.schedule_layout, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.schedule_recycler_view);
        mErrorTextView = (TextView) view.findViewById(R.id.error_text_view);
        mLoadingProgrressBar = (ProgressBar) view.findViewById(R.id.loading_progress_bar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());

        sectionAdapter = new DisplayDataAdapter(this.getContext(), this);

        Bundle bundle = getArguments();
        int date = bundle.getInt("day");
        Log.v("Data z bundla", "dat: " + date);
        loadData();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(sectionAdapter);
        return view;

    }

    /**
     * Jeżeli klinknięto na wykład/referat, to uruchamia nowy widok z opisem refeatu.
     * Wysyła w intencie wykład.
     *
     * @param activ
     */

    @Override
    public void onClick(Activity activ) {
        if (activ.isLecture()) {
            Lecture lect = (Lecture) activ;
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("lect", lect);
            startActivity(intent);
        }
    }

    /**
     * Odświeża recyclerView, bo wykład został usunięty.
     *
     * @param list
     */
    @Override
    public void onLongClick(List<SectionHeader> list) {
        sectionAdapter.setActivitiesData(null);

        //Bundle bd = new Bundle();
        //bd.putString("day", ViewPagerAdapter.getDayString());

        //loadData();
       /* Fragment newFragment = DayScheduleFragment.this; //// TODO: Odświeżanie fragmentu

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(((ViewGroup)getView().getParent()).getId(), newFragment);

        transaction.commit();
*/
    }

    /**
     * Wczytuje dane, w zależności od ViewPager.scheduleId i daty (z bundla)
     */
    private void loadData() {
        Bundle bundle = getArguments();
        int date = bundle.getInt("day");
        new FetchTask().execute(date);
    }

    /**
     * This method will make the View for the schedule data visible and
     * hide the error message.
     */
    private void showDataView() {
        mErrorTextView.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the Schedule
     * View.
     */
    private void showErrorMessage() {
        recyclerView.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.VISIBLE);
    }


    public class FetchTask extends AsyncTask<Integer, Void, List<SectionHeader>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgrressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<SectionHeader> doInBackground(Integer... params) {
            int scheduleId = ViewPagerAdapter.getScheduleId();
            int dateId = params[0];
            Context context = getContext();
            List<SectionHeader> activitiesData = new ArrayList<>();
            if (scheduleId == 0) {
                activitiesData.addAll(ActivityData.getHeaders(context, dateId));
            } else {
                if (DisplayDataAdapter.getUserSchedule() == null) {
                    DisplayDataAdapter.setUserSchedule(new UserSchedule(getContext()));
                }
                activitiesData.addAll(DisplayDataAdapter.getUserSchedule().getUserSchedForDay(context, dateId));
            }
            return activitiesData;
        }

        @Override
        protected void onPostExecute(List<SectionHeader> activities) {
            super.onPostExecute(activities);
            sectionAdapter.setActivitiesData(activities);
            sectionAdapter.addSections();
            mLoadingProgrressBar.setVisibility(View.INVISIBLE);
        }

    }
}
