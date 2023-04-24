package com.oleg1202000.finapp.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oleg1202000.finapp.ui.history.HistoryScreen
import com.oleg1202000.finapp.ui.home.HomeScreen
import com.oleg1202000.finapp.ui.home.HomeViewModel
import com.oleg1202000.finapp.ui.home.adddata.AddDataScreen
import com.oleg1202000.finapp.ui.plan.PlanScreen
import com.oleg1202000.finapp.ui.plan.addplan.AddPlanScreen


@Composable
fun NavGraph(
    navController: NavHostController,
    currentDestination: NavDestination?,
    startDestination: String = Screen.Home.route,
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Screen.Home.route) {backStackEntry ->
            val viewModel = hiltViewModel<HomeViewModel>()

            HomeScreen(
                navController = navController,
                currentDestination = currentDestination,
                viewModel = viewModel
            )

        }

        composable(Screen.AddData.route) {
            AddDataScreen()

        }

        composable(Screen.Plan.route) {
            PlanScreen(
                navController = navController,
                currentDestination = currentDestination,
            )
        }

        composable(Screen.AddPlan.route) {
            AddPlanScreen()

        }

        composable(Screen.History.route) {
            HistoryScreen(
                navController = navController,
                currentDestination = currentDestination,
            )
        }

        composable(Screen.Category.route) {

        }

        composable(Screen.AddCategory.route) {

        }
    }
}