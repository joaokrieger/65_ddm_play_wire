package com.ddm.playwire.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ddm.playwire.R;
import com.ddm.playwire.dao.UserDao;
import com.ddm.playwire.model.User;

public class UserFormActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etPasswordConfirm = findViewById(R.id.etPassword);

        Button btnRegisterUser = findViewById(R.id.btnRegisterUser);
        btnRegisterUser.setOnClickListener(view -> {

            if(etUsername.getText().toString().isEmpty()) {
                Toast.makeText(view.getContext(), "O campo Username é de preenchimento obrigatório!", Toast.LENGTH_SHORT).show();
            }
            else if(etPassword.getText().toString().isEmpty()) {
                Toast.makeText(view.getContext(), "O campo Senha é de preenchimento obrigatório!", Toast.LENGTH_SHORT).show();
            }
            else if(!etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())){
                Toast.makeText(view.getContext(), "A senha e a confirmação de senha não correspondem!", Toast.LENGTH_SHORT).show();
            }
            else{
                UserDao userDao = new UserDao(view.getContext());
                Long userId = userDao.insertUser(new User(etUsername.getText().toString(), etPassword.getText().toString()));

                Intent intent = new Intent(view.getContext(), UserFormActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }
}