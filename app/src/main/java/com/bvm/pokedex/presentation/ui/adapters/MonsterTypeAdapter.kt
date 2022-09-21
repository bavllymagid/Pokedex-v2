package com.bvm.pokedex.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bvm.pokedex.databinding.TypeItemBinding
import com.bvm.pokedex.databinding.TypeItemMonsterDetailsBinding

class MonsterTypeAdapter(private val list:ArrayList<String> , private val viewNum: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class TypeViewHolder(val binding: TypeItemBinding) : RecyclerView.ViewHolder(binding.root)
    class TypeMonsterDetailsViewHolder(val binding: TypeItemMonsterDetailsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewNum == 0) {
            return TypeViewHolder(
                TypeItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }else{
            return TypeMonsterDetailsViewHolder(
                TypeItemMonsterDetailsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is TypeViewHolder) {
            holder.binding.monsterType.text = list[holder.adapterPosition]
        }else if (holder is TypeMonsterDetailsViewHolder){
            holder.binding.monsterType.text = list[holder.adapterPosition]
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}