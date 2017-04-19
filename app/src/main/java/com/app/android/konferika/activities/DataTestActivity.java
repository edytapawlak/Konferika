package com.app.android.konferika.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.android.konferika.R;
import com.app.android.konferika.utils.NetworkUtils;
import com.app.android.konferika.utils.OpenConferenceJsonUtils;

import java.net.URL;

public class DataTestActivity extends AppCompatActivity {

    private TextView mTextView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_test);

        mTextView = (TextView) findViewById(R.id.tv_weather_data);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadData();
    }

    private void showDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mTextView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mTextView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void loadData() {
        new FetchDataTask().execute("get_breaks");
    }

    public class FetchDataTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
        @Override
        protected String[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String dest = params[0];
            URL lectRequestUrl = NetworkUtils.buildUrl(dest);

            try {
                String jsonLectResponse = NetworkUtils
                        .getResponseFromHttpUrl(lectRequestUrl);

                String[] simpleJsonLectData = OpenConferenceJsonUtils
                        .getBreaksStringsFromJson(DataTestActivity.this, jsonLectResponse);

                return simpleJsonLectData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] weatherData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (weatherData != null) {
                showDataView();
                /*
                 * Iterate through the array and append the Strings to the TextView. The reason why we add
                 * the "\n\n\n" after the String is to give visual separation between each String in the
                 * TextView. Later, we'll learn about a better way to display lists of data.
                 */
                for (String weatherString : weatherData) {
                    mTextView.append((weatherString) + "\n\n\n");
                }
            } else {
                showErrorMessage();
            }
        }
    }
}
