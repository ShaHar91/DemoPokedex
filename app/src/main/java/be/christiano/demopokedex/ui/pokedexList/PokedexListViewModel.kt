package be.christiano.demopokedex.ui.pokedexList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.christiano.demopokedex.domain.repository.PokemonRepo
import be.christiano.demopokedex.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokedexListViewModel(
    private val repository: PokemonRepo
) : ViewModel() {

    val state = MutableStateFlow(PokedexListState())

    var coroutineException by mutableStateOf<String?>(null)

    init {
        getPokemons()

        state.map { it.searchQuery }.debounce { if (it.isNotBlank()) 500 else 0 }.flatMapLatest {
            repository.findPokemons(it)
        }.onEach { list ->
            state.update { it.copy(pokemons = list) }
        }.launchIn(viewModelScope)

        repository.findAmountOfFavoritesFlow().onEach { amount ->
            state.update { it.copy(amountOfFavoritePokemons = amount) }
        }.launchIn(viewModelScope)

        repository.findAmountOfPokemonsInTeamFlow().onEach { amount ->
            state.update { it.copy(amountOfPokemonsInTeam = amount) }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: PokedexListEvent) {
        when (event) {
            PokedexListEvent.Refresh -> getPokemons()
            is PokedexListEvent.OnSearchQueryChanged -> {
                state.update { it.copy(searchQuery = event.query) }
            }

            PokedexListEvent.ToggleOrderSection -> Unit
        }
    }

    private fun getPokemons() = viewModelScope.launch {
        repository.fetchPokemons().collect { result ->
            when (result) {
                is Resource.Error -> {
                    coroutineException = result.message
                }

                is Resource.Loading -> {
                    state.update { it.copy(isLoading = result.isLoading) }
                }

                else -> Unit
            }
        }
    }
}