package com.bvm.pokedex.domain.usecases

import com.bvm.pokedex.domain.models.PokeSpecies
import com.bvm.pokedex.domain.repository.PokedexRepository
import javax.inject.Inject

class GetPokeSpecies @Inject constructor(private val repository: PokedexRepository) {
    suspend fun execute(id:Int):PokeSpecies?{
        return repository.getSpeciesByID(id)
    }
}