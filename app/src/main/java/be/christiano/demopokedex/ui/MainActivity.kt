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