package com.ddm.playwire.network;

import com.ddm.playwire.model.Game;
import com.ddm.playwire.repository.GameRepository;
import com.ddm.playwire.services.GameApiService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class GameRetrofitInitializer {

    private final Retrofit retrofit;
    private static final String BASE_URL = "https://www.freetogame.com/";

    public GameRetrofitInitializer() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public GameApiService getGame(){
        return retrofit.create(GameApiService.class);
    }

    public static void getGameNames(GameRepository callback) {

        Call<List<Game>> call = new GameRetrofitInitializer().getGame().select();
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
