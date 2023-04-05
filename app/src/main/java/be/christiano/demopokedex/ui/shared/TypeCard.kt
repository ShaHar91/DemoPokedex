package be.christiano.demopokedex.ui.shared

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.christiano.demopokedex.ui.theme.DemoPokedexTheme

@Composable
fun TypeCard(
    type: Type,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .background(type.color, RoundedCornerShape(percent = 50))
            .padding(horizontal = 10.dp, vertical = 3.dp)
    ) {
        Text(
            text = type.typeName,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TypeCardPreview() {
    DemoPokedexTheme {
        TypeCard(type = Type.Grass)
    }
}

enum class Type(val typeName: String, val color: Color) {
    Normal("Normal", Color(0xFF979797)),
    Fighting("Fighting", Color(0xFFFF9D00)),
    Flying("Flying", Color(0xFF95C9FA)),
    Poison("Poison", Color(0xFF9750CD)),
    Ground("Ground", Color(0xFFA8773D)),
    Rock("Rock", Color(0xFFB5BC83)),
    Bug("Bug", Color(0xFFA1A323)),
    Ghost("Ghost", Color(0xFF6C4172)),
    Steel("Steel", Color(0xFF6BB0D4)),
    Fire("Fire", Color(0xFFFF5114)),
    Water("Water", Color(0xFF2E90FE)),
    Grass("Grass", Color(0xFF42BE29)),
    Electric("Electric", Color(0xFFFBD903)),
    Psychic("Psychic", Color(0xFFFE5F7C)),
    Ice("Ice", Color(0xFF46D5FD)),
    Dragon("Dragon", Color(0xFF5463D5)),
    Dark("Dark", Color(0xFF474747)),
    Fairy("Fairy", Color(0xFFFEAEFD));

    companion object {
        fun parseType(type: String): Type {
            return when (type) {
                "normal" -> Normal
                "fighting" -> Fighting
                "flying" -> Flying
                "poison" -> Poison
                "ground" -> Ground
                "rock" -> Rock
                "bug" -> Bug
                "ghost" -> Ghost
                "steel" -> Steel
                "fire" -> Fire
                "water" -> Water
                "grass" -> Grass
                "electric" -> Electric
                "psychic" -> Psychic
                "ice" -> Ice
                "dragon" -> Dragon
                "dark" -> Dark
                "fairy" -> Fairy
                else -> throw IllegalArgumentException()
            }
        }
    }
}

