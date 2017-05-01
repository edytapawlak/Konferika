package com.app.android.konferika.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.android.konferika.R;
import com.app.android.konferika.activities.PosterDetailsActivity;
import com.app.android.konferika.obj.Poster;

import java.util.List;
/*
Adapter dla GridRecyclerView
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {//implements View.OnClickListener {

    public void setItems(List<Poster> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    private Cursor it;
    private List<Poster> items;
    private OnItemClickListener onItemClickListener;

    public RecyclerViewAdapter() {
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_recycler_item, parent, false);
//        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        it.moveToPosition(position);
        holder.text.setText(it.getString(1));
        holder.author.setText(it.getString(2));
//        holder.tags.setText("tag -- napraw to"); // Plakaty nie mają tagów
//        holder.image.setImageBitmap(null);
        holder.ratingBar.setRating(it.getFloat(3));
        holder.idTv.setText(it.getInt(0) + "");

    }

    @Override public int getItemCount() {
        if(it == null){
            return -1;
        }
        return it.getCount();
    }

//    @Override public void onClick(final View v) {
//        onItemClickListener.onItemClick(v, (Poster) v.getTag());
//    }

    public void swapCursor(Cursor newCursor) {
        it = newCursor;
        // After the new Cursor is set, call notifyDataSetChanged
        notifyDataSetChanged();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView image;
        public TextView text;
        public TextView author;
        public TextView tags;
        public RatingBar ratingBar;
        public TextView idTv;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            image = (ImageView) itemView.findViewById(R.id.image);
            text = (TextView) itemView.findViewById(R.id.text);
            author = (TextView) itemView.findViewById(R.id.poster_author);
            tags = (TextView) itemView.findViewById(R.id.poster_tags_text_view);
            ratingBar = (RatingBar) itemView.findViewById(R.id.poster_list_rating_bar);
            idTv = (TextView) itemView.findViewById(R.id.poster_id_text_view);
        }

        @Override
        public void onClick(View v) {
            String text = idTv.getText().toString();
            if (text != "") {
                Context context = v.getContext();
                    int id = Integer.parseInt(text);
                    Intent intent = new Intent(context, PosterDetailsActivity.class);
                    intent.putExtra("posterId", id);
                    context.startActivity(intent);
                }
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, Poster viewModel);

    }
}
