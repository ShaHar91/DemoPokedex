package be.christiano.demopokedex.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// The ColumnInfo with a default value was needed as long as the field was NOT-NULL... -.-'
@Entity("pokemon")
data class PokemonEntity(
    @PrimaryKey override val id: Long = 0L,
    override val frontDefault: String = "",
    override val name: String = "",
    override val type1: String = "",
    override val type2: String? = null,
    @ColumnInfo(defaultValue = "")
    val ability1: String = "",
    val ability2: String? = null,
    val abilityHidden: String? = null,
    @ColumnInfo(defaultValue = "0")
    val baseExperience: Int = 0,
    @ColumnInfo(defaultValue = "0")
    val height: Int = 0,
    @ColumnInfo(defaultValue = "false")
    val isDefault: Boolean = false,
    @ColumnInfo(defaultValue = "0")
    val order: Int? = 0,
    @ColumnInfo(defaultValue = "0")
    val weight: Int = 0
) : BasePokemonEntity

data class SimplePokemonEntity(
    override val id: Long = 0L,
    override val frontDefault: String,
    override val name: String = "",
    override val type1: String = "",
    override val type2: String? = null
) : BasePokemonEntity


interface BasePokemonEntity {
    val id: Long
    val frontDefault: String
    val name: String
    val type1: String
    val type2: String?
}

//TODO: sprite will be a new Dao with a relation to this pokemon
//TODO: forms will be a new Dao with a relation to this pokemon
//TODO: stats will be a new Dao with a relation to this pokemon

//TODO: moves will be added to a new Dao as a standalone moveDao -- A LinkedDao will be created to have a relation mapping from this pokemon to the specific move set


