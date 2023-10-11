package com.mk1morebugs.finapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.oleg1202000.finapp.R
import com.mk1morebugs.finapp.ui.Screen


@Composable
fun FinappNavigationBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
) {

    NavigationBar(
        windowInsets = WindowInsets.navigationBars
    ) {

        navItems.forEach { screen ->
            NavigationBarItem(

                label = {
                    Text(text = screen.label,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                        },

                selected = currentDestination?.route == screen.route,

                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true

                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                          },

                icon = {
                    Icon(painter = painterResource(screen.icon),
                    contentDescription = screen.label)
                       },
            )
        }
    }
}


data class ScreenItem(val route: String, val label: String, val icon: Int)

val navItems = listOf(
    ScreenItem(Screen.Home.route, "Главная", R.drawable.ic_home),
    ScreenItem(Screen.Plan.route, "План", R.drawable.ic_plan),
    ScreenItem(Screen.History.route, "История", R.drawable.ic_history)
)
