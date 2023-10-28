package com.ddm.playwire.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ddm.playwire.R;
import com.ddm.playwire.dao.ReviewDao;
import com.ddm.playwire.ui.adapter.ReviewRankAdapter;

import java.util.List;

public class RankFragment extends Fragment {

    private View rootView;
    private ListView lvRank;
    private ReviewDao reviewDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_rank, container, false);
        this.displayData();

        return rootView;
    }

    private void displayData() {

        lvRank = rootView.findViewById(R.id.lvRank);
        reviewDao = new ReviewDao(getContext());
        List<String[]> reviews = reviewDao.listRank();

        ReviewRankAdapter reviewRankAdapter = new ReviewRankAdapter(getContext(), reviews);
        lvRank.setAdapter(reviewRankAdapter);
    }
}