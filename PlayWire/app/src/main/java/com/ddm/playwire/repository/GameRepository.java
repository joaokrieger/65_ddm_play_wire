package com.ddm.playwire.repository;

import com.ddm.playwire.model.Game;
import com.ddm.playwire.services.RetrofitInitializer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameRepository {

    public interface GameNamesCallback {
        void onGameNamesLoaded(String[] gameNames);
        void onGameNamesFailed();
    }

    public static void getGameNames(GameNamesCallback callback) {

        Call<List<Game>> call = new RetrofitInitializer().getGame().select();
        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Game> games = response.body();
                    String[] gameNames = extractGameNames(games);
                    callback.onGameNamesLoaded(gameNames);
                } else {
                    callback.onGameNamesFailed();
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                callback.onGameNamesFailed();
            }
        });
    }

    private static String[] extractGameNames(List<Game> games) {
        List<String> gameNames = new ArrayList<>();
        for (Game game : games) {
            gameNames.add(game.getTitle());
        }
        return gameNames.toArray(new String[0]);
    }
}

