package com.ddm.playwire.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.ddm.playwire.dao.UserDao;
import com.ddm.playwire.model.User;

public class UserFormViewModel extends AndroidViewModel {

    private UserDao userDao;

    public UserFormViewModel(@NonNull Application application) {
        super(application);
        userDao = new UserDao(application);
    }

    public User registerUser(String username, String password) {
        User user = userDao.insert(new User(username, password));
        return user;
    }
}
