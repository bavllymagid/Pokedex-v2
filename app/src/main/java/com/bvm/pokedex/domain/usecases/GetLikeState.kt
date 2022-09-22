package com.bvm.pokedex.domain.usecases

import com.bvm.pokedex.domain.repository.PokedexRepository
import javax.inject.Inject

class GetLikeState @Inject constructor(private val repository: PokedexRepository) {

    fun executeGetState(id:Int):Boolean{
        return repository.getLikeState(id)
    }

    fun executeSetSate(id:Int,state:Boolean){
        repository.setLikeSate(id,state)
    }
}