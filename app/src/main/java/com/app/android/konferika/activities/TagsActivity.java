package com.app.android.konferika.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.app.android.konferika.R;
import com.app.android.konferika.data.ActivityData;
import com.app.android.konferika.obj.Tag;

import java.util.List;

public class TagsActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_tags, null, false);
        super.navigationView.setCheckedItem(R.id.drawer_tags);

        super.lay.addView(contentView, 0);
        initToolbar();

        List<Tag> tags = ActivityData.getTagArray(this);
        LinearLayout linear = (LinearLayout) findViewById(R.id.tags_linear_layout);
        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_tags);
        Button btn1;
        for (Tag t :
                tags) {


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(70,2,70,2);
            Button btn = new Button(this);
            btn.setId(t.getId());
            final int id_ = btn.getId();
            btn.setText(t.getTitle());
            btn.setMinHeight(20);
            btn.setPadding(10,1,10,1);
            btn.setTransformationMethod(null);
            btn.setTextColor(ContextCompat.getColor(mActivity, R.color.blu));
            btn.setBackgroundColor(Color.argb(100, 255, 255, 255));
            int color1 = (int) (Math.random() * 127);
            int color2 = (int) (Math.random() * 127) + 127;
            int color3 = (int) (Math.random() * 225);
//            if (Math.random() > 0.5) {
//                btn.setBackgroundColor(Color.argb(60, 0, color2, 0));
//            } else {
//                btn.setBackgroundColor(Color.argb(60, color2, 0, 0));
//            }
            linear.addView(btn, params);
            btn1 = ((Button) findViewById(id_));
            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),
                            "Button clicked index = " + id_, Toast.LENGTH_SHORT)
                            .show();
                    Intent i = new Intent(TagsActivity.this, TagDataActivity.class);
                    i.putExtra("tagId", id_);
                    startActivity(i);
                }
            });
        }
    }
}
