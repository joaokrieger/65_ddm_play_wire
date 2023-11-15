package com.ddm.playwire.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ddm.playwire.R;
import com.ddm.playwire.model.Review;

import java.util.List;

public class ReviewFeedAdapter extends BaseAdapter {

    private Context context;
    private List<Review> reviews;

    public ReviewFeedAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    public List<Review> getReviews() {
        return reviews;
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
        return reviews.get(position).getReviewId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.review_feed_item, viewGroup, false);
        Review review = reviews.get(position);

        TextView tvGameTitle = item.findViewById(R.id.tvUsernameComment);
        TextView tvUserName = item.findViewById(R.id.tvUserName);
        TextView tvReviewDescription = item.findViewById(R.id.tvComment);
        TextView tvFeedback = item.findViewById(R.id.tvRankPosition);

        tvGameTitle.setText(review.getGameTitle());
        tvUserName.setText(review.getUser().getUsername());
        tvReviewDescription.setText(review.getReviewDescription());
        tvFeedback.setText(review.getFeedback());

        return item;
    }
}
