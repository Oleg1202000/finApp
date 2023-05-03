package com.oleg1202000.finapp.ui


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oleg1202000.finapp.ui.components.FinappFloatingActionButton
import com.oleg1202000.finapp.ui.components.FinappNavigationBar
import com.oleg1202000.finapp.ui.components.FinappStatusBar
import com.oleg1202000.finapp.ui.theme.FinappTheme


@Composable
fun Finapp() {

    FinappTheme {

        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Scaffold(
            topBar = {
                FinappStatusBar(
                   // item = "- ${uiState.sumAmount}    + 0",  // Для отображения суммы доходов и расходов
                )
            },

            bottomBar = {
                FinappNavigationBar(
                    navController = navController,
                    currentDestination = currentDestination,
                )
            },

            floatingActionButton = {
                if (currentDestination?.route == "home" || currentDestination?.route == "plan") {
                    FinappFloatingActionButton(
                        navController = navController,
                        currentDestination = currentDestination
                    )
                }
            }

        ) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                NavGraph(
                    navController = navController,
                    currentDestination = currentDestination
                )
            }
        }

    }
}