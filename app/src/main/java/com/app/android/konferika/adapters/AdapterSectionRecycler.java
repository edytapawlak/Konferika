package com.app.android.konferika.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.android.konferika.Lecture;
import com.app.android.konferika.R;
import com.app.android.konferika.SectionHeader;
import com.app.android.konferika.data.ActivityData;
import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterSectionRecycler extends SectionRecyclerViewAdapter<SectionHeader, Lecture, SectionViewHolder, ForecastAdapter.ForecastAdapterViewHolder> {

    Context context;
    private ArrayList<Lecture> mRefDataForData;
    private final ForecastAdapter.ForecastAdapterOnClickHandler mClickHandler;

    public AdapterSectionRecycler(Context context, List<SectionHeader> sectionItemList, ForecastAdapter.ForecastAdapterOnClickHandler clickHandler) {
        super(context, sectionItemList);
        this.context = context;
        ActivityData ad = new ActivityData(context);
        this.mRefDataForData = ad.getLectures();
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
    public void onBindChildViewHolder(ForecastAdapter.ForecastAdapterViewHolder childViewHolder, int sectionPosition, int childPosition, Lecture child) {
        childViewHolder.mRefDataTextView.setText(child.getTitle() );
        childViewHolder.mAuthorTextView.setText(child.getAuthor());
        int id = child.getId() - 1;
        childViewHolder.mIdDataTextView.setText(id + "");

    }

    public void setmRefData(ArrayList<Lecture> mRefDataForData) {
        this.mRefDataForData = mRefDataForData;
        notifyDataSetChanged();
    }
}