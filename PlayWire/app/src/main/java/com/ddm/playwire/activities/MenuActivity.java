package com.ddm.playwire.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.ddm.playwire.data.UserDatabaseHelper;
import com.ddm.playwire.fragments.FeedFragment;
import com.ddm.playwire.fragments.ProfileFragment;
import com.ddm.playwire.R;
import com.ddm.playwire.fragments.RankFragment;
import com.ddm.playwire.databinding.ActivityMenuBinding;
import com.ddm.playwire.models.User;

public class MenuActivity extends AppCompatActivity {

    private ActivityMenuBinding binding;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new FeedFragment());

        getSupportActionBar().hide();

        int userId = getIntent().getIntExtra("userId", -1);
        this.loadUser(userId);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.rank:
                    replaceFragment(new RankFragment());
                    break;
                case R.id.feed:
                    replaceFragment(new FeedFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }

            return true;
        });
    }

    private void loadUser(int userId){
        if(userId != -1){
            UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper(this);
            this.user = userDatabaseHelper.loadByUserId(userId);
        }
    }

    public User getSessionUser(){
        return this.user;
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout, fragment);
        fragmentTransaction.commit();
    }
}