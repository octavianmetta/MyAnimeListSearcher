package com.octavianmetta.android.myanimelistsearcher.rest;

import com.octavianmetta.android.myanimelistsearcher.models.MALResponse;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {

    String BASE_URL = "https://api.jikan.moe/v3";

    @GET("search/type/?q={title}&page=1")
    Observable<List<MALResponse>> getSearch(@Path("title")String title);
}
