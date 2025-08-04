package be.christiano.demopokedex.ui.pokedexList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.christiano.demopokedex.domain.repository.PokemonRepo
import be.christiano.demopokedex.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokedexListViewModel(
    private val repository: PokemonRepo
) : ViewModel(), PokedexListContract {

    private val _state = MutableStateFlow(PokedexListContract.State())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<PokedexListContract.Effect>()
    override val effect = _effect.asSharedFlow()

    var coroutineException by mutableStateOf<String?>(null)

    init {
        sendIntent(PokedexListContract.Intent.Refresh)

        combine(
            repository.findAmountOfFavoritesFlow(),
            repository.findAmountOfPokemonsInTeamFlow(),
            state.map { it.searchQuery }.debounce { if (it.isNotBlank()) 500 else 0 }.flatMapLatest {
                repository.findPokemons(it)
            }
        ) { amountOfFavorites, amountOfPokemonsInTeam, pokemons ->
            val currentState = _state.value
            currentState.copy(
                amountOfPokemonsInTeam = amountOfPokemonsInTeam,
                amountOfFavoritePokemons = amountOfFavorites,
                pokemons = pokemons
            )
        }.onEach { newState ->
            updateState { newState }
        }.launchIn(viewModelScope)
    }

    override fun sendIntent(intent: PokedexListContract.Intent) = viewModelScope.launch {
        when (intent) {
            is PokedexListContract.Intent.Refresh -> getPokemons()
            is PokedexListContract.Intent.OnSearchQueryChanged -> updateState { it.copy(searchQuery = intent.query) }
            is PokedexListContract.Intent.ToggleOrderSection -> Unit
        }
    }

    override fun emitEffect(effect: PokedexListContract.Effect) = viewModelScope.launch {
        _effect.emit(effect)
    }

    override fun updateState(block: (PokedexListContract.State) -> PokedexListContract.State) {
        _state.update(block)
    }

    private suspend fun getPokemons() {
        repository.fetchPokemons().collect { result ->
            when (result) {
                is Resource.Error -> coroutineException = result.message
                is Resource.Loading -> updateState { it.copy(isLoading = result.isLoading) }
                else -> Unit
            }
        }
    }
}