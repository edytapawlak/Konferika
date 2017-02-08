package com.app.android.konferika.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.app.android.konferika.R;
import com.app.android.konferika.adapters.ViewPagerAdapter;

public class MyScheduleActivity extends FragmentActivity {//implements TabLayout.OnTabSelectedListener {//AppCompatActivity {

    static ViewPager viewPager;
    static TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);

        // LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        viewPager = (ViewPager) findViewById(R.id.main_view_pager_myschedule);



    setupViewPager(viewPager);

    tabLayout=(TabLayout)

    findViewById(R.id.tab_layout_myschedule);

    tabLayout.setupWithViewPager(viewPager);

    Log.v("SelectedTabPos",tabLayout.getSelectedTabPosition()+"");
}

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new DayScheduleFragment(), "Piątek");
        adapter.addFrag(new DayScheduleFragment(), "Sobota");
        adapter.addFrag(new DayScheduleFragment(), "Niedziela");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ViewPagerAdapter.setScheduleId(0);
    }


    public static int getTab() {
        if (viewPager != null) {
            return viewPager.getCurrentItem();
        } else return -1;
    }

}
