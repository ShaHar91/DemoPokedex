package be.christiano.demopokedex.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import be.christiano.demopokedex.R
import be.christiano.demopokedex.ui.MainNavGraph
import be.christiano.demopokedex.ui.components.MyLargeTopAppBar
import be.christiano.demopokedex.ui.destinations.PokemonDetailScreenDestination
import be.christiano.demopokedex.ui.shared.PokemonCard
import be.christiano.demopokedex.ui.theme.DemoPokedexTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination
@MainNavGraph
fun FavoritesScreen(
    navController: NavController
) {
    val viewModel = koinViewModel<FavoritesViewModel>()
    val state by viewModel.state.collectAsState()

    FavoritesScreenContent(state = state, navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreenContent(
    state: FavoritesState,
    navController: NavController
) {
    val behavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Box(
        modifier = Modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        Scaffold(
            Modifier
                .nestedScroll(behavior.nestedScrollConnection)
                .fillMaxSize(),
            topBar = {
                MyLargeTopAppBar("Favourites", navController, behavior = { behavior })
            }
        ) { contentPadding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                contentAlignment = Alignment.Center
            ) {
                if (state.favoritePokemons.isEmpty()) {
                    NoTeamPokemons()
                } else {
                    ListOfFavoritePokemons(navController = navController, state = state)
                }
            }
        }

//        if (state.isLoading) {
//            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
//        }
    }
}

@Composable
fun NoTeamPokemons() {
    Column(
        modifier = Modifier.padding(horizontal = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(ImageVector.vectorResource(id = R.drawable.pokeball_empty), contentDescription = "Sort")

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            text = "No pokemons as favorite yet"
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            modifier = Modifier,
            textAlign = TextAlign.Center,
            text = "Go to a pokemon's detail to add it as a favorite!"
        )
    }
}

@Composable
fun ListOfFavoritePokemons(
    navController: NavController,
    state: FavoritesState
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp, start = 16.dp, end = 16.dp)
    ) {
        itemsIndexed(state.favoritePokemons) { index, pokemon ->
            if (index == 0) {
                Spacer(modifier = Modifier.height(6.dp))
            }

            PokemonCard(pokemon = pokemon, modifier = Modifier.fillMaxWidth()) {
                navController.navigate(PokemonDetailScreenDestination(pokemon.number, pokemon.name))
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Preview
@Composable
fun FavoritesScreenPreview() {
    DemoPokedexTheme {
        FavoritesScreenContent(state = FavoritesState(), navController = rememberNavController())
    }
}
