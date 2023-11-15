package com.ddm.playwire.repository;

import com.ddm.playwire.model.Game;

import java.util.List;

public interface GameRepository {
    void onGameLoaded(List<Game> gameList);
    void onGameFailed();
}