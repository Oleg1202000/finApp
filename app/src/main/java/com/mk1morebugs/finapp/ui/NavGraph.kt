package com.mk1morebugs.finapp.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.mk1morebugs.finapp.ui.categories.addcategory.AddCategoryScreen
import com.mk1morebugs.finapp.ui.costs.CostsScreen
import com.mk1morebugs.finapp.ui.costs.addcost.AddCostScreen
import com.mk1morebugs.finapp.ui.history.HistoryScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data class Costs(val isFactCosts: Boolean) : Screen()

    @Serializable
    data object History : Screen()

    @Serializable
    data class AddCost(val isFactCosts: Boolean) : Screen()

    @Serializable
    data object AddCategory : Screen()
}

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.Costs(isFactCosts = true),
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    navActions: NavigationActions = remember(navController) {
        NavigationActions(navController = navController)
    },
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable<Screen.Costs> { backStack ->
            val costsScreen: Screen.Costs = backStack.toRoute()
            CostsScreen(
                snackbarHostState = snackbarHostState,
                currentDestination = costsScreen,
                fromNavBarNavigateTo = navActions::fromNavBarNavigateTo,
                floatingActionButtonOnClick = {
                    navActions.fromNavBarNavigateTo(
                        Screen.AddCost(isFactCosts = costsScreen.isFactCosts)
                    )
                },
                isFactCosts = costsScreen.isFactCosts,
            )
        }

        composable<Screen.AddCost> { backStack ->
            val addCostScreen: Screen.AddCost = backStack.toRoute()
            AddCostScreen(
                snackbarHostState = snackbarHostState,
                coroutineScope = coroutineScope,
                currentDestination = addCostScreen,
                fromNavBarNavigateTo = navActions::fromNavBarNavigateTo,
                backToPreviousDestination = navActions::backToPreviousDestination,
                navigateTo = navActions::navigateTo,
                isPlanned = !addCostScreen.isFactCosts,
            )
        }

        composable<Screen.History> { backStack ->
            HistoryScreen(
                snackbarHostState = snackbarHostState,
                currentDestination = backStack.toRoute<Screen.History>(),
                fromNavBarNavigateTo = navActions::fromNavBarNavigateTo,
            )
        }

        composable<Screen.AddCategory> {
            AddCategoryScreen(
                snackBarHostState = snackbarHostState,
                coroutineScope = coroutineScope,
                backToPreviousDestination = navActions::backToPreviousDestination
            )

        }
    }
}