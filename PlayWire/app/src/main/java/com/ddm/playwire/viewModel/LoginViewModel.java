package com.ddm.playwire.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.ddm.playwire.dao.UserDao;
import com.ddm.playwire.model.User;

public class LoginViewModel extends AndroidViewModel {

    private UserDao userDao;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        userDao = new UserDao(application);
    }

    public User login(String username, String password) {
        User user = userDao.loadUserByCredentials(username, password);
        return user;
    }
}
