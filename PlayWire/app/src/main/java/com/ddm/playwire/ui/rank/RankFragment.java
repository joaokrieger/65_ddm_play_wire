package com.ddm.playwire.ui.rank;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ddm.playwire.R;
import com.ddm.playwire.data.dao.ReviewDao;
import com.ddm.playwire.repository.ReviewRepository;
import com.ddm.playwire.ui.adapter.ReviewRankAdapter;
import com.ddm.playwire.viewmodel.review.ReviewViewModel;
import com.ddm.playwire.viewmodel.review.ReviewViewModelFactory;

import java.util.List;

public class RankFragment extends Fragment {

    private View rootView;
    private ReviewViewModel reviewViewModel;
    private ReviewRankAdapter reviewRankAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReviewRepository reviewRepository = new ReviewDao(getActivity().getApplicationContext());
        reviewViewModel = new ViewModelProvider(this, new ReviewViewModelFactory(reviewRepository)).get(ReviewViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_rank, container, false);
        reviewViewModel.getGameRankLive().observe(getActivity(), reviews -> updateListView(reviews));
        return rootView;
    }

    private void updateListView(List<String[]> rankGames) {
        if(reviewRankAdapter == null) {
            setReviewRankAdapter(rankGames);
        }
        else {
            reviewRankAdapter.getReviewRank().clear();
            reviewRankAdapter.getReviewRank().addAll(rankGames);
            reviewRankAdapter.notifyDataSetChanged();
        }
    }

    private void setReviewRankAdapter(List<String[]> rankGames) {
        ListView lvRank = rootView.findViewById(R.id.lvRank);
        reviewRankAdapter = new ReviewRankAdapter(getContext(), rankGames);
        lvRank.setAdapter(reviewRankAdapter);
    }
}