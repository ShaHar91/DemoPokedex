package be.christiano.demopokedex.ui.pokedexList

sealed class PokedexListEvent {
    data class OnSearchQueryChanged(val query: String) : PokedexListEvent()
    object Refresh : PokedexListEvent()
    object ToggleOrderSection : PokedexListEvent() // TODO: not sure if needed!!
}
