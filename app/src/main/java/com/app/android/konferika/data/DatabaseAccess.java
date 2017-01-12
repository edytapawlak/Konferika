package com.app.android.konferika.data;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.app.android.konferika.Break;
import com.app.android.konferika.Lecture;
import com.app.android.konferika.SectionHeader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Returns lecture titles as ArrayList
     */

    public ArrayList<String> getLectureTitles() {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT title FROM Ref", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<String> getAuthors() {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT author FROM Ref", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    /**
     * Return all lectures as ArrayList<Activity>
     */

    public ArrayList<com.app.android.konferika.Activity> getLectData() {
        ArrayList<com.app.android.konferika.Activity> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT title, author, abstract, date, _id FROM Ref", null);
        cursor.moveToFirst();
        Lecture lec;
        while (!cursor.isAfterLast()) {
            lec = new Lecture(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            list.add(lec);
            cursor.moveToNext();
        }
        cursor = database.rawQuery("SELECT title FROM Break", null);
        cursor.moveToFirst();
        Break bre;
        while (!cursor.isAfterLast()) {
            bre = new Break(cursor.getString(0));
            list.add(bre);
            cursor.moveToNext();
        }

        cursor.close();
        return list;
    }

    /**
     * Return all lectures and brakes for date and time as ArrayList<Activity>
     *
     * @param date Date in format DD-MM-YY
     * @param time Time in format HH:MM
     */
    public ArrayList<com.app.android.konferika.Activity> getLectOnDateAndTime(String date, String time) {
        ArrayList<com.app.android.konferika.Activity> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT title, author, abstract, date, _id FROM Ref WHERE date=\"" + date + "\" AND startTime=\"" + time + "\"", null);
        cursor.moveToFirst();
        Lecture lec;
        while (!cursor.isAfterLast()) {
            lec = new Lecture(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            list.add(lec);
            cursor.moveToNext();
        }

        cursor = database.rawQuery("SELECT title FROM Break WHERE startTime = \"" + time + "\"", null);
        cursor.moveToFirst();
        Break bre;
        while (!cursor.isAfterLast()) {
            bre = new Break(cursor.getString(0));
            list.add(bre);
            cursor.moveToNext();
        }

        cursor.close();

        for (int j = 0; j < list.size(); j++) {
            String tekst;
            Lecture lect;
            Break brek = null;
            if(list.get(j).isLecture()){
                lect = (Lecture) list.get(j);
                tekst = lect.getAuthor();
            }else{
                brek = (Break) list.get(j);
                tekst = brek.getTitle();
            }
            Log.v("Msg: ", tekst + "\n");
        }

        return list;
    }


    /**
     * Return all start times in format HH:MM as an array of Strings
     */

    public String[] getAllStartTime() {
        String[] list = new String[15];
        Cursor cursor = database.rawQuery("SELECT * FROM (SELECT startTime FROM Ref UNION SELECT startTime FROM Break) ORDER BY time(startTime)", null);
        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast()) {
            list[i] = cursor.getString(0);
            cursor.moveToNext();
            i++;
        }
        cursor.close();

        return list;
    }


    /**
     * Return lectures for a day grouped by time
     *
     * @param date String represents date in format DD-MM-YY
     * @return List of section headers (date String and list of Lectures)
     */

    public List<SectionHeader> getChildForDate(String date) {
        String[] times = getAllStartTime();
        ArrayList<com.app.android.konferika.Activity> lectChild;
        List<SectionHeader> list = new LinkedList<>();
        SectionHeader sec;
        int i = 0;

        while (times[i] != null) {
            lectChild = getLectOnDateAndTime(date, times[i]);
            sec = new SectionHeader(lectChild, times[i]);
            list.add(sec);
            i++;
        }
        return list;
    }
}
