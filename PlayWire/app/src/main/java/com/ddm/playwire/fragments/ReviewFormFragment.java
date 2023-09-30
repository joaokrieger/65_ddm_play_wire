package com.ddm.playwire.fragments;

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
import com.ddm.playwire.activities.MenuActivity;
import com.ddm.playwire.data.ReviewDatabaseHelper;
import com.ddm.playwire.models.Review;
import com.ddm.playwire.models.User;

public class ReviewFormFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText etGameTitle, etReviewDescription;
    Spinner spFeedback;
    Button btnRegisterReview;
    String feedback;

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

        spFeedback = rootView.findViewById(R.id.spFeedback);
        @SuppressLint("ResourceType") ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.game_review_score, R.drawable.spinner_item_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFeedback.setAdapter(adapter);
        spFeedback.setOnItemSelectedListener(this);

        etGameTitle = rootView.findViewById(R.id.etGameTitle);
        etReviewDescription = rootView.findViewById(R.id.etReviewDescription);
        btnRegisterReview = rootView.findViewById(R.id.btnRegisterReview);

        btnRegisterReview.setOnClickListener(view -> {
            ReviewDatabaseHelper reviewDatabaseHelper = new ReviewDatabaseHelper(getContext());

            reviewDatabaseHelper.insertReview(new Review(
                    etGameTitle.getText().toString(),
                    etReviewDescription.getText().toString(),
                    feedback,
                    new User(1,"jek","123")));

            MenuActivity activity = (MenuActivity) getActivity();
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