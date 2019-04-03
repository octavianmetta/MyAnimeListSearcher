package com.octavianmetta.android.myanimelistsearcher.rest

import com.octavianmetta.android.myanimelistsearcher.models.MALSearchResponse
import com.octavianmetta.android.myanimelistsearcher.models.MALTopResponse
import com.octavianmetta.android.myanimelistsearcher.models.anime.AnimeModel
import com.octavianmetta.android.myanimelistsearcher.models.anime.characterStaff.CharacterStaff


import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("search/{type}")
    fun getSearch(@Path("type") type: String,
                  @Query("q") title: String,
                  @Query("page") page: Int?,
                  @Query("limit") limit: Int?): Observable<MALSearchResponse>

    @GET("top/{type}/{page}/airing")
    fun getTopAnime(@Path("type") type: String,
                    @Path("page") page: Int?): Observable<MALTopResponse>

    @GET("anime/{malId}/")
    fun getAnime(@Path("malId") malId: Int?): Observable<AnimeModel>

    @GET("anime/{malId}/characters_staff")
    fun getCharacter(@Path("malId") malId: Int?): Observable<CharacterStaff>

    companion object {

        val BASE_URL = "https://api.jikan.moe/v3/"
    }
}
