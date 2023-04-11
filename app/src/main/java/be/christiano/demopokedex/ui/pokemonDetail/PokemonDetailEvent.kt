package be.christiano.demopokedex.ui.pokemonDetail

sealed class PokemonDetailEvent {
    object Refresh: PokemonDetailEvent()
    object LikeUnlike: PokemonDetailEvent()
    object AddToTeam: PokemonDetailEvent()
}