package com.bvm.pokedex.data.remote

import com.bvm.pokedex.domain.models.MonsterDetailsModel
import com.bvm.pokedex.domain.models.PokeSpecies
import com.bvm.pokedex.domain.models.PokemonModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexApi {

    @GET("pokemon")
    suspend fun getAllPokemon(@Query("offset") offset:Int, @Query("limit") limit:Int) : Response<PokemonModel>

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name") name:String):Response<MonsterDetailsModel>

    @GET("pokemon-species/{id}")
    suspend fun getSpeciesByName(@Path("id") id:Int):Response<PokeSpecies>
}