package com.octavianmetta.android.myanimelistsearcher.models.anime.characterStaff

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Staff(
        @SerializedName("image_url")
        val imageUrl: String,
        @SerializedName("mal_id")
        val malId: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("positions")
        val positions: List<String>,
        @SerializedName("url")
        val url: String
)
