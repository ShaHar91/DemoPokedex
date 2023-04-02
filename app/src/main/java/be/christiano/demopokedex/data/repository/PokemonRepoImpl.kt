package be.christiano.demopokedex.data.repository

import be.christiano.demopokedex.data.local.PokemonDatabase
import be.christiano.demopokedex.data.mapper.toPokemon
import be.christiano.demopokedex.data.mapper.toPokemonEntity
import be.christiano.demopokedex.data.remote.PokemonApi
import be.christiano.demopokedex.domain.model.Pokemon
import be.christiano.demopokedex.domain.repository.PokemonRepo
import be.christiano.demopokedex.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class PokemonRepoImpl(
    private val api: PokemonApi,
    db: PokemonDatabase
) : PokemonRepo {

    private val dao = db.dao

    override suspend fun fetchPokemons(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<Pokemon>>> {
        return flow {
            emit(Resource.Loading(true))

            val pokemons = dao.findAll(query)
            emit(Resource.Success(pokemons.map { it.toPokemon() }))

            val isDbEmpty = pokemons.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remotePokemons = try {
                api.fetchPokemons()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remotePokemons?.let { list ->
                dao.upsertPokemons(list.map { it.toPokemonEntity() })
                emit(Resource.Success(dao.findAll("").map { it.toPokemon() }))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun fetchPokemon(id: Long): Flow<Resource<Pokemon>> {
        return flow {
            emit(Resource.Loading(true))

            val pokemon = dao.findById(id)
            emit(Resource.Success(pokemon?.toPokemon()))

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
                dao.upsertPokemon(mon.toPokemonEntity())
                emit(Resource.Success(dao.findById(id)?.toPokemon()))
                emit(Resource.Loading(false))
            }
        }
    }
}