package be.christiano.demopokedex.di

import be.christiano.demopokedex.ui.favorites.FavoritesViewModel
import be.christiano.demopokedex.ui.pokedexList.PokedexListViewModel
import be.christiano.demopokedex.ui.pokedexList.filter.BottomSheetFilterViewModel
import be.christiano.demopokedex.ui.pokemonDetail.PokemonDetailViewModel
import be.christiano.demopokedex.ui.team.TeamViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::PokedexListViewModel)
    viewModelOf(::PokemonDetailViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::TeamViewModel)
    viewModelOf(::BottomSheetFilterViewModel)
}