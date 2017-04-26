//package com.app.android.konferika.data;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//import android.widget.LinearLayout;
//
//import com.app.android.konferika.obj.Tag;
//import com.app.android.konferika.utils.Utils;
//import com.app.android.konferika.obj.Activity;
//import com.app.android.konferika.obj.Break;
//import com.app.android.konferika.obj.Dinner;
//import com.app.android.konferika.obj.Lecture;
//import com.app.android.konferika.obj.Poster;
//import com.app.android.konferika.obj.PosterSesion;
//import com.app.android.konferika.obj.SectionHeader;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.TreeMap;
//
//public class DatabaseAccess {
//
//    private SQLiteOpenHelper openHelper;
//    private SQLiteDatabase database;
//    private static DatabaseAccess instance;
//
//    /**
//     * Private constructor to aboid object creation from outside classes.
//     *
//     * @param context
//     */
//    private DatabaseAccess(Context context) {
//        //try {
//        //    this.openHelper = new DataBaseHelper(context);
//        //} catch (IOException e) {
//       // this.openHelper = new DatabaseOpenHelper(context);
//        //}
//    }
//
//    /**
//     * Return a singleton instance of DatabaseAccess.
//     *
//     * @param context the Context
//     * @return the instance of DabaseAccess
//     */
//    public static DatabaseAccess getInstance(Context context) {
//        if (instance == null) {
//            instance = new DatabaseAccess(context);
//        }
//        return instance;
//    }
//
//    /**
//     * Open the database connection.
//     */
//    public void open() {
//        this.database = openHelper.getWritableDatabase();
//    }
//
//    /**
//     * Close the database connection.
//     */
//    public void close() {
//        if (database != null) {
//            this.database.close();
//        }
//    }
//
//    public SQLiteDatabase getDatabase(){
//        if(database == null){
//            open();
//        }
//        return database;
//    }
//
//    /**
//     * Returns lecture titles as ArrayList
//     */
//    public ArrayList<String> getLectureTitles() {
//        ArrayList<String> list = new ArrayList<>();
//        Cursor cursor = database.rawQuery("SELECT title FROM Ref", null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            list.add(cursor.getString(0));
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return list;
//    }
//
//    public int getPosterSesionId() {
//        int sesionId = 0;
//        Cursor cursor = database.rawQuery("SELECT id FROM BreakData WHERE title = \"Sesja plakatowa\"", null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            sesionId = cursor.getInt(0);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return sesionId;
//    }
//
//    public int getBreakId() {
//        int sesionId = 0;
//        Cursor cursor = database.rawQuery("SELECT id FROM BreakData WHERE title = \"Przerwa kawowa\"", null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            sesionId = cursor.getInt(0);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return sesionId;
//    }
//
//    public int getDinnerId() {
//        int sesionId = 0;
//        Cursor cursor = database.rawQuery("SELECT id FROM BreakData WHERE title = \"Przerwa obiadowa\"", null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            sesionId = cursor.getInt(0);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return sesionId;
//    }
//
//    public ArrayList<String> getAuthors() {
//        ArrayList<String> list = new ArrayList<>();
//        Cursor cursor = database.rawQuery("SELECT author FROM Ref", null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            list.add(cursor.getString(0));
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return list;
//    }
//
//    /**
//     * Returns tags as ArrayList
//     */
//    public ArrayList<Tag> getTagsTitles() {
//        ArrayList<Tag> list = new ArrayList<>();
//        Cursor cursor = database.rawQuery("SELECT id, tag_text FROM Tags", null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            list.add(new Tag(cursor.getString(1), cursor.getInt(0)));
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return list;
//    }
//
//    public ArrayList<Lecture> getOnlyLectData() {
//        ArrayList<Lecture> list = new ArrayList<>();
//        Cursor cursor = database.rawQuery("SELECT title, author, abstract, date_id, Ref._id, startTime, room, is_in_usr\n" +
//                "FROM Ref  JOIN Rooms ON Ref.room_id = Rooms._id\n" +
//                "ORDER BY Ref._id;", null);
//        cursor.moveToFirst();
//        Lecture lec;
//        List<Tag> tagList;
//        Cursor tagsCursor;
//        int lectId;
//        int date_id;
//        boolean isInUsr;
//        int tagId;
//        while (!cursor.isAfterLast()) {
//            tagList = new LinkedList<>();
//            isInUsr = (cursor.getInt(7) != 0);
//            date_id = cursor.getInt(3);
//            lectId = cursor.getInt(4);
//            tagsCursor = database.rawQuery("SELECT tag_id, tag_text FROM Ref Join Lectures_tags On " +
//                    "Ref._id = Lectures_tags.lecture_id Join Tags on Lectures_tags.tag_id = Tags.id Where Ref._id = " + lectId, null);
//            tagsCursor.moveToFirst();
//            while (!tagsCursor.isAfterLast()) {
//                tagId = tagsCursor.getInt(0);
//                Tag t = new Tag(tagsCursor.getString(1), tagId);
//                tagList.add(t);
//                Log.v("Tags: ", t.getTitle());
//                tagsCursor.moveToNext();
//            }
//            lec = new Lecture(cursor.getString(0), cursor.getString(1), cursor.getString(2), date_id, cursor.getInt(4),
//                    cursor.getString(5), cursor.getString(6), tagList, isInUsr);
//            list.add(lec);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return list;
//    }
//
//    public HashMap<Integer, Lecture> getOnlyLectData2() {
//        HashMap<Integer, Lecture> list = new HashMap<>();
//        Cursor cursor = database.rawQuery("SELECT title, author, abstract, date_id, Ref._id, startTime, room, is_in_usr\n" +
//                "FROM Ref  JOIN Rooms ON Ref.room_id = Rooms._id\n" +
//                "ORDER BY Ref._id;", null);
//        cursor.moveToFirst();
//        Lecture lec;
//        List<Tag> tagList;
//        Cursor tagsCursor;
//        int lectId;
//        int date_id;
//        boolean isInUsr;
//        int tagId;
//        while (!cursor.isAfterLast()) {
//            tagList = new LinkedList<>();
//            isInUsr = (cursor.getInt(7) != 0);
//            date_id = cursor.getInt(3);
//            lectId = cursor.getInt(4);
//            tagsCursor = database.rawQuery("SELECT tag_id, tag_text FROM Ref Join Lectures_tags On " +
//                    "Ref._id = Lectures_tags.lecture_id Join Tags on Lectures_tags.tag_id = Tags.id Where Ref._id = " + lectId, null);
//            tagsCursor.moveToFirst();
//            while (!tagsCursor.isAfterLast()) {
//                tagId = tagsCursor.getInt(0);
//                Tag t = new Tag(tagsCursor.getString(1), tagId);
//                tagList.add(t);
//                Log.v("Tags: ", t.getTitle());
//                tagsCursor.moveToNext();
//            }
//            lec = new Lecture(cursor.getString(0), cursor.getString(1), cursor.getString(2), date_id, cursor.getInt(4),
//                    cursor.getString(5), cursor.getString(6), tagList, isInUsr);
//            list.put(lec.getId(), lec);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return list;
//    }
//
//
//    /**
//     * Return all lectures and breaks as ArrayList<Activity>
//     */
//
//    public ArrayList<Activity> getLectData() {
//        ArrayList<Activity> list = new ArrayList<>();
//        Cursor cursor = database.rawQuery("SELECT title, author, abstract, date_id, Ref._id, startTime, room, is_in_usr\n" +
//                "FROM Ref  JOIN Rooms ON Ref.room_id = Rooms._id\n" +
//                "ORDER BY Ref._id;", null);
//        cursor.moveToFirst();
//        Lecture lec;
//        Cursor tagsCursor;
//        int lectId;
//        int date_id;
//        boolean isInUsr;
//        int tagId;
//        while (!cursor.isAfterLast()) {
//            List<Tag> tagList = new LinkedList<>();
//            tagList.clear();
//            isInUsr = (cursor.getInt(7) != 0);
//            date_id = cursor.getInt(3);
//            lectId = cursor.getInt(4);
//            tagsCursor = database.rawQuery("SELECT tag_id, tag_text FROM Ref Join Lectures_tags On " +
//                    "Ref._id = Lectures_tags.lecture_id Join Tags on Lectures_tags.tag_id = Tags.id Where Ref._id = " + lectId, null);
//            tagsCursor.moveToFirst();
//            while (!tagsCursor.isAfterLast()) {
//                tagId = tagsCursor.getInt(0);
//                tagList.add(new Tag(tagsCursor.getString(1), tagId));
//                tagsCursor.moveToNext();
//            }
//            lec = new Lecture(cursor.getString(0), cursor.getString(1), cursor.getString(2), date_id, cursor.getInt(4),
//                    cursor.getString(5), cursor.getString(6), tagList, isInUsr);
//            list.add(lec);
//            cursor.moveToNext();
//            tagsCursor.close();
//        }
//        cursor.close();
//
//        Cursor cursor1 = database.rawQuery("SELECT title, startTime FROM Break JOIN BreakData ON Break.type=BreakData.id", null);
//        cursor1.moveToFirst();
//        Break bre;
//        Dinner din;
//        while (!cursor1.isAfterLast()) {
//
//            switch (cursor1.getInt(1)) {
//                case 3:
//                    bre = new Break(cursor1.getString(0), cursor1.getString(1));
//                    list.add(bre);
//                    break;
//                case 2:
//                    din = new Dinner(cursor1.getString(0), cursor1.getString(1));
//                    list.add(din);
//                    break;
//                /*case 1:
//                    if (cursor.getInt(2) == date) {
//                        bre = new PosterSesion(cursor.getInt(2), time, cursor.getString(0));
//                        list.add(bre);
//                    }
//                    break; */
//            }
//
//            /*if (cursor.getString(0).equals("Przerwa kawowa")) {
//                bre = new Break(cursor.getString(0), cursor.getString(1));
//                list.add(bre);
//            } else {
//                din = new Dinner(cursor.getString(0), cursor.getString(1));
//                list.add(din);
//            }
//            cursor.moveToNext();
//        }*/
//
//            cursor1.moveToNext();
//
//        /*for (int j = 0; j < list.size(); j++) {
//            String tekst;
//            Lecture lect;
//            Break brek = null;
//            if(list.get(j).isLecture()){
//                lect = (Lecture) list.get(j);
//                tekst = lect.getTitle() +" "+ lect.getId();
//                Log.v("Lecture ", tekst +"\n");
//            }else{
//                brek = (Break) list.get(j);
//                tekst = brek.getTitle();
//                Log.v("Break ", tekst +"\n");
//            }
//        }*/
//        }
//        cursor1.close();
//        return list;
//    }
//
//
//    /**
//     * Return all lectures and brakes for date and time as ArrayList<Activity>
//     *
//     * @param date Date in int
//     * @param time Time in format HH:MM
//     */
//    public ArrayList<Activity> getLectOnDateAndTime(int date, String time) {
//        ArrayList<Activity> list = new ArrayList<>();
//        Cursor cursor = database.rawQuery("SELECT title, author, abstract, date_id, Ref._id, startTime, room, is_in_usr  " +
//                "FROM Ref JOIN Rooms ON Ref.room_id = Rooms._id " +
//                "WHERE date_id=\"" + date + "\" AND startTime=\"" + time + "\"", null);
//
//        cursor.moveToFirst();
//        Lecture lec;
//        Cursor tagsCursor;
//        while (!cursor.isAfterLast()) {
//            List<Tag> tagList = new LinkedList<>();
//            boolean isInUsr = (cursor.getInt(7) != 0);
//            int dateId = cursor.getInt(3);
//            int lectId = Integer.parseInt(cursor.getString(4));
//            tagsCursor = database.rawQuery("SELECT tag_id, tag_text FROM Ref Join Lectures_tags On " +
//                    "Ref._id = Lectures_tags.lecture_id Join Tags on Lectures_tags.tag_id = Tags.id Where Ref._id = " + lectId, null);
//            tagsCursor.moveToFirst();
//            while (!tagsCursor.isAfterLast()) {
//                int tagId = tagsCursor.getInt(0);
//                tagList.add(new Tag(tagsCursor.getString(1), tagId));
//                tagsCursor.moveToNext();
//            }
//            lec = new Lecture(cursor.getString(0), cursor.getString(1), cursor.getString(2), dateId, cursor.getInt(4),
//                    cursor.getString(5), cursor.getString(6), tagList, isInUsr);
//            list.add(lec);
//            cursor.moveToNext();
//            tagsCursor.close();
//        }
//        cursor.close();
//
//        //cursor = database.rawQuery("SELECT title, startTime FROM Break WHERE startTime = \"" + time + "\"", null);
//
//        cursor = database.rawQuery("SELECT title, type, date FROM Break JOIN BreakData ON " +
//                "Break.type=BreakData.id WHERE startTime = \"" + time + "\"", null);
//        cursor.moveToFirst();
//        Activity bre;
//        while (!cursor.isAfterLast()) {
//
//            // TODO: Zrobić to lepiej.
//
//            switch (cursor.getInt(1)) {
//                case 3:
//                    bre = new Break(cursor.getString(0), time);
//                    list.add(bre);
//                    break;
//                case 2:
//                    bre = new Dinner(cursor.getString(0), time);
//                    list.add(bre);
//                    break;
//                case 1:
//                    if (cursor.getInt(2) == date) {
//                        bre = new PosterSesion(cursor.getInt(2), time, cursor.getString(0));
//                        list.add(bre);
//                    } else {
//
//                    }
//                    break;
//            }
//
//         /*   if (cursor.getInt(1) == 3) {
//                bre = new Break(cursor.getString(0), cursor.getString(1));
//                list.add(bre);
//            } else {
//                bre = new Dinner(cursor.getString(0), cursor.getString(1));
//                list.add(bre);
//            }
//            */
//            cursor.moveToNext();
//        }
//
//        cursor.close();
//
//        /*for (int j = 0; j < list.size(); j++) {
//            String tekst;
//            Lecture lect;
//            Break brek = null;
//            if(list.get(j).isLecture()){
//                lect = (Lecture) list.get(j);
//                tekst = lect.getTitle();
//                Log.v("Lecture ", tekst +"\n");
//            }else{
//                brek = (Break) list.get(j);
//                tekst = brek.getTitle();
//                Log.v("Break ", tekst +"\n");
//            }
//        }*/
//
//        return list;
//    }
//
//
//    /**
//     * Return all start times in format HH:MM as an array of Strings
//     */
//
//    public String[] getAllStartTime() {
//        String[] list = new String[15];
//        Cursor cursor = database.rawQuery("SELECT * FROM (SELECT startTime FROM Ref UNION SELECT startTime FROM Break) " +
//                "ORDER BY time(startTime)", null);
//        cursor.moveToFirst();
//        int i = 0;
//        while (!cursor.isAfterLast()) {
//            list[i] = cursor.getString(0);
//            cursor.moveToNext();
//            i++;
//        }
//        cursor.close();
//
//        return list;
//    }
//
//
//    /**
//     * Return lectures for a day grouped by time
//     *
//     * @param dateId
//     * @return List of section headers (date String and list of Lectures)
//     */
//
//    public List<SectionHeader> getChildForDate(int dateId) {
//        String[] times = getAllStartTime();
//        ArrayList<Activity> lectChild;
//        List<SectionHeader> list = new LinkedList<>();
//        SectionHeader sec;
//        int i = 0;
//
//        while (times[i] != null) {
//            lectChild = getLectOnDateAndTime(dateId, times[i]);
//            boolean isLect;
//            if (!lectChild.isEmpty()) {
//                if (lectChild.get(0).isLecture()) {
//                    isLect = true;
//                } else {
//                    isLect = false;
//                }
////                sec = new SectionHeader(lectChild, times[i], isLect);
////                list.add(sec);
//            }
//            i++;
//        }
//        return list;
//    }
//
//
//    public HashMap<Integer, Activity> getBrakes() {
//        HashMap<Integer, Activity> list = new HashMap<>();
//        Cursor cursor = database.rawQuery("SELECT title, startTime, type FROM Break JOIN BreakData ON Break.type=BreakData.id", null);
//        cursor.moveToFirst();
//        Activity bre;
//        int i = 0;
//        while (!cursor.isAfterLast()) {
//
//            switch (cursor.getInt(2)) {
//                case Break.ID:
//                    bre = new Break(cursor.getString(0), cursor.getString(1));
//                    list.put(Break.ID, bre);
//                    break;
//                case Dinner.ID:
//                    bre = new Dinner(cursor.getString(0), cursor.getString(1));
//                    list.put(Dinner.ID, bre);
//                    break;
//            }
//            cursor.moveToNext();
//            i++;
//        }
//        cursor.close();
//        return list;
//
//    }
//
//
//    public ArrayList<Poster> getPosters() {
//        ArrayList<Poster> outputList = new ArrayList<>();
//        Cursor cursor = database.rawQuery("SELECT id, title, author, abstract, mark FROM Posters ORDER BY id", null);
//        cursor.moveToFirst();
//        Poster poster;
//        Cursor tagCursor;
//        while (!cursor.isAfterLast()) {
//            List<Tag> tagList = new LinkedList<Tag>();
//            tagList.clear();
//            String[] tab = new String[2];
//            tab[0] = cursor.getString(2);
//            int posterId = cursor.getInt(0);
//            tagCursor = database.rawQuery("SELECT tag_id, tag_text FROM Posters JOIN Posters_tags ON " +
//                    "Posters.id = Posters_tags.poster_id Join Tags on " +
//                    "Posters_tags.tag_id = Tags.id WHERE Posters.id = " + posterId, null);
//            tagCursor.moveToFirst();
//            while (!tagCursor.isAfterLast()) {
//                int tagId = tagCursor.getInt(0);
//                tagList.add(new Tag(tagCursor.getString(1), tagId));
//                tagCursor.moveToNext();
//            }
//            poster = new Poster(cursor.getInt(0), cursor.getString(1), tab, cursor.getString(3), tagList, cursor.getFloat(4));
//            outputList.add(poster);
//            cursor.moveToNext();
//            tagCursor.close();
//        }
//        cursor.close();
//        return outputList;
//    }
//
//    public void updatePosterMark(int id, float mark) {
//        ContentValues cv = new ContentValues();
//        cv.put("mark", mark);
//
//        database.update("Posters", cv, "id = " + id, null);
//    }
//
//    public void setIsInSched(int id, boolean isInSched) {
//        ContentValues cv = new ContentValues();
//        if (isInSched) {
//            cv.put("is_in_usr", 1);
//        } else {
//            cv.put("is_in_usr", 0);
//        }
//        database.update("Ref", cv, "_id = " + id, null);
//    }
//
//    /**
//     * Return users lectures and brakes for date and time as ArrayList<Activity>
//     *
//     * @param date Date in int
//     * @param time Time in format HH:MM
//     */
//    public ArrayList<Activity> getUserLectOnDateAndTime(int date, String time) {
//        ArrayList<Activity> list = new ArrayList<>();
//        Cursor cursor = database.rawQuery("SELECT title, author, abstract, date_id, Ref._id, startTime, room, is_in_usr  " +
//                "FROM Ref JOIN Rooms ON Ref.room_id = Rooms._id " +
//                "WHERE date_id=\"" + date + "\" AND startTime=\"" + time + "\" AND is_in_usr = 1", null);
//
//        cursor.moveToFirst();
//        Lecture lec;
//        Cursor tagCursor;
//        while (!cursor.isAfterLast()) {
//            List<Tag> tagList = new LinkedList<Tag>();
//            boolean isInUsr = (cursor.getInt(7) != 0);
//            int dateId = cursor.getInt(3);
//            int lectId = cursor.getInt(4);
//            Log.v("LectID: ", lectId + "");
//            tagCursor = database.rawQuery("SELECT tag_id, tag_text FROM Ref JOIN Lectures_tags ON " +
//                    "Ref._id = Lectures_tags.lecture_id JOIN Tags ON Lectures_tags.tag_id = Tags.id WHERE " +
//                    "Ref._id = " + lectId, null);
//            tagCursor.moveToFirst();
//            while (!tagCursor.isAfterLast()) {
//                int tagId = tagCursor.getInt(0);
//                tagList.add(new Tag(tagCursor.getString(1), tagId));
//                tagCursor.moveToNext();
//            }
//            lec = new Lecture(cursor.getString(0), cursor.getString(1), cursor.getString(2), dateId, cursor.getInt(4),
//                    cursor.getString(5), cursor.getString(6), tagList, isInUsr);
//            list.add(lec);
//            cursor.moveToNext();
//            tagCursor.close();
//        }
//        cursor.close();
//
//        cursor = database.rawQuery("SELECT title, startTime, type, date FROM Break JOIN BreakData ON Break.type=BreakData.id WHERE startTime = \"" + time + "\"", null);
//        cursor.moveToFirst();
//        Activity bre;
//        while (!cursor.isAfterLast()) {
//
//            switch (cursor.getInt(2)) {
//                case Break.ID:
//                    bre = new Break(cursor.getString(0), time);
//                    list.add(bre);
//                    break;
//                case Dinner.ID:
//                    bre = new Dinner(cursor.getString(0), time);
//                    list.add(bre);
//                    break;
//                case PosterSesion.ID:
//                    if (cursor.getInt(3) == date) {
//                        bre = new PosterSesion(cursor.getInt(3), time, cursor.getString(0));
//                        list.add(bre);
//                    }
//                    break;
//            }
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return list;
//    }
//
//
//     /*for (int j = 0; j < list.size(); j++) {
//            String tekst;
//            Lecture lect;
//            Break brek = null;
//            if(list.get(j).isLecture()){
//                lect = (Lecture) list.get(j);
//                tekst = lect.getTitle();
//                Log.v("Lecture ", tekst +"\n");
//            }else{
//                brek = (Break) list.get(j);
//                tekst = brek.getTitle();
//                Log.v("Break ", tekst +"\n");
//            }
//        }*/
//
//    public TreeMap<String, List<Activity>> getUserChildForDate(int dateId) {
//        String[] times = getAllStartTime();
//        ArrayList<Activity> lectChild;
//        TreeMap<String, List<Activity>> list = new TreeMap<>();
//        int i = 0;
//
//        while (times[i] != null) {
//            lectChild = getUserLectOnDateAndTime(dateId, times[i]);
//            if (!lectChild.isEmpty()) {
//                list.put(Utils.formatTime(times[i]), lectChild);
//            }
//            i++;
//        }
//        return list;
//    }
//
//    public ArrayList<Lecture> getLectForTag(int tagId) {
//        ArrayList<Lecture> list = new ArrayList<>();
//        Cursor cursor = database.rawQuery("SELECT title, author, abstract, date_id, Ref._id, startTime, room, is_in_usr " +
//                "FROM Ref  JOIN Rooms ON Ref.room_id = Rooms._id JOIN Lectures_tags ON " +
//                "Ref._id = Lectures_tags.lecture_id JOIN Tags ON Lectures_tags.tag_id = Tags.id " +
//                "WHERE Tags.id = " + tagId +
//                " ORDER BY Ref._id;", null);
//        cursor.moveToFirst();
//        Lecture lec;
//        int date_id;
//        boolean isInUsr;
//        while (!cursor.isAfterLast()) {
//            isInUsr = (cursor.getInt(7) != 0);
//            date_id = cursor.getInt(3);
//            lec = new Lecture(cursor.getString(0), cursor.getString(1), cursor.getString(2), date_id, cursor.getInt(4),
//                    cursor.getString(5), cursor.getString(6), new ArrayList<Tag>(), isInUsr);
//            list.add(lec);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return list;
//    }
//
//    public ArrayList<Poster> getPostersForTag(int tagId) {
//        ArrayList<Poster> outputList = new ArrayList<>();
//        Cursor cursor = database.rawQuery("SELECT Posters.id, Posters.title, author, abstract, mark FROM Posters JOIN Posters_tags ON " +
//                "Posters.id = Posters_tags.poster_id JOIN Tags ON Posters_tags.tag_id = Tags.id WHERE Tags.id = "
//                + tagId + " ORDER BY Posters.id", null);
//        cursor.moveToFirst();
//        Poster poster;
//        while (!cursor.isAfterLast()) {
//            String[] tab = new String[2];
//            tab[0] = cursor.getString(2);
//            poster = new Poster(cursor.getInt(0), cursor.getString(1), tab, cursor.getString(3),
//                    new ArrayList<Tag>(), cursor.getFloat(4));
//            outputList.add(poster);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return outputList;
//    }
//
//    public List<Integer> selectAllId() {
//        List<Integer> list = new ArrayList<Integer>();
//        Cursor cursor = this.database.query("Ref", new String[]{"_id"},
//                null, null, null, null, null);
//        if (cursor.moveToFirst()) {
//            do {
//                list.add(cursor.getInt(0));
//            } while (cursor.moveToNext());
//        }
//        if (cursor != null && !cursor.isClosed()) {
//            cursor.close();
//        }
//
//        return list;
//    }
//
//    public void updateLecture(Lecture lect) {
//
//        List<Integer> list = selectAllId();
//        ContentValues cv = new ContentValues();
//        int id = lect.getId();
//        cv.put("title", lect.getTitle());
//        cv.put("author", lect.getAuthor());
//        cv.put("abstract", lect.getAbs());
//        cv.put("startTime", lect.getStartTime());
//        cv.put("date_id", lect.getDate());
//        cv.put("room_id", lect.getRoom());
//
//
//        if (list.contains(lect.getId())) {
//            database.update("Ref", cv, "_id = " + id, null);
//        } else {
//            cv.put("_id", id);
//            database.insert("Ref", null, cv);
//            Log.v("UPDATE: ", "uuu " + lect.getTitle() + " " + lect.getDate());
//        }
//    }
//
//    public void updatePoster(Poster pos) {
//        ContentValues cv = new ContentValues();
//        int id = pos.getId();
//        cv.put("title", pos.getTitle());
//        cv.put("author", pos.getAuthors()[0]);
//        cv.put("abstract", pos.getAbs());
//
//        database.update("Posters", cv, "id = " + id, null);
//    }
//
//}
