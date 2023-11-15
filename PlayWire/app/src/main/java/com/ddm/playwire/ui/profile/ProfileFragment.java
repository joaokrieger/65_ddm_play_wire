package com.ddm.playwire.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ddm.playwire.R;
import com.ddm.playwire.dao.ReviewDao;
import com.ddm.playwire.dao.UserDao;
import com.ddm.playwire.model.User;
import com.ddm.playwire.repository.ReviewRepository;
import com.ddm.playwire.repository.UserRepository;
import com.ddm.playwire.ui.adapter.ReviewProfileRankAdapter;
import com.ddm.playwire.viewmodel.review.ReviewViewModel;
import com.ddm.playwire.viewmodel.review.ReviewViewModelFactory;
import com.ddm.playwire.viewmodel.user.UserViewModel;
import com.ddm.playwire.viewmodel.user.UserViewModelFactory;

import java.util.List;

public class ProfileFragment extends Fragment {

    private View rootView;
    private ReviewViewModel reviewViewModel;
    private UserViewModel userViewModel;
    private ReviewProfileRankAdapter reviewRankAdapterFav, reviewRankAdapterUnfav;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ReviewRepository reviewRepository = new ReviewDao(getActivity().getApplicationContext());
        reviewViewModel = new ViewModelProvider(this, new ReviewViewModelFactory(reviewRepository)).get(ReviewViewModel.class);

        UserRepository userRepository = new UserDao(getActivity().getApplicationContext());
        userViewModel = new ViewModelProvider(this, new UserViewModelFactory(userRepository)).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        userViewModel.getCurrentUserLiveData().observe(getActivity(), user -> this.user = user);
        reviewViewModel.getFavouriteReviewsLive(user).observe(getActivity(), favoriteReviews -> updateFavoriteListView(favoriteReviews));
        reviewViewModel.getUnfavouriteReviewsLive(user).observe(getActivity(), unfavoriteReviews -> updateUnfavoriteListView(unfavoriteReviews));
        reviewViewModel.getCountReviewByUserLive(user).observe(getActivity(), countReviewUser -> updateUi(countReviewUser));

        initComponents();

        return rootView;
    }

    private void updateUi(Integer countReviewUser) {
        TextView tvUserCountReview = rootView.findViewById(R.id.tvUserCountReview);
        tvUserCountReview.setText(countReviewUser + " Análise(s)");
    }

    private void initComponents() {
        TextView tvUsernameProfile = rootView.findViewById(R.id.tvUsernameProfile);
        tvUsernameProfile.setText(user.getUsername());
    }

    private void updateUnfavoriteListView(List<String[]> unfavoriteReviews) {
        if(reviewRankAdapterUnfav == null)
            setUnfavouriteReviewAdapter(unfavoriteReviews);
        reviewRankAdapterUnfav.getReviewRank().clear();
        reviewRankAdapterUnfav.getReviewRank().addAll(unfavoriteReviews);
        reviewRankAdapterUnfav.notifyDataSetChanged();
    }

    private void updateFavoriteListView(List<String[]> favoriteReviews) {
        if(reviewRankAdapterFav == null)
            setFavouriteReviewAdapter(favoriteReviews);
        reviewRankAdapterFav.getReviewRank().clear();
        reviewRankAdapterFav.getReviewRank().addAll(favoriteReviews);
        reviewRankAdapterFav.notifyDataSetChanged();
    }

    private void setFavouriteReviewAdapter(List<String[]> favouriteGames) {
        ListView lvFavouriteGame = rootView.findViewById(R.id.lvFavouriteGame);
        reviewRankAdapterFav = new ReviewProfileRankAdapter(getContext(), favouriteGames);
        lvFavouriteGame.setAdapter(reviewRankAdapterFav);
    }

    private void setUnfavouriteReviewAdapter(List<String[]> unfavouriteGames) {
        ListView lvUnfavouriteGame = rootView.findViewById(R.id.lvUnfavouriteGame);
        reviewRankAdapterUnfav = new ReviewProfileRankAdapter(getContext(), unfavouriteGames);
        lvUnfavouriteGame.setAdapter(reviewRankAdapterUnfav);
    }
}