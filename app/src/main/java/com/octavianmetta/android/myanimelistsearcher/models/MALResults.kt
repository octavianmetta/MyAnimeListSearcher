package com.octavianmetta.android.myanimelistsearcher.models

import android.databinding.BaseObservable
import android.databinding.BindingAdapter
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName

data class MALResults(
        @SerializedName("mal_id") var malId: Int?,
        var url: String,
        @SerializedName("image_url") var imageUrl: String,
        var title: String,
        var airing: Boolean?,
        var synopsis: String,
        var type: String,
        var episodes: Int?,
        var score: Double?,
        @SerializedName("start_date") var startDate: String,
        @SerializedName("end_date") var endDate: String,
        //var members: Int?,
        var rated: String) : BaseObservable() {
    /*companion object {
        @JvmStatic @BindingAdapter("imageUrl")
        fun setImageUrl(imageView: ImageView, image_url: String?) {
            Glide.with(imageView.context)
                    .load(image_url)
                    .into(imageView)
        }
    } */
    val start_year: String
        get() = startDate?.substring(0,4)


}
