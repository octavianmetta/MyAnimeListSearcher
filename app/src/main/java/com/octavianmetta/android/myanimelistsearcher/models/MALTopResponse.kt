package com.octavianmetta.android.myanimelistsearcher.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MALTopResponse(
        val request_hash : String,
        val request_cached : Boolean,
        val request_cache_expiry : Int,
        val top : ArrayList<MALResults>
)
