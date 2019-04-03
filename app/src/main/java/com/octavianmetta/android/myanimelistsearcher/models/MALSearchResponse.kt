package com.octavianmetta.android.myanimelistsearcher.models

import android.databinding.BaseObservable

data class MALSearchResponse(
        var request_hash: String,
        var request_cached: Boolean?,
        var request_cache_expiry: Int?,
        var results: ArrayList<MALResults>,
        var last_page: Int?) : BaseObservable()