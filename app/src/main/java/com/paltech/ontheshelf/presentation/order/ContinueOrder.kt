package com.paltech.ontheshelf.presentation.order

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

@Composable
fun OrderStepperHost(navController: NavController) {
    OrderStepperWithBottomSheet(
        navigateToHome = {
            navController.navigate("home_route") {
                popUpTo(navController.graph.findStartDestination().id) {
                    inclusive = true
                }
            }
        }
    )
}
