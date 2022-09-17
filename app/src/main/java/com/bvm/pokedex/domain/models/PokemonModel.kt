package com.bvm.pokedex.domain.models

data class PokemonModel(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Pokemon>
)