package com.app.android.konferika.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.android.konferika.Lecture;
import com.app.android.konferika.R;
import com.app.android.konferika.activities.DetailsActivity;
import com.app.android.konferika.adapters.AdapterSectionRecycler;
import com.app.android.konferika.adapters.ForecastAdapter;
import com.app.android.konferika.data.ActivityData;

public class ScheduleFragment extends Fragment implements ForecastAdapter.ForecastAdapterOnClickHandler {

    private ActivityData act;

    public ScheduleFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.schedule_layout, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.schedule_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        Bundle bundle = getArguments();
        String date = bundle.getString("day");
        act = new ActivityData(getContext(), date);
        AdapterSectionRecycler adapter = new AdapterSectionRecycler(getContext(), act.getHeaders(), this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(Lecture lect) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("lect", lect);
        startActivity(intent);
    }
}
