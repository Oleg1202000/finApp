package com.oleg1202000.finapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddData : Screen("home/add-data")
    object Plan : Screen("plan")
    object AddPlan : Screen("home/add-plan")
    object History : Screen("history")
    object Category : Screen("category")
    object AddCategory : Screen("category/add-category")

}



@Composable
fun rememberNavigation(
    navController: NavHostController = rememberNavController(),
) = remember(navController) {
    Navigation(navController)
}


class Navigation(navController: NavHostController) {

    val navigateToHome: () -> Unit = {
        navController.navigate(Screen.Home.route) {
            restoreState = true
        }
    }

    val navigateToAddData: () -> Unit = {
        navController.navigate(Screen.AddData.route) {
            popUpTo(Screen.Home.route)

        }
    }

    val navigateToPlan: () -> Unit = {
        navController.navigate(Screen.Plan.route) {

        }
    }

    val navigateToAddPlan: () -> Unit = {
        navController.navigate(Screen.AddPlan.route) {

            popUpTo(Screen.Plan.route)
        }
    }

    val navigateToHistory: () -> Unit = {
        navController.navigate(Screen.History.route) {

        }
    }

    val navigateCategory: () -> Unit = {
        navController.navigate(Screen.Category.route) {

        }
    }

    val navigateToAddCategory: () -> Unit = {
        navController.navigate(Screen.AddCategory.route) {

            popUpTo(Screen.Category.route)
        }
    }

    fun navigateToHome1() {

    }



}
