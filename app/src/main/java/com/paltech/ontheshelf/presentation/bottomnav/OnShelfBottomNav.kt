package com.paltech.ontheshelf.presentation.bottomnav

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.paltech.ontheshelf.presentation.bottomnav.viewmodel.BottomNavViewModel
import com.paltech.ontheshelf.presentation.bottomnav.viewmodel.bottomNavItems


@Composable

fun BottomNavigationBar(
    navController: NavHostController,
    viewModel: BottomNavViewModel
) {
    val selectedItemIndex = viewModel.selectedItemIndex

    NavigationBar {
        bottomNavItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = index == selectedItemIndex,
                onClick = {
                    viewModel.selectItem(index)
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
