package com.bvm.pokedex.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bvm.pokedex.databinding.MonsterItemBinding
import com.bvm.pokedex.domain.models.MonsterDetailsModel
import com.bvm.pokedex.domain.models.Type
import com.bvm.pokedex.domain.models.TypeX
import com.bvm.pokedex.utils.AllMonstersDiffUtils
import com.bvm.pokedex.utils.ImageLoader

class AllMonstersAdapter() : ListAdapter<MonsterDetailsModel, AllMonstersAdapter.MonsterViewHolder>(AllMonstersDiffUtils()){

    lateinit var adapter:MonsterTypeAdapter

    class MonsterViewHolder(val binding: MonsterItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonsterViewHolder {
        return MonsterViewHolder(
            MonsterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MonsterViewHolder, position: Int) {
        val item = getItem(position)
        adapter = MonsterTypeAdapter(typeListOfMonster(item.types))
        holder.binding.apply {
            monsterName.text = item.name
            ImageLoader.loadImageIntoImageView(item.sprites.front_default,monsterImg)
            typeList.adapter = adapter
        }
    }


    fun typeListOfMonster(types: List<Type>):ArrayList<String>{
        val list = ArrayList<String>()
        for (item in types){
            list.add(item.type.name)
        }

        return list
    }
}