package com.octavianmetta.android.myanimelistsearcher.utils

import android.databinding.BindingAdapter
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso

class ImageBindingAdapter {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String) {
            Log.d("Image", url)
            Picasso.get().load(url).into(view)
        }
    }
}