package com.app.android.konferika.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.android.konferika.Lecture;
import com.app.android.konferika.R;
import com.app.android.konferika.SectionHeader;
import com.app.android.konferika.UsersSchedule;
import com.app.android.konferika.adapters.SectionForecastAdapter;
import com.app.android.konferika.adapters.ForecastAdapter;
import com.app.android.konferika.adapters.ViewPagerAdapter;
import com.app.android.konferika.data.ActivityData;

import java.util.List;

public class DayScheduleFragment extends Fragment implements ForecastAdapter.ForecastAdapterOnClickHandler {


    private List<SectionHeader> activitiesData;
    SectionForecastAdapter adapter;
    RecyclerView recyclerView;

    public DayScheduleFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.schedule_layout, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.schedule_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        Bundle bundle = getArguments();
        String date = bundle.getString("day");

        int scheduleId = ViewPagerAdapter.getScheduleId();
        if (scheduleId == 0) {
            activitiesData = ActivityData.getHeaders(getContext(), date);
        } else {
            activitiesData = UsersSchedule.getUserSchedule(getContext());
        }

        if (activitiesData != null) {
            adapter = new SectionForecastAdapter(getContext(), this.activitiesData, this);
            recyclerView.setAdapter(adapter);
        } else {
            Log.e("ERR", "Brak danych do wyświetlenia");
        }

        return view;
    }

    @Override
    public void onClick(Lecture lect) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("lect", lect);
        startActivity(intent);
    }

}
