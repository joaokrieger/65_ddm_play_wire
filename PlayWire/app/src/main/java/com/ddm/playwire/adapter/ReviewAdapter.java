package com.ddm.playwire.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ddm.playwire.R;
import com.ddm.playwire.models.Review;

import java.util.List;

public class ReviewAdapter extends BaseAdapter {

    private Context context;
    private List<Review> reviews;

    public ReviewAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return reviews.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.review_feed_item, viewGroup, false);
        Review review = reviews.get(position);

        TextView tvGameTitle = item.findViewById(R.id.tvGameTitle);
        TextView tvUserName = item.findViewById(R.id.tvUserName);
        TextView tvReviewDescription = item.findViewById(R.id.tvReviewDescription);
        TextView tvFeedback = item.findViewById(R.id.tvFeedback);

        tvGameTitle.setText(review.getGameTitle());
        tvUserName.setText("Steve");
        tvReviewDescription.setText(review.getReviewDescription());
        tvFeedback.setText(review.getFeedback());

        return item;
    }
}
