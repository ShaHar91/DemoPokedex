package be.christiano.demopokedex.ui.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.christiano.demopokedex.domain.repository.PokemonRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class TeamViewModel(
    repository: PokemonRepo
) : ViewModel() {

    val state = MutableStateFlow(TeamState())

    init {
        repository.findTeamPokemonsFlow().onEach { list ->
            state.update { it.copy(teamPokemons = list) }
        }.launchIn(viewModelScope)
    }
}