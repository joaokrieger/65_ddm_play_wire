package com.ddm.playwire.network;

import com.ddm.playwire.model.Game;
import com.ddm.playwire.repository.GameRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameRetrofit {

    public static void getGameNames(GameRepository gameRepository) {

        Call<List<Game>> call = new RetrofitInitializer().getGame().select();
        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Game> games = response.body();
                    gameRepository.onGameLoaded(games);
                } else {
                    gameRepository.onGameFailed();
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                gameRepository.onGameFailed();
            }
        });
    }
}
