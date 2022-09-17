package com.bvm.pokedex.utils

import androidx.recyclerview.widget.DiffUtil
import com.bvm.pokedex.domain.models.Pokemon
import com.bvm.pokedex.domain.models.PokemonModel

class AllMonstersDiffUtils : DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }
}