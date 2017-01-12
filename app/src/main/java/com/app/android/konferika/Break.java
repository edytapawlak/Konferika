package com.app.android.konferika;

public class Break implements Activity {

    private String startTime;
    private String endTime;
    private String title;

    public Break(String title) {
        this.title = title;
    }

    @Override
    public boolean isLecture() {
        return false;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getTitle() {
        return title;
    }
}
