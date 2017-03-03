package com.app.android.konferika.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.app.android.konferika.R;
import com.app.android.konferika.adapters.ViewPagerAdapter;

public class MyScheduleActivity extends BaseActivity {

    static ViewPager viewPager;
    static TabLayout tabLayout;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_schedule);
        mActivity = this;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        super.drawerLayout.

        View contentView = inflater.inflate(R.layout.activity_my_schedule, null, false);
        super.lay.addView(contentView, 0);

        viewPager = (ViewPager) findViewById(R.id.main_view_pager_myschedule);
        initToolbar();
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout_myschedule);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewPagerAdapter.setScheduleId(ViewPagerAdapter.USER_SCHED);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ViewPagerAdapter.setScheduleId(ViewPagerAdapter.USER_SCHED);
        viewPager.getAdapter().notifyDataSetChanged();
        setupViewPager(viewPager);
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new DayScheduleFragment(), "Piątek");
        adapter.addFrag(new DayScheduleFragment(), "Sobota");
        adapter.addFrag(new DayScheduleFragment(), "Niedziela");
        viewPager.setAdapter(adapter);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(this, "nowy intent", Toast.LENGTH_SHORT).show();
        ViewPagerAdapter.setScheduleId(ViewPagerAdapter.USER_SCHED);
        viewPager.getAdapter().notifyDataSetChanged();
        setupViewPager(viewPager);

    }
}
