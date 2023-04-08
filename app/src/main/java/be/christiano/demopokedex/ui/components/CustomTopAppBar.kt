package be.christiano.demopokedex.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import be.christiano.demopokedex.ui.theme.DemoPokedexTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MyLargeTopAppBar(
    toolbarTitle: String,
    navController: NavController,
    behavior: @Composable () -> TopAppBarScrollBehavior = { TopAppBarDefaults.exitUntilCollapsedScrollBehavior() },
    actions: @Composable RowScope.() -> Unit = {},
) {

    val collapsed = 17
    val expanded = 34

    val topAppBarTextSize = (collapsed + (expanded - collapsed) * (1 - behavior().state.collapsedFraction)).sp

    MediumTopAppBar(
        modifier = Modifier.fillMaxWidth(),
        scrollBehavior = behavior(),
        actions = actions,
        colors = TopAppBarDefaults.largeTopAppBarColors(
            scrolledContainerColor = Color.Transparent,
            containerColor = Color.Transparent
        ),
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.Default.ArrowBack, "Navigation icon")
                }
            }
        },
        title = {
            Text(
                text = toolbarTitle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontSize = topAppBarTextSize
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MyLargeTopAppBarPreview() {
    DemoPokedexTheme {
        MyLargeTopAppBar("Pokédex", rememberNavController())
    }
}