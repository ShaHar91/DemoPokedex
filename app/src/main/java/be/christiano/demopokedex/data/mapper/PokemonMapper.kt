package be.christiano.demopokedex.data.mapper

import be.christiano.demopokedex.data.local.entities.PokemonEntity
import be.christiano.demopokedex.data.local.entities.SimplePokemonEntity
import be.christiano.demopokedex.data.remote.dto.PokemonDto
import be.christiano.demopokedex.data.remote.dto.PokemonSimpleDto
import be.christiano.demopokedex.data.remote.dto.SpritesDto
import be.christiano.demopokedex.domain.model.Pokemon
import be.christiano.demopokedex.domain.model.PokemonDetail
import be.christiano.demopokedex.domain.model.Sprites

fun SpritesDto.toSprites() = Sprites(
    back_default,
    back_female,
    back_shiny,
    back_shiny_female,
    front_default,
    front_female,
    front_shiny,
    front_shiny_female
)

fun PokemonSimpleDto.toPokemonEntity() = SimplePokemonEntity(
    id,
    Sprites(front_default = sprites.front_default),
    name.replaceFirstChar { it.uppercaseChar() },
    types.find { it.slot == 1 }?.type?.name ?: "",
    types.find { it.slot == 2 }?.type?.name
)

fun SimplePokemonEntity.toPokemon() = Pokemon(
    id.toInt(),
    sprites,
    name,
    type1,
    type2
)

fun PokemonEntity.toPokemonDetail() = PokemonDetail(
    id.toInt(),
    sprites,
    name,
    type1,
    type2,
    ability1,
    ability2,
    abilityHidden,
    height,
    weight,
    stats
)

fun PokemonDto.toPokemonEntity() = PokemonEntity(
    id,
    sprites.toSprites(),
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
    weight,
    listOf(
        stats[0].base_stat,
        stats[1].base_stat,
        stats[2].base_stat,
        stats[3].base_stat,
        stats[4].base_stat,
        stats[5].base_stat
    )
)