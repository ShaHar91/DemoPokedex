package be.christiano.demopokedex.data.mapper

import be.christiano.demopokedex.data.local.entities.PokemonEntity
import be.christiano.demopokedex.data.local.entities.SimplePokemonEntity
import be.christiano.demopokedex.data.remote.dto.PokemonDto
import be.christiano.demopokedex.data.remote.dto.PokemonSimpleDto
import be.christiano.demopokedex.domain.model.Pokemon
import be.christiano.demopokedex.domain.model.PokemonDetail

fun PokemonSimpleDto.toPokemonEntity() = SimplePokemonEntity(
    id,
    sprites.front_default,
    name.replaceFirstChar { it.uppercaseChar() },
    types.find { it.slot == 1 }?.type?.name ?: "",
    types.find { it.slot == 2 }?.type?.name
)

fun SimplePokemonEntity.toPokemon() = Pokemon(
    id.toInt(),
    frontDefault,
    name,
    type1,
    type2
)

fun PokemonEntity.toPokemon() = Pokemon(
    id.toInt(),
    frontDefault,
    name,
    type1,
    type2
)

fun PokemonEntity.toPokemonDetail() = PokemonDetail(
    id.toInt(),
    frontDefault,
    name,
    type1,
    type2,
    ability1,
    ability2,
    abilityHidden,
    height,
    weight
)

fun PokemonDto.toPokemonEntity() = PokemonEntity(
    id,
    sprites.front_default,
    name.replaceFirstChar { it.uppercaseChar() },
    types.find { it.slot == 1 }?.type?.name ?: "",
    types.find { it.slot == 2 }?.type?.name,
    abilities.find { it.slot == 1 }?.ability?.name ?: "",
    abilities.find { it.slot == 2 }?.ability?.name,
    abilities.find { it.slot == 3 }?.ability?.name,
    base_experience,
    height,
    is_default,
    order,
    0, // TODO: only save the ID... get it from the url!!
    weight,
)