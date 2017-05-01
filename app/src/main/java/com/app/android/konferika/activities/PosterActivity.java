package com.app.android.konferika.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.app.android.konferika.R;
import com.app.android.konferika.adapters.RecyclerViewAdapter;
import com.app.android.konferika.data.DatabaseContract;
import com.app.android.konferika.obj.Poster;

public class PosterActivity extends BaseActivity implements RecyclerViewAdapter.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    //private static Context mContext;
    Cursor mCursorPosters;
    private RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    private static final int ID_POSTERS_LOADER = 44;

    public static final String[] MAIN_FORECAST_PROJECTION = {
            DatabaseContract.PostersEntry.COLUMN_ID,
            DatabaseContract.PostersEntry.COLUMN_TITLE,
            DatabaseContract.PostersEntry.COLUMN_AUTHOR,
            DatabaseContract.PostersEntry.COLUMN_MARK
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = this;
        super.navigationView.setCheckedItem(R.id.drawer_posters);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_poster, null, false);
        super.drawerLayout.addView(contentView, 0);

       // mContext = this;

        getSupportLoaderManager().initLoader(ID_POSTERS_LOADER, null, this);
        initRecyclerView();
        initToolbar();

        setRecyclerAdapter(recyclerView);
    }


    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.poster_recycled_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    private void setRecyclerAdapter(RecyclerView recyclerView) {
        adapter = new RecyclerViewAdapter();
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, Poster viewModel) {
        Intent intent = new Intent(PosterActivity.this, PosterDetailsActivity.class);
        intent.putExtra("poster", viewModel);
//        intent.putExtra("posterId", )
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        switch (loaderId) {

            case ID_POSTERS_LOADER:
                Uri forecastQueryUri = DatabaseContract.PostersEntry.CONTENT_URI;
                String sortOrder = DatabaseContract.PostersEntry.COLUMN_MARK + " DESC";
                String selection = null;

                return new CursorLoader(this,
                        forecastQueryUri,
                        MAIN_FORECAST_PROJECTION,
                        selection,
                        null,
                        sortOrder);

            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}

