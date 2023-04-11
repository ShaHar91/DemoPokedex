package be.christiano.demopokedex.ui.pokemonDetail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import be.christiano.demopokedex.domain.model.Pokemon
import be.christiano.demopokedex.ui.MainNavGraph
import be.christiano.demopokedex.ui.components.MyLargeTopAppBar
import be.christiano.demopokedex.ui.theme.DemoPokedexTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
@Destination
@MainNavGraph
fun PokemonDetailScreen(
    navController: NavController,
    pokemonId: Int,
    pokemonName: String
) {
    val viewModel = koinViewModel<PokemonDetailViewModel> {
        parametersOf(Pokemon(pokemonId, name = pokemonName))
    }
    val state by viewModel.state.collectAsState()

    PokemonDetailScreenContent(navController, state, viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreenContent(
    navController: NavController,
    state: PokemonDetailState,
    onEvent: (PokemonDetailEvent) -> Unit
) {
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = state.isRefreshing
    )

    val behavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }

    Box(contentAlignment = Alignment.BottomCenter) {
        Scaffold(
            Modifier
                .nestedScroll(behavior.nestedScrollConnection)
                .fillMaxSize(),
            topBar = {
                MyLargeTopAppBar(state.pokemon?.name ?: "", navController = navController, behavior = { behavior }) {
                    IconButton(onClick = { }) {
                        //TODO: add a difference when the pokemon is favorite or not!
                        Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Favorite this pokemon")
                    }
                }
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { contentPadding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {

                SwipeRefresh(state = swipeRefreshState, onRefresh = {
                    onEvent(PokemonDetailEvent.Refresh)
                }) {

                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(vertical = 8.dp)
                    ) {
                    }
                }
            }
        }

        if (state.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PokemonDetailScreenPreview() {
    DemoPokedexTheme {
        PokemonDetailScreenContent(
            rememberNavController(),
            PokemonDetailState()
        ) {}
    }
}