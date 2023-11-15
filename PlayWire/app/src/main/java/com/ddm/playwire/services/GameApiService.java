package com.ddm.playwire.services;

import com.ddm.playwire.model.Game;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GameApiService {

    @GET("api/games")
    Call<List<Game>> select();
}
