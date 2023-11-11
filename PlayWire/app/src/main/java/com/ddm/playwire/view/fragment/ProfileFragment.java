package com.ddm.playwire.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ddm.playwire.R;
import com.ddm.playwire.model.User;
import com.ddm.playwire.view.activity.MenuActivity;
import com.ddm.playwire.view.adapter.ReviewProfileRankAdapter;
import com.ddm.playwire.viewmodel.ReviewViewModel;

import java.util.List;

public class ProfileFragment extends Fragment {

    private View rootView;
    private TextView tvUsernameProfile;
    private TextView tvUserCountReview;
    private ListView lvFavouriteGame;
    private ListView lvUnfavouriteGame;
    private ReviewViewModel reviewViewModel;

    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reviewViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(ReviewViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        user = ((MenuActivity) getActivity()).getSessionUser();
        tvUsernameProfile = rootView.findViewById(R.id.tvUsernameProfile);
        tvUsernameProfile.setText(user.getUsername());

        lvUnfavouriteGame = rootView.findViewById(R.id.lvUnfavouriteGame);
        lvFavouriteGame = rootView.findViewById(R.id.lvFavouriteGame);
        tvUserCountReview = rootView.findViewById(R.id.tvUserCountReview);

        listProfileReviews();

        return rootView;
    }


    private void listProfileReviews() {
        reviewViewModel.getFavouriteGameLive(user.getUserId()).observe(requireActivity(), favouriteGames -> setFavouriteReviewAdapter(favouriteGames));
        reviewViewModel.getUnfavouriteGameLive(user.getUserId()).observe(requireActivity(), unfavouriteGames -> setUnfavouriteReviewAdapter(unfavouriteGames));
        reviewViewModel.getCountReviewByUserLive(user.getUserId()).observe(requireActivity(), countReview -> {
            tvUserCountReview.setText(countReview + " Análise(s)");
        });
    }

    private void setFavouriteReviewAdapter(List<String[]> favouriteGames) {
        ReviewProfileRankAdapter reviewRankAdapterFav = new ReviewProfileRankAdapter(getContext(), favouriteGames);
        lvFavouriteGame.setAdapter(reviewRankAdapterFav);
    }

    private void setUnfavouriteReviewAdapter(List<String[]> unfavouriteGames) {
        ReviewProfileRankAdapter reviewRankAdapterUnfav = new ReviewProfileRankAdapter(getContext(), unfavouriteGames);
        lvUnfavouriteGame.setAdapter(reviewRankAdapterUnfav);
    }
}