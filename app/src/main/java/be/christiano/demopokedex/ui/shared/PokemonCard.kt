package be.christiano.demopokedex.ui.shared

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.christiano.demopokedex.domain.model.Pokemon
import be.christiano.demopokedex.domain.model.Sprites
import be.christiano.demopokedex.ui.theme.DemoPokedexTheme
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonCard(
    pokemon: Pokemon,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.size(50.dp),
                model = pokemon.sprites.front_default,
                contentDescription = "Image for ${pokemon.name}"
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = pokemon.name)
                Text(text = "Nr. ${pokemon.number}")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Row {
                TypeCard(type = Type.parseType(pokemon.type1))

                if (pokemon.type2 != null) {
                    Spacer(modifier = Modifier.width(6.dp))
                    TypeCard(type = Type.parseType(pokemon.type2))
                }
            }

            Icon(Icons.Default.KeyboardArrowRight, tint = Color(0xFFBABEC2), contentDescription = null)
        }
    }
}


@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PokemonCardPreview() {
    DemoPokedexTheme {
        PokemonCard(pokemon = Pokemon(1, Sprites(front_default = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"), "Bulbasaur", "Water", "Ghost"), modifier = Modifier.fillMaxWidth()) {}
    }
}