package com.octavianmetta.android.myanimelistsearcher.rest;

import com.octavianmetta.android.myanimelistsearcher.models.MALResponse;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    String BASE_URL = "https://api.jikan.moe/v3/";

    @GET("search/{type}")
    Observable<MALResponse> getSearch(@Path("type") String type,
                                      @Query("q") String title,
                                      @Query("page") Integer page);
}
