package be.christiano.demopokedex.ui.pokedexList

sealed class PokedexListEvent {
    object OnSearchQueryChanged : PokedexListEvent()
    object Refresh : PokedexListEvent()
    object ToggleOrderSection : PokedexListEvent() // TODO: not sure if needed!!
}
