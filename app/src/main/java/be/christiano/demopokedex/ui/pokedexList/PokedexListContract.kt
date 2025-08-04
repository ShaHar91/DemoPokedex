package be.christiano.demopokedex.ui.pokedexList

import be.christiano.demopokedex.domain.model.Pokemon
import be.christiano.demopokedex.util.MVI

interface PokedexListContract : MVI<PokedexListContract.State, PokedexListContract.Intent, PokedexListContract.Effect> {
    data class State(
        val pokemons: List<Pokemon> = emptyList(),
        val amountOfFavoritePokemons: Int = 0,
        val amountOfPokemonsInTeam: Int = 0,
        val isLoading: Boolean = false,
        val isRefreshing: Boolean = false,
        val searchQuery: String = ""
    )

    sealed class Intent {
        data class OnSearchQueryChanged(val query: String) : Intent()
        object Refresh : Intent()
        object ToggleOrderSection : Intent() // TODO: not sure if needed!!
    }

    sealed class Effect
}
