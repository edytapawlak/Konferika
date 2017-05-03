package pl.edu.amu.wmi.oblicze.konferika.obj;

import java.io.Serializable;

/**
 * Created by edyta on 18.04.17.
 */

public class Tag implements Serializable{
    private String title;
    private int id;

    public Tag(String title, int id){
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getId(){
        return this.id;
    }
}
