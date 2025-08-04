package be.christiano.demopokedex.ui.pokemonDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.christiano.demopokedex.domain.model.PokemonDetail
import be.christiano.demopokedex.domain.repository.PokemonRepo
import be.christiano.demopokedex.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val pokemon: PokemonDetail,
    private val repository: PokemonRepo
) : ViewModel(), PokemonDetailContract {

    private val _state = MutableStateFlow(PokemonDetailContract.State())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<PokemonDetailContract.Effect>()
    override val effect = _effect.asSharedFlow()

    private var coroutineException by mutableStateOf<String?>(null)

    init {
        sendIntent(PokemonDetailContract.Intent.Refresh)

        combine(
            repository.findPokemonByIdFlow(pokemon.number),
            repository.findAmountOfPokemonsInTeamFlow()
        ) { pokemon, amountOfPokemonsInTeam ->
            val currentState = _state.value
            currentState.copy(
                pokemon = pokemon,
                amountOfPokemonsInTeam = amountOfPokemonsInTeam,
            )
        }.onEach { newState ->
            updateState { newState }
        }.launchIn(viewModelScope)
    }

    override fun sendIntent(intent: PokemonDetailContract.Intent) = viewModelScope.launch {
        when (intent) {
            PokemonDetailContract.Intent.Refresh -> getPokemonDetail()
            PokemonDetailContract.Intent.LikeUnlike -> updateFavoritePokemon()
            PokemonDetailContract.Intent.AddToTeam -> updateInTeam(true)
            PokemonDetailContract.Intent.RemoveFromTeam -> updateInTeam(false)
        }
    }

    override fun emitEffect(effect: PokemonDetailContract.Effect) = viewModelScope.launch {
        _effect.emit(effect)
    }

    override fun updateState(block: (PokemonDetailContract.State) -> PokemonDetailContract.State) {
        _state.update(block)
    }

    private suspend fun updateInTeam(isInTeam: Boolean) {
        val pok = state.value.pokemon ?: return
        repository.updateInTeam(pok.number, isInTeam)
    }

    private suspend fun updateFavoritePokemon() {
        val pok = state.value.pokemon ?: return
        repository.updateFavoritePokemon(pok.number, !pok.isFavorite)
    }

    private suspend fun getPokemonDetail() {
        repository.fetchPokemon(pokemon.number.toLong())
            .collect { result ->
                when (result) {
                    is Resource.Error -> coroutineException = result.message
                    is Resource.Loading -> updateState { it.copy(isLoading = result.isLoading) }
                    else -> Unit
                }
            }
    }
}