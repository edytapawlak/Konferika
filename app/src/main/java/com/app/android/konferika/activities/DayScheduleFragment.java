package com.app.android.konferika.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.android.konferika.R;
import com.app.android.konferika.adapters.DisplayActDataAdapter;
import com.app.android.konferika.adapters.ViewPagerAdapter;
import com.app.android.konferika.data.DatabaseContract;
import com.app.android.konferika.obj.Activity;
import com.app.android.konferika.obj.ConferencePlanData;
import com.app.android.konferika.obj.ConferenceSchedule;
import com.app.android.konferika.obj.DisplayData;
import com.app.android.konferika.obj.Lecture;
import com.app.android.konferika.obj.Schedule;
import com.app.android.konferika.obj.UserSchedule;


public class DayScheduleFragment extends Fragment implements DisplayActDataAdapter.DispalyAdapterOnClickHandler, SchedFragment {
//    LoaderManager.LoaderCallbacks<DisplayData>,
    private RecyclerView recyclerView;
    private TextView mErrorTextView;
    private ProgressBar mLoadingProgrressBar;
    private DisplayActDataAdapter sectionAdapter;

    private Schedule schedule;
    private Context mContext;
    private UserSchedule userSchedule;

    private static final int ID_CONFERENCE_LOADER = 55;

    public DayScheduleFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        //Toast.makeText(mContext, "OnStart", Toast.LENGTH_SHORT).show();
//        userSchedule = UserSchedule.getInstance(mContext, null);
        //loadData();
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

//        userSchedule = UserSchedule.getInstance(mContext, savedInstanceState);
        View view = inflater.inflate(R.layout.schedule_layout, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.schedule_recycler_view);
        mErrorTextView = (TextView) view.findViewById(R.id.error_text_view);
        mLoadingProgrressBar = (ProgressBar) view.findViewById(R.id.loading_progress_bar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());

        sectionAdapter = new DisplayActDataAdapter(this.getContext(), this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        loadData();
//        Bundle bundle = getArguments();
//        int dateId = bundle.getInt("day") + 1;
//        this.getLoaderManager().initLoader(0, bundle, this).forceLoad();

        sectionAdapter.addSections();

        recyclerView.setAdapter(sectionAdapter);
        //Toast.makeText(mContext, "OnCreateView", Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("userSchedule", userSchedule);
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
        activ.handleOnClick(mContext, this);
    }

    @Override
    public void onStarChanged(boolean isCheck, int lectureId) {

        ContentValues cv = new ContentValues();
//        cv.put(DatabaseContract.LecturesEntry.COLUMN_IS_IN_USR, isCheck);
//        mContext.getContentResolver().update(DatabaseContract.LecturesEntry.buildLecturesUriWithDate(lectureId), cv, null, null);
        int check = isCheck?1:0;
        cv.put(DatabaseContract.ScheduleEntry.COLUMN_IS_IN_USR, check);
        mContext.getContentResolver().update(DatabaseContract.ScheduleEntry.buildScheduleUriWithDate(lectureId), cv, null, null);

        Bundle bundle = getArguments();
        int dateId = bundle.getInt("day") + 1;
        schedule = new ConferenceSchedule(mContext);
        DisplayData dd = schedule.getUserSchedForDay(mContext, dateId);
//        mLoadingProgrressBar.setVisibility(View.INVISIBLE);
        sectionAdapter.setActivitiesData(dd);
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
        // Log.v("DataId z bundle: ", dateId + "");

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
    public void onDetach() {
        super.onDetach();
        sectionAdapter.closeActData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sectionAdapter.closeActData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        sectionAdapter.closeActData();
    }

//    @Override
//    public Loader<DisplayData> onCreateLoader(int loaderId, Bundle args) {
//        return new FetchData(mContext, args.getInt("day") + 1);
//    }
//
//    @Override
//    public void onLoadFinished(Loader<DisplayData> loader, DisplayData data) {
//        sectionAdapter.setActivitiesData(data);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<DisplayData> loader) {
//
//    }
//
//    private static class FetchData extends AsyncTaskLoader<DisplayData> {
//
//        int dateId;
//        public FetchData(Context context, int dateID) {
//            super(context);
//            dateId = dateID;
//        }
//
//        @Override
//        public DisplayData loadInBackground() {
//            Log.v("IDDate",dateId+"");
//            return new ConferencePlanData(getContext(),  dateId);
//        }
//
//        @Override
//        public void deliverResult(DisplayData data) {
//            super.deliverResult(data);
//        }
//    }

}
