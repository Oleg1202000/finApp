package com.oleg1202000.finapp.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oleg1202000.finapp.ui.categories.addcategory.AddCategoryScreen
import com.oleg1202000.finapp.ui.categories.addcategory.AddCategoryViewModel
import com.oleg1202000.finapp.ui.history.HistoryScreen
import com.oleg1202000.finapp.ui.history.HistoryViewModel
import com.oleg1202000.finapp.ui.home.HomeScreen
import com.oleg1202000.finapp.ui.home.HomeViewModel
import com.oleg1202000.finapp.ui.home.adddata.AddDataScreen
import com.oleg1202000.finapp.ui.home.adddata.AddDataViewModel
import com.oleg1202000.finapp.ui.plan.PlanScreen
import com.oleg1202000.finapp.ui.plan.PlanViewModel
import com.oleg1202000.finapp.ui.plan.addplan.AddPlanScreen
import com.oleg1202000.finapp.ui.plan.addplan.AddPlanViewModel
import kotlinx.coroutines.CoroutineScope


@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Home.route,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    finappStatusbarTitle: MutableState<String>
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Screen.Home.route) {backStackEntry ->
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                viewModel = viewModel,
                finappStatusbarTitle = finappStatusbarTitle
            )

        }

        composable(Screen.AddData.route) {
            val viewModel = hiltViewModel<AddDataViewModel>()
            finappStatusbarTitle.value = ""
            AddDataScreen(
                navController = navController,
                viewModel = viewModel,
                snackBarHostState = snackbarHostState,
                coroutineScope = coroutineScope
            )

        }

        composable(Screen.Plan.route) {
            finappStatusbarTitle.value = ""
            val viewModel = hiltViewModel<PlanViewModel>()
            PlanScreen(
                viewModel = viewModel,
                finappStatusBarTitle = finappStatusbarTitle

            )
        }

        composable(Screen.AddPlan.route) {
            finappStatusbarTitle.value = ""
            val viewModel = hiltViewModel<AddPlanViewModel>()
            AddPlanScreen(
                navController = navController,
                viewModel = viewModel,
                snackBarHostState = snackbarHostState,
                coroutineScope = coroutineScope
            )

        }

        composable(Screen.History.route) {
            finappStatusbarTitle.value = ""
            val viewModel = hiltViewModel<HistoryViewModel>()
            HistoryScreen(
                viewModel = viewModel
            )
        }

        composable(Screen.AddCategory.route) {
            val viewModel = hiltViewModel<AddCategoryViewModel>()
            AddCategoryScreen(
                viewModel = viewModel,
                navController = navController,
                snackBarHostState = snackbarHostState,
                coroutineScope = coroutineScope
            )

        }
    }
}