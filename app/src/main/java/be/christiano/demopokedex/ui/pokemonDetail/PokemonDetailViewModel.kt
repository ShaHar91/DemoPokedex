package be.christiano.demopokedex.ui.pokemonDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.christiano.demopokedex.domain.model.PokemonDetail
import be.christiano.demopokedex.domain.repository.PokemonRepo
import be.christiano.demopokedex.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val pokemon: PokemonDetail,
    private val repository: PokemonRepo
) : ViewModel() {

    val state = MutableStateFlow(PokemonDetailState(pokemon))
    var coroutineException by mutableStateOf<String?>(null)

    init {
        getPokemonDetail()

        repository.findPokemonByIdFlow(pokemon.number).onEach {
            it ?: return@onEach
            state.tryEmit(state.value.copy(pokemon = it))
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: PokemonDetailEvent) {
        when (event) {
            PokemonDetailEvent.Refresh -> getPokemonDetail()
            PokemonDetailEvent.LikeUnlike -> updateFavoritePokemon()
            PokemonDetailEvent.AddToTeam -> updateInTeam(true)
            PokemonDetailEvent.RemoveFromTeam -> updateInTeam(false)
        }
    }

    private fun updateInTeam(isInTeam: Boolean) {
        viewModelScope.launch {
            val pok = state.value.pokemon ?: return@launch
            repository.updateInTeam(pok.number, isInTeam)
        }
    }

    private fun updateFavoritePokemon() {
        viewModelScope.launch {
            val pok = state.value.pokemon ?: return@launch
            repository.updateFavoritePokemon(pok.number, !pok.isFavorite)
        }
    }

    private fun getPokemonDetail() {
        viewModelScope.launch {
            repository.fetchPokemon(pokemon.number.toLong())
                .collect { result ->
                    when (result) {
                        is Resource.Error -> coroutineException = result.message
                        is Resource.Loading -> state.tryEmit(state.value.copy(isLoading = result.isLoading))
                        else -> Unit
                    }
                }
        }
    }
}