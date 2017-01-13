package com.app.android.konferika.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.android.konferika.Activity;
import com.app.android.konferika.Break;
import com.app.android.konferika.Lecture;
import com.app.android.konferika.R;
import com.app.android.konferika.data.ActivityData;

import java.util.ArrayList;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

    private ArrayList<Activity> mRefData;

    private final ForecastAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface ForecastAdapterOnClickHandler {
        void onClick(Lecture lect);
    }


    public ForecastAdapter(Context con, ForecastAdapterOnClickHandler listener) {
        this.mRefData = ActivityData.getLectures(con);
        this.mClickHandler = listener;
    }

    public ForecastAdapterViewHolder viewHolder(View view) {
        return new ForecastAdapterViewHolder(view);
    }


    /**
     * Cache of the children views for a forecast list item
     */
    public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mRefDataTextView;
        public final TextView mAuthorTextView;
        public final TextView mIdDataTextView;
        public final CardView mCardView;


        public ForecastAdapterViewHolder(View itemView) {
            super(itemView);
            mRefDataTextView = (TextView) itemView.findViewById(R.id.tv_ref);
            mAuthorTextView = (TextView) itemView.findViewById(R.id.tv_author);
            mIdDataTextView = (TextView) itemView.findViewById(R.id.tv_id);
            mCardView = (CardView) itemView.findViewById(R.id.forecast_card_view);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            String text = mIdDataTextView.getText().toString();
            if (text != "") {
                int id = Integer.parseInt(text);
                Activity activ = mRefData.get(id);
                if (activ.isLecture()) {
                    Lecture lect = (Lecture) activ;
                    mClickHandler.onClick(lect);
                }
            } else {
                Toast.makeText(v.getContext(), "Przerwa", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.forecast_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ForecastAdapterViewHolder(view);

    }


    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder holder, int position) {
        // Nieużywany kod, ale wyświetla wszystkie referaty.
        Activity activity = mRefData.get(position);
        if (activity.isLecture()) {
            activity.setContent(holder);
        }
    }

    @Override
    public int getItemCount() {
        if (mRefData == null) {
            return 0;
        }
        return mRefData.size();
    }

    public void setmRefData(ArrayList<Activity> refData) {
        mRefData = refData;
        notifyDataSetChanged();
    }
}