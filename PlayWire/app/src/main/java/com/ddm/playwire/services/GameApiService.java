package com.ddm.playwire.services;

import com.ddm.playwire.model.Game;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GameApiService {

    @GET("api/games")
    Call<List<Game>> select();
}
