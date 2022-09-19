package com.bvm.pokedex.utils

import androidx.recyclerview.widget.DiffUtil
import com.bvm.pokedex.domain.models.MonsterDetailsModel
import com.bvm.pokedex.domain.models.Pokemon
import com.bvm.pokedex.domain.models.PokemonModel

class AllMonstersDiffUtils : DiffUtil.ItemCallback<MonsterDetailsModel>() {
    override fun areItemsTheSame(oldItem: MonsterDetailsModel, newItem: MonsterDetailsModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MonsterDetailsModel, newItem: MonsterDetailsModel): Boolean {
        return oldItem == newItem
    }
}