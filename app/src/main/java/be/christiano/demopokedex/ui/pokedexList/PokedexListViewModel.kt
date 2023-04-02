package be.christiano.demopokedex.ui.pokedexList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.christiano.demopokedex.domain.repository.PokemonRepo
import be.christiano.demopokedex.util.Resource
import kotlinx.coroutines.launch

class PokedexListViewModel(
    private val repository: PokemonRepo
) : ViewModel() {

    var state by mutableStateOf(PokedexListState())

    init {
        getPokemons(fetchFromRemote = true)
    }

    fun onEvent(event: PokedexListEvent) {
        when (event) {
            PokedexListEvent.Refresh -> getPokemons(fetchFromRemote = true)
            PokedexListEvent.OnSearchQueryChanged -> {}
            PokedexListEvent.ToggleOrderSection -> {}
        }
    }

    private fun getPokemons(
        query: String = "",
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            repository.fetchPokemons(fetchFromRemote, query)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let {
                                state = state.copy(pokemons = it)
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }
}