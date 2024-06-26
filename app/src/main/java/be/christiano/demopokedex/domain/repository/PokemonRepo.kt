package be.christiano.demopokedex.domain.repository

import be.christiano.demopokedex.domain.model.Pokemon
import be.christiano.demopokedex.domain.model.PokemonDetail
import be.christiano.demopokedex.util.Resource
import kotlinx.coroutines.flow.Flow

interface PokemonRepo {

    fun findPokemons(query: String): Flow<List<Pokemon>>

    fun findAmountOfFavoritesFlow(): Flow<Int>

    fun findFavoritesFlow(): Flow<List<Pokemon>>

    fun findAmountOfPokemonsInTeamFlow(): Flow<Int>

    fun findTeamPokemonsFlow(): Flow<List<Pokemon>>

    fun findPokemonByIdFlow(id: Int): Flow<PokemonDetail?>

    fun fetchPokemons(): Flow<Resource<Unit>>

    fun fetchPokemon(id: Long): Flow<Resource<Unit>>

    suspend fun updateFavoritePokemon(id: Int, isFavorite: Boolean)
    suspend fun updateInTeam(id: Int, isInTeam: Boolean)
}