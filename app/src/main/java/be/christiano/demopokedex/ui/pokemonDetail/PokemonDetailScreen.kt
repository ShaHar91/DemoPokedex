package be.christiano.demopokedex.ui.pokemonDetail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import be.christiano.demopokedex.domain.model.PokemonDetail
import be.christiano.demopokedex.domain.model.Sprites
import be.christiano.demopokedex.domain.model.heightInMeters
import be.christiano.demopokedex.domain.model.listOfAbilities
import be.christiano.demopokedex.domain.model.weightInKg
import be.christiano.demopokedex.ui.MainNavGraph
import be.christiano.demopokedex.ui.components.MyLargeTopAppBar
import be.christiano.demopokedex.ui.shared.Type
import be.christiano.demopokedex.ui.shared.TypeCard
import be.christiano.demopokedex.ui.theme.DemoPokedexTheme
import coil.compose.AsyncImage
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
        parametersOf(PokemonDetail(pokemonId, name = pokemonName))
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
    val scrollState = rememberScrollState()

    Box(contentAlignment = Alignment.BottomCenter) {
        Scaffold(
            Modifier
                .nestedScroll(behavior.nestedScrollConnection)
                .fillMaxSize(),
            topBar = {
                MyLargeTopAppBar(state.pokemon?.name ?: "", navController = navController, behavior = { behavior }) {
                    IconButton(onClick = { onEvent(PokemonDetailEvent.LikeUnlike) }) {
                        if (state.pokemon?.isFavorite == true) {
                            Icon(Icons.Filled.Favorite, contentDescription = "Unfavorite this pokemon")
                        } else {
                            Icon(Icons.Filled.FavoriteBorder, contentDescription = "Favorite this pokemon")
                        }
                    }
                }
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { contentPadding ->

            SwipeRefresh(modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding), state = swipeRefreshState, onRefresh = {
                onEvent(PokemonDetailEvent.Refresh)
            }) {

                Column(
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(vertical = 8.dp)
                ) {

                    AsyncImage(
                        modifier = Modifier
                            .size(200.dp)
                            .align(Alignment.CenterHorizontally),
                        model = state.pokemon?.sprites?.front_default,
                        contentDescription = "Image for ${state.pokemon?.name}"
                    )

                    Section(modifier = Modifier.padding(horizontal = 16.dp), headerText = "About") {
                        SectionItem(labelText = "Type") {
                            state.pokemon?.type1?.let {
                                if (it.isBlank()) return@let
                                TypeCard(type = Type.parseType(it))
                            }
                            state.pokemon?.type2?.let {
                                if (it.isBlank()) return@let
                                Spacer(modifier = Modifier.width(6.dp))

                                TypeCard(type = Type.parseType(it))
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        SectionItem(labelText = "Number") {
                            Text(text = state.pokemon?.number?.toString() ?: "")
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        SectionItem(labelText = "Hoogte") {
                            Text(text = state.pokemon?.heightInMeters() ?: "")
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        SectionItem(labelText = "Gewicht") {
                            Text(text = state.pokemon?.weightInKg() ?: "")
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        SectionItem(labelText = "Vaardigheden") {
                            Text(text = state.pokemon?.listOfAbilities()?.joinToString(", ") ?: "")
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Section(modifier = Modifier.padding(horizontal = 16.dp), headerText = "Statistics") {
                        StatisticSectionItem(labelText = "HP", staticsLabel = state.pokemon?.stats?.getOrNull(0).toString(), progress = state.pokemon?.stats?.getOrNull(0)?.div(200f) ?: 0f)

                        Spacer(modifier = Modifier.height(10.dp))

                        StatisticSectionItem(labelText = "Attack", staticsLabel = state.pokemon?.stats?.getOrNull(1).toString(), progress = state.pokemon?.stats?.getOrNull(1)?.div(200f) ?: 0f)

                        Spacer(modifier = Modifier.height(10.dp))

                        StatisticSectionItem(labelText = "Defense", staticsLabel = state.pokemon?.stats?.getOrNull(2).toString(), progress = state.pokemon?.stats?.getOrNull(2)?.div(200f) ?: 0f)

                        Spacer(modifier = Modifier.height(10.dp))

                        StatisticSectionItem(labelText = "Sp. Atk", staticsLabel = state.pokemon?.stats?.getOrNull(3).toString(), progress = state.pokemon?.stats?.getOrNull(3)?.div(200f) ?: 0f)

                        Spacer(modifier = Modifier.height(10.dp))

                        StatisticSectionItem(labelText = "Sp. Def", staticsLabel = state.pokemon?.stats?.getOrNull(4).toString(), progress = state.pokemon?.stats?.getOrNull(4)?.div(200f) ?: 0f)

                        Spacer(modifier = Modifier.height(10.dp))

                        StatisticSectionItem(labelText = "Speed", staticsLabel = state.pokemon?.stats?.getOrNull(5).toString(), progress = state.pokemon?.stats?.getOrNull(5)?.div(200f) ?: 0f)
                    }
                }
            }
        }

        if (state.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PokemonDetailScreenPreview() {
    DemoPokedexTheme {
        PokemonDetailScreenContent(
            rememberNavController(),
            PokemonDetailState(
                PokemonDetail(1, Sprites(), "Bulbasaur", "grass", "poison", "Overgrow", null, "Chlorophyl", 18, 20, listOf(45, 60, 48, 65, 65, 45), isFavorite = true)
            )
        ) {}
    }
}