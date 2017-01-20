package com.app.android.konferika.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.android.konferika.activities.DayScheduleFragment;
import com.app.android.konferika.activities.TabChangeListener;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public static final TabChangeListener tabChangedListiner = new TabChangeListener();
    private static int scheduleId;
    private static String dayString;

    public static void setScheduleId(int id) {
        scheduleId = id;
    }

    public static int getScheduleId() {

        return scheduleId;
    }

    public static String getDayString() {
        return dayString;
    }

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        Fragment frag = null;
        /*switch (position){
            case 0:
                dayString = "24-01-17";

                bundle.putString("day", "24-01-17");
                frag = (DayScheduleFragment) mFragmentList.get(0);
               // break;
            case 1:
                dayString = "25-01-17";
                bundle.putString("day", "25-01-17");
                frag = (DayScheduleFragment) mFragmentList.get(1);
               // break;
            case 2:
                dayString = "26-01-17";
                bundle.putString("day", "26-01-17");
                frag = (DayScheduleFragment) mFragmentList.get(2);
               // break;
        }*/

        //DayScheduleFragment sh = new DayScheduleFragment();
        frag = mFragmentList.get(position);
        bundle.putInt("day", position + 1);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
