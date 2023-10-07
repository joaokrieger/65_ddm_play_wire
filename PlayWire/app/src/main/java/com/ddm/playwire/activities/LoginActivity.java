package com.ddm.playwire.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ddm.playwire.R;
import com.ddm.playwire.data.UserDatabaseHelper;
import com.ddm.playwire.models.User;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(view -> {
            UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper(view.getContext());
            User user = userDatabaseHelper.loadUserByCredentials(etUsername.getText().toString(), etPassword.getText().toString());

            if(user != null){
                Toast.makeText(view.getContext(), "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), MenuActivity.class);
                intent.putExtra("userId", user.getUserId());
                startActivity(intent);
            }
            else{
                Toast.makeText(view.getContext(), "Usuário inválido!", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnRegisterUser = findViewById(R.id.btnRegisterUser);
        btnRegisterUser.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), UserFormActivity.class);
            startActivity(intent);
        });
    }
}