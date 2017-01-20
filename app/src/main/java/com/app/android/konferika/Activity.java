package com.app.android.konferika;

//import com.app.android.konferika.adapters.ForecastAdapter;
import com.app.android.konferika.adapters.MyItemViewHolder;

public interface Activity {
        boolean isLecture();

    /**
     * This method sets data to layout.
     * @param holder
     */
       // void setContent(ForecastAdapter.ForecastAdapterViewHolder holder);

    void setContent(MyItemViewHolder holder);

    public String getStartTime();
    public String getTitle();
}

