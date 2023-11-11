package com.ddm.playwire.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.ddm.playwire.dao.UserDao;
import com.ddm.playwire.model.User;

public class UserViewModel extends AndroidViewModel {

    private UserDao userDao;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userDao = new UserDao(application);
    }

    public User login(String username, String password) {
        User user = userDao.loadUserByCredentials(username, password);
        return user;
    }

    public User registerUser(String username, String password) {
        User user = userDao.insert(new User(username, password));
        return user;
    }

    public User loadUser(int userId) {
        User user = userDao.loadUserById(userId);
        return user;
    }
}
