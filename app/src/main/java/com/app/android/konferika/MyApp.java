package com.app.android.konferika;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.android.konferika.activities.DataTestActivity;
import com.app.android.konferika.utils.NetworkUtils;
import com.app.android.konferika.utils.OpenConferenceJsonUtils;

import java.net.URL;

public class MyApp extends Application {

    Context con;
    @Override
    public void onCreate() {
        super.onCreate();
        con  =  this;
//        Toast.makeText(this, "Tu bym ten", Toast.LENGTH_SHORT).show();
//        loadData();

    }

    private void loadData() {
        new MyApp.FetchDataTask().execute("get_lectures");
    }

    public class FetchDataTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mLoadingIndicator.setVisibility(View.VISIBLE);
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
                Log.v("Wynik: ", jsonLectResponse);

                String[] simpleJsonLectData = OpenConferenceJsonUtils
                        .getLecturesStringsFromJson(con, jsonLectResponse);

                return simpleJsonLectData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] weatherData) {
//            mLoadingIndicator.setVisibility(View.INVISIBLE);
                int i = 0;
            if (weatherData != null) {
//                showDataView();
                /*
                 * Iterate through the array and append the Strings to the TextView. The reason why we add
                 * the "\n\n\n" after the String is to give visual separation between each String in the
                 * TextView. Later, we'll learn about a better way to display lists of data.
                 */
//                for (String weatherString : weatherData) {
////                    mTextView.append((weatherString) + "\n\n\n");
//                    i++;
//
//                }
            Toast.makeText(con, "Jest ich " + weatherData.length, Toast.LENGTH_SHORT).show();
            } else {
//                showErrorMessage();
            }
        }
    }
}
