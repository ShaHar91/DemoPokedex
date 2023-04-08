package be.christiano.demopokedex.ui.pokemonDetail

sealed class PokemonDetailEvent {
    object Refresh: PokemonDetailEvent()
}