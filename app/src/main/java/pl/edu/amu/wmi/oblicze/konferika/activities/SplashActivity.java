package pl.edu.amu.wmi.oblicze.konferika.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

import pl.edu.amu.wmi.oblicze.konferika.utils.NetworkUtils;
import pl.edu.amu.wmi.oblicze.konferika.utils.OpenConferenceJsonUtils;


public class SplashActivity extends AppCompatActivity {
    private Context con;
    private Intent intent;
    private int isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con = this;
        loadData();
    }

    private void loadData() {
        new SplashActivity.FetchDataTask().execute("get_lectures", "get_posters", "get_breaks", "get_special");
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
            String special = params[3];
            URL specialRequestUrl = NetworkUtils.buildUrl(special);

            isRunning = -1;
            try {
                isRunning = NetworkUtils.isRunning();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            Log.v("IsRunning", isRunning + "");

            if (isRunning == 2) { // to oznacza, ze akurat jest przerwa techniczna i nie chcę aktualizować danych
                return null;
            }
            if (isRunning == 0) {
                Log.v("IsRunning jest 0", isRunning + "");
                intent = new Intent(SplashActivity.this, StopActivity.class);
                intent.putExtra("info", isRunning);
                startActivity(intent);
                finish();
            } else {
                try {
                    String jsonLectResponse = NetworkUtils
                            .getResponseFromHttpUrl(lectRequestUrl);
                    String jsonPosResponse = NetworkUtils
                            .getResponseFromHttpUrl(postersRequestUrl);
                    String jsonBreakResponse = NetworkUtils
                            .getResponseFromHttpUrl(breaksRequestUrl);
                    String jsonSpecialResponse = NetworkUtils
                            .getResponseFromHttpUrl(specialRequestUrl);

                    String[] simpleJsonSpecialData = OpenConferenceJsonUtils
                            .getSpecialLectStringsFromJson(con, jsonSpecialResponse);
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
            return null;
        }

        @Override
        protected void onPostExecute(String[] data) {
            if (data == null) {
                // To znaczy, że nie ma internetu albo jest przerwa techniczna.
//                showErrorMessage();
//                Toast.makeText(con, "PrzerwaTech", Toast.LENGTH_SHORT).show();
            }
            if (isRunning != 0) {
                intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

}
