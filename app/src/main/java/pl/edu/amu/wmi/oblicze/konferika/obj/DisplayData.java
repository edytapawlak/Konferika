package pl.edu.amu.wmi.oblicze.konferika.obj;

import android.content.Context;

import java.util.List;

public abstract class DisplayData {

    private int dateId;
    private List<SectionHeader> sectionList;


    //  public abstract void handleLongClick(Context context, Lecture lecture);

    public abstract void addActivityToList(Context context, Lecture activity);

    public abstract void deleteActivityFromList(Lecture lecture);

    public int getDate() {
        return dateId;
    }

    public void setDateId(int dateId) {
        this.dateId = dateId;
    }

    public List<SectionHeader> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<SectionHeader> sectionList) {
        this.sectionList = null;
        this.sectionList = sectionList;
        this.swapSectionList(sectionList);
    }


    public int getListSize() {
        return sectionList.size();
    }

    public SectionHeader getSectionHeader(int position) {
        return sectionList.get(position);
    }

    public void clear() {
        sectionList.clear();
    }

    public void addAll(DisplayData disp) {
        this.sectionList.addAll(disp.getSectionList());
    }

    public void closeSectionList(){
        for (SectionHeader sc :
                sectionList) {
            sc.getChildrens().close();
        }
    }

    public void swapSectionList(List<SectionHeader> secList){
        for (SectionHeader sc :
                sectionList) {
            //get sctionHeader on time sc.getTime
            String time = sc.getTitle();
            SectionHeader newsc = null;
            for (int i = 0; i < secList.size(); i++) {
                if(secList.get(i).getTitle().equals(time)){
                    newsc = secList.get(i);
                    i = secList.size();
                }
            }
            sc.swapCursor(newsc.getChildrens());
        }
    }
}
