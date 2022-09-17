package com.bvm.pokedex.data.remote

import com.bvm.pokedex.domain.models.PokemonModel
import retrofit2.http.GET

interface PokedexApi {

    @GET("pokemon")
    fun getAllPokemon() : PokemonModel

}