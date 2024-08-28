package com.paltech.ontheshelf.presentation.bottomnav.viewmodel
import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.paltech.ontheshelf_prod.R
import com.paltech.ontheshelf.presentation.navigation.Route

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route:String,
)

val bottomNavItems = listOf(
    BottomNavItem(
        label = "Home",
        icon = Icons.Filled.Home,
        route = Route.HomeScreen.route
    ),
    BottomNavItem(
        label = "Vegetables",
        icon = Icons.Filled.Menu,
        route = Route.VegetablesScreen.route
    ),

    BottomNavItem(
        label = "Favorites",
        icon = Icons.Filled.Favorite,
        route = Route.FavouriteScreen.route
    ),
            BottomNavItem(
            label = "Add to Basket",
    icon = Icons.Default.ShoppingCart,
    route = Route.BasketScreen.route
),
)