package com.bvm.pokedex.data.remote

import com.bvm.pokedex.domain.models.MonsterDetailsModel
import com.bvm.pokedex.domain.models.PokemonModel
import retrofit2.http.Query

class RemoteDataSourceImpl(private val api : PokedexApi) : RemoteDataSource {
    override suspend fun getAllPokemon(offset:Int, limit:Int): PokemonModel? {
        return api.getAllPokemon(offset,limit).body()
    }

    override suspend fun getPokemonByName(name: String) : MonsterDetailsModel?{
        return api.getPokemonByName(name).body()
    }
}