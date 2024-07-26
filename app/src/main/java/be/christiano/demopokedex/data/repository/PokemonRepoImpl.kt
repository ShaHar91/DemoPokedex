package be.christiano.demopokedex.data.repository

import be.christiano.demopokedex.data.local.PokemonDatabase
import be.christiano.demopokedex.data.local.entities.SimplePokemonEntity
import be.christiano.demopokedex.data.mapper.toDetailedPokemonEntity
import be.christiano.demopokedex.data.mapper.toPokemon
import be.christiano.demopokedex.data.mapper.toPokemonDetail
import be.christiano.demopokedex.data.remote.PokemonApi
import be.christiano.demopokedex.domain.model.PokemonInTeam
import be.christiano.demopokedex.domain.model.PokemonIsFavorite
import be.christiano.demopokedex.domain.model.Sprites
import be.christiano.demopokedex.domain.repository.PokemonRepo
import be.christiano.demopokedex.util.Resource
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import be.christiano.demopokedex.GetPokemonsQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class PokemonRepoImpl(
    private val api: PokemonApi,
    private val apolloClient: ApolloClient,
    db: PokemonDatabase
) : PokemonRepo {

    private val dao = db.dao
    override fun findPokemons(query: String) = dao.findAllFlow(query).map { it.map { entity -> entity.toPokemon() } }

    override fun findAmountOfFavoritesFlow() = dao.findAmountOfFavoritesFlow()

    override fun findFavoritesFlow() = dao.findFavoritesFlow().map { it.map { entity -> entity.toPokemon() } }

    override fun findAmountOfPokemonsInTeamFlow() = dao.findAmountOfPokemonsInTeamFlow()

    override fun findTeamPokemonsFlow() = dao.findTeamPokemonsFlow().map { it.map { entity -> entity.toPokemon() } }

    override fun findPokemonByIdFlow(id: Int) = dao.findByIdFlow(id.toLong()).map { it?.toPokemonDetail() }

    override fun fetchPokemons(): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading(true))

            val remotePokemons = try {
                val response = apolloClient.query(GetPokemonsQuery()).fetchPolicy(FetchPolicy.CacheFirst).execute()
                response.data?.pokemon_v2_pokemon.orEmpty().map {
                    SimplePokemonEntity(
                        id = it.id.toLong(),
                        name = it.name,
                        sprites = Sprites(front_default = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${it.id}.png"),
                        type1 = it.pokemon_v2_pokemontypes.first().pokemon_v2_type?.type?.name ?: "",
                        type2 = it.pokemon_v2_pokemontypes.last().pokemon_v2_type?.type?.name,
                    )
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.Loading(false))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.Loading(false))
                null
            }

            remotePokemons?.let { list ->
                dao.upsertPokemons(list)
                emit(Resource.Success(Unit))
                emit(Resource.Loading(false))
            }
        }
    }

    override fun fetchPokemon(id: Long): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading(true))

            val remotePokemon = try {
                api.fetchPokemon(id)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remotePokemon?.let { mon ->
                dao.upsertPokemon(mon.toDetailedPokemonEntity())
                emit(Resource.Success(Unit))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun updateFavoritePokemon(id: Int, isFavorite: Boolean) {
        dao.upsertPokemonFavorite(PokemonIsFavorite(id.toLong(), isFavorite))
    }

    override suspend fun updateInTeam(id: Int, isInTeam: Boolean) {
        dao.upsertPokemonInTeam(PokemonInTeam(id.toLong(), isInTeam))
    }
}