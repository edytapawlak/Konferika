package pl.edu.amu.wmi.oblicze.konferika.obj;


import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.util.List;

import pl.edu.amu.wmi.oblicze.konferika.activities.PosterDetailsActivity;

public class Poster implements Serializable {

    private String title;
    private int id;
    private String[] authors;
    private String abs;
    private float mark;
    private List<Tag> tags;

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


    public Poster(int id, String title, String[] authors, String abs, List<Tag> tags, float mark) {
        this.title = title;
        this.id = id;
        this.authors = authors;
        this.abs = abs;
        this.tags = tags;
        this.mark = mark;
    }

    public void handleOnClick(Context context){
        Intent intent = new Intent(context, PosterDetailsActivity.class);
        intent.putExtra("poster", this);
        context.startActivity(intent);
    }

    public String getTags() {
        String out = "";
        for (Tag t :
                this.tags) {
            out += t.getTitle() + "\n";
        }
        return out;
    }
}
