package com.bvm.pokedex.domain.models

data class PokeSpecies(
    val base_happiness: Int,
    val capture_rate: Int,
    val color: Color,
    val egg_groups: List<EggGroup>,
    val evolves_from_species: EvolvesFromSpecies,
    val flavor_text_entries: List<FlavorTextEntry>,
    val gender_rate: Int,
    val generation: Generation,
    val growth_rate: GrowthRate,
    val habitat: Habitat,
    val has_gender_differences: Boolean,
    val hatch_counter: Int,
    val id: Int,
    val is_baby: Boolean,
    val is_legendary: Boolean,
    val is_mythical: Boolean,
    val order: Int,
    val varieties: List<Variety>
)