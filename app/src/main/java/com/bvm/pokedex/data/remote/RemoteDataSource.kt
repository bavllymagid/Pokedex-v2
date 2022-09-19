package com.bvm.pokedex.data.remote

import com.bvm.pokedex.domain.models.MonsterDetailsModel
import com.bvm.pokedex.domain.models.PokemonModel

interface RemoteDataSource {
    suspend fun getAllPokemon(offset:Int, limit:Int) : PokemonModel?
    suspend fun getPokemonByName(name:String): MonsterDetailsModel?
}