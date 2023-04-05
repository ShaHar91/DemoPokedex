package be.christiano.demopokedex.data.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Upsert
import be.christiano.demopokedex.data.local.entities.PokemonEntity
import be.christiano.demopokedex.data.local.entities.SimplePokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Upsert(PokemonEntity::class)
    suspend fun upsertPokemons(simplePokemons: List<SimplePokemonEntity>)

    @Upsert(PokemonEntity::class)
    suspend fun upsertPokemon(pokemonEntity: PokemonEntity)

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
}