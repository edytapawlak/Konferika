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


public class MainActivity extends BaseActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = this;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        super.navigationView.setCheckedItem(R.id.drawer_con_pan);
        View contentView = inflater.inflate(R.layout.activity_main, null, false);

        super.lay.addView(contentView, 0);

        viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        initToolbar();
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewPagerAdapter.setScheduleId(ViewPagerAdapter.CONFERENCE_SCHED);
        super.navigationView.setCheckedItem(R.id.drawer_con_pan);
        viewPager.getAdapter().notifyDataSetChanged();
        setupViewPager(viewPager);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ViewPagerAdapter.setScheduleId(ViewPagerAdapter.CONFERENCE_SCHED);
        viewPager.getAdapter().notifyDataSetChanged();
        //setupViewPager(viewPager);
    }

    /**
     * This method adds tabs to a viewPager
     *
     * @param viewPager
     */
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
//        Toast.makeText(this, "nowy intent", Toast.LENGTH_SHORT).show();
        ViewPagerAdapter.setScheduleId(ViewPagerAdapter.CONFERENCE_SCHED);
        viewPager.getAdapter().notifyDataSetChanged();
        setupViewPager(viewPager);
    }

}