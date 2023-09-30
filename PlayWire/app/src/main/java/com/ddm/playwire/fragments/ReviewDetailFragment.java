package com.ddm.playwire.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ddm.playwire.R;

public class ReviewDetailFragment extends Fragment {

    private int reviewId;

    public static ReviewDetailFragment newInstance(int reviewId) {
        ReviewDetailFragment fragment = new ReviewDetailFragment();
        reviewId = reviewId;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review_detail, container, false);
    }
}