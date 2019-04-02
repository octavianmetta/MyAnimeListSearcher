package com.octavianmetta.android.myanimelistsearcher.models.anime.characterStaff

import android.databinding.BaseObservable
import android.databinding.Bindable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.octavianmetta.android.myanimelistsearcher.BR

data class VoiceActor(
        @SerializedName("image_url")
        val imageUrl: String,
        @SerializedName("language")
        val language: String,
        @SerializedName("mal_id")
        val malId: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("url")
        val url: String) : BaseObservable()