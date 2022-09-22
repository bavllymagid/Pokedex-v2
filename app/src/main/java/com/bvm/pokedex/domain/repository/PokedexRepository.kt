package com.bvm.pokedex.domain.repository

import com.bvm.pokedex.domain.models.MonsterDetailsModel
import com.bvm.pokedex.domain.models.PokeSpecies
import com.bvm.pokedex.domain.models.PokemonModel

interface PokedexRepository {
    suspend fun getPokemonData(offset:Int, limit:Int): PokemonModel?
    suspend fun getPokemonByName(name:String): MonsterDetailsModel?
    suspend fun getSpeciesByID(id:Int):PokeSpecies?
    fun getLikeState(id:Int):Boolean
    fun setLikeSate(id:Int,state:Boolean)
}