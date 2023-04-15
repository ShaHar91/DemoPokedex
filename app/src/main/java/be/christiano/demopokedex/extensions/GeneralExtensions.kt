package be.christiano.demopokedex.extensions

import be.appwise.measurements.converters.UnitConverterLinear
import be.appwise.measurements.units.UnitMass

val UnitMass.Companion.hectograms get() = UnitMass("hg", UnitConverterLinear(1e-1))
val UnitMass.Companion.decagrams get() = UnitMass("dag", UnitConverterLinear(1e-2))
