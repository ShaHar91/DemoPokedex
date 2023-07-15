package be.christiano.demopokedex.ui.favorites

import be.christiano.demopokedex.domain.model.Pokemon

data class FavoritesState(
    val favoritePokemons: List<Pokemon> = emptyList()
)