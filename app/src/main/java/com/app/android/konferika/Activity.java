package com.app.android.konferika;

import com.app.android.konferika.adapters.ForecastAdapter;

public interface Activity {
        boolean isLecture();

    /**
     * This method sets data to layout.
     * @param holder
     */
        void setContent(ForecastAdapter.ForecastAdapterViewHolder holder);

    public String getStartTime();
    public String getTitle();
}

