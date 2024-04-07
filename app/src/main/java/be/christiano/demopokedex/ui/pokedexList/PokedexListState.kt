package be.christiano.demopokedex.ui.pokedexList

import be.christiano.demopokedex.domain.model.Pokemon

data class PokedexListState(
    val pokemons: List<Pokemon> = emptyList(),
    val amountOfFavoritePokemons: Int = 0,
    val amountOfPokemonsInTeam: Int = 0,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)