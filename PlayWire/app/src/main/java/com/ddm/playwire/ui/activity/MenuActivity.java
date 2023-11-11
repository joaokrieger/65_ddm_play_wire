package com.ddm.playwire.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;

import com.ddm.playwire.databinding.ActivityMenuBinding;
import com.ddm.playwire.ui.fragment.FeedFragment;
import com.ddm.playwire.ui.fragment.ProfileFragment;
import com.ddm.playwire.R;
import com.ddm.playwire.ui.fragment.RankFragment;
import com.ddm.playwire.model.User;
import com.ddm.playwire.viewModel.MenuViewModel;

public class MenuActivity extends AppCompatActivity {

    private ActivityMenuBinding binding;
    private MenuViewModel menuViewModel;
    private User sessionUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        replaceFragment(new FeedFragment());

        int userId = getIntent().getIntExtra("userId", -1);
        menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        this.sessionUser = menuViewModel.loadSessionUser(userId);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> setFragment(item));
    }

    public User getSessionUser(){
        return sessionUser;
    }

    private boolean setFragment(MenuItem item){
        switch (item.getItemId()){
            case R.id.rank:
                replaceFragment(new RankFragment());
                return true;
            case R.id.feed:
                replaceFragment(new FeedFragment());
                return true;
            case R.id.profile:
                replaceFragment(new ProfileFragment());
                return true;
        }

        return false;
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout, fragment);
        fragmentTransaction.commit();
    }
}