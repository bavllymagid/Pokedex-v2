package com.bvm.pokedex.domain.usecases

import com.bvm.pokedex.domain.models.MonsterDetailsModel
import com.bvm.pokedex.domain.models.PokemonModel
import com.bvm.pokedex.domain.repository.PokedexRepository
import javax.inject.Inject

class GetPokemonByName @Inject constructor(private val repository: PokedexRepository) {
    suspend fun execute(name:String): MonsterDetailsModel?{
        return repository.getPokemonByName(name)
    }
}