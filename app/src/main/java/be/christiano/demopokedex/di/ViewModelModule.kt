package be.christiano.demopokedex.di

import be.christiano.demopokedex.ui.pokedexList.PokedexListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::PokedexListViewModel)
}