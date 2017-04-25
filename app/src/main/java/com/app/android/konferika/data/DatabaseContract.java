package com.app.android.konferika.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by edyta on 25.04.17.
 */

public class DatabaseContract {
    public static final String CONTENT_AUTHORITY = "com.app.android.konferika";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_LECTURES = "Ref";

    public static final class LecturesEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_LECTURES)
                .build();

        public static final String TABLE_NAME = "Ref";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_ABSTRACT = "abstract";
        public static final String COLUMN_START_TIME = "startTime";
        public static final String COLUMN_END_TIME = "endTime";
        public static final String COLUMN_DATE_ID = "date_id";
        public static final String COLUMN_ROOM_ID = "room_id";
        public static final String COLUMN_IS_IN_USR = "is_in_usr";

        /**
         * @param id LectID
         * @return Uri to query details about a single weather entry
         */
        public static Uri buildWeatherUriWithDate(int id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(id + "")
                    .build();
        }

//        /**
//         * Returns just the selection part of the weather query from a normalized today value.
//         * This is used to get a weather forecast from today's date. To make this easy to use
//         * in compound selection, we embed today's date as an argument in the query.
//         *
//         * @return The selection part of the weather query for today onwards
//         */
//        public static String getSqlSelectForTodayOnwards() {
//            long normalizedUtcNow = SunshineDateUtils.normalizeDate(System.currentTimeMillis());
//            return WeatherContract.WeatherEntry.COLUMN_DATE + " >= " + normalizedUtcNow;
//        }
    }
}
