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
    public static final String PATH_POSTERS = "Posters";
    public static final String PATH_BREAK = "Break";
    public static final String PATH_TAGS_LECT = "Lectires_tags";
    public static final String PATH_TAGS_POS = "Posters_tags";
    public static final String PATH_TAGS = "Tags";
    public static final String PATH_ALL_START_TIME = "StartTimes";
    public static final String PATH_ALL_USERS_START_TIME = "UserStartTimes";
    public static final String PATH_LECTURES_JOIN_SCHED = "LecturesJoinSchedule";
    public static final String PATH_SCHED = "UserSched";
    public static final String PATH_USERS_LECTURES = "UserLectures";





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
        public static Uri buildLecturesUriWithDate(int id) {
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

    public static final class PostersEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_POSTERS)
                .build();

        public static final String TABLE_NAME = "Posters";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_ABSTRACT = "abstract";
        public static final String COLUMN_MARK = "mark";

        /**
         * @param id LectID
         * @return Uri to query details about a single weather entry
         */
        public static Uri buildPostersUriWithDate(int id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(id + "")
                    .build();
        }

    }

    public static final class BreakEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_BREAK)
                .build();

        public static final String TABLE_NAME = "Break";

        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_START_TIME = "startTime";
        public static final String COLUMN_END_TIME = "endTime";
        public static final String COLUMN_DATE_ID = "date_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_ID = "id";


        /**
         * @param id LectID
         * @return Uri to query details about a single weather entry
         */
        public static Uri buildPostersUriWithDate(int id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(id + "")
                    .build();
        }
    }

    public static final class StartTimesEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_ALL_START_TIME)
                .build();


    }

    public static final class LecturesJoinScheduleEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_LECTURES_JOIN_SCHED)
                .build();
        public static final String TABLE_NAME = "Ref LEFT JOIN UserSchedule ON Ref._id = UserSchedule.id";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_ABSTRACT = "abstract";
        public static final String COLUMN_START_TIME = "startTime";
        public static final String COLUMN_END_TIME = "endTime";
        public static final String COLUMN_DATE_ID = "date_id";
        public static final String COLUMN_ROOM_ID = "room_id";
        public static final String COLUMN_IS_IN_USR = "is_in_usr_sched";
        public static final String COLUMN_RATE = "rate";

        public static Uri buildLecturesJoinScheduleUriWithDate(int id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(id + "")
                    .build();
        }
    }

    public static final class UserLecturesEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_USERS_LECTURES)
                .build();
        public static final String TABLE_NAME = "Ref LEFT JOIN UserSchedule ON Ref._id = UserSchedule.id";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_ABSTRACT = "abstract";
        public static final String COLUMN_START_TIME = "startTime";
        public static final String COLUMN_END_TIME = "endTime";
        public static final String COLUMN_DATE_ID = "date_id";
        public static final String COLUMN_ROOM_ID = "room_id";
        public static final String COLUMN_IS_IN_USR = "is_in_usr_sched";

    }

    public static final class ScheduleEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_SCHED)
                .build();
        public static final String TABLE_NAME = "UserSchedule";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_IS_IN_USR = "is_in_usr_sched";
        public static final String COLUMN_RATE = "rate";

        /**
         * @param id LectID
         * @return Uri to query details about a single weather entry
         */
        public static Uri buildScheduleUriWithDate(int id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(id + "")
                    .build();
        }
    }


    public static final class UserStartTimesEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_ALL_USERS_START_TIME)
                .build();
    }

    public static final class TagsEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_TAGS)
                .build();

        public static final String TABLE_NAME = "Tags";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "tag_text";

        /**
         * @param id TagID
         * @return Uri to query details about a single weather entry
         */
        public static Uri buildTagUriWithDate(int id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(id + "")
                    .build();
        }
    }

    public static final class LectureTagsEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_TAGS_LECT)
                .build();

        public static final String TABLE_NAME = "Lectures_tags";

        public static final String COLUMN_LECT_ID = "lecture_id";
        public static final String COLUMN_TAG_ID = "tag_id";


        /**
         * @param id TagID
         * @return Uri to query details about a single weather entry
         */
        public static Uri buildTagUriWithDate(int id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(id + "")
                    .build();
        }
    }

    public static final class PostersTagsEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_TAGS_POS)
                .build();

        public static final String TAGS_TABLE_NAME = "Tags";
        public static final String POSTERS_TAGS_TABLE_NAME = "Posters_tags";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "tag_text";
        public static final String COLUMN_POSTER_ID = "poster_id";
        public static final String COLUMN_POSTER_LECT_TAG_ID = "tag_id";


        /**
         * @param id TagID
         * @return Uri to query details about a single weather entry
         */
        public static Uri buildTagUriWithDate(int id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(id + "")
                    .build();
        }
    }

}
