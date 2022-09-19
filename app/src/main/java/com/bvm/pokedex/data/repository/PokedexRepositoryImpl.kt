package com.bvm.pokedex.data.repository

import com.bvm.pokedex.data.remote.RemoteDataSource
import com.bvm.pokedex.domain.models.MonsterDetailsModel
import com.bvm.pokedex.domain.models.PokemonModel
import com.bvm.pokedex.domain.repository.PokedexRepository
import javax.inject.Inject

class PokedexRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) : PokedexRepository {
    override suspend fun getPokemonData(offset:Int, limit:Int): PokemonModel? {
        return remoteDataSource.getAllPokemon(offset, limit)
    }

    override suspend fun getPokemonByName(name: String): MonsterDetailsModel? {
        return remoteDataSource.getPokemonByName(name)
    }
}