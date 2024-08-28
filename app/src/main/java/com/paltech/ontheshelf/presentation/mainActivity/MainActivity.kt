package com.paltech.ontheshelf.presentation.mainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.paltech.ontheshelf.presentation.navigation.NavGraph
import com.paltech.ontheshelf.ui.theme.OnTheShelfTheme
import dagger.hilt.android.AndroidEntryPoint



import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.paltech.ontheshelf.presentation.bottomnav.viewmodel.BottomNavViewModel
import com.paltech.ontheshelf.presentation.navigation.Route

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        installSplashScreen().apply {


            setKeepOnScreenCondition { viewModel.splashCondition.value }
        }

        setContent {
            OnTheShelfTheme(dynamicColor = false) {
                val isSystemInDarkMode = isSystemInDarkTheme()
                val systemUiColor = rememberSystemUiController()
                SideEffect {
                    systemUiColor.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = !isSystemInDarkMode
                    )
                }

                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                ) {
                    val navController = rememberNavController()

                    // Handling the back button behavior
                    val backStackEntry = navController.currentBackStackEntryAsState()
                    val currentDestination = backStackEntry.value?.destination?.route
                    val startDestinations = listOf(
                        Route.HomeScreen.route,
                        Route.VegetablesScreen.route,
                        Route.FavouriteScreen.route,
                        Route.BasketScreen.route
                    )

                    val showBottomBar = currentDestination in startDestinations

                    NavGraph(
navController = navController,
                        startDestination = viewModel.startDestination.value,
                        showBottomBar = showBottomBar
                    )
                }
            }
        }
    }
}
