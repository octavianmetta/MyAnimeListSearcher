package com.octavianmetta.android.myanimelistsearcher.models.anime.characterStaff

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.BindingAdapter
import android.util.Log
import android.widget.ImageView
import android.widget.Toast

import com.bumptech.glide.Glide
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.octavianmetta.android.myanimelistsearcher.BR

data class Character(
        @SerializedName("image_url")
        val imageUrl: String,
        @SerializedName("mal_id")
        val malId: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("role")
        val role: String,
        @SerializedName("url")
        val url: String,
        @SerializedName("voice_actors")
        val voiceActors: List<VoiceActor>) : BaseObservable() {

            /*@BindingAdapter("imageUrl")
            fun loadImage(view: ImageView, imageUrl: String) {
                Glide.with(view.context)
                        .load(imageUrl)
                        .into(view)
                Log.d("Picture", "Loaded")
            } */


}
