package com.ddm.playwire.viewmodel.game;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ddm.playwire.model.Game;
import com.ddm.playwire.network.GameRetrofit;
import com.ddm.playwire.repository.GameRepository;

import java.util.List;

public class GameViewModel extends AndroidViewModel {

    private MutableLiveData<List<Game>> games = new MutableLiveData<>();

    public GameViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Game>> getAllGames() {
        GameRetrofit.getGameNames(new GameRepository() {
            @Override
            public void onGameLoaded(List<Game> gameNames) {
                games.setValue(gameNames);
            }

            @Override
            public void onGameFailed() {
            }
        });

        return games;
    }
}
