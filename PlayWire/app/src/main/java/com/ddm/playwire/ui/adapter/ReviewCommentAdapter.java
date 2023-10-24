package com.ddm.playwire.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ddm.playwire.R;
import com.ddm.playwire.model.Review;
import com.ddm.playwire.model.ReviewComment;

import java.util.List;

public class ReviewCommentAdapter extends BaseAdapter {

    private Context context;
    private List<ReviewComment> reviewComments;

    public ReviewCommentAdapter(Context context, List<ReviewComment> reviewComments) {
        this.context = context;
        this.reviewComments = reviewComments;
    }

    @Override
    public int getCount() {
        return reviewComments.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return reviewComments.get(position).getReviewCommentId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.review_comment_item, viewGroup, false);
        ReviewComment reviewComment = reviewComments.get(position);

        TextView tvUsernameComment = item.findViewById(R.id.tvUsernameComment);
        TextView tvComment = item.findViewById(R.id.tvComment);

        tvUsernameComment.setText(reviewComment.getUser().getUsername());
        tvComment.setText(reviewComment.getComment());

        return item;
    }
}
