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
//import com.app.android.konferika.adapters.SectionForecastAdapter;
//import com.app.android.konferika.adapters.ForecastAdapter;
import com.app.android.konferika.adapters.ViewPagerAdapter;
import com.app.android.konferika.data.ActivityData;
import com.app.android.konferika.newSections.DisplayData;
import com.app.android.konferika.newSections.SectionForTime;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class DayScheduleFragment extends Fragment implements DisplayData.DispalyAdapterOnClickHandler //implements ForecastAdapter.ForecastAdapterOnClickHandler {
{

    private List<SectionHeader> activitiesData;
    //SectionForecastAdapter adapter;
    public RecyclerView recyclerView;

    private DisplayData sectionAdapter;

    public DayScheduleFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.schedule_layout, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.schedule_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());


        Bundle bundle = getArguments();
        String date = bundle.getString("day");



            //adapter = new SectionForecastAdapter(getContext(), this.activitiesData, this);

            sectionAdapter = new DisplayData(this.getContext(), this);
            sectionAdapter.setActivityData(getContext(), date);

            DisplayData.addSections(getContext(), this, sectionAdapter);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(sectionAdapter);


        // else {
        //    Log.e("ERR", "Brak danych do wyświetlenia");
        //}

        return view;
    }

    @Override
    public void onClick(Lecture lect) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("lect", lect);
        startActivity(intent);
    }

    @Override
    public void onLongClick(List<SectionHeader> list) {
        //if(activitiesData == null){
        //    activitiesData = new ArrayList<>();
        //}
        //TODO nie wiem co tu się dzieje.
       // activitiesData.clear();
       // activitiesData.addAll(list);
       // sectionAdapter = new DisplayData(this.getContext(), this);
       // recyclerView.setAdapter(sectionAdapter);

    }


}
