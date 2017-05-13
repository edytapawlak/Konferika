package pl.edu.amu.wmi.oblicze.konferika.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.github.kexanie.library.MathView;
import pl.edu.amu.wmi.oblicze.konferika.R;
import pl.edu.amu.wmi.oblicze.konferika.data.DatabaseContract;
import pl.edu.amu.wmi.oblicze.konferika.obj.Poster;
import pl.edu.amu.wmi.oblicze.konferika.obj.Tag;

public class PosterDetailsActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Context mContext;
    private Poster poster;
    private RatingBar posterRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_details);

        mContext = this;
//        poster = (Poster) getIntent().getSerializableExtra("poster");
        final int posterId = getIntent().getIntExtra("posterId", -1);
        String[] projection = {
                DatabaseContract.PostersEntry.COLUMN_TITLE,
                DatabaseContract.PostersEntry.COLUMN_AUTHOR,
                DatabaseContract.PostersEntry.COLUMN_ABSTRACT,
                DatabaseContract.PostersEntry.COLUMN_MARK
        };
        Cursor posterCur = mContext.getContentResolver().query(DatabaseContract.PostersEntry.buildPostersUriWithDate(posterId), projection, null, null, null);
        posterCur.moveToFirst();
        while (!posterCur.isAfterLast()){
            String title = posterCur.getString(0);
            String author = posterCur.getString(1);
            String authors[] = new String[1];
            authors[0] = author; //TODO
            String abtract = posterCur.getString(2);
            float mark = posterCur.getFloat(3);

            poster = new Poster(posterId, title, authors, abtract, new ArrayList<Tag>(), mark);
            posterCur.moveToNext();
        }
        posterCur.close();

        initToolbar();
        if (poster == null) {
            Toast.makeText(this, "Jest null,sorry", Toast.LENGTH_SHORT).show();
        }

        TextView tvTitle = (TextView) findViewById(R.id.tv_poster_title);
        MathView tvBody = (MathView) findViewById(R.id.tv_poster_description);
        TextView tvAuthor = (TextView) findViewById(R.id.tv_poster_author);
        posterRatingBar = (RatingBar) findViewById(R.id.poster_details_rating_bar);

        posterRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                PosterSesion.setMarkOnPos(mContext, poster.getId(), rating);
                ContentValues cv = new ContentValues();
                cv.put(DatabaseContract.PostersEntry.COLUMN_MARK, rating);
                mContext.getContentResolver().update(DatabaseContract.PostersEntry.buildPostersUriWithDate(posterId), cv, null, null);
            }
        });

        tvTitle.setText(poster.getTitle());
        tvBody.setText(poster.getAbs());
        tvAuthor.setText(poster.getAuthors()[0]);
        posterRatingBar.setRating(poster.getMark());

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
