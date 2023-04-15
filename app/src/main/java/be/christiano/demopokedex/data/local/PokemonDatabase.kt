package be.christiano.demopokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import be.christiano.demopokedex.data.local.daos.PokemonDao
import be.christiano.demopokedex.data.local.entities.PokemonEntity
import be.christiano.demopokedex.data.local.utils.Converters

@Database(
    entities = [PokemonEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {

    abstract val dao: PokemonDao
}