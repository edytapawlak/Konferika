package com.app.android.konferika.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.app.android.konferika.R;
import com.app.android.konferika.adapters.RecyclerViewAdapter;
import com.app.android.konferika.data.ActivityData;
import com.app.android.konferika.obj.Poster;

import java.util.List;

public class PosterActivity extends BaseActivity implements RecyclerViewAdapter.OnItemClickListener {

    private static Context mContext;
    private static List<Poster> items;

    private DrawerLayout drawerLayout;
    private View content;
    private RecyclerView recyclerView;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_poster);
        mActivity = this;

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_poster, null, false);
        super.drawerLayout.addView(contentView, 0);

        mContext = this;
        items = ActivityData.getPosters(mContext);

        initRecyclerView();
       // initFab();
        initToolbar();
        //setupDrawerLayout();

        content = findViewById(R.id.content_poster);

       // if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setRecyclerAdapter(recyclerView);
       // }
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.poster_recycled_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    private void setRecyclerAdapter(RecyclerView recyclerView) {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(items);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void initFab() {
       // findViewById(R.id.poster_fab).setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View v) {
       //         Snackbar.make(content, "FAB Clicked", Snackbar.LENGTH_SHORT).show();
       //     }
       // });
    }

    /*private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.poster_toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }*/

  /*  private void setupDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.poster_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Snackbar.make(content, menuItem.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }*/

    @Override
    public void onItemClick(View view, Poster viewModel) {
        Intent intent = new Intent(PosterActivity.this, PosterDetailsActivity.class);
        intent.putExtra("poster", viewModel);
        startActivity(intent);
    }
}

