package com.ddm.playwire.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ddm.playwire.R;
import com.ddm.playwire.model.User;
import com.ddm.playwire.viewModel.UserFormViewModel;

public class UserFormActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etPasswordConfirm;
    private UserFormViewModel userFormViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userFormViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(UserFormViewModel.class);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etPasswordConfirm = findViewById(R.id.etPassword);

        Button btnRegisterUser = findViewById(R.id.btnRegisterUser);
        btnRegisterUser.setOnClickListener(view -> {

            if(validateFormFields()) {

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                submitUser(userFormViewModel.registerUser(username, password));
            }
        });
    }

    private boolean validateFormFields() {
        if(etUsername.getText().toString().isEmpty()) {
            Toast.makeText(this, "O campo Username é de preenchimento obrigatório!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(etPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "O campo Senha é de preenchimento obrigatório!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())){
            Toast.makeText(this, "A senha e a confirmação de senha não correspondem!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void submitUser(User user) {
        if(user != null){
            Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
            navigateToMenuActivity(user);
        }
        else{
            Toast.makeText(this, "Usuário inválido!", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToMenuActivity(User user) {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("userId", user.getUserId());
        startActivity(intent);
    }
}