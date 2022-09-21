package com.bvm.pokedex.presentation.viewmodels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bvm.pokedex.domain.models.MonsterDetailsModel
import com.bvm.pokedex.domain.models.PokemonModel
import com.bvm.pokedex.domain.usecases.GetAllPokemon
import com.bvm.pokedex.domain.usecases.GetPokemonByName
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val getAllPokemon: GetAllPokemon,
    private val getPokemonByName: GetPokemonByName
) : ViewModel() {

    suspend fun getAllPokemon(offset:Int, limit:Int):PokemonModel?{
        return getAllPokemon.execute(offset,limit)
    }

    suspend fun getPokemonByName(name:String):MonsterDetailsModel?{
        return getPokemonByName.execute(name)
    }

}