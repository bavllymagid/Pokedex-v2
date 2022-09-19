package com.bvm.pokedex.domain.models

data class PokemonModel(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Pokemon>
)