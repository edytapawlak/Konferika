package com.app.android.konferika.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.app.android.konferika.R;
import com.app.android.konferika.adapters.RecyclerViewAdapter;
import com.app.android.konferika.obj.Poster;
import com.app.android.konferika.obj.PosterSesion;

import java.util.List;

public class PosterActivity extends BaseActivity implements RecyclerViewAdapter.OnItemClickListener {

    private static Context mContext;
    private List<Poster> items;
    private RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_poster);
        mActivity = this;

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_poster, null, false);
        super.drawerLayout.addView(contentView, 0);

        mContext = this;
        items = PosterSesion.getPosterList(this);

        initRecyclerView();
        initToolbar();

        // if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        setRecyclerAdapter(recyclerView);
        // }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        items = PosterSesion.getPosterList(this);
        adapter.setItems(items);

    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.poster_recycled_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    private void setRecyclerAdapter(RecyclerView recyclerView) {
        adapter = new RecyclerViewAdapter(items);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, Poster viewModel) {
        Intent intent = new Intent(PosterActivity.this, PosterDetailsActivity.class);
        intent.putExtra("poster", viewModel);
        startActivity(intent);
    }
}

