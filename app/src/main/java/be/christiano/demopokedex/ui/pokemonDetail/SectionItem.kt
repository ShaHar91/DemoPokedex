package be.christiano.demopokedex.ui.pokemonDetail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import be.christiano.demopokedex.ui.theme.DemoPokedexTheme

@Composable
fun SectionItem(labelText: String, modifier: Modifier = Modifier, labelWidthFraction: Float = 0.4f, content: @Composable RowScope.() -> Unit) {
    Row(modifier = modifier) {
        Text(modifier = Modifier.fillMaxWidth(labelWidthFraction), text = labelText)

        content()
    }
}

@Composable
fun StatisticSectionItem(
    labelText: String,
    staticsLabel: String,
    progress: Float,
    modifier: Modifier = Modifier,
    labelWidthFraction: Float = 0.25f,
    staticsWidthFraction: Float = 0.15f,
) {
    SectionItem(labelText = labelText, labelWidthFraction = labelWidthFraction, modifier = modifier) {
        Text(modifier = Modifier.fillMaxWidth(staticsWidthFraction), text = staticsLabel)

        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            progress = progress,
            strokeCap = StrokeCap.Round
        )
    }
}

@Preview
@Composable
fun SectionItemPreview() {
    DemoPokedexTheme {
        SectionItem("Label") {
            Text(text = "Content text")
        }
    }
}

@Preview
@Composable
fun StatisticSectionItemPreview() {
    DemoPokedexTheme {
        StatisticSectionItem("Label", "HP", 0.65f)
    }
}