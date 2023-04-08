package be.christiano.demopokedex.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import be.christiano.demopokedex.ui.theme.DemoPokedexTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoPokedexTheme {
                val navController = rememberAnimatedNavController()
                val animatedNavHostEngine = rememberAnimatedNavHostEngine()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.main, navController = navController, engine = animatedNavHostEngine)
                }
            }
        }
    }
}
//
//@Preview
//@Composable
//@OptIn(ExperimentalMaterial3Api::class)
//fun Default(
//    viewModel: MainViewModel? = null
//) {
//
//    // Collapsing toolbar layout and how to style them
//    // https://proandroiddev.com/creating-a-collapsing-topappbar-with-jetpack-compose-d25ad19d6113
//
////    val viewModel = koinViewModel<MainViewModel>()
//
//    val checked = remember {
//        mutableStateOf(false)
//    }
//
//    val scrollState = rememberScrollState()
//
//    val behavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
//
//    val text = rememberSaveable { mutableStateOf("") }
//    val focusManager = LocalFocusManager.current
//
//    fun closeSearchBar() {
//        focusManager.clearFocus()
//    }
//
//    DemoPokedexTheme {
//        Box(contentAlignment = Alignment.BottomCenter) {
//            Scaffold(Modifier.nestedScroll(behavior.nestedScrollConnection), topBar = {
//                MyLargeTopAppBar("Pokédex", { behavior }) {
//                    IconButton(onClick = { }) {
//                        Icon(ImageVector.vectorResource(id = R.drawable.ic_sort), contentDescription = "Sort")
//                    }
//                }
//            }) { contentPadding ->
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(contentPadding)
//                        .verticalScroll(scrollState)
//                        .padding(horizontal = 16.dp, vertical = 8.dp),
//                ) {
//
//                    // Searchbar has to be outside of a scrollable view/layout since it opens up a complete new "screen" for the results???
//                    SearchBar(
//                        modifier = Modifier.fillMaxWidth(),
//                        query = text.value,
//                        onQueryChange = {
//                            text.value = it
//                        },
//                        onSearch = {
//                            closeSearchBar()
//                        },
//                        shape = RoundedCornerShape(10.dp),
//                        colors = SearchBarDefaults.colors(
//                            containerColor = if (isSystemInDarkTheme()) Color.White.copy(0.33f) else Color(0x1F767680)
//                        ),
//                        active = false,
//                        onActiveChange = {},
//                        placeholder = { Text("Hinted search text") },
//                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
//                    ) { }
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Button(onClick = {}) {
//                        Text(text = "Hello This is a button")
//                    }
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = "text field", onValueChange = {})
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    TextField(modifier = Modifier.fillMaxWidth(), value = "other text field", onValueChange = {})
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Checkbox(checked = checked.value, onCheckedChange = { checked.value = it })
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Switch(checked = checked.value, onCheckedChange = { checked.value = it })
//
//                        Spacer(Modifier.width(8.dp))
//
//                        Text("Some text for the switch!", modifier = Modifier
//                            .clip(RoundedCornerShape(8.dp))
//                            .clickable {
//                                checked.value = !checked.value
//                            }
//                            .padding(8.dp))
//                    }
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .shadow(15.dp, spotColor = Color(0x0A000000), shape = CardDefaults.shape),
//                        colors = CardDefaults.cardColors(
//                            containerColor = if (isSystemInDarkTheme()) Color(0x33FFFFFF) else Color.White
//                        )
//                    ) {
//                        Column(modifier = Modifier.padding(16.dp)) {
//                            Text(text = "Hello Android!")
//
//                            Spacer(modifier = Modifier.height(8.dp))
//
//                            Button(onClick = {}) {
//                                Text(text = "Hello This is a button")
//                            }
//
//                            Spacer(modifier = Modifier.height(8.dp))
//
//                            FloatingActionButton(onClick = {}) {
//                                Text(text = "Hello")
//                            }
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    // Surface color for the background!!
//                    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
//                        Column(modifier = Modifier.padding(16.dp)) {
//                            Text(text = "Hello Android!")
//
//                            Spacer(modifier = Modifier.height(8.dp))
//
//                            Button(onClick = {}) {
//                                Text(text = "Hello This is a button")
//                            }
//
//                            Spacer(modifier = Modifier.height(8.dp))
//
//                            FloatingActionButton(onClick = {}) {
//                                Text(text = "Hello")
//                            }
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Snackbar(modifier = Modifier.fillMaxWidth()) {
//                        Text("This is a snackbar!!")
//                    }
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    FloatingActionButton(onClick = {}) {
//                        Text(text = "Hello")
//                    }
//                }
//            }
//
//            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
//        }
//    }
//}