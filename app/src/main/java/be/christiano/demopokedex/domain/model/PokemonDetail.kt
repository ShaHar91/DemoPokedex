package be.christiano.demopokedex.domain.model

import be.appwise.measurements.Measurement
import be.appwise.measurements.units.UnitLength
import be.appwise.measurements.units.UnitMass
import be.christiano.demopokedex.extensions.hectograms

data class PokemonDetail(
    val number: Int = 0,
    val sprites: Sprites = Sprites(),
    val name: String = "",
    val type1: String = "",
    val type2: String? = null,
    val ability1: String = "",
    val ability2: String? = null,
    val abilityHidden: String? = null,
    val height: Int = 0,
    val weight: Int = 0,
    val stats: List<Int> = emptyList()
)

fun PokemonDetail.listOfAbilities() = listOfNotNull(ability1, ability2, abilityHidden)

fun PokemonDetail.heightInMeters() = run {
    Measurement(height.toDouble(), UnitLength.decimeters).converted(UnitLength.meters).formattedDescription()
}

fun PokemonDetail.weightInKg() = run {
    Measurement(weight.toDouble(), UnitMass.hectograms).converted(UnitMass.kilograms).formattedDescription()
}

data class Sprites(
    val back_default: String? = null,
    val back_female: String? = null,
    val back_shiny: String? = null,
    val back_shiny_female: String? = null,
    val front_default: String = "",
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
)

//data class StatItem(
//    val value: Int = 0,
//    val identifier: String = ""
//)