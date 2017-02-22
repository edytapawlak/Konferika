package com.app.android.konferika.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.android.konferika.R;
import com.app.android.konferika.obj.Poster;

import java.util.List;
/*
Adapter dla GridRecyclerView
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    public void setItems(List<Poster> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    private List<Poster> items;
    private OnItemClickListener onItemClickListener;

    public RecyclerViewAdapter(List<Poster> items) {
        this.items = items;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_recycler_item, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        Poster item = items.get(position);
        holder.text.setText(item.getTitle());
        holder.author.setText(item.getAuthors()[0]);
//        holder.image.setImageBitmap(null);
        holder.ratingBar.setRating(item.getMark());
        holder.itemView.setTag(item);

    }

    @Override public int getItemCount() {
        return items.size();
    }

    @Override public void onClick(final View v) {
        onItemClickListener.onItemClick(v, (Poster) v.getTag());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView text;
        public TextView author;
        public RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            text = (TextView) itemView.findViewById(R.id.text);
            author = (TextView) itemView.findViewById(R.id.poster_author);
            ratingBar = (RatingBar) itemView.findViewById(R.id.poster_list_rating_bar);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, Poster viewModel);

    }
}
