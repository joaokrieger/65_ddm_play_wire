package com.ddm.playwire.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ddm.playwire.R;
import com.ddm.playwire.dao.UserDao;
import com.ddm.playwire.model.User;
import com.ddm.playwire.repository.UserRepository;
import com.ddm.playwire.viewmodel.UserViewModel;
import com.ddm.playwire.viewmodel.UserViewModelFactory;

public class UserFormActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        UserRepository userRepository = new UserDao(getApplicationContext());
        userViewModel = new ViewModelProvider(this, new UserViewModelFactory(userRepository)).get(UserViewModel.class);
        userViewModel.getCurrentUserLiveData().observe(this, user -> updateUi(user));

        initComponents();
    }

    private void initComponents() {
        Button btnRegisterUser = findViewById(R.id.btnRegisterUser);
        btnRegisterUser.setOnClickListener(v -> submitUser());
    }

    private void submitUser() {

        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etPasswordConfirm = findViewById(R.id.etPassword);

        if(etUsername.getText().toString().isEmpty()) {
            Toast.makeText(this, "O campo Username é de preenchimento obrigatório!", Toast.LENGTH_SHORT).show();
        }
        else if(etPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "O campo Senha é de preenchimento obrigatório!", Toast.LENGTH_SHORT).show();
        }
        else if(!etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())){
            Toast.makeText(this, "A senha e a confirmação de senha não correspondem!", Toast.LENGTH_SHORT).show();
        }
        else{
            userViewModel.registerUser(etUsername.getText().toString(), etPassword.getText().toString());
        }
    }

    private void navigateToMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    private void updateUi(User user) {
        if(user != null){
            Toast.makeText(this, "Bem vindo " + user.getUsername() + "!", Toast.LENGTH_SHORT).show();
            navigateToMenuActivity();
        }
    }
}