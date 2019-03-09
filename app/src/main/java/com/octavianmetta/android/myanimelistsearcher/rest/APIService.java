package com.octavianmetta.android.myanimelistsearcher.rest;

import com.octavianmetta.android.myanimelistsearcher.models.MALSearchResponse;
import com.octavianmetta.android.myanimelistsearcher.models.MALTopResponse;
import com.octavianmetta.android.myanimelistsearcher.models.anime.AnimeModel;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    String BASE_URL = "https://api.jikan.moe/v3/";

    @GET("search/{type}")
    Observable<MALSearchResponse> getSearch(@Path("type") String type,
                                            @Query("q") String title,
                                            @Query("page") Integer page);

    @GET("top/{type}/{page}/airing")
    Observable<MALTopResponse> getTopAnime(@Path("type") String type,
                                           @Path("page") Integer page);

    @GET("anime/{malId}")
    Observable<AnimeModel> getAnime(@Path("malId") Integer malId);
}
