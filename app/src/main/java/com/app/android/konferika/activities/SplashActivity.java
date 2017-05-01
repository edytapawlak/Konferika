package com.app.android.konferika.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.app.android.konferika.utils.NetworkUtils;
import com.app.android.konferika.utils.OpenConferenceJsonUtils;

import java.net.URL;


public class SplashActivity extends AppCompatActivity {
    Context con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con  =  this;
        loadData();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadData() {
        new SplashActivity.FetchDataTask().execute("get_lectures", "get_posters", "get_breaks");
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

            String lectures = params[0];
            URL lectRequestUrl = NetworkUtils.buildUrl(lectures);
            String posters = params[1];
            URL postersRequestUrl = NetworkUtils.buildUrl(posters);
            String breaks = params[2];
            URL breaksRequestUrl = NetworkUtils.buildUrl(breaks);


            try {
                String jsonLectResponse = NetworkUtils
                        .getResponseFromHttpUrl(lectRequestUrl);
                String jsonPosResponse = NetworkUtils
                        .getResponseFromHttpUrl(postersRequestUrl);
                String jsonBreakResponse = NetworkUtils
                        .getResponseFromHttpUrl(breaksRequestUrl);
//                Log.v("Wynik: ", jsonLectResponse);

                String[] simpleJsonLectData = OpenConferenceJsonUtils
                        .getLecturesStringsFromJson(con, jsonLectResponse);
                String[] simpleJsonPostersData = OpenConferenceJsonUtils
                        .getPostersStringsFromJson(con, jsonPosResponse);
                String[] simpleJsonBreakData = OpenConferenceJsonUtils
                        .getBreaksStringsFromJson(con, jsonBreakResponse);

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
                Toast.makeText(con, "Już! " + weatherData.length, Toast.LENGTH_SHORT).show();
            } else {
//                showErrorMessage();
                Toast.makeText(con, "Cos nie tego! ", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
