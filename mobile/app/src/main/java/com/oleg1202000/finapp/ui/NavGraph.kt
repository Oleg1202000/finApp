package com.oleg1202000.finapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oleg1202000.finapp.ui.history.HistoryScreen
import com.oleg1202000.finapp.ui.home.HomeScreen
import com.oleg1202000.finapp.ui.plan.PlanScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route,
    navigation: Navigation = rememberNavigation()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {



        composable(Screen.Home.route) {
            HomeScreen(
                // onNavigateToHome = { navController.navigate(Screen.Home.route) }
                onNavigateToHome = { navigation.navigateToHome }
            )

        }

        composable(Screen.AddData.route) {

        }

        composable(Screen.Plan.route) {
            PlanScreen()
        }

        composable(Screen.AddPlan.route) {

        }

        composable(Screen.History.route) {
            HistoryScreen()
        }

        composable(Screen.Category.route) {

        }

        composable(Screen.AddCategory.route) {

        }
    }
}