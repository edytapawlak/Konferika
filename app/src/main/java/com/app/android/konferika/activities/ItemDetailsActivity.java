package com.app.android.konferika.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.app.android.konferika.R;
import com.app.android.konferika.obj.Activity;
import com.app.android.konferika.obj.Lecture;

public class ItemDetailsActivity extends AppCompatActivity {
    ItemDetailsFragment fragmentItemDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        Lecture item = (Lecture) getIntent().getSerializableExtra("lect");
        if(item == null){
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
}
