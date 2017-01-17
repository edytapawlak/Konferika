package com.app.android.konferika.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.android.konferika.Activity;
import com.app.android.konferika.Break;
import com.app.android.konferika.Lecture;
import com.app.android.konferika.R;
import com.app.android.konferika.SectionHeader;
import com.app.android.konferika.data.ActivityData;
import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/*public class SectionForecastAdapter extends SectionRecyclerViewAdapter<SectionHeader, Activity, SectionViewHolder, ForecastAdapter.ForecastAdapterViewHolder> {

    Context context;
    private final ForecastAdapter.ForecastAdapterOnClickHandler mClickHandler;
    private List<SectionHeader> activitiesData;

    public SectionForecastAdapter(Context context, List<SectionHeader> sectionItemList, ForecastAdapter.ForecastAdapterOnClickHandler clickHandler) {
        super(context, sectionItemList);
        activitiesData = sectionItemList;
        this.context = context;
        this.mClickHandler = clickHandler;

    }


    @Override
    public SectionViewHolder onCreateSectionViewHolder(ViewGroup sectionViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.section_header, sectionViewGroup, false);
        return new SectionViewHolder(view);
    }

    @Override
    public ForecastAdapter.ForecastAdapterViewHolder onCreateChildViewHolder(ViewGroup childViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forecast_list_item, childViewGroup, false);
        ForecastAdapter fa = new ForecastAdapter(context, mClickHandler);
        return fa.viewHolder(view);
    }

    @Override
    public void onBindSectionViewHolder(SectionViewHolder sectionViewHolder, int sectionPosition, SectionHeader section) {
        sectionViewHolder.name.setText(section.getSectionText());
    }

    @Override
    public void onBindChildViewHolder(ForecastAdapter.ForecastAdapterViewHolder childViewHolder, int sectionPosition, int childPosition, Activity child) {

       // child.setContent(childViewHolder);

    }

    public void setActivitiesData(List<SectionHeader> activitiesData) {
        //this.activitiesData.clear();
        this.activitiesData.addAll(activitiesData);
        notifyDataSetChanged();
    }
}
*/