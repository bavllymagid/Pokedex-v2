package com.bvm.pokedex.data.remote

import com.bvm.pokedex.domain.models.MonsterDetailsModel
import com.bvm.pokedex.domain.models.PokeSpecies
import com.bvm.pokedex.domain.models.PokemonModel
import com.bvm.pokedex.domain.models.Species

interface RemoteDataSource {
    suspend fun getAllPokemon(offset:Int, limit:Int) : PokemonModel?
    suspend fun getPokemonByName(name:String): MonsterDetailsModel?
    suspend fun getSpeciesById(id:Int): PokeSpecies?
}