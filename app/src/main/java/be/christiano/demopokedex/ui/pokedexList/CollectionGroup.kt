package be.christiano.demopokedex.ui.pokedexList

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.christiano.demopokedex.R
import be.christiano.demopokedex.ui.theme.DemoPokedexTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionGroup(
    modifier: Modifier = Modifier,
    title: String = "",
    subTitle: String = "",
    gradientColors: List<Color> = emptyList(),
    onClick: () -> Unit
) {
    ElevatedCard(modifier = modifier, onClick = onClick) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(gradientColors)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.alpha(0.5f),
                    color = Color.White,
                    text = subTitle,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Normal
                )
            }

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = 13.dp)
                    .alpha(0.2f),
                alignment = Alignment.CenterEnd,
                imageVector = ImageVector.vectorResource(R.drawable.ic_pokeball),
                contentDescription = "see through pokeball"
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CollectionGroupPreview() {
    DemoPokedexTheme {
        CollectionGroup(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth(),
            title = "My team",
            subTitle = "0 pokémons",
            gradientColors = listOf(
                Color(0xFF46469C),
                Color(0xFF7E32E0)
            )
        ) { }
    }
}