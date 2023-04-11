package be.christiano.demopokedex.data.repository

import be.christiano.demopokedex.data.local.PokemonDatabase
import be.christiano.demopokedex.data.mapper.toPokemon
import be.christiano.demopokedex.data.mapper.toPokemonDetail
import be.christiano.demopokedex.data.mapper.toPokemonEntity
import be.christiano.demopokedex.data.remote.PokemonApi
import be.christiano.demopokedex.domain.model.Pokemon
import be.christiano.demopokedex.domain.repository.PokemonRepo
import be.christiano.demopokedex.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class PokemonRepoImpl(
    private val api: PokemonApi,
    db: PokemonDatabase
) : PokemonRepo {

    private val dao = db.dao
    override fun findPokemons(query: String) = dao.findAllFlow(query).map { it.map { entity -> entity.toPokemon() } }

    override fun findPokemonByIdFlow(id: Int) = dao.findByIdFlow(id.toLong()).map { it?.toPokemonDetail() }

    override suspend fun fetchPokemons(): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading(true))

            val remotePokemons = try {
                api.fetchPokemons()
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
                dao.upsertPokemons(list.map { it.toPokemonEntity() })
                emit(Resource.Success(Unit))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun fetchPokemon(id: Long): Flow<Resource<Unit>> {
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
                dao.upsertPokemon(mon.toPokemonEntity())
                emit(Resource.Success(Unit))
                emit(Resource.Loading(false))
            }
        }
    }
}