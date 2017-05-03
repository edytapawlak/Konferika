package pl.edu.amu.wmi.oblicze.konferika.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class SectionViewHolder extends RecyclerView.ViewHolder {

    public TextView name;

    public SectionViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(pl.edu.amu.wmi.oblicze.konferika.R.id.section);
    }
}