package com.paltech.ontheshelf.presentation.navigation

import BasketScreen
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.paltech.ontheshelf.presentation.bottomnav.BottomNavigationBar
import com.paltech.ontheshelf.presentation.bottomnav.viewmodel.BottomNavViewModel
import com.paltech.ontheshelf.presentation.categories.CategoriesScreen
import com.paltech.ontheshelf.presentation.common.DetailsScreen
import com.paltech.ontheshelf.presentation.favourites.FavouriteScreen
import com.paltech.ontheshelf.presentation.home.ShopScreen
import com.paltech.ontheshelf.presentation.login.LoginScreen
import com.paltech.ontheshelf.presentation.onboarding.OnBoardingScreen
import com.paltech.ontheshelf.presentation.onboarding.OnBoardingViewModel
import com.paltech.ontheshelf.presentation.order.OrderStepperScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    showBottomBar: Boolean
) {
    val bottomNavViewModel: BottomNavViewModel = hiltViewModel()
    val activity = LocalContext.current as? Activity

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController = navController, viewModel = bottomNavViewModel)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Onboarding Navigation
            navigation(
                route = Route.AppStartNavigation.route,
                startDestination = Route.OnBoardingScreen.route
            ) {
                composable(route = Route.OnBoardingScreen.route) {
                    val viewModel: OnBoardingViewModel = hiltViewModel()
                    OnBoardingScreen(onEvent = viewModel::onEvent)
                }
            }

            // Login Navigation
            navigation(
                route = Route.LoginNavigation.route,
                startDestination = Route.LoginScreen.route
            ) {
                composable(route = Route.LoginScreen.route) {
                    LoginScreen(navController = navController)
                }
            }

            // Main Navigation with Bottom Navigation
            navigation(
                route = Route.MainNavigation.route,
                startDestination = Route.HomeScreen.route
            ) {
                composable(Route.HomeScreen.route) {
                    ShopScreen(navController = navController)
                }
                composable(Route.VegetablesScreen.route) {
                    CategoriesScreen(navController = navController)
                }
                composable(Route.FavouriteScreen.route) {
                    FavouriteScreen(navController = navController)
                }
                composable(Route.BasketScreen.route) {
                    BasketScreen(navController=navController)
                }
                composable("details/{productId}") { backStackEntry ->
                    val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
                    if (productId != null) {
                        DetailsScreen(productId = productId, navController = navController)
                    } else {
                        // Handle the case where productId is null, e.g., show an error screen
                    }
                }

                // Add a route for the Order Stepper Screen
                composable(Route.OrderNow.route) {
                    OrderStepperScreen(navController = navController, onComplete = {
                        navController.navigate(Route.MainNavigation.route)
                    })
                }
            }

        }


    }

    // Observe the current route and update the selected index in BottomNavViewModel
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    currentBackStackEntry?.destination?.route?.let { route ->
        bottomNavViewModel.updateIndexBasedOnRoute(route)

        // Custom back press handler for HomeScreen
        if (route != Route.DetailsScreen.route) {
            BackHandler {
                activity?.finish() // Exit the application
            }
        }
    }
}
