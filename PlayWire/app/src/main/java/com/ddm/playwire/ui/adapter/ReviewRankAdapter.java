package com.ddm.playwire.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ddm.playwire.R;

import java.util.List;

public class ReviewRankAdapter extends BaseAdapter {

    private Context context;
    private List<String[]> reviewRank;

    public ReviewRankAdapter(Context context, List<String[]> reviewRank) {
        this.context = context;
        this.reviewRank = reviewRank;
    }

    @Override
    public int getCount() {
        return reviewRank.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewRank.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.review_rank_item, viewGroup, false);

        String[] reviewData = reviewRank.get(i);
        String gameTitle = reviewData[0];
        String reviewCount= reviewData[1];
        String feedbackAvg = reviewData[2];

        TextView tvGameTitleRank = item.findViewById(R.id.tvUsernameComment);
        TextView tvReviewCount = item.findViewById(R.id.tvComment);
        TextView tvRankPosition = item.findViewById(R.id.tvRankPosition);

        tvGameTitleRank.setText(gameTitle);
        tvReviewCount.setText(reviewCount + " Análise(s)");
        tvRankPosition.setText(getFeedback(feedbackAvg));

        return item;
    }

    private String getFeedback(String feedbackAvg) {

        String feedback = "";

        switch (feedbackAvg){
            case "1":
                feedback = "Muito Insatisfatório";
                break;
            case "2":
                feedback = "Insatisfatório";
                break;
            case "3":
                feedback = "Regular";
                break;
            case "4":
                feedback = "Bom";
                break;
            default:
                feedback = "Excelente";
        }
        return feedback;
    }
}
