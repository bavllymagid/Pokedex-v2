package com.bvm.pokedex.presentation.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bvm.pokedex.R
import com.bvm.pokedex.databinding.MonsterItemBinding
import com.bvm.pokedex.domain.models.MonsterDetailsModel
import com.bvm.pokedex.domain.models.Type
import com.bvm.pokedex.utils.AllMonstersDiffUtils
import com.bvm.pokedex.utils.ImageLoader
import com.google.android.material.card.MaterialCardView
import java.util.*
import kotlin.collections.ArrayList


class AllMonstersAdapter(val context: Context, private val onMonsterSelected: OnMonsterSelected) : ListAdapter<MonsterDetailsModel, AllMonstersAdapter.MonsterViewHolder>(AllMonstersDiffUtils()){

    private lateinit var adapter:MonsterTypeAdapter

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
        adapter = MonsterTypeAdapter(typeListOfMonster(item.types),0)
        holder.binding.apply {
            monsterName.text = item.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            }
            ImageLoader.loadImageIntoImageView(item.sprites.other.officialArtwork.front_default,monsterImg)
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(1.7f)
            val filter = ColorMatrixColorFilter(colorMatrix)
            monsterImg.colorFilter = filter
            typeList.adapter = adapter
            colorizeView(monsterCard, item)
        }

        holder.itemView.findViewById<MaterialCardView>(R.id.monster_card).setOnClickListener{
            onMonsterSelected.onMonsterClicked(item,holder.adapterPosition)
        }
    }


    private fun typeListOfMonster(types: List<Type>):ArrayList<String>{
        val list = ArrayList<String>()
        for (item in types){
            list.add(item.type.name)
        }

        return list
    }

    @SuppressLint("ResourceAsColor")
    private fun colorizeView(view: MaterialCardView, item:MonsterDetailsModel){
        item.color = when (item.types[0].type.name.lowercase()) {
            "fire" -> R.color.red
            "grass" -> R.color.light_green
            "bug" -> R.color.brown
            "ground" ->  R.color.brown
            "water" -> R.color.cyan
            "electric" -> R.color.orange
            "fairy" -> R.color.pink
            "poison" -> R.color.purple
            else -> R.color.grey
        }
        view.setCardBackgroundColor(getColor(context, item.color))
    }

    interface OnMonsterSelected {
        fun onMonsterClicked(item : MonsterDetailsModel,position: Int)
    }
}