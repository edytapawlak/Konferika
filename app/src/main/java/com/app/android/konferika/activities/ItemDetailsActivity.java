package com.app.android.konferika.activities;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.app.android.konferika.R;
import com.app.android.konferika.obj.Activity;
import com.app.android.konferika.obj.Lecture;

public class ItemDetailsActivity extends AppCompatActivity {
    ItemDetailsFragment fragmentItemDetail;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);


        Lecture item = (Lecture) getIntent().getSerializableExtra("lect");
        initToolbar(item.getTitle());
        if (item == null) {
            Toast.makeText(this, "Jest null,sorry", Toast.LENGTH_SHORT).show();
        }
        TextView tvTitle = (TextView) findViewById(R.id.tv_lect_title);
        TextView tvBody = (TextView) findViewById(R.id.tv_lect_description);
        TextView tvAuthor = (TextView) findViewById(R.id.tv_lect_author);
        tvTitle.setText(item.getTitle());
        tvBody.setText(item.getAbs() + item.getAbs() + item.getAbs());
        tvAuthor.setText(item.getAuthor());
        //ItemDetailsFragment fragmentDemo = new ItemDetailsFragment();
        //Bundle args = new Bundle();
        //args.putSerializable("item", item);
        //fragmentDemo.setArguments(args);
        /*TextView tvTitle = (TextView) findViewById(R.id.tv_details_title);
        TextView tvBody = (TextView) findViewById(R.id.tv_details_description);
        tvTitle.setText(item.getAuthor());
        tvBody.setText(item.getTitle());*/
        if (savedInstanceState == null) {
            // Insert detail fragment based on the item passed

            /*fragmentItemDetail = ItemDetailsFragment.newInstance(item);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flDetailContainer, fragmentItemDetail);
            ft.commit();*/

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* Intent intent = new Intent(MainActivity.this, MyScheduleActivity.class);
        startActivity(intent);
        ViewPagerAdapter.setScheduleId(1);
        return true;*/

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(ItemDetailsActivity.this, MainActivity.class); //Todo poprzednie activity
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);

    }


    private void initToolbar(String title) {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(title);
        //collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}

