package com.bvm.pokedex.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bvm.pokedex.R
import com.bvm.pokedex.di.PokedexApp

object ImageLoader {
    fun loadImageIntoImageView(url:String, view: ImageView){
        Glide.with(PokedexApp.appContext)
            .load(url)
            .placeholder(R.drawable.poke)
            .error(R.drawable.poke)
            .apply(RequestOptions().override(300, 300))
            .into(view)
    }

    fun loadImageIntoImageView600(url:String, view: ImageView){
        Glide.with(PokedexApp.appContext)
            .load(url)
            .placeholder(R.drawable.poke)
            .error(R.drawable.poke)
            .apply(RequestOptions().override(600, 600))
            .into(view)
    }
}