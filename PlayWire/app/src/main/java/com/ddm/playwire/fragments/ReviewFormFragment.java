package com.ddm.playwire.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ddm.playwire.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewFormFragment extends Fragment implements AdapterView.OnItemSelectedListener {

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

        Spinner spinner = rootView.findViewById(R.id.spGameReviewScore);
        @SuppressLint("ResourceType") ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.game_review_score, R.drawable.spinner_item_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}