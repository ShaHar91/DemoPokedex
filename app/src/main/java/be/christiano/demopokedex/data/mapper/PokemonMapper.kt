package be.christiano.demopokedex.data.mapper

import be.christiano.demopokedex.data.local.entities.PokemonEntity
import be.christiano.demopokedex.data.local.entities.SimplePokemonEntity
import be.christiano.demopokedex.data.remote.dto.PokemonDto
import be.christiano.demopokedex.data.remote.dto.PokemonSimpleDto
import be.christiano.demopokedex.domain.model.Pokemon

fun PokemonSimpleDto.toPokemonEntity() = SimplePokemonEntity(
    id,
    name,
    types.find { it.slot == 1 }?.type?.name ?: "",
    types.find { it.slot == 2 }?.type?.name
)

fun SimplePokemonEntity.toPokemon() = Pokemon(
    id.toInt(),
    name,
    type1,
    type2
)

fun PokemonEntity.toPokemon() = Pokemon(
    id.toInt(),
    name,
    type1,
    type2
)

fun PokemonDto.toPokemonEntity() = PokemonEntity(
    id,
    name,
    types.find { it.slot == 1 }?.type?.name ?: "", //TODO: save the type in their own Table and get them trough an ID???? Not sure if this can work 🤔
    types.find { it.slot == 2 }?.type?.name, //TODO: save the type in their own Table and get them trough an ID???? Not sure if this can work 🤔
    abilities.find { it.slot == 1 }?.ability?.name ?: "", //TODO: save the abilities in their own Table and get them through an ID
    abilities.find { it.slot == 2 }?.ability?.name, //TODO: save the abilities in their own Table and get them through an ID
    abilities.find { it.slot == 3 }?.ability?.name, //TODO: save the abilities in their own Table and get them through an ID
    base_experience,
    height,
    is_default,
    order,
    0, // TODO: only save the ID... get it from the url!!
    weight,
)