package com.app.android.konferika.activities;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.android.konferika.R;
import com.app.android.konferika.obj.Lecture;
import com.app.android.konferika.obj.LecturesList;
import com.app.android.konferika.obj.Poster;
import com.app.android.konferika.obj.UserSchedule;

public class PosterDetailsActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Context mContext;
    private Poster poster;
    private UserSchedule userSched;
    private   FloatingActionButton fabulousBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_details);

    mContext = this;
    poster = (Poster) getIntent().getSerializableExtra("poster");

    initToolbar();
    if (poster == null) {
        Toast.makeText(this, "Jest null,sorry", Toast.LENGTH_SHORT).show();
    }

    TextView tvTitle = (TextView) findViewById(R.id.tv_poster_title);
    TextView tvBody = (TextView) findViewById(R.id.tv_poster_description);
    TextView tvAuthor = (TextView) findViewById(R.id.tv_poster_author);
    fabulousBtn = (FloatingActionButton) findViewById(R.id.button_fabulous_poster);
   /* if(item.getIsInUserSchedule()){
        fabulousBtn.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.fill_star));
    }
    else {
        fabulousBtn.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.empty_star));
    }*/

    fabulousBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        /*    LecturesList mRefData = LecturesList.getInstance(v.getContext());
            int id = poster.getId();

            boolean isChanged;
            userSched = UserSchedule.getInstance(mContext, null);
            if (poster.getIsInUserSchedule()) {
                userSched.deleteActivity(poster, poster.getDate());
                Toast.makeText(mContext, "Usunięto z planu", Toast.LENGTH_SHORT).show();
                isChanged = false;
                fabulousBtn.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.empty_star));
            } else {
                userSched.addActivity(mContext, poster, poster.getDate());
                Toast.makeText(mContext, "Dodano do planu", Toast.LENGTH_SHORT).show();
                isChanged = true;
                fabulousBtn.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.fill_star));
            }

            mRefData.setCheckOnPos(id, isChanged);
            poster.setIsInUserSchedule(isChanged);
            Log.v("Clik", "Clicked " + isChanged);
                /*if(isChanged){
                    fabulousBtn.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.fill_star));
                }
                else {
                    fabulousBtn.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.empty_star));
                }*/


        }
    });

    tvTitle.setText(poster.getTitle());
    tvBody.setText(poster.getAbs() + poster.getAbs() + poster.getAbs());
    tvAuthor.setText(poster.getAuthors()[0]);
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

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }


    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.details_toolbar_poster);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" ");

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout_poster);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_poster);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(poster.getTitle());
                    //collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(mContext, android.R.color.transparent));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}
