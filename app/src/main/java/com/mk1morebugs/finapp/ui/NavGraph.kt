package com.mk1morebugs.finapp.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mk1morebugs.finapp.ui.categories.addcategory.AddCategoryScreen
import com.mk1morebugs.finapp.ui.categories.addcategory.AddCategoryViewModel
import com.mk1morebugs.finapp.ui.history.HistoryScreen
import com.mk1morebugs.finapp.ui.history.HistoryViewModel
import com.mk1morebugs.finapp.ui.costs.CostsScreen
import com.mk1morebugs.finapp.ui.costs.CostsViewModel
import com.mk1morebugs.finapp.ui.costs.adddata.AddDataScreen
import com.mk1morebugs.finapp.ui.costs.adddata.AddDataViewModel
import com.mk1morebugs.finapp.ui.plan.PlanScreen
import com.mk1morebugs.finapp.ui.plan.PlanViewModel
import com.mk1morebugs.finapp.ui.plan.addplan.AddPlanScreen
import com.mk1morebugs.finapp.ui.plan.addplan.AddPlanViewModel
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

        composable(Screen.Home.route) {
            CostsScreen(
                finappStatusbarTitle = finappStatusbarTitle,
                isFactCosts = false
            )

        }

        composable(Screen.AddData.route) {
            val viewModel = hiltViewModel<AddDataViewModel>()
            finappStatusbarTitle.value = "Добавление записи"
            AddDataScreen(
                navController = navController,
                viewModel = viewModel,
                snackBarHostState = snackbarHostState,
                coroutineScope = coroutineScope
            )

        }

        composable(Screen.AddPlan.route) {
            finappStatusbarTitle.value = "Планирование расходов"
            val viewModel = hiltViewModel<AddPlanViewModel>()
            AddPlanScreen(
                navController = navController,
                viewModel = viewModel,
                snackBarHostState = snackbarHostState,
                coroutineScope = coroutineScope
            )

        }

        composable(Screen.History.route) {
            finappStatusbarTitle.value = "История"
            val viewModel = hiltViewModel<HistoryViewModel>()
            HistoryScreen(
                viewModel = viewModel
            )
        }

        composable(Screen.AddCategory.route) {
            val viewModel = hiltViewModel<AddCategoryViewModel>()
            finappStatusbarTitle.value = "Создание категории"
            AddCategoryScreen(
                viewModel = viewModel,
                navController = navController,
                snackBarHostState = snackbarHostState,
                coroutineScope = coroutineScope
            )

        }
    }
}