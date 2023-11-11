package com.ddm.playwire.services;

import com.ddm.playwire.model.Game;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GameService {

    @Headers({
            "Client-ID: 7576h4b2ecrtsu5yn56lgvtkqxg89r",
            "Authorization: Bearer gx8u96kuyskkk3uoyndouwshjb3r9x",
            "Content-Type: text/plain"
    })
    @POST("games")
    Call<List<Game>> searchGames(@Body String requestBody);
}
