package com.ddm.playwire.ui.review;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ddm.playwire.R;
import com.ddm.playwire.ui.main.MenuActivity;
import com.ddm.playwire.viewmodel.game.GameViewModel;
import com.ddm.playwire.viewmodel.review.ReviewViewModel;

public class ReviewFormFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private View rootView;
    private EditText etReviewDescription;
    private AutoCompleteTextView acGameTitle;
    private Spinner spFeedback;
    private Button btnRegisterReview;
    private String feedback;
    private ReviewViewModel reviewViewModel;
    private GameViewModel gameViewModel;

    private ArrayAdapter<String> autoCompleteAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reviewViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(ReviewViewModel.class);
        gameViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(GameViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_review_form, container, false);

        @SuppressLint("ResourceType")
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.game_review_score, R.drawable.spinner_item_layout);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFeedback = rootView.findViewById(R.id.spFeedback);
        spFeedback.setAdapter(spinnerAdapter);
        spFeedback.setOnItemSelectedListener(this);

        etReviewDescription = rootView.findViewById(R.id.etReviewDescription);
        btnRegisterReview = rootView.findViewById(R.id.btnRegisterReview);

        acGameTitle = rootView.findViewById(R.id.acGameTitle);

        gameViewModel.getAllGames().observe(requireActivity(), gameNames -> {
            if (gameNames != null) {
                autoCompleteAdapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, gameNames);
                acGameTitle.setAdapter(autoCompleteAdapter);
            }
        });

        btnRegisterReview.setOnClickListener(view -> insertReview());

        return rootView;
    }

    private void insertReview() {
        /*
        if(validateFormFields()) {
            reviewViewModel.insertReview(new Review(acGameTitle.getText().toString(), etReviewDescription.getText().toString(), feedback, ((MenuActivity) getActivity()).getSessionUser()));
            navigateToFeedFragment();
        }

         */
    }

    private boolean validateFormFields() {
        if(acGameTitle.getText().toString().isEmpty()) {
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