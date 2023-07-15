package be.christiano.demopokedex.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.christiano.demopokedex.domain.repository.PokemonRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class FavoritesViewModel(
    repository: PokemonRepo
) : ViewModel() {

    val state = MutableStateFlow(FavoritesState())

    init {
        repository.findFavoritesFlow().onEach { list ->
            state.update { it.copy(favoritePokemons = list) }
        }.launchIn(viewModelScope)
    }
}