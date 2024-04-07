package be.christiano.demopokedex.ui.team

import be.christiano.demopokedex.domain.model.Pokemon

data class TeamState(
    val teamPokemons: List<Pokemon> = emptyList()
)