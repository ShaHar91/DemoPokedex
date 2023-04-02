package be.christiano.demopokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import be.christiano.demopokedex.data.local.entities.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 1
)
abstract class PokemonDatabase : RoomDatabase() {

    abstract val dao: PokemonDao
}