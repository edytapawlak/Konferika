package com.app.android.konferika.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.app.android.konferika.R;

public class BaseActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    Intent intent;
    protected BaseActivity mActivity;
    protected FrameLayout lay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    public void onCreateDrawer() {
        setupDrawerLayout();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        onCreateDrawer();

    }

    Handler handler = new Handler(new Handler.Callback() {
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

    private void setupDrawerLayout() {
        lay = (FrameLayout) findViewById(R.id.tabsy);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.navigation_view_base);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.drawer_con_pan:
                        intent = new Intent(mActivity, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        handler.sendEmptyMessageDelayed(1, 100);
                        break;
                    case R.id.drawer_favourite:
                        intent = new Intent(mActivity, MyScheduleActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        handler.sendEmptyMessageDelayed(1, 100);
                        break;
                    case R.id.drawer_posters:
                        intent = new Intent(mActivity, PosterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        handler.sendEmptyMessageDelayed(1, 100);
                        break;
                    case R.id.drawer_map:
                        String loc = "Wydział Matematyki i Informatyki UAM, Umultowska, Poznań";
                        Uri geolocation = Uri.parse("geo:0,0?q=" + loc);
                        showMap(geolocation);
                        break;

                }

                //Toast.makeText(mActivity, menuItem.getTitle() + " pressed", Toast.LENGTH_SHORT).show();
                menuItem.setChecked(false);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void showMap(Uri geoloc) {
        Intent geoIntent = new Intent(Intent.ACTION_VIEW);
        geoIntent.setData(geoloc);
        if (geoIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(geoIntent);
        }
    }

    protected void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle(title);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
}
