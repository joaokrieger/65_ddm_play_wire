package com.ddm.playwire.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ddm.playwire.R;
import com.ddm.playwire.dao.ReviewDao;
import com.ddm.playwire.model.User;
import com.ddm.playwire.ui.activity.MenuActivity;
import com.ddm.playwire.ui.adapter.ReviewProfileRankAdapter;

import java.util.List;

public class ProfileFragment extends Fragment {

    private View rootView;
    private TextView tvUsernameProfile;
    private TextView tvUserCountReview;
    private ReviewDao reviewDao;
    private ListView lvFavouriteGame;
    private ListView lvUnfavouriteGame;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MenuActivity activity = (MenuActivity) getActivity();
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        User user = activity.getSessionUser();
        tvUsernameProfile = rootView.findViewById(R.id.tvUsernameProfile);
        tvUsernameProfile.setText(user.getUsername());

        this.displayData(user.getUserId());

        return rootView;
    }


    private void displayData(int userId) {

        reviewDao = new ReviewDao(getContext());

        lvFavouriteGame = rootView.findViewById(R.id.lvFavouriteGame);
        List<String[]> favoriteReviews = reviewDao.listRankByUser(userId, "DESC");
        ReviewProfileRankAdapter reviewRankAdapterFav = new ReviewProfileRankAdapter(getContext(), favoriteReviews);
        lvFavouriteGame.setAdapter(reviewRankAdapterFav);

        lvUnfavouriteGame = rootView.findViewById(R.id.lvUnfavouriteGame);
        List<String[]> unfavoriteReviews = reviewDao.listRankByUser(userId, "ASC");
        ReviewProfileRankAdapter reviewRankAdapterUnfav = new ReviewProfileRankAdapter(getContext(), unfavoriteReviews);
        lvUnfavouriteGame.setAdapter(reviewRankAdapterUnfav);

        tvUserCountReview = rootView.findViewById(R.id.tvUserCountReview);
        int countReview = reviewDao.getCountReviewByUser(userId);
        tvUserCountReview.setText(countReview + " Análise(s)");
    }
}