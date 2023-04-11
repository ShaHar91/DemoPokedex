package be.christiano.demopokedex.ui.pokemonDetail

import be.christiano.demopokedex.domain.model.PokemonDetail

data class PokemonDetailState(
    val pokemon: PokemonDetail? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isFavorite: Boolean = false,
    val isInTeam: Boolean = false
)