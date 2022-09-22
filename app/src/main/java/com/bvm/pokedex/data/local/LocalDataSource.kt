package com.bvm.pokedex.data.local

interface LocalDataSource {
    fun getLikeState(id:Int):Boolean
    fun setLikeSate(id:Int,state:Boolean)
}