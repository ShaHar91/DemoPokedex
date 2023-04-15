package be.christiano.demopokedex.domain.model

import be.appwise.measurements.Measurement
import be.appwise.measurements.converters.UnitConverterLinear
import be.appwise.measurements.units.UnitEnergy
import be.appwise.measurements.units.UnitLength
import be.appwise.measurements.units.UnitMass
import be.christiano.demopokedex.extensions.hectograms

data class PokemonDetail(
    val number: Int = 0,
    val frontDefault: String = "",
    val name: String = "",
    val type1: String = "",
    val type2: String? = null,
    val ability1: String = "",
    val ability2: String? = null,
    val abilityHidden: String? = null,
    val height: Int = 0,
    val weight: Int = 0,
    val species: String = ""
)

fun PokemonDetail.listOfAbilities() = listOfNotNull(ability1, ability2, abilityHidden)

fun PokemonDetail.heightInMeters() = run {
    Measurement(height.toDouble(), UnitLength.decimeters).converted(UnitLength.meters).formattedDescription()
}

fun PokemonDetail.weightInKg() = run {
    Measurement(weight.toDouble(), UnitMass.hectograms).converted(UnitMass.kilograms).formattedDescription()
}