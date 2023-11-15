package com.ddm.playwire.repository;

public interface GameRepository {
    void onGameNamesLoaded(String[] gameNames);
    void onGameNamesFailed();
}


