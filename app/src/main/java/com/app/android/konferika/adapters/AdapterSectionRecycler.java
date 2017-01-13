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

public class AdapterSectionRecycler extends SectionRecyclerViewAdapter<SectionHeader, Activity, SectionViewHolder, ForecastAdapter.ForecastAdapterViewHolder> {

    Context context;
    private ArrayList<Activity> mRefDataForData;
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
    public void onBindChildViewHolder(ForecastAdapter.ForecastAdapterViewHolder childViewHolder, int sectionPosition, int childPosition, Activity child) {

        if(child.isLecture()) {
            Lecture lect = (Lecture) child;
            String title = lect.getTitle();
            childViewHolder.mRefDataTextView.setTextSize(25);
            childViewHolder.mRefDataTextView.setGravity(Gravity.LEFT);
            childViewHolder.mCardView.setCardBackgroundColor(Color.WHITE);
            childViewHolder.mRefDataTextView.setText(lect.getTitle());
            String author = lect.getAuthor();
            childViewHolder.mAuthorTextView.setText(lect.getAuthor());
            int id = lect.getId() - 1;
            childViewHolder.mIdDataTextView.setText(id + "");
        }
        else {
            Break bre = (Break) child;
            int unicode = 0x1F357; // 0x2615;
            String emoji = new String(Character.toChars(unicode));
            childViewHolder.mAuthorTextView.setText(bre.getTitle());
            childViewHolder.mRefDataTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            childViewHolder.mRefDataTextView.setTextSize(45);
            childViewHolder.mRefDataTextView.setText(emoji);
            childViewHolder.mIdDataTextView.setText("");
            int coffeColor = Color.rgb(228, 226, 217);
            childViewHolder.mCardView.setCardBackgroundColor(coffeColor);
        }

    }

    public void setmRefData(ArrayList<Activity> mRefDataForData) {
        this.mRefDataForData = mRefDataForData;
        notifyDataSetChanged();
    }
}