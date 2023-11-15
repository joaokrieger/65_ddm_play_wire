package com.ddm.playwire.network;

import com.ddm.playwire.services.GameApiService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInitializer {

    private final Retrofit retrofit;
    private static final String BASE_URL = "https://www.freetogame.com/";

    public RetrofitInitializer() {

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
}
