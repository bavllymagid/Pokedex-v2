package com.bvm.pokedex.presentation.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
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


class AllMonstersAdapter(val context: Context , val onMonsterSelected: OnMonsterSelected) : ListAdapter<MonsterDetailsModel, AllMonstersAdapter.MonsterViewHolder>(AllMonstersDiffUtils()){

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
            ImageLoader.loadImageIntoImageView(item.sprites.other.officialArtwork.front_default,monsterImg)
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(1.7f)
            val filter = ColorMatrixColorFilter(colorMatrix)
            monsterImg.colorFilter = filter
            typeList.adapter = adapter
            colorizeView(monsterCard, item)
        }

        holder.itemView.findViewById<MaterialCardView>(R.id.monster_card).setOnClickListener{
            onMonsterSelected.onMonsterClicked(item)
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
        when (item.types[0].type.name.lowercase()) {
            "fire" -> view.setCardBackgroundColor(getColor(context, R.color.red))
            "grass" -> view.setCardBackgroundColor(getColor(context, R.color.light_green))
            "bug" -> view.setCardBackgroundColor(getColor(context, R.color.brown))
            "ground" -> view.setCardBackgroundColor(getColor(context, R.color.brown))
            "water" -> view.setCardBackgroundColor(getColor(context, R.color.cyan))
            "electric" -> view.setCardBackgroundColor(getColor(context, R.color.orange))
            "fairy" -> view.setCardBackgroundColor(getColor(context, R.color.pink))
            "poison" -> view.setCardBackgroundColor(getColor(context, R.color.purple))
            else -> view.setCardBackgroundColor(getColor(context, R.color.grey))
        }
    }

    interface OnMonsterSelected {
        fun onMonsterClicked(item : MonsterDetailsModel)
    }
}