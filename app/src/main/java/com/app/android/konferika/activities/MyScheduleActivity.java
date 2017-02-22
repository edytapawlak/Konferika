package com.app.android.konferika.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.app.android.konferika.R;
import com.app.android.konferika.adapters.ViewPagerAdapter;

public class MyScheduleActivity extends BaseActivity {//implements TabLayout.OnTabSelectedListener {//AppCompatActivity {

    static ViewPager viewPager;
    static TabLayout tabLayout;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_schedule);
        mActivity = this;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_my_schedule, null, false);
        super.lay.addView(contentView, 0);

        viewPager = (ViewPager) findViewById(R.id.main_view_pager_myschedule);

        initToolbar();
        //setupDrawerLayout();
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout_myschedule);

        tabLayout.setupWithViewPager(viewPager);

        Log.v("SelectedTabPos", tabLayout.getSelectedTabPosition() + "");
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

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);

    }*/

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(this, "nowy intent", Toast.LENGTH_SHORT).show();
        ViewPagerAdapter.setScheduleId(ViewPagerAdapter.USER_SCHED);
        viewPager.getAdapter().notifyDataSetChanged();
        setupViewPager(viewPager);


    }

   /* private void initToolbar() {
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
                switch (menuItem.getItemId()) {
                    case R.id.drawer_home:
                        i = new Intent(MyScheduleActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        handler.sendEmptyMessageDelayed(1, 100);
                }

                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }
*/
}
