package com.bvm.pokedex.data.local

import android.content.SharedPreferences
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val sharedPreferences: SharedPreferences) : LocalDataSource {
    override fun getLikeState(id:Int): Boolean {
        return sharedPreferences.getBoolean(id.toString(),false)
    }

    override fun setLikeSate(id:Int,state: Boolean) {
        sharedPreferences.edit().putBoolean(id.toString(),state).apply()
    }
}