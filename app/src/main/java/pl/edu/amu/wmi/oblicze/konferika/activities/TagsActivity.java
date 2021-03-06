package pl.edu.amu.wmi.oblicze.konferika.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import pl.edu.amu.wmi.oblicze.konferika.data.DatabaseContract;
import pl.edu.amu.wmi.oblicze.konferika.obj.Tag;
import pl.edu.amu.wmi.oblicze.konferika.R;

public class TagsActivity extends BaseActivity {
    private ScrollView scrollView;
    private LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_tags, null, false);
        super.navigationView.setCheckedItem(R.id.drawer_tags);

        super.lay.addView(contentView, 0);
        initToolbar();

        final List<Tag> tags = getTagLis();
        linear = (LinearLayout) findViewById(R.id.tags_linear_layout);
        scrollView = (ScrollView) findViewById(R.id.scroll_tags);
        Button btn1;
        for (Tag t :
                tags) {


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(70, 2, 70, 2);
            Button btn = new Button(this);
            btn.setId(t.getId());
            final int id_ = btn.getId();
            btn.setText("#" + t.getTitle());
            btn.setMinHeight(20);
            btn.setPadding(10, 1, 10, 1);
            btn.setTransformationMethod(null);
            btn.setTextColor(ContextCompat.getColor(mActivity, R.color.blu));
            btn.setBackgroundColor(Color.argb(100, 255, 255, 255));

            linear.addView(btn, params);
            btn1 = ((Button) findViewById(id_));
            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
//                    Toast.makeText(view.getContext(),
//                            "Button clicked index = " + id_, Toast.LENGTH_SHORT)
//                            .show();
                    Intent i = new Intent(TagsActivity.this, TagDataActivity.class);
                    i.putExtra("tagId", id_);
                    String tagTitle = findTagTitleById(tags, id_);
                    i.putExtra("tagTitle", tagTitle);
                    startActivity(i);
                }
            });
        }
        scrollView.invalidate();
        linear.invalidate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        super.navigationView.setCheckedItem(R.id.drawer_tags);
    }

    private String findTagTitleById(List<Tag> arr, int id) {
        for (Tag t :
                arr) {
            if (t.getId() == id) {
                return t.getTitle();
            }
        }
        return "";
    }


    private List<Tag> getTagLis() {
        String[] projection = {DatabaseContract.TagsEntry.COLUMN_ID, DatabaseContract.TagsEntry.COLUMN_TITLE};
        Cursor tagsCursor = this.getContentResolver().query(DatabaseContract.TagsEntry.CONTENT_URI, projection, null, null, null);
        tagsCursor.moveToFirst();
        List<Tag> tagList = new ArrayList<>();
        Tag tag;
        while (!tagsCursor.isAfterLast()) {
            tag = new Tag(tagsCursor.getString(1), tagsCursor.getInt(0));
            tagList.add(tag);
            tagsCursor.moveToNext();
        }
        tagsCursor.close();
        return tagList;
    }

}
