package com.oleg1202000.finapp.ui.plan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.oleg1202000.finapp.ui.components.FinappFloatingActionButton
import com.oleg1202000.finapp.ui.components.FinappNavigationBar
import com.oleg1202000.finapp.ui.components.FinappStatusBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanScreen(
    navController: NavHostController,
    currentDestination: NavDestination?,
) {
    Scaffold(
        topBar = {
            FinappStatusBar()
        },
        bottomBar = {
            FinappNavigationBar(
                navController = navController,
                currentDestination = currentDestination,
            )
        },
        floatingActionButton = {
            FinappFloatingActionButton(
                navController = navController,
                currentDestination = currentDestination
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(text = "Plan")

        }
    }
}