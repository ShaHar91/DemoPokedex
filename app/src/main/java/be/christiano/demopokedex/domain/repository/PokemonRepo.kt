package be.christiano.demopokedex.domain.repository

import be.christiano.demopokedex.domain.model.Pokemon
import be.christiano.demopokedex.domain.model.PokemonDetail
import be.christiano.demopokedex.util.Resource
import kotlinx.coroutines.flow.Flow

interface PokemonRepo {

    fun findPokemons(query: String) : Flow<List<Pokemon>>

    fun findPokemonByIdFlow(id: Int): Flow<PokemonDetail?>

    suspend fun fetchPokemons(): Flow<Resource<Unit>>

    suspend fun fetchPokemon(id:Long): Flow<Resource<Unit>>
}