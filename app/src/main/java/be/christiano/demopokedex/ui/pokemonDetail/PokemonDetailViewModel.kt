package be.christiano.demopokedex.ui.pokemonDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.christiano.demopokedex.domain.repository.PokemonRepo
import be.christiano.demopokedex.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val repository: PokemonRepo
) : ViewModel() {

    val state = MutableStateFlow(PokemonDetailState())
    var coroutineException by mutableStateOf<String?>(null)

    init {
        getPokemonDetail()
    }

    fun onEvent(event: PokemonDetailEvent) {

    }

    private fun getPokemonDetail() {
        viewModelScope.launch {
            repository.fetchPokemon(1)
                .collect { result ->
                    when (result) {
                        is Resource.Error ->{
                            coroutineException
                        }
                        else -> Unit
                    }
                }
        }
    }
}