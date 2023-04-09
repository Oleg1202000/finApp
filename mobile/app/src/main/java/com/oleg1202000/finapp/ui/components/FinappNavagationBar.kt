package com.oleg1202000.finapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

import com.oleg1202000.finapp.R
import com.oleg1202000.finapp.ui.Screen



@Composable
fun FinappNavigationBar(navController: NavController) {

    NavigationBar(tonalElevation = 60.dp,
        windowInsets = WindowInsets.navigationBars,
        modifier = Modifier.height(150.dp)
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

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
                        popUpTo(Screen.Home.route) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                          },
                icon = { Icon(painter = painterResource(screen.icon),
                    contentDescription = screen.label) },
            )
        }
    }
}


data class ScreenItem(val route: String, val label: String, val icon: Int)

val navItems = listOf(
    ScreenItem("home", "Home", R.drawable.ic_home),
    ScreenItem("plan", "Plan", R.drawable.ic_plan),
    ScreenItem("history", "History", R.drawable.ic_history)
)
