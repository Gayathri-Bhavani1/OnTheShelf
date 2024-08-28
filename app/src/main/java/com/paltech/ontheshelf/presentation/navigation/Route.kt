package com.paltech.ontheshelf.presentation.navigation

import androidx.navigation.NamedNavArgument

sealed class Route(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object OnBoardingScreen : Route(route = "onBoardingScreen")
    data object LoginScreen : Route(route = "loginScreen")
    data object HomeScreen : Route(route = "homeScreen")
    data object SearchScreen : Route(route = "searchScreen")
    data object DetailsScreen : Route(route = "detailsScreen")
    data object AppStartNavigation : Route(route = "appStartNavigation")
    data object NewsNavigation : Route(route = "newsNavigation")
    data object LoginNavigation : Route(route = "loginNavigation")
    data object VegetablesScreen : Route(route = "vegetablesScreen")
    data object FavouriteScreen : Route(route = "favouriteScreen")
    data object BasketScreen : Route(route = "basketScreen")
    data object MainNavigation : Route(route = "main_navigation")
    data object OrderNow : Route(route = "orderNow") // Define newsScreen
    data object OrderBottomSheet : Route(route = "orderBottomSheet") // Define newsScreen
}
