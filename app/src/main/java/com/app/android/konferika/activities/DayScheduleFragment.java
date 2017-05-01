package com.app.android.konferika.activities;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.android.konferika.R;
import com.app.android.konferika.adapters.DisplayActDataAdapter;
import com.app.android.konferika.adapters.ViewPagerAdapter;
import com.app.android.konferika.data.DatabaseContract;
import com.app.android.konferika.obj.Activity;
import com.app.android.konferika.obj.ConferenceSchedule;
import com.app.android.konferika.obj.DisplayData;
import com.app.android.konferika.obj.Schedule;


public class DayScheduleFragment extends Fragment implements DisplayActDataAdapter.DispalyAdapterOnClickHandler, SchedFragment {

    private RecyclerView recyclerView;
    private TextView mErrorTextView;
    private ProgressBar mLoadingProgrressBar;
    private DisplayActDataAdapter sectionAdapter;

    private Schedule schedule;
    private Context mContext;

    public DayScheduleFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerView.setAdapter(sectionAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.schedule_layout, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.schedule_recycler_view);
        mErrorTextView = (TextView) view.findViewById(R.id.error_text_view);
        mLoadingProgrressBar = (ProgressBar) view.findViewById(R.id.loading_progress_bar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());

        sectionAdapter = new DisplayActDataAdapter(this.getContext(), this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        loadData();

        sectionAdapter.addSections();

        recyclerView.setAdapter(sectionAdapter);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("scheduleID", ViewPagerAdapter.getScheduleId());
    }

    /**
     * Jeżeli klinknięto na wykład/referat, to uruchamia nowy widok z opisem refeatu.
     * Wysyła w intencie wykład.
     *
     * @param activ
     */

    @Override
    public void onClick(Activity activ) {
//        activ.handleOnClick(mContext, this);
    }

    @Override
    public void onStarChanged(boolean isCheck, int lectureId) {

        ContentValues cv = new ContentValues();
//        cv.put(DatabaseContract.LecturesEntry.COLUMN_IS_IN_USR, isCheck);
//        mContext.getContentResolver().update(DatabaseContract.LecturesEntry.buildLecturesUriWithDate(lectureId), cv, null, null);
        int check = isCheck ? 1 : 0;
        cv.put(DatabaseContract.ScheduleEntry.COLUMN_IS_IN_USR, check);
        mContext.getContentResolver().update(DatabaseContract.ScheduleEntry.buildScheduleUriWithDate(lectureId), cv, null, null);

        loadData();

        sectionAdapter.notifyDataSetChanged();
        Vibrator vb = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

        vb.vibrate(100);
    }

    /**
     * Wczytuje dane, w zależności od ViewPager.scheduleId i daty (z bundla)
     */
    private void loadData() {

        Bundle bundle = getArguments();
        int dateId = bundle.getInt("day") + 1;

        schedule = new ConferenceSchedule(mContext);
        
        DisplayData dd = schedule.getUserSchedForDay(mContext, dateId);
        mLoadingProgrressBar.setVisibility(View.INVISIBLE);
        sectionAdapter.setActivitiesData(dd);
        if (dd == null) {
            showErrorMessage();
        } else {
            showDataView();
        }

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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        sectionAdapter.closeActData();
    }

}
