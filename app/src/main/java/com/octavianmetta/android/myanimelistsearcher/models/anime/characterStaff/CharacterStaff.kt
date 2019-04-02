package com.octavianmetta.android.myanimelistsearcher.models.anime.characterStaff

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CharacterStaff(
    @SerializedName("characters")
    val characters: List<Character>,
    @SerializedName("request_cache_expiry")
    val requestCacheExpiry: Int,
    @SerializedName("request_cached")
    val requestCached: Boolean,
    @SerializedName("request_hash")
    val requestHash: String,
    @SerializedName("staff")
    val staff: List<Staff>) : BaseObservable()
