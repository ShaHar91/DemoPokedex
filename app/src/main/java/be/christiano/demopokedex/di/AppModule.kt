package be.christiano.demopokedex.di

import androidx.room.Room
import be.christiano.demopokedex.data.local.PokemonDatabase
import be.christiano.demopokedex.data.remote.PokemonApi
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.CacheKey
import com.apollographql.apollo3.cache.normalized.api.CacheKeyGenerator
import com.apollographql.apollo3.cache.normalized.api.CacheKeyGeneratorContext
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        GsonBuilder()
            .setPrettyPrinting()
            .create()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .build()
            .create(PokemonApi::class.java)
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            PokemonDatabase::class.java,
            "pokemon.db"
        ).build()
    }

    single {
        // Create a 10MB NormalizedCacheFactory
        val cacheFactory = MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)

        ApolloClient.Builder()
            .serverUrl("https://beta.pokeapi.co/graphql/v1beta")
            .normalizedCache(cacheFactory, generator)
            .build()
    }
}

private val generator: CacheKeyGenerator = object : CacheKeyGenerator {
    override fun cacheKeyForObject(obj: Map<String, Any?>, context: CacheKeyGeneratorContext): CacheKey? {
        try {
            val typename = context.field.type.rawType().name

            val id = if (obj.containsKey("id")) {
                obj["id"].toString()
            } else {
                val child = obj.entries.first()

                if (child.value is LinkedHashMap<*, *>) {
                    (child.value as? LinkedHashMap<*, *>)?.get("id").toString()
                } else throw Exception("something")
            }

            return CacheKey(typename, id)
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
    }
}
