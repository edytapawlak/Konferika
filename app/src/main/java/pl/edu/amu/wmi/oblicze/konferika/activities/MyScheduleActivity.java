package pl.edu.amu.wmi.oblicze.konferika.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import pl.edu.amu.wmi.oblicze.konferika.R;
import pl.edu.amu.wmi.oblicze.konferika.adapters.ViewPagerAdapter;

public class MyScheduleActivity extends BaseActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = this;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


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
        super.navigationView.setCheckedItem(R.id.drawer_favourite);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ViewPagerAdapter.setScheduleId(ViewPagerAdapter.USER_SCHED);
        viewPager.getAdapter().notifyDataSetChanged();
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new UserDayFragment(), "Piątek");
        adapter.addFrag(new UserDayFragment(), "Sobota");
        adapter.addFrag(new UserDayFragment(), "Niedziela");
        viewPager.setAdapter(adapter);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //Toast.makeText(this, "nowy intent", Toast.LENGTH_SHORT).show();
        ViewPagerAdapter.setScheduleId(ViewPagerAdapter.USER_SCHED);
        viewPager.getAdapter().notifyDataSetChanged();
        setupViewPager(viewPager);

    }
}
