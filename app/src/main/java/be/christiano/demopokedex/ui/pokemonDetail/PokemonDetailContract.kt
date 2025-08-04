package be.christiano.demopokedex.ui.pokemonDetail

import be.christiano.demopokedex.domain.model.PokemonDetail
import be.christiano.demopokedex.util.MVI

interface PokemonDetailContract: MVI<PokemonDetailContract.State, PokemonDetailContract.Intent, PokemonDetailContract.Effect> {
    data class State(
        val pokemon: PokemonDetail? = null,
        val amountOfPokemonsInTeam: Int = 0,
        val isLoading: Boolean = false,
        val isRefreshing: Boolean = false
    ) {
        val isInTeam get() = pokemon?.isInTeam == true

        val canAddToTeam get() = (!isInTeam && amountOfPokemonsInTeam < 6) || isInTeam
    }

    sealed class Intent {
        object Refresh : Intent()
        object LikeUnlike : Intent()
        object AddToTeam : Intent()
        object RemoveFromTeam : Intent()
    }

    sealed class Effect
}
