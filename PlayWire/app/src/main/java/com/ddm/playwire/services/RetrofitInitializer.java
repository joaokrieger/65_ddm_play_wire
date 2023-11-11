package com.ddm.playwire.services;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInitializer {

    private final Retrofit retrofit;
    private static final String BASE_URL = "https://api.igdb.com/v4/";

    public RetrofitInitializer() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();
    }

    public GameService getGames(){
        return retrofit.create(GameService.class);
    }
}


