package com.ddm.playwire.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ddm.playwire.R;
import com.ddm.playwire.ui.activities.MenuActivity;
import com.ddm.playwire.dao.ReviewDao;
import com.ddm.playwire.model.Review;

public class ReviewFormFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private EditText etGameTitle, etReviewDescription;
    private Spinner spFeedback;
    private Button btnRegisterReview;
    private String feedback;

    public static ReviewFormFragment newInstance() {
        ReviewFormFragment fragment = new ReviewFormFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_review_form, container, false);
        MenuActivity activity = (MenuActivity) getActivity();

        spFeedback = rootView.findViewById(R.id.spFeedback);
        @SuppressLint("ResourceType") ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.game_review_score, R.drawable.spinner_item_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFeedback.setAdapter(adapter);
        spFeedback.setOnItemSelectedListener(this);

        etGameTitle = rootView.findViewById(R.id.etGameTitle);
        etReviewDescription = rootView.findViewById(R.id.etReviewDescription);
        btnRegisterReview = rootView.findViewById(R.id.btnRegisterReview);

        btnRegisterReview.setOnClickListener(view -> {
            ReviewDao reviewDao = new ReviewDao(getContext());

            reviewDao.insert(new Review(
                    etGameTitle.getText().toString(),
                    etReviewDescription.getText().toString(),
                    feedback,
                    activity.getSessionUser()));

            activity.replaceFragment(new FeedFragment());
        });

        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        feedback = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}
}