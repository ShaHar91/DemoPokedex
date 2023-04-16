package be.christiano.demopokedex.data.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Upsert
import be.christiano.demopokedex.data.local.entities.DetailedPokemonEntity
import be.christiano.demopokedex.data.local.entities.PokemonEntity
import be.christiano.demopokedex.data.local.entities.SimplePokemonEntity
import be.christiano.demopokedex.domain.model.Pokemon
import be.christiano.demopokedex.domain.model.PokemonInTeam
import be.christiano.demopokedex.domain.model.PokemonIsFavorite
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Upsert(PokemonEntity::class)
    suspend fun upsertPokemons(simplePokemons: List<SimplePokemonEntity>)

    @Upsert(PokemonEntity::class)
    suspend fun upsertPokemon(pokemonEntity: DetailedPokemonEntity)

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM pokemon WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%'")
    fun findAllFlow(query: String): Flow<List<SimplePokemonEntity>>

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM pokemon WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%'")
    suspend fun findAll(query: String): List<SimplePokemonEntity>

    @Query("SELECT * FROM pokemon WHERE id = :id")
    fun findByIdFlow(id: Long): Flow<PokemonEntity?>

    @Query("SELECT * FROM pokemon WHERE id = :id")
    suspend fun findById(id: Long): PokemonEntity?

    @Upsert(PokemonEntity::class)
    suspend fun upsertPokemonFavorite(pokemonIsFavorite: PokemonIsFavorite)

    @Upsert(PokemonEntity::class)
    suspend fun upsertPokemonInTeam(pokemonIsInTeam: PokemonInTeam)

    @Query("SELECT COUNT() FROM pokemon WHERE isFavorite = 1")
    fun findAmountOfFavoritesFlow(): Flow<Int>

    @Query("SELECT * FROM pokemon WHERE isFavorite = 1")
    fun findFavoritesFlow(): Flow<List<SimplePokemonEntity>>
}