package be.christiano.demopokedex.di

import be.christiano.demopokedex.data.repository.PokemonRepoImpl
import be.christiano.demopokedex.domain.repository.PokemonRepo
import org.koin.dsl.module

val repoModule = module {

    single<PokemonRepo> {
        PokemonRepoImpl(get(), get(), get())
    }
}