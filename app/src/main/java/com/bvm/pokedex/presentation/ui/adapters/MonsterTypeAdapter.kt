package com.bvm.pokedex.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bvm.pokedex.databinding.FragmentStartUpBinding
import com.bvm.pokedex.databinding.TypeItemBinding

class MonsterTypeAdapter(val list:ArrayList<String>) :
    RecyclerView.Adapter<MonsterTypeAdapter.TypeViewHolder>() {

    class TypeViewHolder(val binding: TypeItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        return TypeViewHolder(
            TypeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        holder.binding.monsterType.text = list[holder.adapterPosition]
    }

    override fun getItemCount(): Int {
        return list.size
    }
}