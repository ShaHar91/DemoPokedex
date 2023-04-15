package be.christiano.demopokedex.domain.model

data class Pokemon(
    val number: Int = 0,
    val sprites: Sprites = Sprites(),
    val name: String = "",
    val type1: String = "",
    val type2: String? = null
)