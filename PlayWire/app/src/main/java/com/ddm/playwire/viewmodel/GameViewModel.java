package com.ddm.playwire.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ddm.playwire.repository.GameRepository;

public class GameViewModel extends AndroidViewModel {

    private MutableLiveData<String[]> gameNamesLiveData = new MutableLiveData<>();

    public GameViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String[]> getAllGames() {
        GameRepository.getGameNames(new GameRepository.GameNamesCallback() {
            @Override
            public void onGameNamesLoaded(String[] gameNames) {
                gameNamesLiveData.setValue(gameNames);
            }

            @Override
            public void onGameNamesFailed() {
                // Você pode lidar com a falha aqui, se necessário
            }
        });

        return gameNamesLiveData;
    }
}
