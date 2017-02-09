package com.app.android.konferika.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.app.android.konferika.R;
import com.app.android.konferika.adapters.ViewPagerAdapter;

public class MyScheduleActivity extends AppCompatActivity {//implements TabLayout.OnTabSelectedListener {//AppCompatActivity {

    static ViewPager viewPager;
    static TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private View content;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);

        ViewPagerAdapter.setScheduleId(1);

        // LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        viewPager = (ViewPager) findViewById(R.id.main_view_pager_myschedule);


        initToolbar();
        setupDrawerLayout();
        setupViewPager(viewPager);

        tabLayout = (TabLayout)

                findViewById(R.id.tab_layout_myschedule);

        tabLayout.setupWithViewPager(viewPager);

        Log.v("SelectedTabPos", tabLayout.getSelectedTabPosition() + "");
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new DayScheduleFragment(), "Piątek");
        adapter.addFrag(new DayScheduleFragment(), "Sobota");
        adapter.addFrag(new DayScheduleFragment(), "Niedziela");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.sched_toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Mój plan");
        }
    }

    Intent i;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //Strat another Activity Here
                    startActivity(i);

                default:
                    break;
            }
            return false;
        }
    });

    private void setupDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.drawer_home:
                        i = new Intent(MyScheduleActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        //startActivity(i);
                        handler.sendEmptyMessageDelayed(1, 1000);
                        ViewPagerAdapter.setScheduleId(0);
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

}
