package com.app.android.konferika.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.app.android.konferika.activities.DayScheduleFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private static int scheduleId;

    public static void setScheduleId(int id){
        scheduleId = id;
    }

    public static int getScheduleId(){

        return scheduleId;
    }

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        switch (position){
            case 0:
                bundle.putString("day", "24-01-17");
                break;
            case 1:
                bundle.putString("day", "25-01-17");
                break;
            case 2:
                bundle.putString("day", "26-01-17");
                break;
        }


        DayScheduleFragment sh = new DayScheduleFragment();
        sh.setArguments(bundle);
        return sh;
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
