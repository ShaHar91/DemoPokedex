package be.christiano.demopokedex.ui.pokedexList

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import be.christiano.demopokedex.R
import be.christiano.demopokedex.domain.model.Pokemon
import be.christiano.demopokedex.ui.MainNavGraph
import be.christiano.demopokedex.ui.components.MyLargeTopAppBar
import be.christiano.demopokedex.ui.destinations.PokemonDetailScreenDestination
import be.christiano.demopokedex.ui.shared.PokemonCard
import be.christiano.demopokedex.ui.theme.DemoPokedexTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination
@MainNavGraph(start = true)
fun PokedexListScreen(
    navigator: NavController
) {
    val viewModel = koinViewModel<PokedexListViewModel>()
    val state by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = viewModel.coroutineException) {
        viewModel.coroutineException?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.coroutineException = null
        }
    }

    PokedexListScreenContent(state = state, navigator = navigator, viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexListScreenContent(
    state: PokedexListState,
    navigator: NavController,
    onEvent: (PokedexListEvent) -> Unit
) {
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = state.isRefreshing
    )

    val behavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(contentAlignment = Alignment.BottomCenter) {
        Scaffold(
            Modifier
                .nestedScroll(behavior.nestedScrollConnection)
                .fillMaxSize(),
            topBar = {
                MyLargeTopAppBar("Pokédex", navigator, behavior = { behavior }) {
                    IconButton(onClick = { }) {
                        Icon(ImageVector.vectorResource(id = R.drawable.ic_sort), contentDescription = "Sort")
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
                    onEvent(PokedexListEvent.Refresh)
                }) {

                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(vertical = 8.dp)
                    ) {

                        // Searchbar has to be outside of a scrollable view/layout since it opens up a complete new "screen" for the results???
                        SearchBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            query = state.searchQuery,
                            onQueryChange = {
                                onEvent(PokedexListEvent.OnSearchQueryChanged(it))
                            },
                            onSearch = {},
                            shape = RoundedCornerShape(10.dp),
                            colors = SearchBarDefaults.colors(
                                containerColor = if (isSystemInDarkTheme()) Color.White.copy(0.33f) else Color(0x1F767680)
                            ),
                            active = false,
                            onActiveChange = {},
                            placeholder = { Text("Hinted search text") },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
                        ) { }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            CollectionGroup(
                                Modifier
                                    .weight(1f)
                                    .height(100.dp),
                                title = "Mijn team",
                                subTitle = "0 pokemons",
                                gradientColors = listOf(
                                    Color(0xFF46469C),
                                    Color(0xFF7E32E0)
                                )
                            ) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("My team!")
                                }
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            CollectionGroup(
                                Modifier
                                    .weight(1f)
                                    .height(100.dp),
                                title = "Favorieten",
                                subTitle = "0 pokemons",
                                gradientColors = listOf(
                                    Color(0xFF65CB9A),
                                    Color(0xFF15D0DC)
                                )
                            ) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Favorites!")
                                }
                            }
                        }

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 10.dp, start = 16.dp, end = 16.dp)
                        ) {
                            itemsIndexed(state.pokemons) { index, pokemon ->
                                if (index == 0) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                }

                                PokemonCard(pokemon = pokemon, modifier = Modifier.fillMaxWidth()) {
                                    navigator.navigate(PokemonDetailScreenDestination)
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
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
fun PokedexListScreenPreview() {
    DemoPokedexTheme {
        PokedexListScreenContent(
            PokedexListState(
                pokemons = listOf(
                    Pokemon(1, "", "Bulbasaur", "grass", "poison"),
                    Pokemon(3, "", "Charmander", "fire", null),
                )
            ),
            rememberNavController()
        ) {}
    }
}