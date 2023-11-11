package com.ddm.playwire.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ddm.playwire.R;
import com.ddm.playwire.ui.activity.MenuActivity;
import com.ddm.playwire.model.Review;
import com.ddm.playwire.viewmodel.ReviewViewModel;

public class ReviewFormFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private View rootView;
    private EditText etGameTitle, etReviewDescription;
    private Spinner spFeedback;
    private Button btnRegisterReview;
    private String feedback;
    private ReviewViewModel reviewViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reviewViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(ReviewViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_review_form, container, false);

        spFeedback = rootView.findViewById(R.id.spFeedback);
        @SuppressLint("ResourceType") ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.game_review_score, R.drawable.spinner_item_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFeedback.setAdapter(adapter);
        spFeedback.setOnItemSelectedListener(this);

        etGameTitle = rootView.findViewById(R.id.etGameTitle);
        etReviewDescription = rootView.findViewById(R.id.etReviewDescription);
        btnRegisterReview = rootView.findViewById(R.id.btnRegisterReview);

        btnRegisterReview.setOnClickListener(view -> insertReview());

        return rootView;
    }

    private void insertReview() {
        if(validateFormFields()) {
            reviewViewModel.insertReview(new Review(etGameTitle.getText().toString(), etReviewDescription.getText().toString(), feedback, ((MenuActivity) getActivity()).getSessionUser()));
            navigateToFeedFragment();
        }
    }

    private boolean validateFormFields() {
        if(etGameTitle.getText().toString().isEmpty()) {
            Toast.makeText(requireActivity(), "É necessário preencher o campo Nome do Jogo!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(etReviewDescription.getText().toString().isEmpty()) {
            Toast.makeText(requireActivity(), "É necessário preencher o campo Descrição da Análise!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        feedback = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    private void navigateToFeedFragment(){
        ((MenuActivity) getActivity()).replaceFragment(new FeedFragment());
    }
}