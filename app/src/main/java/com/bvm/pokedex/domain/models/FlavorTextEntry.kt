package com.bvm.pokedex.domain.models

data class FlavorTextEntry(
    val flavor_text: String,
    val language: Language,
    val version: Version
)