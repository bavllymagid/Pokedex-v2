package com.bvm.pokedex.domain.usecases

import com.bvm.pokedex.domain.models.MonsterDetailsModel
import com.bvm.pokedex.domain.models.PokemonModel
import com.bvm.pokedex.domain.repository.PokedexRepository
import javax.inject.Inject

class GetAllPokemon @Inject constructor(private val repository: PokedexRepository) {
    suspend fun execute(offset:Int, limit:Int):PokemonModel?{
        return repository.getPokemonData(offset, limit)
    }
}