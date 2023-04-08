package be.christiano.demopokedex.ui.pokemonDetail

import be.christiano.demopokedex.domain.model.Pokemon

data class PokemonDetailState(
    val pokemon: Pokemon? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isFavorite: Boolean = false,
    val isInTeam: Boolean = false
)