package com.app.android.konferika;

public class Lecture {
    private  String title;
    private String author;
    private String abs;
    private String date;
    private int id;


    public Lecture(String title, String author, String abs, String date, String id) {
        this.title = title;
        this.author = author;
        this.abs = abs;
        this.date = date;
        int idd = Integer.parseInt(id);
        this.id = idd;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getAbs() {
        return abs;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }
}
