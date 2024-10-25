package com.mk1morebugs.finapp.ui

import androidx.navigation.NavHostController

class NavigationActions(private val navController: NavHostController) {
    fun navigateTo(destination: Screen) {
        navController.navigate(route = destination) {
            launchSingleTop = true
        }
    }

    fun fromNavBarNavigateTo(destination: Screen) {
        navController.navigate(route = destination) {
            launchSingleTop = true
           /* popUpTo(
                route = navController.graph.last()
            )*/
        }
    }

    fun backToPreviousDestination() {
        navController.popBackStack()
    }
}
