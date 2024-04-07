package be.christiano.demopokedex.ui.pokemonDetail

import be.christiano.demopokedex.domain.model.PokemonDetail

data class PokemonDetailState(
    val pokemon: PokemonDetail? = null,
    val amountOfPokemonsInTeam: Int = 0,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false
) {
    val isInTeam get() = pokemon?.isInTeam == true

    val canAddToTeam get() = (!isInTeam && amountOfPokemonsInTeam < 6) || isInTeam
}