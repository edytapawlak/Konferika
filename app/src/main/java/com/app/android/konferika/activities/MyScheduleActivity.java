package com.app.android.konferika.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.app.android.konferika.R;
import com.app.android.konferika.adapters.ViewPagerAdapter;

public class MyScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        ViewPager viewPager = (ViewPager) findViewById(R.id.main_view_pager_myschedule);

        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_myschedule);
        tabLayout.setupWithViewPager(viewPager);
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
}
