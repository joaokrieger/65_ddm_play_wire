package com.ddm.playwire.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.ddm.playwire.dao.ReviewCommentDao;
import com.ddm.playwire.dao.UserDao;
import com.ddm.playwire.databinding.ActivityMenuBinding;
import com.ddm.playwire.ui.fragment.FeedFragment;
import com.ddm.playwire.ui.fragment.ProfileFragment;
import com.ddm.playwire.R;
import com.ddm.playwire.ui.fragment.RankFragment;
import com.ddm.playwire.model.User;

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
            UserDao userDao = new UserDao(this);
            this.user = userDao.loadByUserId(userId);
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