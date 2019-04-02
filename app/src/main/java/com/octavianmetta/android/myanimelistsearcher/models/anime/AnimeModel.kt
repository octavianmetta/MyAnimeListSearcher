package com.octavianmetta.android.myanimelistsearcher.models.anime

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.BindingAdapter
import android.widget.ImageView

import com.bumptech.glide.Glide

data class AnimeModel(
        val request_hash : String,
        val request_cached : Boolean,
        val request_cache_expiry : Int,
        val mal_id : Int,
        val url : String,
        val image_url : String,
        val trailer_url : String,
        val title : String,
        val title_english : String,
        val title_japanese : String,
        val title_synonyms : List<String>,
        val type : String,
        val source : String,
        val episodes : Int,
        val status : String,
        val airing : Boolean,
        val aired : Aired,
        val duration : String,
        val rating : String,
        val score : Double,
        val scored_by : Int,
        val rank : Int,
        val popularity : Int,
        val members : Int,
        val favorites : Int,
        val synopsis : String,
        val background : String,
        val premiered : String,
        val broadcast : String,
        val related : Related,
        val producers : List<Producers>,
        val licensors : List<Licensors>,
        val studios : List<Studios>,
        val genres : List<Genres>,
        val opening_themes : List<String>,
        val ending_themes : List<String>) : BaseObservable() {

    companion object {
        @JvmStatic @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, image_url: String?) {
            Glide.with(view.context)
                    .load(image_url)
                    .into(view)
        }
    }

    val scoreString : String get() = score.toString()
    val airedString : String get() = aired.toString()


}
