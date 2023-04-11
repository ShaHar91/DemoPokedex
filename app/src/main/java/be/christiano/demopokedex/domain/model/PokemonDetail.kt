package be.christiano.demopokedex.domain.model

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
    val weight: Int = 0
)