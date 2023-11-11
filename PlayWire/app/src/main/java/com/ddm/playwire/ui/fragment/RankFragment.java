package com.ddm.playwire.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ddm.playwire.R;
import com.ddm.playwire.dao.ReviewDao;
import com.ddm.playwire.model.Review;
import com.ddm.playwire.ui.adapter.ReviewRankAdapter;
import com.ddm.playwire.viewmodel.ReviewViewModel;

import java.util.List;

public class RankFragment extends Fragment {

    private View rootView;
    private ListView lvRank;
    private ReviewViewModel reviewViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reviewViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(ReviewViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_rank, container, false);
        lvRank = rootView.findViewById(R.id.lvRank);

        listRankReview();

        return rootView;
    }

    private void listRankReview() {
        reviewViewModel.getGameRankLive().observe(requireActivity(), rankGames -> setReviewRankAdapter(rankGames));
    }

    private void setReviewRankAdapter(List<String[]> rankGames) {
        ReviewRankAdapter reviewRankAdapter = new ReviewRankAdapter(getContext(), rankGames);
        lvRank.setAdapter(reviewRankAdapter);
    }
}