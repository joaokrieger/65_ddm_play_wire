package com.ddm.playwire.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.ddm.playwire.dao.UserDao;
import com.ddm.playwire.model.User;

public class MenuViewModel extends AndroidViewModel {

    private UserDao userDao;

    public MenuViewModel(@NonNull Application application) {
        super(application);
        userDao = new UserDao(application);
    }

    public User loadSessionUser(int userId) {
        User user = userDao.loadUserById(userId);
        return user;
    }
}
