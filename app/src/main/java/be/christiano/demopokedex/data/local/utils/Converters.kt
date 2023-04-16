package be.christiano.demopokedex.data.local.utils

import androidx.room.TypeConverter
import be.christiano.demopokedex.domain.model.Sprites
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun convertSpritesToString(sprites: Sprites): String {
        return Gson().toJson(sprites)
    }

    @TypeConverter
    fun convertStringToSprites(sprites: String): Sprites {
        return Gson().fromJson(sprites, Sprites::class.java)
    }

    private val intListType = object : TypeToken<List<Int>?>() {}.type

    @TypeConverter
    fun fromIntList(list: List<Int>): String {
        return Gson().toJson(list, intListType)
    }

    @TypeConverter
    fun toIntList(json: String): List<Int> {
        return Gson().fromJson(json, intListType)
    }

}