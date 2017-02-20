package com.app.android.konferika.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;

import com.app.android.konferika.R;
import com.app.android.konferika.adapters.ViewPagerAdapter;


public class MainActivity extends BaseActivity {

    public ViewPager viewPager;
    TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private View content;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
//        setContentView(R.layout.activity_main);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_main, null, false);
        super.lay.addView(contentView, 0);


        initToolbar();
        //setupDrawerLayout();
        viewPager = (ViewPager) findViewById(R.id.main_view_pager);

        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }


    @Override
    protected void onResume() {
        super.onResume();
        ViewPagerAdapter.setScheduleId(ViewPagerAdapter.CONFERENCE_SCHED);

    }

    /**
     * This method adds tabs to a viewPager
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new DayScheduleFragment(), "Piątek");
        adapter.addFrag(new DayScheduleFragment(), "Sobota");
        adapter.addFrag(new DayScheduleFragment(), "Niedziela");

        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //Toast.makeText(this, "nowy intent", Toast.LENGTH_SHORT).show();
        ViewPagerAdapter.setScheduleId(ViewPagerAdapter.CONFERENCE_SCHED);
        viewPager.getAdapter().notifyDataSetChanged();
        setupViewPager(viewPager);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ViewPagerAdapter.setScheduleId(ViewPagerAdapter.CONFERENCE_SCHED);
       // Toast.makeText(this, "OnRestart", Toast.LENGTH_SHORT).show();

    }


   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

  /*  private void initToolbar() {
        final Toolbar toolbar = (Toolbar) content.findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    Intent intent;

   /* Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    startActivity(intent);
                default:
                    break;
            }
            return false;
        }
    });

/*    private void setupDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.drawer_favourite:
                        intent = new Intent(MainActivity.this, MyScheduleActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        handler.sendEmptyMessageDelayed(1, 100);
                        break;
                    case R.id.drawer_posters:
                        intent = new Intent(MainActivity.this, PosterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        handler.sendEmptyMessageDelayed(1, 100);
                        break;
                }

//                Snackbar.make(content, menuItem.getTitle() + " pressed", Snackbar.LENGTH_LONG).show()
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }
*/
}