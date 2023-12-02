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
import com.ddm.playwire.data.dao.ReviewDao;
import com.ddm.playwire.data.dao.UserDao;
import com.ddm.playwire.model.Game;
import com.ddm.playwire.model.User;
import com.ddm.playwire.repository.ReviewRepository;
import com.ddm.playwire.repository.UserRepository;
import com.ddm.playwire.services.NotificationService;
import com.ddm.playwire.ui.adapter.AutoCompleteGameAdapter;
import com.ddm.playwire.ui.main.MenuActivity;
import com.ddm.playwire.viewmodel.game.GameViewModel;
import com.ddm.playwire.viewmodel.review.ReviewViewModel;
import com.ddm.playwire.viewmodel.review.ReviewViewModelFactory;
import com.ddm.playwire.viewmodel.user.UserViewModel;
import com.ddm.playwire.viewmodel.user.UserViewModelFactory;

import java.util.List;

public class ReviewFormFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private View rootView;
    private ReviewViewModel reviewViewModel;
    private GameViewModel gameViewModel;
    private UserViewModel userViewModel;
    private String feedback;
    private AutoCompleteGameAdapter autoCompleteAdapter;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(GameViewModel.class);

        ReviewRepository reviewRepository = new ReviewDao(getActivity().getApplicationContext());
        reviewViewModel = new ViewModelProvider(this, new ReviewViewModelFactory(reviewRepository)).get(ReviewViewModel.class);

        UserRepository userRepository = new UserDao(getActivity().getApplicationContext());
        userViewModel = new ViewModelProvider(this, new UserViewModelFactory(userRepository)).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_review_form, container, false);

        userViewModel.getCurrentUserLiveData().observe(getActivity(), user -> this.user = user);
        gameViewModel.getAllGames().observe(requireActivity(), games -> updateAutoComplete(games));

        initComponents();

        return rootView;
    }

    private void updateAutoComplete(List<Game> gameList) {
        if (autoCompleteAdapter == null) {
            setAutoCompleteAdapter(gameList);
        }
        else{
            autoCompleteAdapter.getGameListFull().clear();
            autoCompleteAdapter.addAll(gameList);
            autoCompleteAdapter.notifyDataSetChanged();
        }
    }

    private void setAutoCompleteAdapter(List<Game>  gameList) {
        autoCompleteAdapter = new AutoCompleteGameAdapter(getActivity().getApplicationContext(), gameList);
        AutoCompleteTextView acGameTitle = rootView.findViewById(R.id.acGameTitle);
        acGameTitle.setAdapter(autoCompleteAdapter);
    }

    private void initComponents() {
        @SuppressLint("ResourceType")
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.game_review_score, R.drawable.spinner_item_layout);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spFeedback = rootView.findViewById(R.id.spFeedback);
        spFeedback.setAdapter(spinnerAdapter);
        spFeedback.setOnItemSelectedListener(this);

        Button  btnRegisterReview = rootView.findViewById(R.id.btnRegisterReview);
        btnRegisterReview.setOnClickListener(view -> insertReview());
    }

    private void insertReview() {

        EditText etReviewDescription = rootView.findViewById(R.id.etReviewDescription);
        AutoCompleteTextView acGameTitle = rootView.findViewById(R.id.acGameTitle);

        if(acGameTitle.getText().toString().isEmpty()) {
            Toast.makeText(requireActivity(), "É necessário preencher o campo Nome do Jogo!", Toast.LENGTH_SHORT).show();
        }
        else if(etReviewDescription.getText().toString().isEmpty()) {
            Toast.makeText(requireActivity(), "É necessário preencher o campo Descrição da Análise!", Toast.LENGTH_SHORT).show();
        }
        else{
            reviewViewModel.insertReview(acGameTitle.getText().toString(), etReviewDescription.getText().toString(), feedback, user);
            NotificationService.getInstance().showNotification("Feedback Recebido!", "Continue jogando e compartilhando suas experiências!", getActivity());
            navigateToFeedFragment();
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