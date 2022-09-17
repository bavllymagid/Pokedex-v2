package com.bvm.pokedex.domain.models

import java.text.Normalizer

data class MonsterDetailsModel(
    val base_experience: Int,
    val forms: List<Normalizer.Form>,
    val height: Int,
    val held_items: List<Any>,
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val name: String,
    val order: Int,
    val past_types: List<Any>,
    val species: Species,
    val sprites: Sprites,
    val weight: Int
)