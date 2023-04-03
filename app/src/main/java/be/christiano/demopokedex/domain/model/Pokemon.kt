package be.christiano.demopokedex.domain.model

data class Pokemon(
    val number: Int,
    val frontDefault: String,
    val name: String,
    val type1: String,
    val type2: String?
)