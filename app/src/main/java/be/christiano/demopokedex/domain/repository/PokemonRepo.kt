package be.christiano.demopokedex.domain.repository

import be.christiano.demopokedex.domain.model.Pokemon
import be.christiano.demopokedex.util.Resource
import kotlinx.coroutines.flow.Flow

interface PokemonRepo {

    suspend fun fetchPokemons(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<Pokemon>>>

    suspend fun fetchPokemon(id:Long): Flow<Resource<Pokemon>>
}