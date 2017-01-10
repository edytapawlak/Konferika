package com.app.android.konferika.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.android.konferika.R;

public class SectionViewHolder extends RecyclerView.ViewHolder {

    TextView name;

    public SectionViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.section);
    }
}