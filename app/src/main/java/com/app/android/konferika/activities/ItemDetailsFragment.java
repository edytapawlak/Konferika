package com.app.android.konferika.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.android.konferika.R;
import com.app.android.konferika.obj.Activity;
import com.app.android.konferika.obj.Lecture;

public class ItemDetailsFragment extends android.support.v4.app.Fragment {

    private Lecture lecture;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lecture = (Lecture) getArguments().getSerializable("item");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate view
        View view = inflater.inflate(R.layout.fragment_item_detail,
                container, false);

/*        TextView tvTitle = (TextView) view.findViewById(R.id.tv_details_title);
        TextView tvBody = (TextView) view.findViewById(R.id.tv_details_description);
        tvTitle.setText(lecture.getAuthor());
        tvBody.setText(lecture.getTitle());
  */      // Return view
        return view;
    }


    public static ItemDetailsFragment newInstance(Lecture item) {
        ItemDetailsFragment fragmentDemo = new ItemDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("item", item);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }
}
