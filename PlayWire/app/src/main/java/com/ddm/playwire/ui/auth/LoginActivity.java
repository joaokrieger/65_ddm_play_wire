package com.ddm.playwire.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.ddm.playwire.R;
import com.ddm.playwire.data.SharedPreferenceDataSource;
import com.ddm.playwire.data.dao.UserDao;
import com.ddm.playwire.model.User;
import com.ddm.playwire.repository.UserRepository;
import com.ddm.playwire.ui.main.MenuActivity;
import com.ddm.playwire.viewmodel.user.UserViewModel;
import com.ddm.playwire.viewmodel.user.UserViewModelFactory;

public class LoginActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferenceDataSource.getInstance().init(getApplicationContext());
        UserRepository userRepository = new UserDao(getApplicationContext());

        userViewModel = new ViewModelProvider(this, new UserViewModelFactory(userRepository)).get(UserViewModel.class);
        userViewModel.getCurrentUserLiveData().observe(this, user -> updateUi(user));

        initComponents();
    }

    private void initComponents() {
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> authenticateUser());

        Button btnRegisterUser = findViewById(R.id.btnRegisterUser);
        btnRegisterUser.setOnClickListener(v -> navigateToUserFormActivity());
    }

    private void authenticateUser(){
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        userViewModel.login(username, password);
    }

    private void updateUi(User user) {
        if(user != null){
            Toast.makeText(this, "Bem vindo " + user.getUsername() + "!", Toast.LENGTH_SHORT).show();
            navigateToMenuActivity();
        }
    }

    private void navigateToMenuActivity(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    private void navigateToUserFormActivity(){
        Intent intent = new Intent(this, UserFormActivity.class);
        startActivity(intent);
    }
}