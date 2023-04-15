package be.christiano.demopokedex.data.local.utils

import androidx.room.TypeConverter
import be.christiano.demopokedex.domain.model.Sprites
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun convertSpritesToString(sprites: Sprites): String {
        return Gson().toJson(sprites)
    }

    @TypeConverter
    fun convertStringToSprites(sprites: String): Sprites {
        return Gson().fromJson(sprites, Sprites::class.java)
    }
}