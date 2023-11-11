package com.ddm.playwire.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.ddm.playwire.R;
import com.ddm.playwire.model.User;
import com.ddm.playwire.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(UserViewModel.class);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(view -> {

            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            authenticateUser(userViewModel.login(username, password));
        });

        Button btnRegisterUser = findViewById(R.id.btnRegisterUser);
        btnRegisterUser.setOnClickListener(view -> {
            navigateToUserFormActivity();
        });
    }

    private void authenticateUser(User user){
        if(user != null){
            Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
            navigateToMenuActivity(user);
        }
        else{
            Toast.makeText(this, "Usuário inválido!", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToMenuActivity(User user){
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("userId", user.getUserId());
        startActivity(intent);
    }

    private void navigateToUserFormActivity(){
        Intent intent = new Intent(this, UserFormActivity.class);
        startActivity(intent);
    }
}