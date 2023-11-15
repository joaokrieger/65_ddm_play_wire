package com.ddm.playwire.viewmodel.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ddm.playwire.data.SharedPreferenceDataSource;
import com.ddm.playwire.model.User;
import com.ddm.playwire.repository.UserRepository;

public class UserViewModel extends ViewModel {

    private MutableLiveData<User> currentUserLiveData = new MutableLiveData<>();;
    private UserRepository userRepository;

    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void login(String username, String password) {
        User currentUser = userRepository.loadUserByCredentials(username, password);
        if(currentUser != null){
            initSessionUser(currentUser);
        }
    }

    public void registerUser(String username, String password) {
        User newUser = userRepository.insertUser(new User(username, password));
        if(newUser != null) {
            initSessionUser(newUser);
        }
    }

    public LiveData<User> getCurrentUserLiveData() {
        loadSessionUser();
        return currentUserLiveData;
    }

    private void loadSessionUser() {
        User user = userRepository.loadUserById(SharedPreferenceDataSource.getInstance().getSessionUserId());
        if(user != null)
            this.currentUserLiveData.setValue(user);
    }

    private void initSessionUser(User user){
        SharedPreferenceDataSource.getInstance().setSessionUserId(user.getUserId());
        loadSessionUser();
    }
}
