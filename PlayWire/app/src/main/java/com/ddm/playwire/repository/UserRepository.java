package com.ddm.playwire.repository;

import com.ddm.playwire.model.User;

public interface UserRepository {

    User insertUser(User user);
    User loadUserByCredentials(String username, String password);
    User loadUserById(int userId);
}
