package com.oleg1202000.finapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
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
    startDestination: String = Screen.Home.route,
    showSystemBar: MutableState<Boolean>,
    showFloatingActionButton: MutableState<Boolean>,
    statusBarItem: MutableState<String>
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {



        composable(Screen.Home.route) {backStackEntry ->
            val viewModel = hiltViewModel<HomeViewModel>()

            showSystemBar.value = true
            showFloatingActionButton.value = true
            HomeScreen(
                viewModel = viewModel,
                statusBarItem = statusBarItem

            )

        }

        composable(Screen.AddData.route) {
            showSystemBar.value = false
            AddDataScreen()

        }

        composable(Screen.Plan.route) {
            showSystemBar.value = true
            showFloatingActionButton.value = true
            PlanScreen()
        }

        composable(Screen.AddPlan.route) {
            showSystemBar.value = false
            AddPlanScreen()

        }

        composable(Screen.History.route) {
            showSystemBar.value = true
            showFloatingActionButton.value = false
            HistoryScreen()
        }

        composable(Screen.Category.route) {

        }

        composable(Screen.AddCategory.route) {

        }
    }
}