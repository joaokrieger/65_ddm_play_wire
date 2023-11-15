package com.ddm.playwire.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ddm.playwire.repository.GameRepository;
import com.ddm.playwire.network.GameRetrofitInitializer;

public class GameViewModel extends AndroidViewModel {

    private MutableLiveData<String[]> gameNamesLiveData = new MutableLiveData<>();

    public GameViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String[]> getAllGames() {
        GameRetrofitInitializer.getGameNames(new GameRepository() {
            @Override
            public void onGameNamesLoaded(String[] gameNames) {
                gameNamesLiveData.setValue(gameNames);
            }

            @Override
            public void onGameNamesFailed() {
            }
        });

        return gameNamesLiveData;
    }
}
