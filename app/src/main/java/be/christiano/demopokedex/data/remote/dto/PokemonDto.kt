package be.christiano.demopokedex.data.remote.dto


interface BasePokemonDto {
    val id: Long
    val name: String
    val sprites: BaseSpriteDto
    val types: List<TypeItemDto>
}

data class PokemonSimpleDto(
    override val id: Long,
    override val name: String,
    override val sprites: SpritesSimpleDto,
    override val types: List<TypeItemDto>
) : BasePokemonDto

data class PokemonDto(
    override val id: Long,
    override val name: String,
    override val sprites: SpritesDto,
    override val types: List<TypeItemDto>,
    val abilities: List<AbilityItemDto>,
    val base_experience: Int,
    val forms: List<FormDto>,
    val height: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: List<MoveItemDto>,
    val order: Int,
    val past_types: List<Any>,
    val species: SpeciesDto,
    val stats: List<StatItemDto>,
    val weight: Int
) : BasePokemonDto

data class AbilityItemDto(
    val ability: AbilityDto,
    val is_hidden: Boolean,
    val slot: Int
)

data class FormDto(
    val name: String,
    val url: String
)

data class MoveItemDto(
    val move: Move,
    val version_group_details: List<VersionGroupDetailDto>
)

data class SpeciesDto(
    val name: String,
    val url: String
)

interface BaseSpriteDto {
    val front_default: String
}

data class SpritesSimpleDto(
    override val front_default: String
) : BaseSpriteDto

data class SpritesDto(
    val back_default: String,
    val back_female: String,
    val back_shiny: String,
    val back_shiny_female: String,
    override val front_default: String,
    val front_female: String,
    val front_shiny: String,
    val front_shiny_female: String,
    val other: Other
) : BaseSpriteDto

data class StatItemDto(
    val base_stat: Int,
    val effort: Int,
    val stat: StatDto
)

data class TypeItemDto(
    val slot: Int,
    val type: TypeDto
)

data class AbilityDto(
    val name: String,
    val url: String
)

data class Move(
    val name: String,
    val url: String
)

data class VersionGroupDetailDto(
    val level_learned_at: Int,
    val move_learn_method: MoveLearnMethodDto,
    val version_group: VersionGroupDto
)

data class MoveLearnMethodDto(
    val name: String,
    val url: String
)

data class VersionGroupDto(
    val name: String,
    val url: String
)

data class Other(
    val dream_world: DreamWorldDto,
    val home: Home
//    val official-artwork: OfficialArtworkDto
)

data class DreamWorldDto(
    val front_default: String,
    val front_female: Any
)

data class Home(
    val front_default: String,
    val front_female: Any,
    val front_shiny: String,
    val front_shiny_female: Any
)

data class OfficialArtworkDto(
    val front_default: String
)

data class StatDto(
    val name: String
)

data class TypeDto(
    val name: String
)