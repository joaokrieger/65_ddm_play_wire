package com.ddm.playwire.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ddm.playwire.R;
import com.ddm.playwire.model.User;
import com.ddm.playwire.ui.activity.MenuActivity;

public class ProfileFragment extends Fragment {

    private View rootView;
    private TextView tvUsernameProfile;

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

        return rootView;
    }
}