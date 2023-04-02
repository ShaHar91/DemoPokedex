package be.christiano.demopokedex.ui.pokedexList

import be.christiano.demopokedex.domain.model.Pokemon

data class PokedexListState(
    val pokemons: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false
)