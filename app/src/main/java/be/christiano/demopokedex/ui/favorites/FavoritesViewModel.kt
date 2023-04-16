package be.christiano.demopokedex.ui.favorites

import androidx.lifecycle.ViewModel
import be.christiano.demopokedex.domain.repository.PokemonRepo

class FavoritesViewModel(
    private val repository: PokemonRepo
) : ViewModel() {

}