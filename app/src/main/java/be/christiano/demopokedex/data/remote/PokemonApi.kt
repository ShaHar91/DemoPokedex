package be.christiano.demopokedex.data.remote

import be.christiano.demopokedex.data.remote.dto.PokemonDto
import be.christiano.demopokedex.data.remote.dto.PokemonSimpleDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface PokemonApi {

    @GET("")
    suspend fun fetchPokemons(
        @Url url: String = "https://stoplight.io/mocks/appwise-be/pokemon/57519009/pokemon"
    ): List<PokemonSimpleDto>

    @GET("{id}")
    suspend fun fetchPokemon(
        @Path("id") id: Long
    ): PokemonDto
}