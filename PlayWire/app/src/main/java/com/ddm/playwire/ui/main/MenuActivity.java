package com.ddm.playwire.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.ddm.playwire.databinding.ActivityMenuBinding;
import com.ddm.playwire.services.NotificationService;
import com.ddm.playwire.ui.review.FeedFragment;
import com.ddm.playwire.ui.profile.ProfileFragment;
import com.ddm.playwire.R;
import com.ddm.playwire.ui.rank.RankFragment;

public class MenuActivity extends AppCompatActivity {

    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> setFragment(item));

        replaceFragment(new FeedFragment());
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