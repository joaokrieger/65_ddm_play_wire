package com.ddm.playwire.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ddm.playwire.R;
import com.ddm.playwire.model.Review;

import java.util.List;

public class ReviewRankAdapter extends BaseAdapter {

    private Context context;
    private List<Review> reviews;

    public ReviewRankAdapter(Context context, List<Review> reviews) {
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
        return reviews.get(position).getReviewId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.review_rank_item, viewGroup, false);
        Review review = reviews.get(position);

        TextView tvGameTitleRank = item.findViewById(R.id.tvGameTitleRank);
        TextView tvReviewCount = item.findViewById(R.id.tvReviewCount);
        TextView tvFeedbackRank = item.findViewById(R.id.tvFeedbackRank);

        return item;
    }
}
