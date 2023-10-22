package com.ddm.playwire.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ddm.playwire.R;

import java.util.List;

public class ReviewProfileRankAdapter extends BaseAdapter {

    private Context context;
    private List<String[]> reviewRank;

    public ReviewProfileRankAdapter(Context context, List<String[]> reviewRank) {
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
        View item = inflater.inflate(R.layout.review_rank_profile_item, viewGroup, false);

        String[] reviewData = reviewRank.get(i);
        String gameTitle = reviewData[0];
        TextView tvGameTitleRank = item.findViewById(R.id.tvGameTitleRank);

        //Realizando a alternância das cores da tabela
        int[] colors = {Color.parseColor("#36373A"), Color.parseColor("#141517")};

        TableRow tbReviewItemRow = item.findViewById(R.id.tbReviewItemRow);
        tbReviewItemRow.setBackgroundColor(colors[i % colors.length]);

        tvGameTitleRank.setText(gameTitle);

        return item;
    }
}
