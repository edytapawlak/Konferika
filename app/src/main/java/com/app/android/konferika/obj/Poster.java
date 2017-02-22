package com.app.android.konferika.obj;


import java.io.Serializable;

public class Poster implements Serializable{

    public String getTitle() {
        return title;
    }

    public String[] getAuthors() {
        return authors;
    }

    public String getAbs() {
        return abs;
    }

    public int getId() {
        return id;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public float getMark() {
        return mark;
    }

    private String title;
    private int id;
    private String[] authors;
    private String abs;
    private float mark;

    public Poster(int id, String title, String[] authors, String abs){
        this.title = title;
        this.id = id;
        this.authors = authors;
        this.abs = abs;
        this.mark = 0;
    }
}
